/**
 *  This software program has been written by Karim de Fombelle.
 */
package fr.kdefombelle.xmlcompare.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;

import org.junit.Rule;
import org.junit.Test;
import org.xmlunit.diff.Difference;

public class SimpleXmlComparatorTest {

	@Rule
	public FileResourcesRule files = new FileResourcesRule();
	// ~
	// ----------------------------------------------------------------------------------------------------------------
	// ~ Instance fields
	// ~
	// ----------------------------------------------------------------------------------------------------------------

	private SimpleXmlComparator xmlComparator = new SimpleXmlComparator();

	// ~
	// ----------------------------------------------------------------------------------------------------------------
	// ~ Methods
	// ~
	// ----------------------------------------------------------------------------------------------------------------

	@Test
	public void testCompare() throws Exception {
		XmlComparatorConfiguration configuration = new XmlComparatorConfiguration();
		Iterable<Difference> differences = xmlComparator.compare(getFile("xml1.xml"), getFile("xml2.xml"),
				configuration);
		assertTrue(differences.iterator().hasNext());
	}

	@Test
	public void testCompareIgnoreAttributes() throws Exception {
		XmlComparatorConfiguration configuration = new XmlComparatorConfiguration();
		configuration.setIgnoreAttributes(true);
		Iterable<Difference> differences = xmlComparator.compare(getFile("xml1.xml"), getFile("xml2.xml"),
				configuration);
		assertFalse(differences.iterator().hasNext());
	}

	@Test
	public void testCompareNamespaces() throws Exception {
		XmlComparatorConfiguration configuration = new XmlComparatorConfiguration();
		Iterable<Difference> differences = xmlComparator.compare(getFile("namespaces/control.xml"),
				getFile("namespaces/test.xml"), configuration);
		// just test execution does not trigger any exception
	}

	@Test
	public void testCompareSequence() throws Exception {
		XmlComparatorConfiguration configuration = new XmlComparatorConfiguration();
		Iterable<Difference> differences = xmlComparator.compare(getFile("sequence/control.xml"),
				getFile("sequence/test.xml"), configuration);
		// TODO: revisit this test and associated resources
	}

	private static File getFile(String path) {
		URL url = SimpleXmlComparatorTest.class.getClassLoader().getResource(path);
		return new File(url.getFile());
	}

}
