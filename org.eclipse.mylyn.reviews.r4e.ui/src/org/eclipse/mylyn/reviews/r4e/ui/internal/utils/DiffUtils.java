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
 * This class is adapted from org.eclipse.compare.internal.merge.DocumentMerger
 * and is used to do two or three-way compares
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.ISharedDocumentAdapter;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.SharedDocumentAdapter;
import org.eclipse.compare.contentmergeviewer.ITokenComparator;
import org.eclipse.compare.contentmergeviewer.TokenComparator;
import org.eclipse.compare.rangedifferencer.IRangeComparator;
import org.eclipse.compare.rangedifferencer.RangeDifference;
import org.eclipse.compare.rangedifferencer.RangeDifferencer;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4ECompareEditorInput;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4EFileRevisionTypedElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4EFileTypedElement;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class DiffUtils {

	/**
	 * Field TOO_LONG. (value is 1.0E7)
	 */
	public static final double TOO_LONG = 10000000.0; // the value of N*M when to start binding the run time

	/**
	 * Field DIFF_RANGE_CATEGORY. (value is "Activator.PLUGIN_ID + ".DIFF_RANGE_CATEGORY"")
	 */
	private static final String DIFF_RANGE_CATEGORY = Activator.PLUGIN_ID + ".DIFF_RANGE_CATEGORY"; //$NON-NLS-1$

	/** Selects between smartTokenDiff and mergingTokenDiff */
	private static final boolean USE_MERGING_TOKEN_DIFF = false;

	/**
	 * Field OPTIMIZED_ALGORITHM_USED. (value is ""OPTIMIZED_ALGORITHM_USED"")
	 */
	public static final String OPTIMIZED_ALGORITHM_USED = "OPTIMIZED_ALGORITHM_USED"; //$NON-NLS-1$

	/**
	 * Perform a two level 2- or 3-way diff. The first level is based on line comparison, the second level on token
	 * comparison.
	 * 
	 * @param aIsThreeWay
	 *            boolean
	 * @param aIgnoreWhitespace
	 *            boolean
	 * @param input
	 *            R4ECompareEditorInput
	 * @return List<Diff>
	 * @throws CoreException
	 */
	public List<Diff> doDiff(boolean aIsThreeWay, boolean aIgnoreWhitespace, R4ECompareEditorInput input)
			throws CoreException {

		//THese structures will be used to hold found differences
		List<Diff> changeDiffs = new ArrayList<Diff>();
		List<Diff> allDiffs = null;

		//Get documents to compare form input
		final IDocument lDoc = getDocument(input.getLeftElement());
		final IDocument rDoc = getDocument(input.getRightElement());
		final CompareConfiguration config = input.getCompareConfiguration();

		if (null == lDoc || null == rDoc) {
			return changeDiffs; //Nothing to compare
		}

		IDocument aDoc = null; //No ancestor by default
		final Position aRegion = null; //We will compare whole files only
		if (aIsThreeWay && null != input.getAncestorElement()) {
			aDoc = getDocument(input.getAncestorElement());
		}

		resetPositions(lDoc);
		resetPositions(rDoc);
		resetPositions(aDoc);

		final boolean ignoreWhiteSpace = aIgnoreWhitespace;

		final DocLineComparator sright = new DocLineComparator(rDoc, null, ignoreWhiteSpace);
		final DocLineComparator sleft = new DocLineComparator(lDoc, null, ignoreWhiteSpace);
		DocLineComparator sancestor = null;
		if (null != aDoc) {
			sancestor = new DocLineComparator(aDoc, null, ignoreWhiteSpace);
		}

		final Object[] result = new Object[1];
		final DocLineComparator sa = sancestor, sl = sleft, sr = sright;
		final IRunnableWithProgress runnable = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InterruptedException, InvocationTargetException {
				monitor.beginTask("Computing differences...", maxWork(sa, sl, sr)); //$NON-NLS-1$
				try {
					result[0] = RangeDifferencer.findRanges(monitor, sa, sl, sr);
				} catch (OutOfMemoryError ex) {
					System.gc();
					throw new InvocationTargetException(ex);
				}
				if (monitor.isCanceled()) { // canceled
					throw new InterruptedException();
				}
				monitor.done();
			}
		};

		RangeDifference[] e = null;
		try {
			config.getContainer().run(true, true, runnable);
			e = (RangeDifference[]) result[0];
		} catch (InvocationTargetException ex) {
			// we create a NOCHANGE range for the whole document
			final Diff diff = new Diff(null, RangeDifference.NOCHANGE, aDoc, aRegion, 0, (null != aDoc)
					? aDoc.getLength()
					: 0, lDoc, null, 0, lDoc.getLength(), rDoc, null, 0, rDoc.getLength(), aIsThreeWay, config);

			allDiffs = new ArrayList<Diff>();
			allDiffs.add(diff);
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, "Too many differences found", //$NON-NLS-1$
					ex.getTargetException()));
		} catch (InterruptedException ex) {
			// we create a NOCHANGE range for the whole document
			final Diff diff = new Diff(null, RangeDifference.NOCHANGE, aDoc, aRegion, 0, (null != aDoc)
					? aDoc.getLength()
					: 0, lDoc, null, 0, lDoc.getLength(), rDoc, null, 0, rDoc.getLength(), aIsThreeWay, config);

			allDiffs = new ArrayList<Diff>();
			allDiffs.add(diff);
			return changeDiffs;
		}

		if (isCapped(sa, sl, sr)) {
			config.setProperty(OPTIMIZED_ALGORITHM_USED, new Boolean(true));
		} else {
			config.setProperty(OPTIMIZED_ALGORITHM_USED, new Boolean(false));
		}

		final List<Diff> newAllDiffs = new ArrayList<Diff>();
		for (RangeDifference es : e) {
			int ancestorStart = 0;
			int ancestorEnd = 0;
			if (null != sancestor) {
				ancestorStart = sancestor.getTokenStart(es.ancestorStart());
				ancestorEnd = getTokenEnd2(sancestor, es.ancestorStart(), es.ancestorLength());
			}

			int leftStart = sleft.getTokenStart(es.leftStart());
			int leftEnd = getTokenEnd2(sleft, es.leftStart(), es.leftLength());

			int rightStart = sright.getTokenStart(es.rightStart());
			int rightEnd = getTokenEnd2(sright, es.rightStart(), es.rightLength());

			Diff diff = new Diff(null, es.kind(), aDoc, aRegion, ancestorStart, ancestorEnd, lDoc, null, leftStart,
					leftEnd, rDoc, null, rightStart, rightEnd, aIsThreeWay, config);

			newAllDiffs.add(diff); // remember all range diffs for scrolling

			if (isPatchHunk()) {
				if (useChange(diff, config)) {
					recordChangeDiff(diff, changeDiffs);
				}
			} else {
				if (ignoreWhiteSpace || useChange(es.kind(), config)) {

					// Extract the string for each contributor.
					String a = null;
					if (null != sancestor) {
						a = extract2(aDoc, sancestor, es.ancestorStart(), es.ancestorLength());
					}
					String s = extract2(lDoc, sleft, es.leftStart(), es.leftLength());
					String d = extract2(rDoc, sright, es.rightStart(), es.rightLength());

					// Indicate whether all contributors are whitespace
					if (ignoreWhiteSpace && (null == a || 0 == a.trim().length()) && 0 == s.trim().length()
							&& 0 == d.trim().length()) {
						diff.fIsWhitespace = true;
					}

					// If the diff is of interest, record it and generate the token diffs
					if (useChange(diff, config)) {
						recordChangeDiff(diff, changeDiffs);
						if (s.length() > 0 && d.length() > 0) {
							if (null == a && null != sancestor) {
								a = extract2(aDoc, sancestor, es.ancestorStart(), es.ancestorLength());
							}
							if (USE_MERGING_TOKEN_DIFF) {
								mergingTokenDiff(diff, aDoc, a, rDoc, d, lDoc, s, aIsThreeWay, config);
							} else {
								simpleTokenDiff(diff, aDoc, a, rDoc, d, lDoc, s, aIsThreeWay, config);
							}
						}
					}
				}
			}
		}
		changeDiffs = newAllDiffs;
		return changeDiffs;
	}

	/**
	 * Method getDocument.
	 * 
	 * @param aElement
	 *            ITypedElement
	 * @return IDocument
	 * @throws CoreException
	 */
	private IDocument getDocument(ITypedElement aElement) throws CoreException {

		ISharedDocumentAdapter adapter = null;
		IEditorInput editorInput = null;
		if (aElement instanceof R4EFileTypedElement) {
			adapter = (ISharedDocumentAdapter) ((R4EFileTypedElement) aElement).getAdapter(ISharedDocumentAdapter.class);
			if (null != adapter) {
				editorInput = adapter.getDocumentKey(aElement);
			}
		} else if (aElement instanceof R4EFileRevisionTypedElement) {
			adapter = (ISharedDocumentAdapter) ((R4EFileRevisionTypedElement) aElement).getAdapter(ISharedDocumentAdapter.class);
			if (null != adapter) {
				editorInput = adapter.getDocumentKey(aElement);
			}
		} else {
			return null; //Wrong input type
		}
		if (null == adapter || null == editorInput) {
			return null; //Cannot find editor input
		}
		final IDocumentProvider provider = SharedDocumentAdapter.getDocumentProvider(editorInput);
		if (null == provider) {
			return null; //Cannot find document provider
		}

		adapter.connect(provider, editorInput);
		return provider.getDocument(editorInput);
	}

	/**
	 * Method resetPositions.
	 * 
	 * @param doc
	 *            IDocument
	 */
	private void resetPositions(IDocument doc) {
		if (null == doc) {
			return;
		}
		try {
			doc.removePositionCategory(DIFF_RANGE_CATEGORY);
		} catch (BadPositionCategoryException e) {
			// Ignore
		}
		doc.addPositionCategory(DIFF_RANGE_CATEGORY);
	}

	/**
	 * Method maxWork.
	 * 
	 * @param a
	 *            IRangeComparator
	 * @param l
	 *            IRangeComparator
	 * @param r
	 *            IRangeComparator
	 * @return int
	 */
	private static int maxWork(IRangeComparator a, IRangeComparator l, IRangeComparator r) {
		final int ln = l.getRangeCount();
		final int rn = r.getRangeCount();
		if (null != a) {
			final int an = a.getRangeCount();
			return (2 * Math.max(an, ln)) + (2 * Math.max(an, rn));
		}
		return 2 * Math.max(ln, rn);
	}

	/**
	 * Method isCapped.
	 * 
	 * @param ancestor
	 *            DocLineComparator
	 * @param left
	 *            DocLineComparator
	 * @param right
	 *            DocLineComparator
	 * @return boolean
	 */
	private boolean isCapped(DocLineComparator ancestor, DocLineComparator left, DocLineComparator right) {
		final int aLength = (null == ancestor) ? 0 : ancestor.getRangeCount();
		final int lLength = left.getRangeCount();
		final int rLength = right.getRangeCount();
		if ((double) aLength * (double) lLength > TOO_LONG || (double) aLength * (double) rLength > TOO_LONG
				|| (double) lLength * (double) rLength > TOO_LONG) {
			return true;
		}
		return false;
	}

	/**
	 * Method getTokenEnd2.
	 * 
	 * @param tc
	 *            ITokenComparator
	 * @param start
	 *            int
	 * @param length
	 *            int
	 * @return int
	 */
	private static int getTokenEnd2(ITokenComparator tc, int start, int length) {
		return tc.getTokenStart(start + length);
	}

	/**
	 * Returns the content of lines in the specified range as a String. This includes the line separators.
	 * 
	 * @param doc
	 *            the document from which to extract the characters
	 * @param start
	 *            index of first line
	 * @param length
	 *            number of lines
	 * @param tc
	 *            ITokenComparator
	 * @return the contents of the specified line range as a String
	 */
	private String extract2(IDocument doc, ITokenComparator tc, int start, int length) {
		final int count = tc.getRangeCount();
		if (length > 0 && count > 0) {
			final int startPos = tc.getTokenStart(start);
			int endPos;
			if (1 == length) {
				endPos = startPos + tc.getTokenLength(start);
			} else {
				endPos = tc.getTokenStart(start + length);
			}

			try {
				return doc.get(startPos, endPos - startPos);
			} catch (BadLocationException e) {
				// silently ignored
			}

		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * Method isPatchHunk.
	 * 
	 * @return boolean
	 */
	private boolean isPatchHunk() {
		return false;
	}

	/*
	 * Returns true if kind of change should be shown.
	 */
	/**
	 * Method useChange.
	 * 
	 * @param diff
	 *            Diff
	 * @param aConfig
	 *            CompareConfiguration
	 * @return boolean
	 */
	public boolean useChange(Diff diff, CompareConfiguration aConfig) {
		if (diff.fIsWhitespace) {
			return false;
		}
		int kind = diff.getKind();
		return useChange(kind, aConfig);
	}

	/**
	 * Method useChange.
	 * 
	 * @param kind
	 *            int
	 * @param aConfig
	 *            CompareConfiguration
	 * @return boolean
	 */
	private boolean useChange(int kind, CompareConfiguration aConfig) {
		if (kind == RangeDifference.NOCHANGE) {
			return false;
		}
		if (aConfig.isChangeIgnored(kind)) {
			return false;
		}
		if (kind == RangeDifference.ANCESTOR) {
			return false;
		}
		return true;
	}

	/**
	 * Method recordChangeDiff.
	 * 
	 * @param diff
	 *            Diff
	 * @param aChangeDiffs
	 *            List<Diff>
	 */
	private void recordChangeDiff(Diff diff, List<Diff> aChangeDiffs) {
		aChangeDiffs.add(diff); // here we remember only the real diffs
	}

	/*
	 * Performs a "smart" token based 3-way diff on the character range specified by the given baseDiff.
	 * It is "smart" because it tries to minimize the number of token diffs by merging them.
	 */
	/**
	 * Method mergingTokenDiff.
	 * 
	 * @param baseDiff
	 *            Diff
	 * @param ancestorDoc
	 *            IDocument
	 * @param a
	 *            String
	 * @param rightDoc
	 *            IDocument
	 * @param d
	 *            String
	 * @param leftDoc
	 *            IDocument
	 * @param s
	 *            String
	 * @param aIsThreeWay
	 *            boolean
	 * @param aConfig
	 *            CompareConfiguration
	 */
	private void mergingTokenDiff(Diff baseDiff, IDocument ancestorDoc, String a, IDocument rightDoc, String d,
			IDocument leftDoc, String s, boolean aIsThreeWay, CompareConfiguration aConfig) {
		ITokenComparator sa = null;
		int ancestorStart = 0;
		if (null != ancestorDoc) {
			sa = createTokenComparator(a);
			ancestorStart = baseDiff.fAncestorPos.getOffset();
		}

		int rightStart = baseDiff.fRightPos.getOffset();
		ITokenComparator sm = createTokenComparator(d);

		int leftStart = baseDiff.fLeftPos.getOffset();
		ITokenComparator sy = createTokenComparator(s);

		RangeDifference[] r = RangeDifferencer.findRanges(sa, sy, sm);
		for (int i = 0; i < r.length; i++) {
			RangeDifference es = r[i];
			// determine range of diffs in one line
			int start = i;
			int leftLine = -1;
			int rightLine = -1;
			try {
				leftLine = leftDoc.getLineOfOffset(leftStart + sy.getTokenStart(es.leftStart()));
				rightLine = rightDoc.getLineOfOffset(rightStart + sm.getTokenStart(es.rightStart()));
			} catch (BadLocationException e) {
				// silently ignored
			}
			i++;
			for (; i < r.length; i++) {
				es = r[i];
				try {
					if (leftLine != leftDoc.getLineOfOffset(leftStart + sy.getTokenStart(es.leftStart()))) {
						break;
					}
					if (rightLine != rightDoc.getLineOfOffset(rightStart + sm.getTokenStart(es.rightStart()))) {
						break;
					}
				} catch (BadLocationException e) {
					// silently ignored
				}
			}
			int end = i;

			// find first diff from left
			RangeDifference first = null;
			for (int ii = start; ii < end; ii++) {
				es = r[ii];
				if (useChange(es.kind(), aConfig)) {
					first = es;
					break;
				}
			}

			// find first diff from mine
			RangeDifference last = null;
			for (int ii = end - 1; ii >= start; ii--) {
				es = r[ii];
				if (useChange(es.kind(), aConfig)) {
					last = es;
					break;
				}
			}

			if (null != first && null != last) {

				int ancestorStart2 = 0;
				int ancestorEnd2 = 0;
				if (null != ancestorDoc && null != sa) {
					ancestorStart2 = ancestorStart + sa.getTokenStart(first.ancestorStart());
					ancestorEnd2 = ancestorStart + getTokenEnd(sa, last.ancestorStart(), last.ancestorLength());
				}

				int leftStart2 = leftStart + sy.getTokenStart(first.leftStart());
				int leftEnd2 = leftStart + getTokenEnd(sy, last.leftStart(), last.leftLength());

				int rightStart2 = rightStart + sm.getTokenStart(first.rightStart());
				int rightEnd2 = rightStart + getTokenEnd(sm, last.rightStart(), last.rightLength());
				Diff diff = new Diff(baseDiff, first.kind(), ancestorDoc, null, ancestorStart2, ancestorEnd2, leftDoc,
						null, leftStart2, leftEnd2, rightDoc, null, rightStart2, rightEnd2, aIsThreeWay, aConfig);
				diff.fIsToken = true;
				baseDiff.add(diff);
			}
		}
	}

	/*
	 * Performs a token based 3-way diff on the character range specified by the given baseDiff.
	 */
	/**
	 * Method simpleTokenDiff.
	 * 
	 * @param baseDiff
	 *            Diff
	 * @param ancestorDoc
	 *            IDocument
	 * @param a
	 *            String
	 * @param rightDoc
	 *            IDocument
	 * @param d
	 *            String
	 * @param leftDoc
	 *            IDocument
	 * @param s
	 *            String
	 * @param aIsThreeWay
	 *            boolean
	 * @param aConfig
	 *            CompareConfiguration
	 */
	private void simpleTokenDiff(final Diff baseDiff, IDocument ancestorDoc, String a, IDocument rightDoc, String d,
			IDocument leftDoc, String s, boolean aIsThreeWay, CompareConfiguration aConfig) {

		int ancestorStart = 0;
		ITokenComparator sa = null;
		if (null != ancestorDoc) {
			ancestorStart = baseDiff.fAncestorPos.getOffset();
			sa = createTokenComparator(a);
		}

		int rightStart = baseDiff.fRightPos.getOffset();
		ITokenComparator sm = createTokenComparator(d);

		int leftStart = baseDiff.fLeftPos.getOffset();
		ITokenComparator sy = createTokenComparator(s);

		RangeDifference[] e = RangeDifferencer.findRanges(sa, sy, sm);
		for (RangeDifference es : e) {
			int kind = es.kind();
			if (kind != RangeDifference.NOCHANGE) {

				int ancestorStart2 = ancestorStart;
				int ancestorEnd2 = ancestorStart;
				if (null != ancestorDoc && null != sa) {
					ancestorStart2 += sa.getTokenStart(es.ancestorStart());
					ancestorEnd2 += getTokenEnd(sa, es.ancestorStart(), es.ancestorLength());
				}

				int leftStart2 = leftStart + sy.getTokenStart(es.leftStart());
				int leftEnd2 = leftStart + getTokenEnd(sy, es.leftStart(), es.leftLength());

				int rightStart2 = rightStart + sm.getTokenStart(es.rightStart());
				int rightEnd2 = rightStart + getTokenEnd(sm, es.rightStart(), es.rightLength());

				Diff diff = new Diff(baseDiff, kind, ancestorDoc, null, ancestorStart2, ancestorEnd2, leftDoc, null,
						leftStart2, leftEnd2, rightDoc, null, rightStart2, rightEnd2, aIsThreeWay, aConfig);

				// ensure that token diff is smaller than basediff
				int leftS = baseDiff.fLeftPos.offset;
				int leftE = baseDiff.fLeftPos.offset + baseDiff.fLeftPos.length;
				int rightS = baseDiff.fRightPos.offset;
				int rightE = baseDiff.fRightPos.offset + baseDiff.fRightPos.length;
				if (leftS != leftStart2 || leftE != leftEnd2 || rightS != rightStart2 || rightE != rightEnd2) {
					diff.fIsToken = true;
					// add to base Diff
					baseDiff.add(diff);
				}
			}
		}
	}

	/**
	 * Method createTokenComparator.
	 * 
	 * @param s
	 *            String
	 * @return ITokenComparator
	 */
	private ITokenComparator createTokenComparator(String s) {
		return new TokenComparator(s);
	}

	/**
	 * Method getTokenEnd.
	 * 
	 * @param tc
	 *            ITokenComparator
	 * @param start
	 *            int
	 * @param count
	 *            int
	 * @return int
	 */
	private int getTokenEnd(ITokenComparator tc, int start, int count) {
		if (count <= 0) {
			return tc.getTokenStart(start);
		}
		int index = start + count - 1;
		return tc.getTokenStart(index) + tc.getTokenLength(index);
	}
}
