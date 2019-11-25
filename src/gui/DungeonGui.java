package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.ListView;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.Optional;

public class DungeonGui<toReturn> extends Application {

    /**
     * the file chooser pointer.
     */
    private FileChooser fileChooser;

    /**
     * the dungeon generator controller.
     */
    private Controller theController;

    /**
     * the root element of this gui.
     */
    private BorderPane root;

    /**
     * the stage used in the root.
     */
    private Stage primaryStage;

    /**
     * the bottom text area.
     */
    private TextArea textArea;

    /**
     * the list view of passages/chambers.
     */
    private ListView myListView;

    /**
     * the combo box containing the doors.
     */
    private ComboBox myDoorsList;

    /**
     * the offset for the list view (used when accessing passages).
     */
    private final int listViewOffset = 5;

    /**
     * path to the music.
     */
    private MediaPlayer mediaPlayer;

    /**
     * my media player.
     */
    private String musicPath;

    /**
     * grid pane used for visual representation.
     */
    private ChamberView myRoom;


    /**
     * starts the application.
     *
     * @param assignedStage the JavaFX stage to be used.
     */
    @Override
    public void start(Stage assignedStage) {
        this.theController = new Controller(this);

        this.fileChooser = setupFileChooser();

        setupMusic();

        primaryStage = assignedStage;
        primaryStage.setTitle("Dungeon Generator");

        this.root = setUpRoot();

        Scene myScene = new Scene(root, 750, 500);
        setApplicationIcon();

        primaryStage.setScene(myScene);
        primaryStage.show();
    }

