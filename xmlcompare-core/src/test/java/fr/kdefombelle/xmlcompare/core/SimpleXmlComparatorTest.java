/**
 *  This software program has been written by Karim de Fombelle.
 */
package fr.kdefombelle.xmlcompare.core;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.custommonkey.xmlunit.Difference;
import org.junit.Test;


public class SimpleXmlComparatorTest {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    private SimpleXmlComparator xmlComparator = new SimpleXmlComparator();

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    @Test
    public void testCompare() throws Exception {
        XmlComparatorConfiguration configuration = new XmlComparatorConfiguration();
        List<Difference> differences = xmlComparator.compare(getFile("xml1.xml"), getFile("xml2.xml"), configuration);
        assertTrue(differences.size() != 0);
    }

    @Test
    public void testCompareIgnoreAttributes() throws Exception {
        XmlComparatorConfiguration configuration = new XmlComparatorConfiguration();
        configuration.setIgnoreAttributes(true);
        List<Difference> differences = xmlComparator.compare(getFile("xml1.xml"), getFile("xml2.xml"), configuration);
        assertTrue(differences.size() == 0);
    }

    @Test
    public void testCompareNamespaces() throws Exception {
        XmlComparatorConfiguration configuration = new XmlComparatorConfiguration();
        List<Difference> differences = xmlComparator.compare(getFile("namespaces/control.xml"), getFile("namespaces/test.xml"), configuration);
        //just test execution does not trigger any exception
    }

    @Test
    public void testCompareSequence() throws Exception {
        XmlComparatorConfiguration configuration = new XmlComparatorConfiguration();
        List<Difference> differences = xmlComparator.compare(getFile("sequence/control.xml"), getFile("sequence/test.xml"), configuration);
        //TODO: revisit this test and associated resources
    }

    private static File getFile(String path) {
        URL url1 = SimpleXmlComparatorTest.class.getClassLoader().getResource(path);
        return new File(url1.getFile());
    }

}
