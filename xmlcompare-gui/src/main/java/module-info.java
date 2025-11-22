module fr.kdefombelle.xmlcompare.gui {
    requires fr.kdefombelle.xmlcompare.core;
    opens fr.kdefombelle.xmlcompare.gui to javafx.fxml;
    requires org.slf4j;
    requires org.xmlunit;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    exports fr.kdefombelle.xmlcompare.gui;
}