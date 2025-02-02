package htwmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * This PlayerModel class consist of operations to control a player. It contains
 * operations to create a maze, move a player and shoot an arrow. The class
 * implements the Player interface
 * 
 */
public class PlayerModel implements Player {

  private int numberOfArrows;
  private String startPosition;
  private Maze maze;
  private String currentPosition;
  private Map<String, List> tunnelLeads;
  private List<String> rooms;
  private Set<String> bottomlessPits;
  private Set<String> batPositions;

  /**
   * Constructs a PlayerModel object given the maze information.
   * 
   * @param mazeType       the Maze type
   * @param rows           the number of rows
   * @param columns        the number of columns
   * @param remainingWalls the number of remaining walls
   * @param bats           the number of bats
   * @param pits           the number of pits
   * 
   * @throws IllegalArgumentException if an exception occurs
   */
  public PlayerModel(String mazeType, int rows, int columns, int remainingWalls, int bats, int pits,
      int arrows) throws IllegalArgumentException {
    createMaze(mazeType, rows, columns, remainingWalls, bats, pits, arrows);
  }

  /**
   * Constructs a PlayerModel object given the entire maze the number of arrows.
   * 
   * @param maze           the Maze
   * @param numberOfArrows the number of arrows
   * 
   */
  public PlayerModel(Maze maze, int numberOfArrows) {
    this.maze = maze;
    this.numberOfArrows = numberOfArrows;
    tunnelLeads = maze.getRoomDirections();
    rooms = maze.getAllRooms();
    bottomlessPits = maze.getPitPositions();
    batPositions = maze.getBatPositions();

  }

  @Override
  public void setArrows(int arrow) {
    this.numberOfArrows = arrow;
  }
  @Override
  public Maze getMaze() {
    return maze;
  }

  @Override
  public void setStartPosition(String startPosition) {
    this.startPosition = startPosition;
    this.currentPosition = startPosition;
    
    if (!rooms.contains(startPosition)) {
      throw new IllegalArgumentException("Invalid Starting Point");
    }
    
  }

  @Override
  public Integer getStartPosition() {
    Map<String, Integer> mv = maze.getMappedValues();
    return mv.get(startPosition);
  }

  @Override
  public List<String> getTunnelLeads() {
    return tunnelLeads.get(currentPosition);
  }

  /**
   * This method allows a player to construct a maze during initialization.
   * 
   */
  private void createMaze(String mazeType, int x, int y, int remainingWalls, int bats, int pits,
      int arrows) throws IllegalArgumentException {

    if (mazeType.equalsIgnoreCase("W")) {
      maze = new WrappedMaze(x, y, remainingWalls, bats, pits);
    } else if (mazeType.equalsIgnoreCase("U")) {
      maze = new UnWrappedMaze(x, y, remainingWalls, bats, pits);
    } else {
      throw new IllegalArgumentException("Invalid Command");
    }

    maze.createMaze();

    tunnelLeads = maze.getRoomDirections();
    rooms = maze.getAllRooms();
    bottomlessPits = maze.getPitPositions();
    batPositions = maze.getBatPositions();
    numberOfArrows = arrows;

  }

  @Override
  public String getCave(String caveDirection) {

    if (caveDirection.equalsIgnoreCase("N")) {
      caveDirection = "North";
    } else if (caveDirection.equalsIgnoreCase("S")) {
      caveDirection = "South";
    } else if (caveDirection.equalsIgnoreCase("E")) {
      caveDirection = "East";
    } else if (caveDirection.equalsIgnoreCase("W")) {
      caveDirection = "West";
    }

    Map<Integer, List> directionNumber = getNumber();
    Map<Integer, List> direction = getCardinalNumber();
    Map<String, Integer> mappedValues = maze.getMappedValues();

    List<String> possibleDirections = direction.get(mappedValues.get(currentPosition));
    int cave = -1;
    if (possibleDirections.contains(caveDirection)) {
      int index = possibleDirections.indexOf(caveDirection);
      List<Integer> possibleCaves = directionNumber.get(mappedValues.get(currentPosition));
      cave = possibleCaves.get(index);
    }

    String result = null;
    if (cave != -1) {
      for (Entry<String, Integer> entry : mappedValues.entrySet()) {
        if (Objects.equals(cave, entry.getValue())) {
          result = entry.getKey();
          break;
        }
      }
    }
    return result;

  }

