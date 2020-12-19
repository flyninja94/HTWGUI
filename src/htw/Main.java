package htw;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import htwcontroller.HtwController;
import htwmodel.Player;
import htwmodel.PlayerModel;
import htwview.IntroView;

/**
 * This is a driver class. It consist of a method that takes the maze
 * configuration details from the user and creates the model object. It then
 * passes the model to the controller object. It also takes in command line
 * arguments for text-based and graphical user interface.
 * 
 */
public class Main {

  /**
   * This represents the main method of the driver class. Based on the command
   * line argument, the method prompts the user to enter the configuration details
   * of the maze, creates a model object and assigns control to the controller
   * class or creates a interactive GUI for the game.
   */
  public static void main(String[] args) {

    if (args[0].equalsIgnoreCase("--text")) {

      Scanner myObj = new Scanner(System.in);

      System.out.println("Enter Maze configuration");
      System.out.println("Wrapped or Unwrapped(W/U)");
      String mazeType = myObj.nextLine();
      System.out.println("Enter rows,columns and number of remaining walls of the Maze");
      int rows = myObj.nextInt();
      int columns = myObj.nextInt();
      int remainingColumns = myObj.nextInt();

      System.out.println("Enter number of bats, pits and arrows");

      int bats = myObj.nextInt();
      int pits = myObj.nextInt();
      int arrows = myObj.nextInt();

      Player p = new PlayerModel(mazeType, rows, columns, remainingColumns, bats, pits, arrows);

      HtwController ic = new HtwController(new InputStreamReader(System.in), System.out);
      try {
        ic.start(p);
      } catch (IOException e) {
        e.printStackTrace();
      }

    }

    else if (args[0].equalsIgnoreCase("--gui")) {

      HtwController c = new HtwController(new InputStreamReader(System.in), System.out);

      IntroView view = new IntroView(c);
    }

    else {
      System.out.println("Invalid Command!!!");
      System.exit(0);
    }

  }

}
