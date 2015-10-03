/**
 *  This software program has been written by Karim de Fombelle.
 */
package fr.kdefombelle.xmlcompare.core;

import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceConstants;
import org.custommonkey.xmlunit.DifferenceListener;
import org.w3c.dom.Node;


public class IgnoreAttributesDifferenceListener implements DifferenceListener {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Static fields/initializers 
    //~ ----------------------------------------------------------------------------------------------------------------

    private static final int[] IGNORE_VALUES = new int[] { DifferenceConstants.ATTR_VALUE.getId(), };

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    @Override
    public int differenceFound(Difference difference) {
        if (isIgnoredDifference(difference)) {
            return RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
        } else {
            return RETURN_ACCEPT_DIFFERENCE;
        }
    }

    @Override
    public void skippedComparison(Node arg0, Node arg1) {
    }

    private boolean isIgnoredDifference(Difference difference) {
        int differenceId = difference.getId();
        for (int i = 0; i < IGNORE_VALUES.length; ++i) {
            if (differenceId == IGNORE_VALUES[i]) {
                return true;
            }
        }
        return false;
    }

}
