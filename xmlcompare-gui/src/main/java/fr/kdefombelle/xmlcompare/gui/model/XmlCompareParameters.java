/**
 *  This software program has been written by Karim de Fombelle.
 */
/**
 *  This software program has been written by Karim de Fombelle.
 */
/**
 *  This software program has been written by Karim de Fombelle.
 */
package fr.kdefombelle.xmlcompare.gui.model;

public class XmlCompareParameters {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    private String controlFileName;
    private String testFileName;
    private boolean ignoreAttributes;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public void setControlFileName(String controlFileName) {
        this.controlFileName = controlFileName;
    }

    public void setTestFileName(String testFileName) {
        this.testFileName = testFileName;
    }

    public void setIgnoreAttributes(boolean ignoreAttributes) {
        this.ignoreAttributes = ignoreAttributes;
    }

    public boolean isIgnoreAttributes() {
        return ignoreAttributes;
    }

    public String getControlFileName() {
        return controlFileName;
    }

    public String getTestFileName() {
        return testFileName;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("XmlCompareParameters [controlFileName=").append(controlFileName).append(", testFileName=").append(testFileName).append(", ignoreAttributes=").append(ignoreAttributes).append("]");
        return builder.toString();
    }

}
