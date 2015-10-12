package fr.kdefombelle.xmlcompare.core;

import java.util.ArrayList;
import java.util.List;


public class XmlComparatorConfiguration {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    private boolean ignoreAttributes;
    private List<String> ignoredXpaths = new ArrayList<>();

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public void setIgnoreAttributes(boolean ignoreAttributes) {
        this.ignoreAttributes = ignoreAttributes;
    }

    public void setIgnoredXpaths(List<String> ignoredXpaths) {
        this.ignoredXpaths = ignoredXpaths;
    }

    public boolean isIgnoreAttributes() {
        return ignoreAttributes;
    }

    public List<String> getIgnoredXpaths() {
        return ignoredXpaths;
    }

}
