PuzzlesProject
==============
Alrighty, this will (hopefully) become a suite of Puzzles, where one can easily make their own puzzles, where one will
be able to solve puzzles, create puzzles to share with their friends, enemies, cat, dog, whatever. 

Puzzles Planned:
Akari: http://en.wikipedia.org/wiki/Light_Up_(puzzle)


Design:
I am currently using the Processing language to create the GUI of the project, as I am familiar with it, and its soooo much
easier to use than Java Guis. http://www.processing.org/
Also, I am currently using Java for the logic and backbone of the program. My plan will be to have:

A GUI written in Processing for each type of Puzzle, so the display can be edited to work with a specific kind of puzzle.
All of the visualization stuff is going to be located in each pde file, unless there is something that will simply must be
in Java proper.

These puzzles are currently all going to be based around a NxM grid of ints, which will correspond to different visuals
in each GUI. 
