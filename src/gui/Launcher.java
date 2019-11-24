package gui;

public final class Launcher {

    /**
     * Empty constructor.
     */
    private Launcher() {

    }

    /**
     * Main launcher.
     *
     * @param args N/A takes in no arguments
     */

    public static void main(String[] args) {
        Launcher myLauncher = new Launcher();
        DungeonGui.main(args);
    }
}
