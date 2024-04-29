import java.util.Scanner;
//=====================================================================================================================
public class Main {
//---------------------------------------------------------------------------------------------------------------------
    private static Scanner keyboard = new Scanner(System.in);
//---------------------------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("Welcome to the Forestry Simulation");
        System.out.println("----------------------------------");

        if (args.length == 0) {
            System.out.println("No forests provided.");
            return;
        }

        int forestIndex = 0;

        while (forestIndex < args.length) {

            // Opens the forest from the CSV file name in the command line
            String forestName = args[forestIndex];
            System.out.println("Initializing from " + forestName);
            Forest currentForest = new Forest(forestName);

            // Checks if the forest name loads a valid CSV file
            if (!currentForest.loadForest(args[forestIndex] + ".csv")) {
                System.out.println("Error opening/reading " + forestName + ".csv");
                forestIndex++;
                continue;

            }

            boolean exitLoop = false; // Flag to control exiting the menu loop

            // Do while loop that creates the menu system
            do {
                char choice;
                System.out.print("\n(P)rint, (A)dd, (C)ut, (G)row, (R)eap, (S)ave, (L)oad, (N)ext, e(X)it : ");
                choice = keyboard.next().charAt(0);
                keyboard.nextLine(); // consume newline

                switch (Character.toUpperCase(choice)) {

                    case 'P':
                        currentForest.printForest(); // Calls the printForest method
                        break;

                    case 'A':
                        currentForest.addRandomTree(); // Calls the addRandom tree method
                        break;

                    case 'C':
                        System.out.print("Tree number to cut down: ");
                        int index;

                        while (!keyboard.hasNextInt()) {
                            System.out.println("That is not an integer");
                            System.out.print("Tree number to cut down: ");
                            keyboard.next(); // consume non-integer input
                        }

                        index = keyboard.nextInt();
                        keyboard.nextLine(); // consume newline
                        currentForest.cutDownTree(index); // Calls the cutDownTree method
                        break;

                    case 'G':
                        currentForest.simulateYear(); // Calls the simulateYear method
                        break;

                    case 'R':
                        System.out.print("Height to reap from: ");
                        double height;

                        while (!keyboard.hasNextDouble()) {
                            System.out.println("That is not an integer");
                            System.out.print("Height to reap from: ");
                            keyboard.next(); // consume non-number input
                        }

                        height = keyboard.nextDouble();
                        keyboard.nextLine(); // consume newline
                        currentForest.reapForest(height); // Calls reapForest method
                        break;

                    case 'S':
                        currentForest.saveForest(); // Calls saveForest method
                        break;

                    case 'L':
                        System.out.print("Enter forest name: ");

                        Forest oldForest = currentForest; // Retains the old forest

                        String newForestName = keyboard.nextLine();
                        currentForest = new Forest(newForestName);

                        if (!currentForest.loadForestFromDB(newForestName + ".db")) {
                            // If loading from the database fails, print an error message
                            System.out.println("Error opening/reading " + newForestName + ".db");
                            System.out.println("Old forest retained");
                            currentForest = oldForest; // Reloads the old forest
                        }
                        break;

                    case 'N':
                        System.out.println("Moving to the next forest");
                        exitLoop = true; // Exit the menu loop to load the next forest
                        break;

                    case 'X':
                        System.out.println("\nExiting the Forestry Simulation");
                        return;

                    default:
                        System.out.println("Invalid menu option, try again");
                }

            } while (!exitLoop);

            forestIndex++; // Move to the next forest

        }// End of while loop

    }// End of main method

}// End of Forestry class
