# SOFTENG 206 Assignment 3

## How to run Quinzical:

1// extract the contents of SOFTENG206-ass4-team33.zip to an empty directory, name this directory "Quinzical".

  1.5// Ensure you have the NZ male festival voice installed.

2// Open a terminal in  the "Quinzical" directory.

  2.5// You may have to run the commands "chmod +x Quinzical.jar" and "chmod +x Quinzical.sh"

3// Run the command "./Quinzical.sh".

4// Play!

## Gameplay:

There are two current modules in this build of Quinzical. On the main menu you will be presented with the Practice Module and Games Module. 
Additionally there will be options to change some settings and exit the game.

### Settings

Currently you are able to change the playback speed for the text-to-speech synthesised voice by moving the slider. Its default value is 1x speed. You can always reset the value to default by clicking the reset button. An example speech will be read out at the latest set speed each time you move the slider. You can keep changing until you are satisfied.

### Practice Module

From this module you will be able to select any of the categories available on file. After selecting a category, a random clue will be displayed and read out to you.
You will have three attempts at answering this. After the 2nd attempt, if you have not got it then the first letter of any acceptable answers will be given as an extra hint.
You will either be congratulated for getting right if you manage to get it at any point, or you will be shown the correct answer.
Either way you will be prompted to return back to the main menu, from which you may have another attempt at the practice module.

### Games Module

This is the main game. When you select this module any save files from previous games will be attempted to be loaded. 
Five random categories will be selected, and for each category five random clues will be chosen. You can attempt to answer the lowest value clue that has not yet been attempted in each category.
You have one try so make sure you are certain of your answer. Correct answers will add to your winnings with the corresponding value that was displayed on the question board.
There is no punishment for wrong answers.
After attempting all possible questions, then you are able to reset the level.

### Adding Extra Clues/Categories

All our clue information is stored in text files in the Quinzical/categories directory. The name of each file is the name of the category of clues contained in that file. If you wish to add a clue to any of the current categories then edit the file by adding a new line with the following format:

This is the clue|prompt|Answer (e.g This person is a part of team 33 for Softeng206 2020|who is|Joel Hutchinson/Bill Song)

In this format the clue shown/read to the user comes first, followed by a "|" separator, then a prompt which is the leading part of the question (eg who is/what is), followed by another "|" separator, finally type out the answer. For the answer, if there are multiple possible options then separate them with a "/".

### Exiting

There is a dedicated back button from every screen that is able to return you to the main menu.
From the main menu you are able to select "Exit" in order to close the application. It is also possible to click the 'X' in the corner of the window to close it as well.

## P.S

As a final word from us at Team 33 Ltd. we hope that you enjoy our beta build of Quinzical, 
Mā te wā!
