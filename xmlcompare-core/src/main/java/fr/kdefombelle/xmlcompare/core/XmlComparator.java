package fr.kdefombelle.xmlcompare.core;

import java.io.File;

import javax.xml.transform.Source;

import org.xmlunit.diff.Difference;


public interface XmlComparator {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

	Iterable<Difference> compare(File controlXml, File testXml);

	Iterable<Difference> compare(File controlXml, File testXml, XmlComparatorConfiguration configuration);

	Iterable<Difference> compare(Source control, Source test);

	Iterable<Difference> compare(Source control, Source test, XmlComparatorConfiguration configuration);

}