  @Override
  public int shoot(String pos, int caves) {

    if (pos == null) {
      --numberOfArrows;
      if (numberOfArrows <= 0) {
        return -1;
      } else {
        return -99;
      }
    }

    String start = currentPosition;
    String next = pos;
    Map<String, List> paths = maze.getRoomDirections();
    Map<String, List> cardinalDirections = maze.getCardinalDirections();

    while (caves != 0) {
      List<String> validPositions = paths.get(next);
      List<String> validDirections = cardinalDirections.get(next);
      int positionIndex = validPositions.indexOf(start);
      String entryDirection = validDirections.get(positionIndex);
      String exitDirection = getOppositeDirection(entryDirection);

      start = next;
      validPositions = paths.get(start);
      validDirections = cardinalDirections.get(start);

      if (!validDirections.contains(exitDirection)) {
        --caves;
        break;
      }

      int directionIndex = validDirections.indexOf(exitDirection);
      next = validPositions.get(directionIndex);

      --caves;
    }

    --numberOfArrows;

    if (caves == 0 && start.equals(maze.getWumpusPosition())) {
      return 1;
    }
    if (numberOfArrows <= 0) {
      return -1;
    }

    return 0;

  }

  @Override
  public void moveDirection(String dir) {

    Map<String, List> path = maze.getCardinalDirections();
    List<String> directions = path.get(currentPosition);
    int index = directions.indexOf(dir);
    Map<String, List> paths = maze.getRoomDirections();
    List<String> validPositions = paths.get(currentPosition);
    String pos = validPositions.get(index);
    if (!tunnelLeads.get(currentPosition).contains(pos)) {
      throw new IllegalArgumentException("Invalid Direction!!");
    }

    // if (!batPositions.contains(pos)) {

    currentPosition = pos;
    // }

  }

  @Override
  public void move(String pos) {

    if (!tunnelLeads.get(currentPosition).contains(pos)) {
      throw new IllegalArgumentException("Invalid Direction!!");
    }

    Map<String, List> paths = maze.getRoomDirections();
    List<String> validPositions = paths.get(currentPosition);

    if (!validPositions.contains(pos)) {
      throw new IllegalArgumentException("Invalid Position");
    }

    if (!batPositions.contains(pos)) {

      currentPosition = pos;
    }

  }

  @Override
  public boolean hasFallen() {

    return bottomlessPits.contains(currentPosition);
  }

  @Override
  public boolean isEaten() {
    String wumpusPos = maze.getWumpusPosition();
    return wumpusPos.equals(currentPosition);
  }

