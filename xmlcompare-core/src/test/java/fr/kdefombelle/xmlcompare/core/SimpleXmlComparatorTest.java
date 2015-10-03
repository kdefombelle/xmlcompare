/**
 *  This software program has been written by Karim de Fombelle.
 */
package fr.kdefombelle.xmlcompare.core;

import java.io.File;
import java.net.URL;
import java.util.List;

import fr.kdefombelle.xmlcompare.core.SimpleXmlComparator;
import org.custommonkey.xmlunit.Difference;
import org.junit.Assert;
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
        URL url1 = this.getClass().getClassLoader().getResource("xml1.xml");
        File file1 = new File(url1.getFile());
        URL url2 = this.getClass().getClassLoader().getResource("xml2.xml");
        File file2 = new File(url2.getFile());
        List<Difference> differences = xmlComparator.compare(file1, file2, false);
        Assert.assertTrue(differences.size() != 0);
    }

    @Test
    public void testCompareIgnoreAttributes() throws Exception {
        URL url1 = this.getClass().getClassLoader().getResource("xml1.xml");
        File file1 = new File(url1.getFile());
        URL url2 = this.getClass().getClassLoader().getResource("xml2.xml");
        File file2 = new File(url2.getFile());
        List<Difference> differences = xmlComparator.compare(file1, file2, true);
        Assert.assertTrue(differences.size() == 0);
    }

}
