package gui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class ChamberView extends GridPane {

    /**
     * the path to the floor image.
     */
    private String floor;

    /**
     * the path to the treasure image.
     */
    private String treasure;

    /**
     * the path to the door image.
     */
    private String door;

    /**
     * the path to the monster.
     */
    private String monster;

    /**
     * constructor for chamber view. creates a default view.
     * @param len the length of tiles
     * @param wid the width of tiles
     */
    public ChamberView(int len, int wid) {
        floor = "/res/floor.png";
        treasure = "/res/pixel_chest.png";
        door = "/res/door.png";
        monster = "/res/monster.png";

        makeBasicFloor(len, wid);
    }

    /**
     * makes floor tiles to specified size.
     * @param x the length of tiles
     * @param y the width of tiles
     */
    public void makeBasicFloor(int x, int y) {

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                this.add(floorFactory(floor), i, j);
            }
        }
    }

    /**
     * adds a door to the specified coordinates.
     * @param x the horizontal coordinate
     * @param y the horizontal coordinate
     */
    public void addDoor(int x, int y) {
        this.add(floorFactory(door), x, y);
    }

    public void addTreasure(int x, int y) {
        this.add(floorFactory(treasure), x, y);
    }

    public void addMonster(int x, int y) {
        this.add(floorFactory(monster), x, y);
    }

    /**
     * produces a tile based on the image provided.
     * @param img the specified image
     * @return the tile
     */
    public Node floorFactory(String img) {
        Image f = new Image(getClass().getResourceAsStream(img));
        Label toReturn = new Label();
        ImageView imageView = new ImageView(f);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        toReturn.setGraphic(imageView);
        return toReturn;
    }
}
