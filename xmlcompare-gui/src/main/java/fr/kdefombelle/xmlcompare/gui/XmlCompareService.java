package fr.kdefombelle.xmlcompare.gui;

import java.io.File;

import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;


public class XmlCompareService extends Service<ObservableList<String>> {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    private File controlFile;
    private File testFile;
    private boolean ignoreAttributes;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public void setControlFile(File controlFile) {
        this.controlFile = controlFile;
    }

    public void setTestFile(File testFile) {
        this.testFile = testFile;
    }

    public void setIgnoreAttributes(boolean ignoreAttributes) {
        this.ignoreAttributes = ignoreAttributes;
    }

    @Override
    protected Task<ObservableList<String>> createTask() {
        return new XmlCompareTask(controlFile, testFile, ignoreAttributes);
    }

}
