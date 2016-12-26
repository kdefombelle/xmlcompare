/**
 *  This software program has been written by Karim de Fombelle.
 */
package fr.kdefombelle.xmlcompare.core;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.Difference;
import org.xmlunit.diff.ElementSelectors;

public class SimpleXmlComparator implements XmlComparator {

	// ~
	// ----------------------------------------------------------------------------------------------------------------
	// ~ Instance fields
	// ~
	// ----------------------------------------------------------------------------------------------------------------

	final Logger logger = LoggerFactory.getLogger(SimpleXmlComparator.class);

	// ~
	// ----------------------------------------------------------------------------------------------------------------
	// ~ Methods
	// ~
	// ----------------------------------------------------------------------------------------------------------------

	@Override
	public Iterable<Difference> compare(File controlXml, File testXml) {
		return compare(controlXml, testXml, new XmlComparatorConfiguration());
	}

	@Override
	public Iterable<Difference> compare(File controlXml, File testXml, XmlComparatorConfiguration configuration) {
		if (controlXml == null)
			throw new IllegalArgumentException("Control file is null");
		if (testXml == null)
			throw new IllegalArgumentException("Test file is null");
		logger.info("Comparing control [" + controlXml + "] and test [" + testXml + "]");
		logger.info("ignoreAttributes? : [" + configuration.isIgnoreAttributes() + "]");

		Source control = Input.fromFile(controlXml).build();
		Source test = Input.fromFile(testXml).build();
		return compare(control, test, configuration);
	}

	@Override
	public Iterable<Difference> compare(Source control, Source test) {
		return compare(control, test, new XmlComparatorConfiguration());
	}

	@Override
	public Iterable<Difference> compare(Source control, Source test, XmlComparatorConfiguration configuration) {
		Iterable<Difference> differences;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setAttribute("http://xml.org/sax/features/namespaces", true);
		dbf.setAttribute("http://xml.org/sax/features/validation", false);
		dbf.setAttribute("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbf.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		dbf.setNamespaceAware(true);
		dbf.setIgnoringElementContentWhitespace(false);
		dbf.setIgnoringComments(false);
		dbf.setValidating(false);

		// NamespaceContext nsContext = new
		// SimpleNamespaceContext(getMdmlNamespaces());
		// XMLUnit.setXpathNamespaceContext(nsContext);

		// Diff diff = DiffBuilder.compare(control).withTest(test)
		// //.withDocumentBuilderFactory(dbf)
		// .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byName))
		// .ignoreComments()
		// .ignoreWhitespace()
		// .checkForIdentical().build();

		DiffBuilder diffBuilder = DiffBuilder.compare(control).withTest(test)
				.withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byName)).checkForIdentical();
		if(configuration.isIgnoreAttributes()){
			diffBuilder.withDifferenceEvaluator(new IgnoreAttributesDifferenceEvaluator());
		}
		Diff diff =	diffBuilder.build();

		differences = diff.getDifferences();

		logger.debug("documents hasDifferences? : " + diff.hasDifferences());

		return differences;
	}

	// private Map<String, String> getMdmlNamespaces() {
	// Map<String, String> namespacesMap = new HashMap<>();
	// namespacesMap.put("", "XmlCache"); //default namespace
	// namespacesMap.put("xc", "XmlCache");
	// namespacesMap.put("mp", "mx.MarketParameters");
	//
	// //fxvl
	// namespacesMap.put("fx", "mx.MarketParameters.Forex");
	// namespacesMap.put("fxvl", "mx.MarketParameters.Forex.Volatilities");
	//
	// //fxsp
	// namespacesMap.put("fx", "mx.MarketParameters.Forex");
	// namespacesMap.put("fxsp", "mx.MarketParameters.Forex.Spot");
	//
	// //rtcs
	// namespacesMap.put("rt", "mx.MarketParameters.Rates");
	// namespacesMap.put("rtcs", "mx.MarketParameters.Rates.CapSmile");
	//
	// //scpr
	// namespacesMap.put("sc", "mx.MarketParameters.Securities");
	// namespacesMap.put("scpr", "mx.MarketParameters.Securities.Prices");
	//
	// return namespacesMap;
	// }

}
