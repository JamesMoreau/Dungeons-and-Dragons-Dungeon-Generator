package gui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;


public class dungeonGui<toReturn> extends Application {
    /* Even if it is a GUI it is useful to have instance variables
    so that you can break the processing up into smaller methods that have
    one responsibility.
     */
    private Controller theController;
    private BorderPane root;  //the root element of this GUI
    private Popup descriptionPane;
    private Stage primaryStage;  //The stage that is passed in on initialization

    @Override
    public void start(Stage assignedStage) {
        primaryStage = assignedStage;
        primaryStage.setTitle("Dungeon Generation App");


        BorderPane rootNode = new BorderPane();
        Scene myScene = new Scene(rootNode, 800, 600);
        setApplicationIcon();

        primaryStage.setScene(myScene);
        primaryStage.show();
    }


    /* an example of a popup area that can be set to nearly any
    type of node
     */
    private Popup createPopUp(int x, int y, String text) {
        Popup popup = new Popup();
        popup.setX(x);
        popup.setY(y);
        TextArea textA = new TextArea(text);
        popup.getContent().addAll(textA);
        textA.setStyle(" -fx-background-color: white;");
        textA.setMinWidth(80);
        textA.setMinHeight(50);
        return popup;
    }

    /*generic button creation method ensure that all buttons will have a
    similar style and means that the style only need to be in one place
     */
    private Button createButton(String text, String format) {
        Button btn = new Button();
        btn.setText(text);
        btn.setStyle("");
        return btn;
    }

    private void changeDescriptionText(String text) {
        ObservableList<Node> list = descriptionPane.getContent();
        for (Node t : list) {
            if (t instanceof TextArea) {
                TextArea temp = (TextArea) t;
                temp.setText(text);
            }

        }

    }

    private void setApplicationIcon() {
        this.primaryStage.getIcons().add(new Image("res/my_Ghidra_application_icon.png" ));
    }

    private void makeEditButton() {
        Button editButton = createButton("Hello world", "-fx-background-color: #ff0000; -fx-background-radius: 10, 10, 10, 10;");
    }

    public static void main(String[] args) {
        launch(args);
    }
}