/**
 *  This software program has been written by Karim de Fombelle.
 */
package fr.kdefombelle.xmlcompare.core;

import org.xmlunit.diff.Comparison;
import org.xmlunit.diff.ComparisonResult;
import org.xmlunit.diff.ComparisonType;
import org.xmlunit.diff.DifferenceEvaluator;

public class IgnoreAttributesDifferenceEvaluator implements DifferenceEvaluator {

	private static final ComparisonType[] IGNORED_TYPES = new ComparisonType[] { ComparisonType.ATTR_VALUE, ComparisonType.ATTR_NAME_LOOKUP, ComparisonType.ELEMENT_NUM_ATTRIBUTES };

	@Override
	public ComparisonResult evaluate(Comparison comparison, ComparisonResult comparisonResult) {
		if (isIgnoredType(comparison)) {
			return ComparisonResult.EQUAL;
		} else {
			return comparisonResult;
		}
	}

	private boolean isIgnoredType(Comparison comparison) {
		ComparisonType type = comparison.getType();
		for (int i = 0; i < IGNORED_TYPES.length; ++i) {
			if (type == IGNORED_TYPES[i]) {
				return true;
			}
		}
		return false;
	}

}
