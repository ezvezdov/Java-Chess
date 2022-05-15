module cz.cvut.fel.pjv {
    requires javafx.controls;
    exports cz.cvut.fel.pjv;
    exports cz.cvut.fel.pjv.view;
    requires javafx.fxml;

    requires javafx.base;
    requires javafx.graphics;

    opens cz.cvut.fel.pjv to javafx.fxml;
    opens cz.cvut.fel.pjv.view to javafx.fxml;
    exports cz.cvut.fel.pjv.model;
    opens cz.cvut.fel.pjv.model to javafx.fxml;
}

