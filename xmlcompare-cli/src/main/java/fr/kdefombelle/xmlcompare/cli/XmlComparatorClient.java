/**
 *  This software program has been written by Karim de Fombelle.
 */
package fr.kdefombelle.xmlcompare.cli;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import org.custommonkey.xmlunit.Difference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.kdefombelle.xmlcompare.core.ExcelDifferenceWriter;
import fr.kdefombelle.xmlcompare.core.SimpleXmlComparator;
import fr.kdefombelle.xmlcompare.core.XmlComparatorConfiguration;


/**
 * XmlComparatorClient
 */
public class XmlComparatorClient {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Static fields/initializers 
    //~ ----------------------------------------------------------------------------------------------------------------

    private static final String OPT_HELP = "help";
    private static final String OPT_HELP_SHORT = "h";
    private static final String OPT_XML_CONTROL_FILE = "control";
    private static final String OPT_XML_CONTROL_FILE_SHORT = "c";
    private static final String OPT_XML_TEST_FILE = "test";
    private static final String OPT_XML_TEST_FILE_SHORT = "t";
    private static final String OPT_REPORT_FILE = "report";
    private static final String OPT_REPORT_FILE_SHORT = "r";
    private static final String OPT_IGNORE_ATTRIBUTES = "ignoreAttributes";
    private static final String OPT_IGNORE_ATTRIBUTES_SHORT = "i";

    static final Logger logger = LoggerFactory.getLogger(XmlComparatorClient.class);

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        logger.info("Parsing options...");

        OptionParser parser = new OptionParser();
        List<String> helpOptions = Arrays.asList(OPT_HELP, OPT_HELP_SHORT);
        OptionSpec<?> help = parser.acceptsAll(helpOptions, "print this help message").withOptionalArg();

        List<String> xmlControlOptions = Arrays.asList(OPT_XML_CONTROL_FILE, OPT_XML_CONTROL_FILE_SHORT);
        OptionSpec<File> xmlControlOption = parser.acceptsAll(xmlControlOptions, "control file").withRequiredArg().ofType(File.class);

        List<String> xmlTestOptions = Arrays.asList(OPT_XML_TEST_FILE, OPT_XML_TEST_FILE_SHORT);
        OptionSpec<File> xmlTestOption = parser.acceptsAll(xmlTestOptions, "file to be compared").withRequiredArg().ofType(File.class);

        List<String> ignoreAttributesOptions = Arrays.asList(OPT_IGNORE_ATTRIBUTES, OPT_IGNORE_ATTRIBUTES_SHORT);
        OptionSpec<String> ignoreAttributesOption = parser.acceptsAll(ignoreAttributesOptions, "ignore attributes comparison").withOptionalArg().ofType(String.class);

        List<String> reportFileOptions = Arrays.asList(OPT_REPORT_FILE, OPT_REPORT_FILE_SHORT);
        long timestamp = System.currentTimeMillis();
        String defaultReportFileName = "report-" + timestamp + ".xlsx";
        OptionSpec<String> reportFileOption = parser.acceptsAll(reportFileOptions, "Excel report file name").withOptionalArg().ofType(String.class).defaultsTo(defaultReportFileName);

        OptionSet options = null;
        options = parser.parse(args);

        if (options.has(help)) {
            // print help and exit
            try {
                parser.printHelpOn(System.out);
                return;
            } catch (IOException e) {
                logger.error(XmlComparatorClient.class.getClass().getSimpleName() + " execution failed:", e);
                return;
            }
        }

        File xmlControl = options.valueOf(xmlControlOption);
        File xmlTest = options.valueOf(xmlTestOption);
        String reportFileName = options.valueOf(reportFileOption);

        SimpleXmlComparator xmlComparator = new SimpleXmlComparator();
        XmlComparatorConfiguration configuration = new XmlComparatorConfiguration();
        configuration.setIgnoreAttributes(options.has(ignoreAttributesOption));
        List<Difference> differences = xmlComparator.compare(xmlControl, xmlTest, configuration);

        ExcelDifferenceWriter excelWriter = new ExcelDifferenceWriter(reportFileName);
        excelWriter.write(xmlControl, xmlTest, differences);
    }
}