    private void setupMusic() {
        this.musicPath = "res/C418.mp3";

        Media media = new Media(new File(musicPath).toURI().toString());

        this.mediaPlayer = new MediaPlayer(media);

        this.mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));

        mediaPlayer.play();
    }

    private FileChooser setupFileChooser() {
        FileChooser f = new FileChooser();
        return f;
    }

    /**
     * creates a button with basic style.
     *
     * @param text   the text inside the button
     * @param format the format of the button.
     * @return the button
     */
    private Button createButton(String text, String format) {
        Button btn = new Button();
        btn.setText(text);
        btn.setStyle("");
        return btn;
    }

    /**
     * sets the custom application icon.
     */
    private void setApplicationIcon() {
        this.primaryStage.getIcons().add(new Image("res/my_Ghidra_application_icon.png"));
    }


    /**
     * sets up the root for the generator's scene.
     *
     * @return the Border Pane
     */
    private BorderPane setUpRoot() {
        BorderPane rootNode = new BorderPane();

        rootNode.setLeft(setupLeftVBox());

        rootNode.setRight(setupRightVBox());

        rootNode.setCenter(setupSpaceView());

        rootNode.setBottom(setupBottomTextBox());

        return rootNode;
    }


    /**
     * creates the list view for spaces.
     *
     * @return the list view
     */
    private Node createSpaceListView() {
        this.myListView = new ListView();

        myListView.setPrefWidth(100);
        myListView.setPrefHeight(300);

        for (int i = 0; i < theController.getChambersList().size(); i++) {
            myListView.getItems().add("Chamber " + i);
        }

        for (int j = 0; j < theController.getPassageList().size(); j++) {
            myListView.getItems().add(("Passage " + j));
        }

        myListView.setOnMouseClicked((MouseEvent event) -> {
            if (myListView.getSelectionModel().getSelectedItem().toString().contains("Chamber")) {
                System.out.println("getting chamber from chamberList index " + (myListView.getSelectionModel().getSelectedIndex()));
                updateBottomTextChamber();

            } else if (myListView.getSelectionModel().getSelectedItem().toString().contains("Passage")) {
                System.out.println("getting passage from passageList index " + (myListView.getSelectionModel().getSelectedIndex() - listViewOffset));
                updateBottomTextPassage();

            } else {
                System.out.println("Bad selection.");
            }

            updateDoorList();
            updateSpaceView();
        });

        return myListView;
    }

    /**
     * updates the bottom text with new info based on a chamber.
     */
    public void updateBottomTextChamber() {
        this.textArea.setText(theController.getNewChamberDescription(myListView.getSelectionModel().getSelectedIndex()));
    }


    /**
     * updates the bottom text box with new info based on a passage.
     */
    public void updateBottomTextPassage() {
        this.textArea.setText(theController.getNewPassageDescription(myListView.getSelectionModel().getSelectedIndex() - listViewOffset));
    }


    /**
     * sets up the JavaFX vbox containing the listView.
     *
     * @return the vbox
     */
    private Node setupLeftVBox() {
        Label myLbl = new Label("Chamber/Passage Selection");

        VBox vBox = new VBox(myLbl, createSpaceListView(), createEditButton(), createFileChooserOpenButton(), createFileChooserSaveButton());
        vBox.setPadding(new Insets(10, 10, 10, 10));

        return vBox;
    }

    private Node createFileChooserOpenButton() {
        Button b = createButton("Open", "-fx-background-color: #FFFFFF; ");
        b.setPrefWidth(150);

        b.setOnAction((ActionEvent event) -> {
            this.fileChooser.setInitialDirectory(new File("Data"));
            this.fileChooser.setInitialFileName("myLevel.ser");

            File f = fileChooser.showOpenDialog(primaryStage);

            if (f != null) {
                System.out.println(f.getName());
                theController.loadLevel(f);
            } else {
                System.out.println("no file selected");
            }
        });

        return b;
    }

    private Node createFileChooserSaveButton() {
        Button b = createButton("Save", "-fx-background-color: #FFFFFF; ");
        b.setPrefWidth(150);

        b.setOnAction((ActionEvent event) -> {
            String saveName = "save.ser";

            this.fileChooser.setInitialFileName(saveName);

            File f = fileChooser.showSaveDialog(primaryStage);

            if (f != null) {
                System.out.println(f.getName());
                theController.saveLevel(f);
            } else {
                System.out.println("no file selected");
            }
        });

        return b;
    }

    /**
     * creates the edit button for spaces.
     *
     * @return the edit button
     */
    private Node createEditButton() {
        Button editButton = createButton("Edit", "-fx-background-color: #FFFFFF; ");
        editButton.setPrefWidth(150);

        editButton.setOnAction((ActionEvent event) -> {

            Alert editAlert = new Alert(Alert.AlertType.INFORMATION);
            editAlert.setTitle("Editor");
            editAlert.setHeaderText(null);

            ButtonType rM = new ButtonType("Monster (-)");
            ButtonType aM = new ButtonType("Monster (+)");

            ButtonType rT = new ButtonType("Treasure (-)");
            ButtonType aT = new ButtonType("Treasure (+)");

            theController.reactToEditButton();

            if (myListView.getSelectionModel().getSelectedIndex() != -1) {

                editAlert.getButtonTypes().addAll(rM, aM, rT, aT);

                if (myListView.getSelectionModel().getSelectedItem().toString().contains("Chamber")) {

                    editAlert.setContentText("Monster Count: " + theController.getChamberMonsters(this.myListView.getSelectionModel().getSelectedIndex()).size()
                            + "\n\n" + "Treasure Count: " + theController.getChamberTreasureList(this.myListView.getSelectionModel().getSelectedIndex()).size());

                    updateBottomTextChamber();

                } else if (myListView.getSelectionModel().getSelectedItem().toString().contains("Passage")) {

                    editAlert.setContentText("Monster Count: 0\n" + "Treasure Count: " + theController.getPassageTreasureList(this.myListView.getSelectionModel().getSelectedIndex() - listViewOffset).size());

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
            } else if (option.get() == aT) {
                this.reactToTreasureAddButton();
            } else if (option.get() == rT) {
                this.reactToTreasureRemoveButton();
            } else {
                System.out.println("Bad Input");
            }
        });

        return editButton;
    }

    /**
     * sets up the JavaFX vbox containing the door list comboBox.
     *
     * @return the vbox
     */
    private Node setupRightVBox() {

        Label myLbl = new Label("Select Door");
        setupDoorList();

        VBox vBox = new VBox(myLbl, myDoorsList); //empty combo box
        vBox.setPadding(new Insets(10, 10, 0, 10));

        return vBox;
    }


    /**
     * sets up the bottom space description box.
     *
     * @return the text area
     */
    private Node setupBottomTextBox() {
        this.textArea = new TextArea();

        //textArea.setPrefWidth(250);
        textArea.setPrefHeight(200);
        textArea.setWrapText(true);
        textArea.editableProperty().setValue(false);
        textArea.setPadding(new Insets(5, 20, 5, 20));
        textArea.contextMenuProperty().unbind();
        textArea.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume);


        textArea.setText("N/A\n");

        return textArea;
    }

    /**
     * sets up the JavaFX door list.
     */
    private void setupDoorList() {
        this.myDoorsList = new ComboBox();

        this.myDoorsList.setPrefWidth(150);
        this.myDoorsList.setVisibleRowCount(10);
        this.myDoorsList.setValue("List of Doors");

    }

    /**
     * updates the doors list based on what is selected in list view.
     */
    private void updateDoorList() {
        this.myDoorsList.getItems().clear();

        if (myListView.getSelectionModel().getSelectedItem().toString().contains("Chamber")) {
            for (int i = 0; i < theController.getChambersList().get(myListView.getSelectionModel().getSelectedIndex()).getDoors().size(); i++) {
                this.myDoorsList.getItems().add("Door " + i);
            }

        } else if (myListView.getSelectionModel().getSelectedItem().toString().contains("Passage")) {
            for (int j = 0; j < theController.getPassageList().get(myListView.getSelectionModel().getSelectedIndex() - listViewOffset).getDoors().size(); j++) {
                this.myDoorsList.getItems().add("Door " + j);
            }
        }

        this.myDoorsList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (myDoorsList.getSelectionModel().getSelectedIndex() != -1) {

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
                        System.out.println(theController.getPassageDoorDescription(myListView.getSelectionModel().getSelectedIndex() - listViewOffset, myDoorsList.getSelectionModel().getSelectedIndex()));
                        myDoorInfoAlert.setContentText(theController.getPassageDoorDescription(myListView.getSelectionModel().getSelectedIndex() - listViewOffset, myDoorsList.getSelectionModel().getSelectedIndex()));
                    }

                    myDoorInfoAlert.showAndWait();
                }
            }
        });

    }


    /**
     * sets up the dungeon/passage visual.
     *
     * @return the grid-pane visual
     */
    private Node setupSpaceView() {
        this.myRoom = new ChamberView(5, 5);

        myRoom.setPadding(new Insets(20, 10, 10, 10));
        myRoom.setAlignment(Pos.TOP_CENTER);
        myRoom.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        return myRoom;
    }

    private void updateSpaceView() {

        System.out.println("updating space view");
        String s;

        if (myListView.getSelectionModel().getSelectedItem().toString().contains("Chamber")) {
            System.out.println("showing a new chamber!");

            this.myRoom.getChildren().clear();

            this.myRoom.makeBasicFloor(5, 5);
            this.myRoom.addDoor(2, 4);

            /* Door placement decision tree */
            for (int i = 0; i < theController.getChambersList().get(myListView.getSelectionModel().getSelectedIndex()).getDoors().size(); i++) {
                s = theController.getChamberDoorDescription(myListView.getSelectionModel().getSelectedIndex(), i);

                if(s.contains("left")) {
                    myRoom.addDoor(0,2);
                } else if (s.contains("ahead")) {
                    myRoom.addDoor(2, 0);
                } else if (s.contains("right")) {
                    myRoom.addDoor(4, 2);
                }
            }

            /* Treasure Placement Decision Tree */
            if (theController.getChamberTreasureList(myListView.getSelectionModel().getSelectedIndex()).size() > 0) {
                this.myRoom.addTreasure(2, 2);
            }
            if (theController.getChamberTreasureList(myListView.getSelectionModel().getSelectedIndex()).size() > 1) {
                this.myRoom.addTreasure(3,3);
            }
            if (theController.getChamberTreasureList(myListView.getSelectionModel().getSelectedIndex()).size() > 2) {
                this.myRoom.addTreasure(3,1);
            }
            if (theController.getChamberTreasureList(myListView.getSelectionModel().getSelectedIndex()).size() > 3) {
                this.myRoom.addTreasure(1,1);
            }
            if (theController.getChamberTreasureList(myListView.getSelectionModel().getSelectedIndex()).size() > 4) {
                this.myRoom.addTreasure(1,3);
            }

            /* Monster Placement Decision Tree */
            if(theController.getChamberMonsters((myListView.getSelectionModel().getSelectedIndex())).size() > 0) {
                this.myRoom.addMonster(0,0);
            }
            if(theController.getChamberMonsters((myListView.getSelectionModel().getSelectedIndex())).size() > 1) {
                this.myRoom.addMonster(4,0);
            }
            if(theController.getChamberMonsters((myListView.getSelectionModel().getSelectedIndex())).size() > 2) {
                this.myRoom.addMonster(4,4);
            }
            if(theController.getChamberMonsters((myListView.getSelectionModel().getSelectedIndex())).size() > 3) {
                this.myRoom.addMonster(0,4);
            }



        } else if (myListView.getSelectionModel().getSelectedItem().toString().contains("Passage")) {
            System.out.println("showing a new passage!");

            this.myRoom.getChildren().clear();

            this.myRoom.makeBasicFloor(6, 3);
            this.myRoom.addDoor(0, 1);

        } else {
            System.out.println("Bad input");
        }
    }

    /**
     * reacts to monster remove button.
     */
    public void reactToMonsterRemoveButton() {

        if (myListView.getSelectionModel().getSelectedItem().toString().contains("Chamber")) {

            if (theController.getChamberMonsters(this.myListView.getSelectionModel().getSelectedIndex()).size() > 0) {

                theController.getChamberMonsters(this.myListView.getSelectionModel().getSelectedIndex()).remove(theController.getChamberMonsters(this.myListView.getSelectionModel().getSelectedIndex()).size() - 1);
                System.out.println("Removed a monster");
                this.updateBottomTextChamber();

            } else {
                System.out.println("Could not remove chamber monster");
            }


        } else if (myListView.getSelectionModel().getSelectedItem().toString().contains("Passage")) {

            if (false) {
                System.out.println("hi");
            } else {
                System.out.println("Could not remove passage monster");
            }

        } else {
            System.out.println("Bad input");
        }

        this.updateSpaceView();
    }

    /**
     * reacts to monster add button.
     */
    private void reactToMonsterAddButton() {

        if (myListView.getSelectionModel().getSelectedItem().toString().contains("Chamber")) {

            theController.addChamberMonster(this.myListView.getSelectionModel().getSelectedIndex());
            System.out.println("Added a monster");
            this.updateBottomTextChamber();

        } else if (myListView.getSelectionModel().getSelectedItem().toString().contains("Passage")) {

            System.out.println("adding monster to passages not implemented yet.");

        } else {
            System.out.println("Bad input");
        }

        this.updateSpaceView();
    }

    /**
     * reacts to treasure remove button.
     */
    private void reactToTreasureRemoveButton() {

        if (myListView.getSelectionModel().getSelectedItem().toString().contains("Chamber")) {

            if (theController.getChamberTreasureList(this.myListView.getSelectionModel().getSelectedIndex()).size() > 0) {

                theController.getChamberTreasureList(this.myListView.getSelectionModel().getSelectedIndex()).remove(theController.getChamberTreasureList(this.myListView.getSelectionModel().getSelectedIndex()).size() - 1);
                System.out.println("Removed a monster");
                this.updateBottomTextChamber();

            } else {
                System.out.println("Could not remove chamber treasure");
            }


        } else if (myListView.getSelectionModel().getSelectedItem().toString().contains("Passage")) {

            if (theController.getPassageTreasureList(this.myListView.getSelectionModel().getSelectedIndex() - listViewOffset).size() > 0) {

                theController.getPassageTreasureList(this.myListView.getSelectionModel().getSelectedIndex() - listViewOffset).remove(theController.getPassageTreasureList(this.myListView.getSelectionModel().getSelectedIndex() - listViewOffset).size() - 1);
                System.out.println("Removed a treasure");
                this.updateBottomTextPassage();

            } else {
                System.out.println("Could not remove passage monster");
            }

        } else {
            System.out.println("Bad input");
        }

        this.updateSpaceView();
    }

    /**
     * reacts to treasure add button.
     */
    private void reactToTreasureAddButton() {
        if (myListView.getSelectionModel().getSelectedItem().toString().contains("Chamber")) {

            theController.addChamberTreasure(this.myListView.getSelectionModel().getSelectedIndex());
            System.out.println("Added treasure");
            this.updateBottomTextChamber();

        } else if (myListView.getSelectionModel().getSelectedItem().toString().contains("Passage")) {

            theController.addPassageTreasure(this.myListView.getSelectionModel().getSelectedIndex() - listViewOffset);
            System.out.println("Added treasure");
            this.updateBottomTextPassage();

        } else {
            System.out.println("Bad input");
        }

        this.updateSpaceView();
    }

    /**
     * launches gui.
     *
     * @param args n/a
     */
    public static void main(String[] args) {
        launch(args);
    }
}
