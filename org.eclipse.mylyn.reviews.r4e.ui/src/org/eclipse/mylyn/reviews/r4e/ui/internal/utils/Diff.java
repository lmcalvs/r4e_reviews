/*******************************************************************************
 * Copyright (c) 2011 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class is adapted from org.eclipse.compare.internal.merge.DocumentMerger.Diff
 * and is used to hold textual differences in two or three-way compares
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.rangedifferencer.RangeDifference;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

public class Diff {

	public static final String CHANGE_TYPE_ADDITION = "addition"; //$NON-NLS-1$

	public static final String CHANGE_TYPE_DELETION = "deletion"; //$NON-NLS-1$

	public static final String CHANGE_TYPE_CHANGE = "change"; //$NON-NLS-1$

	private static final String DIFF_RANGE_CATEGORY = Activator.PLUGIN_ID + ".DIFF_RANGE_CATEGORY"; //$NON-NLS-1$

	CompareConfiguration fConfig;

	boolean fIsThreeWay;

	IDocument fAncestorDoc;

	/** character range in ancestor document */
	Position fAncestorPos;

	IDocument fLeftDoc;

	/** character range in left document */
	Position fLeftPos;

	IDocument fRightDoc;

	/** character range in right document */
	Position fRightPos;

	/** if this is a TokenDiff fParent points to the enclosing LineDiff */
	Diff fParent;

	/** if Diff has been resolved */
	boolean fResolved;

	int fDirection;

	boolean fIsToken = false;

	/** child token diffs */
	ArrayList<Diff> fDiffs;

	boolean fIsWhitespace = false;

	/*
	 * Create Diff from two ranges and an optional parent diff.
	 */
	Diff(Diff parent, int dir, IDocument ancestorDoc, Position aRange, int ancestorStart, int ancestorEnd,
			IDocument leftDoc, Position lRange, int leftStart, int leftEnd, IDocument rightDoc, Position rRange,
			int rightStart, int rightEnd, boolean aThreeWay, CompareConfiguration aConfig) {
		fParent = parent != null ? parent : this;
		fDirection = dir;

		fAncestorDoc = ancestorDoc;
		fLeftDoc = leftDoc;
		fRightDoc = rightDoc;
		fIsThreeWay = aThreeWay;
		fConfig = aConfig;

		fLeftPos = createPosition(leftDoc, lRange, leftStart, leftEnd);
		fRightPos = createPosition(rightDoc, rRange, rightStart, rightEnd);
		if (ancestorDoc != null) {
			fAncestorPos = createPosition(ancestorDoc, aRange, ancestorStart, ancestorEnd);
		}
	}

	public Position getPosition(char type) {
		switch (type) {
		case R4EUIConstants.ANCESTOR_CONTRIBUTOR:
			return fAncestorPos;
		case R4EUIConstants.LEFT_CONTRIBUTOR:
			return fLeftPos;
		case R4EUIConstants.RIGHT_CONTRIBUTOR:
			return fRightPos;
		}
		return null;
	}

	public IDocument getDocument(char type) {
		switch (type) {
		case R4EUIConstants.ANCESTOR_CONTRIBUTOR:
			return fAncestorDoc;
		case R4EUIConstants.LEFT_CONTRIBUTOR:
			return fLeftDoc;
		case R4EUIConstants.RIGHT_CONTRIBUTOR:
			return fRightDoc;
		}
		return null;
	}

	boolean isInRange(char type, int pos) {
		Position p = getPosition(type);
		return (pos >= p.offset) && (pos < (p.offset + p.length));
	}

	public String changeType() {
		boolean leftEmpty = fLeftPos.length == 0;
		boolean rightEmpty = fRightPos.length == 0;

		if (fDirection == RangeDifference.LEFT) {
			if (!leftEmpty && rightEmpty) {
				return CHANGE_TYPE_ADDITION;
			}
			if (leftEmpty && !rightEmpty) {
				return CHANGE_TYPE_DELETION;
			}
		} else {
			if (leftEmpty && !rightEmpty) {
				return CHANGE_TYPE_ADDITION;
			}
			if (!leftEmpty && rightEmpty) {
				return CHANGE_TYPE_DELETION;
			}
		}
		return CHANGE_TYPE_CHANGE;
	}

	public Image getImage() {
		int code = Differencer.CHANGE;
		switch (fDirection) {
		case RangeDifference.RIGHT:
			code += Differencer.LEFT;
			break;
		case RangeDifference.LEFT:
			code += Differencer.RIGHT;
			break;
		case RangeDifference.ANCESTOR:
		case RangeDifference.CONFLICT:
			code += Differencer.CONFLICTING;
			break;
		}
		if (code != 0) {
			return fConfig.getImage(code);
		}
		return null;
	}

	Position createPosition(IDocument doc, Position range, int start, int end) {
		try {
			int l = end - start;
			if (range != null) {
				int dl = range.length;
				if (l > dl) {
					l = dl;
				}
			} else {
				int dl = doc.getLength();
				if (start + l > dl) {
					l = dl - start;
				}
			}

			Position p = null;
			try {
				p = new Position(start, l);
			} catch (RuntimeException ex) {
				p = new Position(0, 0);
			}

			try {
				doc.addPosition(DIFF_RANGE_CATEGORY, p);
			} catch (BadPositionCategoryException ex) {
				// silently ignored
			}
			return p;
		} catch (BadLocationException ee) {
			// silently ignored
		}
		return null;
	}

	void add(Diff d) {
		if (fDiffs == null) {
			fDiffs = new ArrayList<Diff>();
		}
		fDiffs.add(d);
	}

	public boolean isDeleted() {
		if (fAncestorPos != null && fAncestorPos.isDeleted()) {
			return true;
		}
		return fLeftPos.isDeleted() || fRightPos.isDeleted();
	}

	void setResolved(boolean r) {
		fResolved = r;
		if (r) {
			fDiffs = null;
		}
	}

	public boolean isResolved() {
		if (!fResolved && fDiffs != null) {
			Iterator<Diff> e = fDiffs.iterator();
			while (e.hasNext()) {
				Diff d = e.next();
				if (!d.isResolved()) {
					return false;
				}
			}
			return true;
		}
		return fResolved;
	}

	Position getPosition(int contributor) {
		if (contributor == R4EUIConstants.LEFT_CONTRIBUTOR) {
			return fLeftPos;
		}
		if (contributor == R4EUIConstants.RIGHT_CONTRIBUTOR) {
			return fRightPos;
		}
		if (contributor == R4EUIConstants.ANCESTOR_CONTRIBUTOR) {
			return fAncestorPos;
		}
		return null;
	}

	/*
	 * Returns true if given character range overlaps with this Diff.
	 */
	public boolean overlaps(int contributor, int start, int end, int docLength) {
		Position h = getPosition(contributor);
		if (h != null) {
			int ds = h.getOffset();
			int de = ds + h.getLength();
			if ((start < de) && (end >= ds)) {
				return true;
			}
			if ((start == docLength) && (start <= de) && (end >= ds)) {
				return true;
			}
		}
		return false;
	}

	public int getMaxDiffHeight() {
		Point region = new Point(0, 0);
		int h = getLineRange(fLeftDoc, fLeftPos, region).y;
		if (fIsThreeWay) {
			h = Math.max(h, getLineRange(fAncestorDoc, fAncestorPos, region).y);
		}
		return Math.max(h, getLineRange(fRightDoc, fRightPos, region).y);
	}

	public int getAncestorHeight() {
		Point region = new Point(0, 0);
		return getLineRange(fAncestorDoc, fAncestorPos, region).y;
	}

	public int getLeftHeight() {
		Point region = new Point(0, 0);
		return getLineRange(fLeftDoc, fLeftPos, region).y;
	}

	public int getRightHeight() {
		Point region = new Point(0, 0);
		return getLineRange(fRightDoc, fRightPos, region).y;
	}

	public Diff[] getChangeDiffs(int contributor, IRegion region) {
		if (fDiffs != null && intersectsRegion(contributor, region)) {
			List<Diff> result = new ArrayList<Diff>();
			for (Diff diff2 : fDiffs) {
				Diff diff = diff2;
				if (diff.intersectsRegion(contributor, region)) {
					result.add(diff);
				}
			}
			return result.toArray(new Diff[result.size()]);
		}
		return new Diff[0];
	}

	private boolean intersectsRegion(int contributor, IRegion region) {
		Position p = getPosition(contributor);
		if (p != null) {
			return p.overlapsWith(region.getOffset(), region.getLength());
		}
		return false;
	}

	public boolean hasChildren() {
		return fDiffs != null && !fDiffs.isEmpty();
	}

	public int getKind() {
		return fDirection;
	}

	public boolean isToken() {
		return fIsToken;
	}

	public Diff getParent() {
		return fParent;
	}

	public Iterator<Diff> childIterator() {
		if (fDiffs == null) {
			return new ArrayList<Diff>().iterator();
		}
		return fDiffs.iterator();
	}

	/*
	 * Returns the start line and the number of lines which correspond to the given position.
	 * Starting line number is 0 based.
	 */
	protected Point getLineRange(IDocument doc, Position p, Point region) {

		if (p == null || doc == null) {
			region.x = 0;
			region.y = 0;
			return region;
		}

		int start = p.getOffset();
		int length = p.getLength();

		int startLine = 0;
		try {
			startLine = doc.getLineOfOffset(start);
		} catch (BadLocationException e) {
			// silently ignored
		}

		int lineCount = 0;

		if (length == 0) {
//			// if range length is 0 and if range starts a new line
//			try {
//				if (start == doc.getLineStartOffset(startLine)) {
//					lines--;
//				}
//			} catch (BadLocationException e) {
//				lines--;
//			}

		} else {
			int endLine = 0;
			try {
				endLine = doc.getLineOfOffset(start + length - 1); // why -1?
			} catch (BadLocationException e) {
				// silently ignored
			}
			lineCount = endLine - startLine + 1;
		}

		region.x = startLine;
		region.y = lineCount;
		return region;
	}
}
