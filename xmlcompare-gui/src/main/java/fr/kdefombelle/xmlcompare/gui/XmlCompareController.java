package fr.kdefombelle.xmlcompare.gui;

import java.awt.Desktop;
import java.io.File;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import org.custommonkey.xmlunit.Difference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.kdefombelle.xmlcompare.core.ExcelDifferenceWriter;
import fr.kdefombelle.xmlcompare.core.SimpleXmlComparator;
import fr.kdefombelle.xmlcompare.gui.model.XmlCompareParameters;


public class XmlCompareController extends AnchorPane {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    private final Logger logger = LoggerFactory.getLogger(XmlCompareController.class);

    @FXML
    private TextField controlFileTextField;
    @FXML
    private TextField testFileTextField;
    @FXML
    private Button compareButton;
    @FXML
    private Button openControlFileButton;
    @FXML
    private Button openTestFileButton;
    @FXML
    private CheckBox ignoreAttributesCheckbox;
    @FXML
    private Hyperlink resultHyperlink;

    private XmlCompareGuiMain application;

    private XmlCompareParameters parameters = new XmlCompareParameters();
    private SimpleXmlComparator xmlComparator = new SimpleXmlComparator();

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public void setApp(XmlCompareGuiMain xmlCompareGuiMain) {
        this.application = xmlCompareGuiMain;
    }

    @FXML
    private void initialize() {
        logger.debug("Initialise controller");
        controlFileTextField.setTooltip(new Tooltip("Enter absolute path of your control file"));
        testFileTextField.setTooltip(new Tooltip("Enter absolute path of your test file"));
        ignoreAttributesCheckbox.setIndeterminate(false);
        resultHyperlink.setVisible(false);
    }

    @FXML
    private void compare() {
        logger.debug("Comparison between for " + parameters);
        File controlFile = new File(parameters.getControlFileName());
        File testFile = new File(parameters.getTestFileName());
        List<Difference> differences = xmlComparator.compare(controlFile, testFile, parameters.isIgnoreAttributes());
        String resultReportFileName = "report-" + System.nanoTime() + ".xlsx";
        File resultFile = new File(resultReportFileName);
        ExcelDifferenceWriter excelWriter = new ExcelDifferenceWriter(resultFile.getAbsolutePath());
        excelWriter.write(controlFile, testFile, differences);

        resultHyperlink.setText(resultFile.getAbsolutePath());
        resultHyperlink.setOnAction((ActionEvent e) -> {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(resultFile);
                } catch (Exception e1) {
                    logger.error("Impossible to launch application...", e);
                }
            }
        });
        resultHyperlink.setVisible(true);
    }

    private String openFileChooser(Button button) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(button.getScene().getWindow());
        return (file == null) ? "" : file.getAbsolutePath();
    }

    @FXML
    private void openControlFileChooser(ActionEvent event) {
        String controleFileName = openFileChooser((Button) event.getSource());
        if (!controleFileName.isEmpty()) {
            controlFileTextField.setText(controleFileName);
            parameters.setControlFileName(controleFileName);
        }
    }

    @FXML
    private void openTestFileChooser(ActionEvent event) {
        String testFileName = openFileChooser((Button) event.getSource());
        if (!testFileName.isEmpty()) {
            testFileTextField.setText(testFileName);
            parameters.setTestFileName(testFileName);
        }
    }

    @FXML
    private void setIgnoreAttributes(ActionEvent event) {
        parameters.setIgnoreAttributes(((CheckBox) event.getSource()).isSelected());
    }
}
