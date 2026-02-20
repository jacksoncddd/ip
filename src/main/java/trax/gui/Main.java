package trax.gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import trax.main.Trax;

public class Main extends Application {

    private Trax trax = new Trax();

    @Override
    public void start(Stage stage) {
        try {
            stage.setTitle("Trax Hotline!");

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setTrax(trax);  // inject the Trax instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
