package trax.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import trax.main.Trax;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Trax trax;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/akay.png"));
    private Image traxImage = new Image(this.getClass().getResourceAsStream("/images/TraxNYC.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Trax instance */
    public void setTrax(Trax t) {
        trax = t;
        dialogContainer.getChildren().add(DialogBox.getTraxDialog(trax.showWelcome(), traxImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = trax.run(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getTraxDialog(response, traxImage)
        );
        userInput.clear();
    }
}
