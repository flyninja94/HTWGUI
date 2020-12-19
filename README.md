# HUNT THE WUPUS
 Hunt The Wunpus
	Overview

The program designs and implements a controller that uses a refactored version of the room mazes from our previous assignment as a model to implement a text-based version and Graphical User Interface of Hunt The Wumpus game. It uses abstraction wherever applicable and minimizes code repetition.

	List Of Features

1) The program exposes all game settings through menus. These settings include maze size, number of 	walls, number of players (1 or 2), and difficulty (specified as number of superbats, pits, etc).

2) The program provides an option for restarting the game as the same game.

3) The program allows the maze to to be bigger than the area allocated to it on the screen providing 	the ability to scroll the view (10X10) maze.

4) The program allows the player to move through the maze using a mouse click in addition to the 	keyboard arrow keys. A click on an invalid space in the game would not advance the player.

5) The program provides an option for two-players where players take turns making moves or shooting 	arrows as they race to be the first to kill the Wumpus.

6) The GUI provides a clear indication of the results of each action a player takes as well as whose 	turn it is.

7) A player wins by slaying the Wumpus

8) A player loses by falling into a bottomless pit, being eaten by the Wumpus, or running 	out of arrows.

	How to Run

The jar file HWTGUI.jar can be found in res folder.
Use the following command to run the jar file for text-based version:

java -jar HTWGUI.jar --text

Use the following command to run the jar file for text-based version

java -jar HTWGUI.jar --gui

[Note: TO RUN THE GUI VERSION, PLACE THE jar FILE OUTSIDE THE res FOLDER AND RUN IT.]

	How to Use the Program
	For Text-Based Version
	
The user needs to add the configuration details of the maze initially

1) Enter the type of Maze when prompted by the program. (W for Wrapped and U for 	Unwrapped maze) 

2) Then enter the number of rows, columns and remaining walls respectively.Enter each 	number followed by a space. (ex : 3 3 3)

3) The program then prompts the user to add the number of bats, pits and arrows. Enter 	the details in the exact order. (ex : 3 4 3)

Once the configuration details are added, the user can proceed to play the game.

1) The user selects a starting position from a list of available rooms. Invalid position 	will throw an exception.

2) The user is then provided with a list of positions to move to. The user can either 	move to any of the position or shoot an arrow.(M/S)

3) If the user decides to Move, then User can choose from a list of possible positions to 	move to.

4) If the user decides to shoot an arrow, the  user should provide the number of caves 	the arrow should traverse. Then the user is prompted to enter the cave through which 	the arrow needs to be aimed.

5) The program ends if the user slays the Wumpus or if the user is terminated (by falling 	into a pit,running out of arrows or getting eaten by a wumpus).

	For GUI-Based Version

1) The user is first introduced to the game menu where the user can start the game, read the rules of 	the game and quit the game.

2) The user can select the maze type, player mode and change maze settings from the menu bar.

3) The player can use the arrow keys to navigate through the maze using the keyboard.

4) To navigate using the mouse, the player should click on a valid room to move to.

	Description Of Example Run

TextExample Runs

1) Example Run 1 - 

 	i) The example run shows the player being transported by a Super-Bat. The player also 		ducks the bat in multiple locations. The Super-Bat finally drops the player in a 		cave containing a pit.
 	
2) Example Run 2 - 

 	i) The example run shows the player losing by falling into a pit.

3) Example Run 3 - 

 	i) The example run shows the player being eaten by a Wumpus.	

4) Example Run 4 - 

 	i) The example run shows the player killing the Wumpus.	

GUI-Example Runs

1) The example 1 run shows the player losing by falling into a pit.

![image](https://user-images.githubusercontent.com/9563777/102681094-b7630180-41e4-11eb-9fcf-fb9893c2cbc0.png)

2) The example 2 run shows the player being eaten by a Wumpus. It also displays the scrollpane on the panel.

![image](https://user-images.githubusercontent.com/9563777/102681139-027d1480-41e5-11eb-8aec-6c7db02debe8.png)

3) The example 3 run shows the player killing the Wumpus.

![image](https://user-images.githubusercontent.com/9563777/102681146-17f23e80-41e5-11eb-8798-fe4f47cecbeb.png)

4) The example 4 run shows the grid in two player-mode.

![image](https://user-images.githubusercontent.com/9563777/102681156-27718780-41e5-11eb-8447-97ded8a2acbf.png)

	Assumptions

1) If a bat and a Wumpus are in the same location, then the bat picks up the player 50% of the time before the Wumpus can eat it.

2) If no inputs are given by the user then the program sets default values.

3) The number of bats or pits cannot be greater than the number of rooms.
