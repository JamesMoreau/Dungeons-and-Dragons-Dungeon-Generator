package gui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class ChamberView extends GridPane {
    private String floor;
    private String treasure;
    private int length;
    private int width;


    public ChamberView(int len, int wid){
        floor = "/res/floor.png";
        treasure = "/res/treasure.png";
        length = len;
        width = wid; //user these values to decide the size of the view and how many tiles


        Node[] tiles = makeTiles();
        //should definitely be a loop and possibly a method
        add(tiles[0], 0, 0, 1, 1);
        add(tiles[1], 0, 1, 1, 1);
        add(tiles[2], 0, 2, 1, 1);
        add(tiles[3], 0, 3, 1, 1);
        add(tiles[4], 1, 0, 1, 1);
        add(tiles[5], 1, 1, 1, 1);
        add(tiles[6], 1, 2, 1, 1);
        add(tiles[7], 1, 3, 1, 1);
        add(tiles[8], 2, 0, 1, 1);
        add(tiles[9], 2, 1, 1, 1);
        add(tiles[10], 2, 2, 1, 1);
        add(tiles[11], 2, 3, 1, 1);
        add(tiles[12], 3, 0, 1, 1);
        add(tiles[13], 3, 1, 1, 1);
        add(tiles[14], 3, 2, 1, 1);
        add(tiles[15], 3, 3, 1, 1);

    }


    private Node[] makeTiles() {  //should have a parameter and a loop

        Node[] toReturn = {
                floorFactory(floor),
                floorFactory(floor),
                floorFactory(floor),
                floorFactory(floor),
                floorFactory(floor),
                floorFactory(treasure),
                floorFactory(floor),
                floorFactory(floor),
                floorFactory(floor),
                floorFactory(floor),
                floorFactory(floor),
                floorFactory(floor),
                floorFactory(floor),
                floorFactory(floor),
                floorFactory(floor),
                floorFactory(floor)
        };
        return toReturn;
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
