package ui;


import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

/*
 * begins a start menu in the console to allow a new game to be started
 * (credit to the TellerApp lecture lab for implementation of scanner system)
 */

public class StartMenu {

    private Scanner input;

    // EFFECTS: initializes a new StartMenu
    public StartMenu() {
        runStartMenu();
    }

    // MODIFIES: this
    // EFFECTS: runs the console start menu
    public void runStartMenu() {
        boolean keepGoing = true;
        String command;

        init();
        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase(Locale.ROOT);

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                try {
                    handleInput(command);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    // MODIFIES: this
    // EFFECTS: handles user keyboard input
    private void handleInput(String command) throws IOException, InterruptedException {
        if (command.equals("n")) {
            ConsoleGame newGame = new ConsoleGame();
            newGame.start();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes a new scanner
    private void init() {
        this.input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays options for user
    private void displayMenu() {
        System.out.println("\nWelcome to Tank Battles! Please select one of the following:");
        System.out.println("\n ");
        System.out.println("\tn -> begins a new game (press 't' at any time to save and quit)");
        System.out.println("\tl -> loads previous game");
        System.out.println("\tq -> quit");
    }


}
