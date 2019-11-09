package jmorea;

//TODO: getDoors from a chamber and check if that ArrayList.contains(the door) for any door you are trying to get a target for.
 /**
 * Dungeon generator class manages algorithm.
 */
public final class Main {

    /**
    * Empty constructor.
    */
    private Main() {

    }

    /**
     * Main algorithm.
     *
     * @param args N/A takes in no arguments
     */
    public static void main(String[] args) {
        /* Main myMain = new Main(); */
        Level myLevel = new Level();
        StringBuilder finalDescription = new StringBuilder();
        Chamber tempChamber;
        Passage tempPassage;
        int p;
        int sum;

        /* Making 5 Chambers, each with at least 1 door */
        System.out.println("Making 5 Chambers");
        for (int i = 0; i < 5; i++) {
            do {
                tempChamber = new Chamber();
            } while (tempChamber.getDoors().size() == 0);

            myLevel.addChamber(tempChamber);
        }

        for (int i = 0; i < 5; i++) {
            System.out.println("\tChamber: " + myLevel.getSpaces().get(i).toString() + " Door count: " + myLevel.getAvailableDoorCount(i));
        }


        /* finding how many boring passages ill need */
        sum = 0;
        for (Space space : myLevel.getSpaces()) {
            sum += space.getDoors().size();
        }
        System.out.println("Total Doors is " + sum + "\nNumber of spaces is " + myLevel.getSpaces().size());


        /* Go through every door in every chamber and select a target(incrementing to the next) */
        for (int i = 0; i < 5; i++) {
            System.out.println("\nChamber" + i);
            for (int k = 0; myLevel.getAvailableDoorCount(i) > 0; k++) {

                /* if target is the chamber the door already belongs too, skip over */
                p = 0;
                if (k == i) {
                    p++;
                }

                /* Making door targets */
                System.out.println("Door " + k + " targeting chamber "  + (k + p));
                myLevel.selectChamberTarget(myLevel.getSpaces().get(i).getDoors().get(k), (Chamber) myLevel.getSpaces().get(k + p));
                /* myLevel.getSpaces().get(i).getDoors().get(k).addSpace(myLevel.getSpaces().get(k+p)); */

                /* Updating doors */
                myLevel.reduceAvailableDoorCount(i);
            }
        }

        myLevel.printLevel();

        /* Making boring passages that I can use to connect chambers */
        System.out.println("\nMaking boring Passages");
        for (int i = 0; i < sum; i++) {
            tempPassage = new Passage();
            tempPassage.makeBoring();
            myLevel.addPassage(tempPassage);
        }

        for (int i = 5; i < sum + 5; i++) {
            System.out.println("\tPassage: " + myLevel.getSpaces().get(i).toString() + " Door count: " + myLevel.getAvailableDoorCount(i));
        }


        /* Making Description */
        for (int i = 0; i < 5; i++) {
            finalDescription.append("\n").append(i + 1).append(":");
            finalDescription.append(myLevel.getSpaces().get(i).getDescription());
        }
        System.out.println(finalDescription.toString());
    }

}