  @Override
  public boolean isPitClose() {

    Set<String> pits = maze.getPitPositions();

    List<String> getMovements = tunnelLeads.get(currentPosition);

    for (String pos : getMovements) {
      if (pits.contains(pos)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean isWumpusClose() {

    String wumpus = maze.getWumpusPosition();
    List<String> getMovements = tunnelLeads.get(currentPosition);

    return getMovements.contains(wumpus);
  }

  @Override
  public Integer getCurrentPosition() {
    Map<String, Integer> mv = maze.getMappedValues();
    return mv.get(currentPosition);
  }

  @Override
  public List<String> getDirections(int pos) {

    Map<Integer, List> dirs = getCardinalNumber();
    return dirs.get(pos);

  }

  @Override
  public List<Integer> getAdjacentRooms(int pos) {

    Map<Integer, List> rooms = getNumber();
    return rooms.get(pos);

  }

  @Override
  public String getCurPosition() {
    return currentPosition;
  }

  @Override
  public int isCaughtByBat(String pos) {
    Random rand = new Random();
    if (batPositions.contains(pos)) {
      if (rand.nextInt(100) <= 50) {

        int random = new Random().nextInt(rooms.size() - 1);

        Iterator<String> it = rooms.iterator();
        int i = 0;
        while (it.hasNext()) {
          if (i == random) {
            currentPosition = it.next();
            break;
          }
          ++i;
          it.next();
        }

        return 1;
      } else {
        return 0;
      }
    }

    return -1;

  }

  @Override
  public List<String> getRooms() {
    return this.rooms;
  }

  /**
   * This method gets the opposite direction (the direction the arrow leaves a
   * cell), given the entry direction.
   * 
   * @param direction String
   * 
   * @return String
   */
  public String getOppositeDirection(String direction) {

    if (direction.equalsIgnoreCase("North")) {
      return "South";
    }

    else if (direction.equalsIgnoreCase("South")) {
      return "North";
    } else if (direction.equalsIgnoreCase("West")) {
      return "East";
    } else if (direction.equalsIgnoreCase("East")) {
      return "West";
    }

    return "";
  }

  @Override
  public List<Integer> getRoomNumbers() {

    List<Integer> result = new ArrayList<Integer>();

    Map<String, Integer> mv = maze.getMappedValues();

    for (String s : rooms) {

      if (mv.containsKey(s)) {
        result.add(mv.get(s));
      }
    }

    return result;
  }

  /**
   * This method gets all the rooms and the possible directions in numbers for use
   * by GUI.
   * 
   * 
   * @return Map containing rooms and possible directions.
   */
  public Map<Integer, List> getNumber() {

    Map<String, List> dir = maze.getRoomDirections();

    Map<String, Integer> mv = maze.getMappedValues();

    HashMap<Integer, List> mappedDirections = new HashMap<Integer, List>();

    Iterator<Entry<String, List>> itr = dir.entrySet().iterator();

    while (itr.hasNext()) {

      HashMap.Entry<String, List> entry = itr.next();
      List<Integer> mappedValues = new ArrayList<Integer>();
      String parent = entry.getKey();
      List<String> directions = entry.getValue();
      List<String> newPath = new ArrayList<String>();

      int key = mv.get(parent);

      for (String s : directions) {

        mappedValues.add(mv.get(s));

      }

      mappedDirections.put(key, mappedValues);

    }

    return mappedDirections;
  }

  @Override
  public Map<Integer, List> getCardinalNumber() {

    Map<String, List> dir = maze.getCardinalDirections();

    Map<String, Integer> mv = maze.getMappedValues();

    HashMap<Integer, List> mappedDirections = new HashMap<Integer, List>();

    Iterator<Entry<String, List>> itr = dir.entrySet().iterator();

    while (itr.hasNext()) {

      HashMap.Entry<String, List> entry = itr.next();
      List<Integer> mappedValues = new ArrayList<Integer>();
      String parent = entry.getKey();
      List<String> directions = entry.getValue();
      mappedDirections.put(mv.get(parent), directions);

    }

    return mappedDirections;
  }

  /**
   * This method gets all the tunnels in for of numbers on the maze for use by the
   * GUI.
   * 
   * 
   * @return Set of tunnels.
   */
  private Set<Integer> getTunnels() {

    Set<String> tunnels = maze.getCrookedTunnels();
    Set<Integer> result = new HashSet<Integer>();
    Map<String, Integer> mv = maze.getMappedValues();
    for (String s : tunnels) {
      result.add(mv.get(s));
    }

    return result;
  }

  @Override
  public List<String> getCardinalDirections() {
    Map<String, List> dir = maze.getCardinalDirections();
    return dir.get(currentPosition);
  }

  @Override
  public int getRows() {
    return maze.getRows();
  }

  @Override
  public int getColumns() {
    return maze.getColumns();
  }

  @Override
  public List<Integer> getListOfTunnels(int prevPosition, String direction) {
    List<Integer> result = new ArrayList<Integer>();
    Map<String, Integer> mv = maze.getMappedValues();

    Map<Integer, List> dir = getCardinalNumber();
    Map<Integer, List> d = getNumber();

    int currPosition = mv.get(currentPosition);

    int nextPosition = getNewPosition(prevPosition, direction);

    while (nextPosition != currPosition) {

      result.add(nextPosition);

      List<String> dirs = dir.get(nextPosition);

      for (String s : dirs) {
        if (!s.equals(getOppositeDirection(direction))) {
          direction = s;
          break;
        }
      }

      int index = dirs.indexOf(direction);
      List<Integer> nums = d.get(nextPosition);
      nextPosition = nums.get(index);

    }

    return result;

  }

  @Override
  public int getWumpusPosition() {
    Map<String, Integer> mv = maze.getMappedValues();
    return mv.get(maze.getWumpusPosition());

  }

  @Override
  public List<Integer> getPitsPosition() {
    Map<String, Integer> mv = maze.getMappedValues();
    List<Integer> pitPositions = new ArrayList<Integer>();
    for (String s : maze.getPitPositions()) {
      pitPositions.add(mv.get(s));
    }
    return pitPositions;

  }

  @Override
  public List<Integer> getBatsPosition() {
    Map<String, Integer> mv = maze.getMappedValues();
    List<Integer> batPositions = new ArrayList<Integer>();
    for (String s : maze.getBatPositions()) {
      batPositions.add(mv.get(s));
    }
    return batPositions;

  }

  /**
   * This method gets the immediate cell number a player can move to given the
   * position and direction.
   * 
   * 
   * @return Integer.
   */
  public int getNewPosition(int pos, String direction) {

    int x = maze.getRows();
    int y = maze.getColumns();

    if (direction.equals("North")) {
      if (pos >= 0 && pos < y) {
        return pos + (x - 1) * y;
      } else {
        return pos - y;
      }
    }
    if (direction.equals("South")) {
      if (pos >= (x - 1) * y) {
        return pos - (x - 1) * y;
      } else {
        return pos + y;
      }
    }
    if (direction.equals("East")) {
      for (int i = y - 1; i < x * y; i += y) {
        if (pos == i) {
          return pos - (y - 1);
        }
      }
      return pos + 1;
    }
    if (direction.equals("West")) {
      for (int i = 0; i < x * y; i += y) {
        if (pos == i) {
          return pos + (y - 1);
        }
      }
      return pos - 1;
    }

    return -1;
  }

  @Override
  public int getArrowNumber() {
    return numberOfArrows;
  }

}
