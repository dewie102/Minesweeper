# JavaProject Team 5

Project Team
* Zack Romasz
* Genevieve Veres


Thank you for your interest in our game!

----------------
Important Notes
----------------
	
Depending on the size of your terminal and the size of the board you chose, it is possible that the board may not draw
correctly. If this happens, simply resize the terminal and then force the board to re-draw. You can do this by entering
any valid command, such as H to view the Help Menu followed by C to go back to the board. 

	
Your saved data includes statistics about your history playing the game - such as the total number of times you've played, 
how many times you've won, and the fastest you've completed each pre-set difficulty. Data is associated with the 
name that you enter on the welcome screen. To change players, exit the application, open it again, and enter the name of 
the new player. This information is saved in files found in the "data" directory. Prior to playing for the first time this
directory will be empty - but it is important that you do not delete the directory to ensure the game works properly. 

	
-----------
How To Play
-----------

In MineSweeper, your goal is to clear all the tiles that are NOT mines.
When you click on a tile, if it is not a bomb it will show the number of bombs in the surrounding 8 tiles.
If you click on a bomb, the game is over and you lose.

You can also "flag" tiles that you believe are bombs. This prevents you from clicking on them, and
helps keep track of how many bombs you have already found. Flags can be toggled - to remove a flag,
simply select the same tile again while using the flagging tool. The number of flags that you have placed
is tracked above the board - starting with a number of flags equal to the number of bombs on the board.
If the number goes negative, that indicates that you have a flag somewhere that is not actually a bomb.

The timer above the board indicates how long you have been playing the current board in seconds.
Try to beat your best time for each level!

--------------
Valid Actions
--------------
Enter the coordinates of the tiles you would like to click or flag in row-column order, with no spaces (e.g. B8)
Enter [S] to [S]wap from clicking mode to flagging mode, or vice versa.
Enter [H] to view this [H]elp menu.
Enter [X] to E[x]it the application.
Enter [P] to view your Player stats (in between games only)

---------
Board Key
---------
GRAY tiles are still covered

YELLOW tiles have been flagged

RED tiles are bombs

GREEN tiles have been uncovered and are NOT bombs