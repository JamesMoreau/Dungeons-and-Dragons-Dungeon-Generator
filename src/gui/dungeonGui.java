package gui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.awt.event.ActionListener;
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
    private Stage primaryStage;  //The stage that is passed in on initialization
    private TextArea textArea;
    private ListView myListView;
    private ComboBox myDoorsList;

    @Override
    public void start(Stage assignedStage) {
        this.theController = new Controller(this);

        primaryStage = assignedStage;
        primaryStage.setTitle("Dungeon Generator");

        this.root = setUpRoot();

        Scene myScene = new Scene(root, 700, 720);
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

    private void setApplicationIcon() {
        this.primaryStage.getIcons().add(new Image("res/my_Ghidra_application_icon.png"));
    }

    private BorderPane setUpRoot() {
        BorderPane rootNode = new BorderPane();

        rootNode.setLeft(setupLeftVBox());

        rootNode.setRight(setupRightVBox());

        rootNode.setCenter(setupSpaceView());

        rootNode.setBottom(setupBottomTextBox());

        return rootNode;
    }

    private Node createSpaceListView() {
        this.myListView = new ListView();

        myListView.setPrefWidth(125);
        myListView.setPrefHeight(300);

        for(int i = 0; i < theController.getChambersList().size(); i++) {
            myListView.getItems().add("Chamber " + (i+1));
        }

        myListView.setOnMouseClicked((MouseEvent event)->{
            System.out.println("clicked on " + myListView.getSelectionModel().getSelectedItem());
            this.textArea.setText(theController.getNewChamberDescription(myListView.getSelectionModel().getSelectedIndex()));
            updateDoorList();
        });

        return myListView;
    }

    private Node setupLeftVBox() {
        Label myLbl = new Label("Chamber/Passage Selection");

        VBox vBox = new VBox(myLbl, createSpaceListView(), createEditButton());
        vBox.setPadding(new Insets(10, 10, 10, 10));

        return vBox;
    }

    private Node createEditButton() {
        Button editButton = createButton("Edit", "-fx-background-color: #FFFFFF; ");

        editButton.setOnAction((ActionEvent event) -> {
            theController.reactToEditButton();
        });

        //editButton.setPadding(new Insets(10,5,5,10));

        return editButton;
    }


    private Node setupRightVBox() {

        Label myLbl = new Label("Select Door");
        setupDoorList();

        VBox vBox = new VBox(myLbl, myDoorsList); //empty combo box
        vBox.setPadding(new Insets(10, 10, 0, 10));

        return vBox;
    }

    private Node setupBottomTextBox() {
        this.textArea = new TextArea();

        //textArea.setPrefWidth(250);
        textArea.setPrefHeight(400);
        textArea.setWrapText(true);
        textArea.editableProperty().setValue(false);
        textArea.setPadding(new Insets(5,20,5,20));
        textArea.contextMenuProperty().unbind();
        textArea.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume);


        textArea.setText("N/A\n");

        return  textArea;
    }

    private void setupDoorList() {
        this.myDoorsList = new ComboBox();

        this.myDoorsList.setPrefWidth(150);
        this.myDoorsList.setVisibleRowCount(6);
        //this.myDoorsList.setValue("List of Doors");

    }

    private void updateDoorList() {
        this.myDoorsList.getItems().clear();
        //this.myDoorsList.setValue("List of Doors");

        System.out.println(myListView.getSelectionModel().getSelectedItem());
        if (myListView.getSelectionModel().getSelectedItem() != null) {
            for (int i = 0; i < theController.getChambersList().get(myListView.getSelectionModel().getSelectedIndex()).getDoors().size(); i++) {
                this.myDoorsList.getItems().add("Door " + i);
            }
        }

        this.myDoorsList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println(myDoorsList.getValue());
            }
        });

        //TODO action should only occur on dropDown items. Nulls?
        /*this.myDoorsList.getItems().add((options, oldValue, newValue) -> {
                    System.out.println(newValue);
                }
        );*/
    }

    private Node setupSpaceView() {
        GridPane myRoom = new ChamberView(4, 4);
        myRoom.setPadding(new Insets(20, 10, 10, 10));
        myRoom.setAlignment(Pos.TOP_CENTER);
        myRoom.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        return myRoom;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
