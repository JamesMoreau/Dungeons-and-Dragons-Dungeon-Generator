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
import javafx.stage.StageStyle;
import jmorea.Passage;

import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;


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

        Scene myScene = new Scene(root, 650, 500);
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

        myListView.setPrefWidth(100);
        myListView.setPrefHeight(300);

        for(int i = 0; i < theController.getChambersList().size(); i++) {
            myListView.getItems().add("Chamber " + i);
        }

        for(int j = 0; j < theController.getPassageList().size(); j++) {
            myListView.getItems().add(("Passage " + j));
        }

        myListView.setOnMouseClicked((MouseEvent event)->{
                if(myListView.getSelectionModel().getSelectedItem().toString().contains("Chamber")) {
                    System.out.println("getting chamber from chamberList index " + (myListView.getSelectionModel().getSelectedIndex()));
                    updateBottomTextChamber();

                } else if (myListView.getSelectionModel().getSelectedItem().toString().contains("Passage")) {
                    System.out.println("getting passage from passageList index " + (myListView.getSelectionModel().getSelectedIndex() - 5));
                    updateBottomTextPassage();

                } else {
                    System.out.println("Bad selection.");
                }
                updateDoorList();
        });

        return myListView;
    }

    public void updateBottomTextChamber() {
        this.textArea.setText(theController.getNewChamberDescription(myListView.getSelectionModel().getSelectedIndex()));
    }

    public void updateBottomTextPassage() {
        this.textArea.setText(theController.getNewPassageDescription(myListView.getSelectionModel().getSelectedIndex() - 5));
    }

    private Node setupLeftVBox() {
        Label myLbl = new Label("Chamber/Passage Selection");

        VBox vBox = new VBox(myLbl, createSpaceListView(), createEditButton());
        vBox.setPadding(new Insets(10, 10, 10, 10));

        return vBox;
    }

    private Node createEditButton() {
        Button editButton = createButton("Edit", "-fx-background-color: #FFFFFF; ");
        editButton.setPrefWidth(150);

        editButton.setOnAction((ActionEvent event) -> {

            Alert editAlert = new Alert(Alert.AlertType.INFORMATION);
            editAlert.setTitle("Editor");
            editAlert.setHeaderText(null);

            ButtonType rM = new ButtonType("Monster (-)");
            ButtonType aM = new ButtonType("Monster (+)");

            theController.reactToEditButton();

            if (myListView.getSelectionModel().getSelectedIndex() != -1) {

                editAlert.getButtonTypes().addAll(rM, aM);


                if(myListView.getSelectionModel().getSelectedItem().toString().contains("Chamber")) {

                    editAlert.setContentText("Monster Count: " + theController.getChamberMonsters(this.myListView.getSelectionModel().getSelectedIndex()).size() +
                            "\n\n" + "Treasure Count: " + theController.getChamberTreasureList(this.myListView.getSelectionModel().getSelectedIndex()).size() );

                    updateBottomTextChamber();

                } else if (myListView.getSelectionModel().getSelectedItem().toString().contains("Passage")) {

                    editAlert.setContentText("editing passages not implement yet");

                    updateBottomTextPassage();

                }
            } else {
                editAlert.setContentText("No Space Selected!");

            }

            Optional<ButtonType> option = editAlert.showAndWait();

            if (option.get() == null) {
                System.out.println("No selection!");
            } else if (option.get() == aM) {
                this.reactToMonsterAddButton();
            } else if (option.get() == rM) {
                this.reactToMonsterRemoveButton();
            } else {
                System.out.println("Bad Input");
            }
        });

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
        textArea.setPrefHeight(200);
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
        this.myDoorsList.setVisibleRowCount(10);
        this.myDoorsList.setValue("List of Doors");

    }

    private void updateDoorList() {
        this.myDoorsList.getItems().clear();

        if(myListView.getSelectionModel().getSelectedItem().toString().contains("Chamber")) {
            for (int i = 0; i < theController.getChambersList().get(myListView.getSelectionModel().getSelectedIndex()).getDoors().size(); i++) {
                this.myDoorsList.getItems().add("Door " + i);
            }

        } else if (myListView.getSelectionModel().getSelectedItem().toString().contains("Passage")) {
            for (int j = 0; j < theController.getPassageList().get(myListView.getSelectionModel().getSelectedIndex() - 5).getDoors().size(); j++) {
                this.myDoorsList.getItems().add("Door " + j);
            }
        }

        this.myDoorsList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(myDoorsList.getSelectionModel().getSelectedIndex() != -1) {

                    Alert myDoorInfoAlert = new Alert(Alert.AlertType.INFORMATION);
                    myDoorInfoAlert.setTitle("Door Info");
                    myDoorInfoAlert.setHeaderText(null);


                    System.out.println("retrieving from listView Index " + myListView.getSelectionModel().getSelectedIndex());

                    if (myListView.getSelectionModel().getSelectedItem().toString().contains("Chamber")) {
                        System.out.println(myDoorsList.getValue());
                        System.out.println(theController.getChamberDoorDescription(myListView.getSelectionModel().getSelectedIndex(), myDoorsList.getSelectionModel().getSelectedIndex()));
                        myDoorInfoAlert.setContentText(theController.getChamberDoorDescription(myListView.getSelectionModel().getSelectedIndex(), myDoorsList.getSelectionModel().getSelectedIndex()));

                    } else if (myListView.getSelectionModel().getSelectedItem().toString().contains("Passage")) {
                        System.out.println(myDoorsList.getValue());
                        System.out.println(theController.getPassageDoorDescription(myListView.getSelectionModel().getSelectedIndex() - 5, myDoorsList.getSelectionModel().getSelectedIndex()));
                        myDoorInfoAlert.setContentText(theController.getPassageDoorDescription(myListView.getSelectionModel().getSelectedIndex() - 5, myDoorsList.getSelectionModel().getSelectedIndex()));
                    }

                    myDoorInfoAlert.showAndWait();
                }
            }
        });

    }

    private Node setupSpaceView() {
        GridPane myRoom = new ChamberView(5, 4);
        myRoom.setPadding(new Insets(20, 10, 10, 10));
        myRoom.setAlignment(Pos.TOP_CENTER);
        myRoom.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        return myRoom;
    }

    public void reactToMonsterRemoveButton() {

            if(myListView.getSelectionModel().getSelectedItem().toString().contains("Chamber")) {

                if (theController.getChamberMonsters(this.myListView.getSelectionModel().getSelectedIndex()).size() > 0) {

                    theController.getChamberMonsters(this.myListView.getSelectionModel().getSelectedIndex()).remove(theController.getChamberMonsters(this.myListView.getSelectionModel().getSelectedIndex()).size() - 1);
                    System.out.println("Removed a monster");
                    this.updateBottomTextChamber();

                } else {
                    System.out.println("Could not remove chamber monster");
                }


            } else if (myListView.getSelectionModel().getSelectedItem().toString().contains("Passage")) {

                if (false) {

                } else {
                    System.out.println("Could not remove passage monster");
                }

            } else {
                System.out.println("Bad input");
            }
    }

    private void reactToMonsterAddButton() {

        if(myListView.getSelectionModel().getSelectedItem().toString().contains("Chamber")) {

            theController.addChamberMonster(this.myListView.getSelectionModel().getSelectedIndex());
            System.out.println("Added a monster");
            this.updateBottomTextChamber();

        } else if (myListView.getSelectionModel().getSelectedItem().toString().contains("Passage")) {

            if (false) {

            } else {
                System.out.println("Could not add monster");
            }

        } else {
            System.out.println("Bad input");
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
