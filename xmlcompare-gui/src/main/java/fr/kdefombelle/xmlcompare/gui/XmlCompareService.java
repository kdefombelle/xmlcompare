package fr.kdefombelle.xmlcompare.gui;

import java.io.File;

import fr.kdefombelle.xmlcompare.core.XmlComparatorConfiguration;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;


public class XmlCompareService extends Service<ObservableList<String>> {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    private File controlFile;
    private File testFile;
    private XmlComparatorConfiguration configuration;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public void setControlFile(File controlFile) {
        this.controlFile = controlFile;
    }

    public void setTestFile(File testFile) {
        this.testFile = testFile;
    }

    public void setXmlComparatorConfiguration(XmlComparatorConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected Task<ObservableList<String>> createTask() {
        return new XmlCompareTask(controlFile, testFile, configuration);
    }

}
