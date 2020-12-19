package htwcontroller;

import java.util.List;

/**
 * This interface represents a set of features that the program offers. Each
 * feature is exposed as a function in this interface. This function is used
 * suitably as a callback by the view, to pass control to the controller.
 *
 */
public interface Features {

  /**
   * This method generates the grid with the specified maze information.
   * 
   */
  void generateGrid();

  /**
   * This method takes inputs from the view and executes the specified commands.
   * 
   * @param actionInput Input
   * @param commands    List of commands to execute the action
   * 
   */
  void executeActionCommand(String actionInput, List<String> commands);

}
