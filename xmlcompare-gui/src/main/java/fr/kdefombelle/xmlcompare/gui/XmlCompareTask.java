package fr.kdefombelle.xmlcompare.gui;

import java.io.File;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import org.custommonkey.xmlunit.Difference;

import fr.kdefombelle.xmlcompare.core.ExcelDifferenceWriter;
import fr.kdefombelle.xmlcompare.core.SimpleXmlComparator;


public class XmlCompareTask extends Task<ObservableList<String>> {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Static fields/initializers 
    //~ ----------------------------------------------------------------------------------------------------------------

    public static int PAUSE = 175;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    private SimpleXmlComparator xmlComparator = new SimpleXmlComparator();
    private boolean ignoreAttributes;
    private File controlFile;
    private File testFile;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Constructors 
    //~ ----------------------------------------------------------------------------------------------------------------

    public XmlCompareTask(File controlFile, File testFile, boolean ignoreAttributes) {
        this.controlFile = controlFile;
        this.testFile = testFile;
        this.ignoreAttributes = ignoreAttributes;
    }

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    @Override
    protected ObservableList<String> call() throws Exception {
        updateProgress(0, 100);
        updateMessage("start comparison");
        Thread.sleep(PAUSE);
        List<Difference> differences = xmlComparator.compare(controlFile, testFile, ignoreAttributes);

        updateProgress(0, 100);
        updateMessage("writing report");
        Thread.sleep(PAUSE);
        String resultReportFileName = "report-" + System.nanoTime() + ".xlsx";
        File resultFile = new File(resultReportFileName);
        ExcelDifferenceWriter excelWriter = new ExcelDifferenceWriter(resultFile.getAbsolutePath());
        excelWriter.write(controlFile, testFile, differences);

        updateProgress(0, 100);
        updateMessage("done");
        Thread.sleep(PAUSE);

        ObservableList<String> reportInformation = FXCollections.observableArrayList();
        reportInformation.add(resultReportFileName);
        reportInformation.add(resultFile.getAbsolutePath());
        return reportInformation;
    }

}
