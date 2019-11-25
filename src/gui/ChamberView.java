package gui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class ChamberView extends GridPane {
    private String floor;
    private String treasure;
    private String door;


    public ChamberView(int len, int wid){
        floor = "/res/floor.png";
        treasure = "/res/pixel_chest.png";
        door = "/res/door.png";

        makeBasicFloor(len, wid);
    }

    public void makeBasicFloor(int x, int y) {

        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                this.add(floorFactory(floor), i, j);
            }
        }
    }

    public void addDoor(int x, int y) {
        this.add(floorFactory(door) ,x, y);
    }


    public Node floorFactory(String img) {
        Image floor = new Image(getClass().getResourceAsStream(img));
        Label toReturn = new Label();
        ImageView imageView = new ImageView(floor);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        toReturn.setGraphic(imageView);
        return toReturn;
    }


}
