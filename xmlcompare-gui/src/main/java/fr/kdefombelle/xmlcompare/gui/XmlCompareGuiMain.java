/**
 *  This software program has been written by Karim de Fombelle.
 */
/**
 *  This software program has been written by Karim de Fombelle.
 */
/**
 *  This software program has been written by Karim de Fombelle.
 */
/**
 *  This software program has been written by Karim de Fombelle.
 */
/**
 *  This software program has been written by Karim de Fombelle.
 */
package fr.kdefombelle.xmlcompare.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.kdefombelle.xmlcompare.gui.model.XmlCompareParameters;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class XmlCompareGuiMain extends Application {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Static fields/initializers 
    //~ ----------------------------------------------------------------------------------------------------------------

    static final Logger logger = LoggerFactory.getLogger(XmlCompareGuiMain.class);

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    private Stage stage;

    private XmlCompareParameters xmlCompareParameters;
    private final double WINDOW_WIDTH = 650;
    private final double WINDOW_HEIGHT = 400;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        launch(XmlCompareGuiMain.class, (java.lang.String[]) null);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            stage = primaryStage;
            stage.setTitle("XmlCompare");
            gotoDefault();
            primaryStage.show();
        } catch (Exception ex) {
            logger.error("Exception while starting " +
                XmlCompareGuiMain.class.getSimpleName(), ex);
        }
    }

    public XmlCompareParameters getXmlCompareParameters() {
        return xmlCompareParameters;
    }

    public XmlCompareController replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(XmlCompareGuiMain.class.getResource(fxml));

        Pane page = (Pane) loader.load(this.getClass().getClassLoader().getResourceAsStream(fxml));

        Scene scene = new Scene(page, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.sizeToScene();
        Image icon = new Image("product.png");
        stage.getIcons().add(icon);
        return (XmlCompareController) loader.getController();
    }

    private void gotoDefault() {
        try {
            XmlCompareController xmlCompare = (XmlCompareController) replaceSceneContent("xmlcompare-gui.fxml");
            xmlCompare.setApp(this);
        } catch (Exception ex) {
            logger.error("Exception while starting " +
                XmlCompareGuiMain.class.getSimpleName(), ex);
        }
    }
}
