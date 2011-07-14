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

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class Diff {

	/**
	 * Field CHANGE_TYPE_ADDITION.
	 * (value is ""addition"")
	 */
	public static final String CHANGE_TYPE_ADDITION = "addition"; //$NON-NLS-1$

	/**
	 * Field CHANGE_TYPE_DELETION.
	 * (value is ""deletion"")
	 */
	public static final String CHANGE_TYPE_DELETION = "deletion"; //$NON-NLS-1$

	/**
	 * Field CHANGE_TYPE_CHANGE.
	 * (value is ""change"")
	 */
	public static final String CHANGE_TYPE_CHANGE = "change"; //$NON-NLS-1$

	/**
	 * Field DIFF_RANGE_CATEGORY.
	 * (value is "Activator.PLUGIN_ID + ".DIFF_RANGE_CATEGORY"")
	 */
	private static final String DIFF_RANGE_CATEGORY = Activator.PLUGIN_ID + ".DIFF_RANGE_CATEGORY"; //$NON-NLS-1$

	/**
	 * Field fConfig.
	 */
	CompareConfiguration fConfig;

	/**
	 * Field fIsThreeWay.
	 */
	boolean fIsThreeWay;

	/**
	 * Field fAncestorDoc.
	 */
	IDocument fAncestorDoc;

	/** character range in ancestor document */
	Position fAncestorPos;

	/**
	 * Field fLeftDoc.
	 */
	IDocument fLeftDoc;

	/** character range in left document */
	Position fLeftPos;

	/**
	 * Field fRightDoc.
	 */
	IDocument fRightDoc;

	/** character range in right document */
	Position fRightPos;

	/** if this is a TokenDiff fParent points to the enclosing LineDiff */
	Diff fParent;

	/** if Diff has been resolved */
	boolean fResolved;

	/**
	 * Field fDirection.
	 */
	int fDirection;

	/**
	 * Field fIsToken.
	 */
	boolean fIsToken = false;

	/** child token diffs */
	List<Diff> fDiffs;

	/**
	 * Field fIsWhitespace.
	 */
	boolean fIsWhitespace = false;

	/*
	 * Create Diff from two ranges and an optional parent diff.
	 */
	/**
	 * Constructor for Diff.
	 * @param parent Diff
	 * @param dir int
	 * @param ancestorDoc IDocument
	 * @param aRange Position
	 * @param ancestorStart int
	 * @param ancestorEnd int
	 * @param leftDoc IDocument
	 * @param lRange Position
	 * @param leftStart int
	 * @param leftEnd int
	 * @param rightDoc IDocument
	 * @param rRange Position
	 * @param rightStart int
	 * @param rightEnd int
	 * @param aThreeWay boolean
	 * @param aConfig CompareConfiguration
	 */
	Diff(Diff parent, int dir, IDocument ancestorDoc, Position aRange, int ancestorStart, int ancestorEnd,
			IDocument leftDoc, Position lRange, int leftStart, int leftEnd, IDocument rightDoc, Position rRange,
			int rightStart, int rightEnd, boolean aThreeWay, CompareConfiguration aConfig) {
		fParent = (null != parent) ? parent : this;
		fDirection = dir;

		fAncestorDoc = ancestorDoc;
		fLeftDoc = leftDoc;
		fRightDoc = rightDoc;
		fIsThreeWay = aThreeWay;
		fConfig = aConfig;

		fLeftPos = createPosition(leftDoc, lRange, leftStart, leftEnd);
		fRightPos = createPosition(rightDoc, rRange, rightStart, rightEnd);
		if (null != ancestorDoc) {
			fAncestorPos = createPosition(ancestorDoc, aRange, ancestorStart, ancestorEnd);
		}
	}

	/**
	 * Method getPosition.
	 * @param type char
	 * @return Position
	 */
	public Position getPosition(char type) {
		switch (type) {
		case R4EUIConstants.ANCESTOR_CONTRIBUTOR:
			return fAncestorPos;
		case R4EUIConstants.LEFT_CONTRIBUTOR:
			return fLeftPos;
		case R4EUIConstants.RIGHT_CONTRIBUTOR:
			return fRightPos;
		default:
			return null;
		}
	}

	/**
	 * Method getDocument.
	 * @param type char
	 * @return IDocument
	 */
	public IDocument getDocument(char type) {
		switch (type) {
		case R4EUIConstants.ANCESTOR_CONTRIBUTOR:
			return fAncestorDoc;
		case R4EUIConstants.LEFT_CONTRIBUTOR:
			return fLeftDoc;
		case R4EUIConstants.RIGHT_CONTRIBUTOR:
			return fRightDoc;
		default:
			return null;

		}
	}

	/**
	 * Method isInRange.
	 * @param type char
	 * @param pos int
	 * @return boolean
	 */
	boolean isInRange(char type, int pos) {
		final Position p = getPosition(type);
		return (pos >= p.offset) && (pos < (p.offset + p.length));
	}

	/**
	 * Method changeType.
	 * @return String
	 */
	public String changeType() {
		boolean leftEmpty = 0 == fLeftPos.length;
		boolean rightEmpty = 0 == fRightPos.length;

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

	/**
	 * Method getImage.
	 * @return Image
	 */
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
		default:
		}
		if (0 != code) {
			return fConfig.getImage(code);
		}
		return null;
	}

	/**
	 * Method createPosition.
	 * @param doc IDocument
	 * @param range Position
	 * @param start int
	 * @param end int
	 * @return Position
	 */
	Position createPosition(IDocument doc, Position range, int start, int end) {
		try {
			int l = end - start;
			if (null != range) {
				final int dl = range.length;
				if (l > dl) {
					l = dl;
				}
			} else {
				final int dl = doc.getLength();
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

	/**
	 * Method add.
	 * @param d Diff
	 */
	void add(Diff d) {
		if (null == fDiffs) {
			fDiffs = new ArrayList<Diff>();
		}
		fDiffs.add(d);
	}

	/**
	 * Method isDeleted.
	 * @return boolean
	 */
	public boolean isDeleted() {
		if (null != fAncestorPos && fAncestorPos.isDeleted()) {
			return true;
		}
		return fLeftPos.isDeleted() || fRightPos.isDeleted();
	}

	/**
	 * Method setResolved.
	 * @param r boolean
	 */
	void setResolved(boolean r) {
		fResolved = r;
		if (r) {
			fDiffs = null;
		}
	}

	/**
	 * Method isResolved.
	 * @return boolean
	 */
	public boolean isResolved() {
		if (!fResolved && null != fDiffs) {
			final Iterator<Diff> e = fDiffs.iterator();
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

	/**
	 * Method getPosition.
	 * @param contributor int
	 * @return Position
	 */
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
	/**
	 * Method overlaps.
	 * @param contributor int
	 * @param start int
	 * @param end int
	 * @param docLength int
	 * @return boolean
	 */
	public boolean overlaps(int contributor, int start, int end, int docLength) {
		final Position h = getPosition(contributor);
		if (null != h) {
			final int ds = h.getOffset();
			final int de = ds + h.getLength();
			if ((start < de) && (end >= ds)) {
				return true;
			}
			if ((start == docLength) && (start <= de) && (end >= ds)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method getMaxDiffHeight.
	 * @return int
	 */
	public int getMaxDiffHeight() {
		final Point region = new Point(0, 0);
		int h = getLineRange(fLeftDoc, fLeftPos, region).y;
		if (fIsThreeWay) {
			h = Math.max(h, getLineRange(fAncestorDoc, fAncestorPos, region).y);
		}
		return Math.max(h, getLineRange(fRightDoc, fRightPos, region).y);
	}

	/**
	 * Method getAncestorHeight.
	 * @return int
	 */
	public int getAncestorHeight() {
		final Point region = new Point(0, 0);
		return getLineRange(fAncestorDoc, fAncestorPos, region).y;
	}

	/**
	 * Method getLeftHeight.
	 * @return int
	 */
	public int getLeftHeight() {
		final Point region = new Point(0, 0);
		return getLineRange(fLeftDoc, fLeftPos, region).y;
	}

	/**
	 * Method getRightHeight.
	 * @return int
	 */
	public int getRightHeight() {
		final Point region = new Point(0, 0);
		return getLineRange(fRightDoc, fRightPos, region).y;
	}

	/**
	 * Method getChangeDiffs.
	 * @param contributor int
	 * @param region IRegion
	 * @return Diff[]
	 */
	public Diff[] getChangeDiffs(int contributor, IRegion region) {
		if (null != fDiffs && intersectsRegion(contributor, region)) {
			final List<Diff> result = new ArrayList<Diff>();
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

	/**
	 * Method intersectsRegion.
	 * @param contributor int
	 * @param region IRegion
	 * @return boolean
	 */
	private boolean intersectsRegion(int contributor, IRegion region) {
		final Position p = getPosition(contributor);
		if (null != p) {
			return p.overlapsWith(region.getOffset(), region.getLength());
		}
		return false;
	}

	/**
	 * Method hasChildren.
	 * @return boolean
	 */
	public boolean hasChildren() {
		return null != fDiffs && !fDiffs.isEmpty();
	}

	/**
	 * Method getKind.
	 * @return int
	 */
	public int getKind() {
		return fDirection;
	}

	/**
	 * Method isToken.
	 * @return boolean
	 */
	public boolean isToken() {
		return fIsToken;
	}

	/**
	 * Method getParent.
	 * @return Diff
	 */
	public Diff getParent() {
		return fParent;
	}

	/**
	 * Method childIterator.
	 * @return Iterator<Diff>
	 */
	public Iterator<Diff> childIterator() {
		if (null == fDiffs) {
			return new ArrayList<Diff>().iterator();
		}
		return fDiffs.iterator();
	}

	/*
	 * Returns the start line and the number of lines which correspond to the given position.
	 * Starting line number is 0 based.
	 */
	/**
	 * Method getLineRange.
	 * @param doc IDocument
	 * @param p Position
	 * @param region Point
	 * @return Point
	 */
	protected Point getLineRange(IDocument doc, Position p, Point region) {

		if (null == p || null == doc) {
			region.x = 0;
			region.y = 0;
			return region;
		}

		final int start = p.getOffset();
		final int length = p.getLength();

		int startLine = 0;
		try {
			startLine = doc.getLineOfOffset(start);
		} catch (BadLocationException e) {
			// silently ignored
		}

		int lineCount = 0;

		if (0 != length) {
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
