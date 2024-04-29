
import java.io.*;
import java.util.ArrayList;

public class Forest {

    // Variables
    private String name; // The name of the forest
    private ArrayList<Tree> trees; // The list of trees in the forest


    public Forest(String name) {
        this.name = name;
        this.trees = new ArrayList<>();

    }// End of Forest constructor
    public boolean loadForest(String csvFilePath) {

        // Try-catch block that tries to read the CSV file
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;

            // Reads the tree data separated by commas from the CSV file
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Tree.Species species = convertToSpeciesEnum(data[0]);

                // Makes sure the tree species is valid
                if (species == null) {
                    System.out.println("Invalid species in the CSV file.");
                    return false;
                }

                // Converts the CSV data to Tree data
                int yearPlanted = Integer.parseInt(data[1]);
                double height = Double.parseDouble(data[2]);
                double growthRate = Double.parseDouble(data[3]);

                Tree tree = new Tree(species, yearPlanted, height, growthRate);
                trees.add(tree);
            }

            return true;

        } catch (IOException | NumberFormatException e) {
            return false;

        }// End of try-catch block

    }// End of loadForest method

    public boolean loadForestFromDB(String dbfileName) {

        // Try block that tries to read the db file
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(dbfileName))) {

            trees = (ArrayList<Tree>) inputStream.readObject();
            return true;

        } catch (IOException | ClassNotFoundException exception) {
            return false;

        }// End of try-catch block

    }// End of loadForestFromDB method

    private Tree.Species convertToSpeciesEnum(String speciesString) {

        // Converts Strings to Enums
        for (Tree.Species species : Tree.Species.values()) {

            if (species.toString().equalsIgnoreCase(speciesString)) {
                return species;
            }
        }
        return null; // If the species string does not match any enum constant

    }// End of convertToSpeciesEnum method

    public void printForest() {
        System.out.println("Forest name: " + name);

        int index;
        for (index = 0; index < trees.size(); index++) {
            System.out.println("     " + index + " " + trees.get(index));
        }
        System.out.println("There are " + trees.size() + " trees, with an average height of " + calculateAverageHeight());

    }// End of printForest method

    public void addRandomTree() {

        Tree tree = Tree.generateRandomTree();
        trees.add(tree);

    }// End of addRandomTree method

    public void cutDownTree(int index) {

        // Make sure tree number is valid
        if (index >= 0 && index < trees.size()) {
            trees.remove(index);

        } else {
            System.out.println("Tree number " + index + " does not exist");
        }

    }// End of cutDownTree method

    public void simulateYear() {

        for (Tree tree : trees) {
            tree.simulateYear();
        }


    }// End of simulateYear method

    public void reapForest(double height) {

        int index;
        for (index = 0; index < trees.size(); index++) {

            // Make sure the reaped trees are tall enough
            if (trees.get(index).getHeight() > height) {

                System.out.println("Reaping the tall tree " + trees.get(index));

                // Replace the reaped tree with a new tree
                Tree newTree = Tree.generateRandomTree();
                trees.set(index, newTree);
                System.out.println("Replaced with new tree " + newTree);
            }

        }// End of for loop

    }// End of reapForest method

    public void saveForest() {

        // Try block that tries to write the current forest to a db file
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(name + ".db"))) {

            outputStream.writeObject(trees);

        } catch (IOException exception) {

            System.out.println("Unable to save file - try again");;

        }// End of try-catch block

    }// End of saveForest method


    private double calculateAverageHeight() {

        // Get total height
        double totalHeight = 0;
        for (Tree tree : trees) {
            totalHeight += tree.getHeight();
        }

        // Divide total height by amount of trees
        double averageHeight = trees.isEmpty() ? 0 : totalHeight / trees.size();

        return Math.round(averageHeight * 100.0) / 100.0; // rounding to two decimal places

    }// End of calculateAverageHeight method

}// End of Forest class
