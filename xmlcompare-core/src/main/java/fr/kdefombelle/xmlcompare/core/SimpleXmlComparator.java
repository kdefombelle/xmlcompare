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
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilderFactory;

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
            dbf.setAttribute("http://xml.org/sax/features/namespaces", true);
            dbf.setAttribute("http://xml.org/sax/features/validation", false);
            dbf.setAttribute("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
            dbf.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            dbf.setNamespaceAware(true);
            dbf.setIgnoringElementContentWhitespace(false);
            dbf.setIgnoringComments(false);
            dbf.setValidating(false);
                
            XMLUnit.setTestDocumentBuilderFactory(dbf);
            XMLUnit.setControlDocumentBuilderFactory(dbf);

//            NamespaceContext nsContext = new SimpleNamespaceContext(getMdmlNamespaces());
//            XMLUnit.setXpathNamespaceContext(nsContext);

            Diff diff = new Diff(control, test);
            DetailedDiff detailedDiff = new DetailedDiff(diff);
            if (configuration.isIgnoreAttributes())
                detailedDiff.overrideDifferenceListener(new IgnoreAttributesDifferenceListener());

//            diff.overrideElementQualifier(new ElementNameAndAttributeQualifier());
            differences = detailedDiff.getAllDifferences();

            logger.debug("documents identical? : " + diff.identical());
            logger.debug("documents similar? : " + diff.similar());
            logger.debug(differences.size() + " differences found");
        } catch (SAXException | IOException e) {
            logger.debug("Error while comparing the documents", e);
            throw new XmlCompareException("Error while comparing the documents", e);
        }
        List<Difference> nonRecoverableDifferences = differences.stream().filter(d->!d.isRecoverable()).collect(Collectors.toList());
        logger.debug(nonRecoverableDifferences.size() + " non recoverable differences found");
		return nonRecoverableDifferences;
    }

//    private Map<String, String> getMdmlNamespaces() {
//        Map<String, String> namespacesMap = new HashMap<>();
//        namespacesMap.put("", "XmlCache"); //default namespace
//        namespacesMap.put("xc", "XmlCache");
//        namespacesMap.put("mp", "mx.MarketParameters");
//
//        //fxvl
//        namespacesMap.put("fx", "mx.MarketParameters.Forex");
//        namespacesMap.put("fxvl", "mx.MarketParameters.Forex.Volatilities");
//
//        //fxsp
//        namespacesMap.put("fx", "mx.MarketParameters.Forex");
//        namespacesMap.put("fxsp", "mx.MarketParameters.Forex.Spot");
//
//        //rtcs
//        namespacesMap.put("rt", "mx.MarketParameters.Rates");
//        namespacesMap.put("rtcs", "mx.MarketParameters.Rates.CapSmile");
//
//        //scpr
//        namespacesMap.put("sc", "mx.MarketParameters.Securities");
//        namespacesMap.put("scpr", "mx.MarketParameters.Securities.Prices");
//
//        return namespacesMap;
//    }

}
