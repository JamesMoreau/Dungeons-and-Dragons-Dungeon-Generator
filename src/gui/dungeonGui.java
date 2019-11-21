package gui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;


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


        this.root = setUpRoot();

        Scene myScene = new Scene(root, 800, 600);
        setApplicationIcon("res/my_Ghidra_application_icon.png");



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

    private void setApplicationIcon(String path) {
        this.primaryStage.getIcons().add(new Image(path));
    }

    private BorderPane setUpRoot() {
        BorderPane rootNode = new BorderPane();

        rootNode.setLeft(new Label("Chamber Passage Selection"));
        rootNode.setLeft(createListView());

        rootNode.setRight(createComboBox());

        return rootNode;
    }

    private Node createListView() {
        ListView listView = new ListView();

        listView.setPrefWidth(150);
        listView.setPrefHeight(150);

        listView.getItems().add("Item 1");
        listView.getItems().add("Item 2");
        listView.getItems().add("Item 3");

        listView.setOnMouseClicked((MouseEvent event)->{
            System.out.println("clicked on " + listView.getSelectionModel().getSelectedItem());
        });

        return listView;
    }

    private Node createComboBox() {
        ComboBox comboBox = new ComboBox();

        comboBox.getItems().add("Choice 1");
        comboBox.getItems().add("Choice 2");
        comboBox.getItems().add("Choice 3");

        comboBox.setOnMouseClicked((MouseEvent event)->{
            System.out.println("clicked on " + comboBox.getSelectionModel().getSelectedItem());
        });

        return comboBox;
    }

    private void makeEditButton() {
        Button editButton = createButton("Hello world", "-fx-background-color: #ff0000; -fx-background-radius: 10, 10, 10, 10;");
    }

    public static void main(String[] args) {
        launch(args);
    }
}