N-puzzle solver A*
=======================
The N-puzzle solver is a program used to solve the N-puzzle problem using A* and Manhattan distance as heuristic.


The program consists of a Puzzle-class and a Node-class. After compiling, 
the program can be run by using java in the folloving way:
    
    java Puzzle <inputFile> <outputFile>


Example: 
    java Puzzle testPuzzle.txt solution.txt


## Classes

### Puzzle:
The Puzzle class includes the main method and is the main part of the 
program. It handles input/output, moving, solving and structuring the 
tree. The basic structure of the class consists of tree parts: 

1. reading input:
    getRoot(String filename)

2. finding/creating children: 
    generateChildren(Node node)
    moveLeft(Node node)
    moveRight(Node node)
    moveUp(Node node)
    moveDown(Node node)

3. solving the problem:
    solve(String output)

The cloneBoard-method is used as a helper-method to copy the 2D-board 
instead of creating a reference to the same board.

### Node:
The Node class is used to create the Node-objects, which in turn is 
used to represent each node in the search-tree. A Node-object contains 
the board, a reference to its parent, the board-size, its g-value, 
heuristic value, and the position of the free space "X". When a new 
Node is created, the constructor sets the board, size, X, and calculates 
the heuristic of that node.

The heuristic-function (calculateHeuristics) is using Manhattan-Distance 
to calculate the heuristics. I use modulo to calculate the goal 
x-position, and division to find the goal y-position of a given 
value. The rest of the class contains the getters and setters, and an 
override of the toString-method for easy debugging of the program.
