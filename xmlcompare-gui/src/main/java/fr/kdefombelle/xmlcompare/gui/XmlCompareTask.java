package fr.kdefombelle.xmlcompare.gui;

import java.io.File;
import java.io.FileOutputStream;

import org.xmlunit.diff.Difference;

import fr.kdefombelle.xmlcompare.core.ExcelDifferenceWriter;
import fr.kdefombelle.xmlcompare.core.SimpleXmlComparator;
import fr.kdefombelle.xmlcompare.core.XmlComparatorConfiguration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;


public class XmlCompareTask extends Task<ObservableList<String>> {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Static fields/initializers 
    //~ ----------------------------------------------------------------------------------------------------------------

    public static int PAUSE = 175;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    private SimpleXmlComparator xmlComparator = new SimpleXmlComparator();
    private XmlComparatorConfiguration configuration;
    private File controlFile;
    private File testFile;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Constructors 
    //~ ----------------------------------------------------------------------------------------------------------------

    public XmlCompareTask(File controlFile, File testFile, XmlComparatorConfiguration configuration) {
        this.controlFile = controlFile;
        this.testFile = testFile;
        this.configuration = configuration;
    }

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    @Override
    protected ObservableList<String> call() throws Exception {
        updateProgress(0, 100);
        updateMessage("start comparison");
        Thread.sleep(PAUSE);
        Iterable<Difference> differences = xmlComparator.compare(controlFile, testFile, configuration);

        updateProgress(0, 100);
        updateMessage("writing report");
        Thread.sleep(PAUSE);
        String resultReportFileName = "report-" + System.nanoTime() + ".xlsx";
        File resultFile = new File(resultReportFileName);
        try(FileOutputStream fos = new FileOutputStream(resultFile.getAbsolutePath())) {
            ExcelDifferenceWriter excelWriter = new ExcelDifferenceWriter();
            excelWriter.write(controlFile, testFile, differences, fos);
        }
        updateProgress(0, 100);
        updateMessage("done");
        Thread.sleep(PAUSE);

        ObservableList<String> reportInformation = FXCollections.observableArrayList();
        reportInformation.add(resultReportFileName);
        reportInformation.add(resultFile.getAbsolutePath());
        return reportInformation;
    }

}
