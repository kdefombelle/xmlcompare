package fr.kdefombelle.xmlcompare.core;

import java.io.File;
import java.io.Reader;
import java.util.List;

import org.custommonkey.xmlunit.Difference;


public interface XmlComparator {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    List<Difference> compare(File controlXml, File testXml);

    List<Difference> compare(File controlXml, File testXml, boolean ignoreAttributes);

    List<Difference> compare(Reader control, Reader test);

    List<Difference> compare(Reader control, Reader test, boolean ignoreAttributes);

}
