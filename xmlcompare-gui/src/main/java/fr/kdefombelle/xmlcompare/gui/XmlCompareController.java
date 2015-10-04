package fr.kdefombelle.xmlcompare.gui;

import java.awt.Desktop;
import java.io.File;

import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label progressLabel;

    private XmlCompareGuiMain application;

    private XmlCompareParameters parameters = new XmlCompareParameters();
    private XmlCompareService service = new XmlCompareService();

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

        progressBar.visibleProperty().bind(service.runningProperty());
        progressLabel.visibleProperty().bind(service.runningProperty());
        progressBar.progressProperty().bind(service.progressProperty());
        progressLabel.textProperty().bind(service.messageProperty());

        resultHyperlink.setVisible(false);
    }

    @FXML
    private void compare() {
        logger.debug("Comparison between for " + parameters);
        if (parameters.getControlFileName() == null) {
            logger.error("control file is null");
            return;
        }
        if (parameters.getTestFileName() == null) {
            logger.error("test file is null");
            return;
        }
        File controlFile = new File(parameters.getControlFileName());
        File testFile = new File(parameters.getTestFileName());

        service.setControlFile(controlFile);
        service.setTestFile(testFile);
        service.setIgnoreAttributes(parameters.isIgnoreAttributes());
        service.restart();
        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

                @Override
                public void handle(WorkerStateEvent t) {
                    ObservableList<String> rf = (ObservableList<String>) t.getSource().getValue();
                    //TOO: search a better coding via an object rather than position based info
                    String reportFileName = rf.get(0);
                    String reportAboslutePath = rf.get(1);

                    resultHyperlink.setText(reportAboslutePath);
                    resultHyperlink.setOnAction((ActionEvent event) -> {
                        if (Desktop.isDesktopSupported()) {
                            try {
                                Desktop.getDesktop().open(new File(reportFileName));
                            } catch (Exception e) {
                                logger.error("Impossible to launch application...", e);
                            }
                        }
                    });
                    resultHyperlink.setVisited(false);
                    resultHyperlink.setVisible(true);
                }
            });
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

    @FXML
    private void showHelp(MouseEvent me) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        Image icon = new Image("information.png");
        Stage helpStage = (Stage) dialog.getDialogPane().getScene().getWindow();
        helpStage.getIcons().add(icon);
        dialog.setTitle("About XmlComparator");
        dialog.setHeaderText("XmlComparator version 1.0-SNAPSHOT");
        dialog.getDialogPane().setContent(new Label("A tool to compare XML files" +
                "\r\n" +
                "\r\nkarim.defombelle@murex.com" +
                "\r\nhttps://github.com/kdefombelle"));
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Image productIcon = new Image("product.png");
        dialog.setGraphic(new ImageView(productIcon));

        dialog.show();
    }
}
