/**
 *  This software program has been written by Karim de Fombelle.
 */
package fr.kdefombelle.xmlcompare.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.XMLUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;


public class SimpleXmlComparator implements XmlComparator {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    final Logger logger = LoggerFactory.getLogger(SimpleXmlComparator.class);

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    @Override
    public List<Difference> compare(File controlXml, File testXml) {
        return compare(controlXml, testXml, new XmlComparatorConfiguration());
    }

    @Override
    public List<Difference> compare(File controlXml, File testXml, XmlComparatorConfiguration configuration) {
        if (controlXml == null)
            throw new IllegalArgumentException("Control file is null");
        if (testXml == null)
            throw new IllegalArgumentException("Test file is null");
        logger.info("Comparing control [" + controlXml + "] and test [" + testXml + "]");
        logger.info("ignoreAttributes? : [" + configuration.isIgnoreAttributes() + "]");

        FileReader control;
        FileReader test;
        try {
            control = new FileReader(controlXml);
            test = new FileReader(testXml);
        } catch (FileNotFoundException e) {
            throw new XmlCompareException("Error while loadind file content from control [" + controlXml + "] or test [" + testXml + "]", e);
        }
        return compare(control, test, configuration);
    }

    @Override
    public List<Difference> compare(Reader control, Reader test) {
        return compare(control, test, new XmlComparatorConfiguration());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Difference> compare(Reader control, Reader test, XmlComparatorConfiguration configuration) {
        List<Difference> differences = new ArrayList<>();
        try {
            XMLUnit.setIgnoreWhitespace(true);
            XMLUnit.setIgnoreComments(true);
            XMLUnit.setIgnoreAttributeOrder(true);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setValidating(false);
            dbf.setFeature("http://xml.org/sax/features/namespaces", false);
            dbf.setFeature("http://xml.org/sax/features/validation", false);
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            XMLUnit.setTestDocumentBuilderFactory(dbf);
            XMLUnit.setControlDocumentBuilderFactory(dbf);

            Diff diff = new Diff(control, test);
            DetailedDiff detailedDiff = new DetailedDiff(diff);
            if (configuration.isIgnoreAttributes())
                detailedDiff.overrideDifferenceListener(new IgnoreAttributesDifferenceListener());

            differences = detailedDiff.getAllDifferences();

            logger.debug("documents identical? : " + diff.identical());
            logger.debug("documents similar? : " + diff.similar());
            logger.debug(differences.size() + " differences found");
        } catch (SAXException | IOException | ParserConfigurationException e) {
            logger.debug("Error while comparing the documents", e);
            throw new XmlCompareException("Error while comparing the documents", e);
        }
        return differences;
    }

}
