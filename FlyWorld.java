/* FlyWorld.java
 * A program for most of the game play work and information of the game world
 * Part of HW1
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.Color;
import java.util.Random;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;



/**
 * Contains information about the world (i.e., the grid of squares)<br>
 * and handles most of the game play work that is NOT GUI specific
 */
public class FlyWorld
{
    protected int numRows;
    protected int numCols;

    protected GridLocation [][] world;

    protected GridLocation start;
    protected GridLocation goal;
    
    protected Fly mosca;

	protected Frog afrog;
    protected Frog[] frogs;
 

    /**
     * Reads a file containing information about<br>
     * the grid setup.  Initializes the grid<br>
     * and other instance variables for use by<br>
     * FlyWorldGUI and other pieces of code.
     *
     *@param fileName the file containing the world grid information
     */
    public FlyWorld(String fileName){

		try {
			//To read the file using scanner
			File txtfile = new File(fileName);
			Scanner readFile = new Scanner(txtfile);
			
			//To get out the first line that shows number of rows and columns
			String firstLine = readFile.nextLine().trim();
			numRows = Integer.valueOf(firstLine.split(" ")[0]);
			numCols = Integer.valueOf(firstLine.split(" ")[1]);
			
			//To assign value to world - a 2d array list of GridLocation objects
			world = new GridLocation[numRows][numCols];
			for (int i=0; i < numRows; i++) {
				for (int j=0; j < numCols; j++) {
					world[i][j] = new GridLocation(i, j);
				}	
			}	
			
			String temp = "";
			int frogsCount = 0;
			//To read the rest of the data into a string
			while (readFile.hasNextLine()) {
				String data = readFile.nextLine().trim();
				temp+=data;
				
			}	
			readFile.close(); //to close the file after reading
			
			//to count the number of frogs
			for (int g=0; g < temp.length(); g++) {
				if (temp.substring(g,g+1).equals("f")) {
					frogsCount++;
				} 
			} 
				
			//To assign values to start, goal, and frogs
			start = new GridLocation(temp.indexOf("s")/numCols, temp.indexOf("s")%numCols);
			
			goal = new GridLocation(temp.indexOf("h")/numCols, temp.indexOf("h")%numCols);
			
			frogs = new Frog[frogsCount];
			
			int h = 0;	
			for (int k=0; k < temp.length(); k++) {
				
				if (temp.substring(k,k+1).equals("f")) {
					GridLocation a = new GridLocation(k/numCols, k%numCols);
					afrog = new Frog(a, this);
					frogs[h] = new Frog(afrog.getLocation(), this);
					world[frogs[h].getLocation().getRow()][frogs[h].getLocation().getCol()].setFrog(afrog);
					h++;
				}
			}
			
			
		} catch (FileNotFoundException e) {
			//If error, print out a warning
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		GridLocation flyLoc = new GridLocation(start.getRow(), start.getCol());
		//to assign value to mosca and set it
		mosca = new Fly(flyLoc, this);
		
		//if the location is the start, set background to green
		world[start.getRow()][start.getCol()].setBackgroundColor(Color.GREEN);
		world[start.getRow()][start.getCol()].setFly(mosca);
		
		//if location is the goal/home, set background to red
		world[goal.getRow()][goal.getCol()].setBackgroundColor(Color.RED);
		
        // The following print statements are just here to help you know 
        // if you've done part 1 correctly.  You can comment them out or 
        // delete them before you make your final submission
        //System.out.println("numRows: " + this.numRows + "   numCols: " + this.numCols);
        //System.out.println("start: " + this.start + "   goal: " + this.goal);
        //System.out.println("Mosca: " + this.mosca.toString());
           
    }

    /**
     * @return int, the number of rows in the world
     */
    public int getNumRows(){
        return numRows;
    }

    /**
     * @return int, the number of columns in the world
     */
    public int getNumCols(){
        return numCols;
    }

    /**
     * Deterimes if a specific row/column location is<br>
     * a valid location in the world (i.e., it is not out of bounds)
     *
     * @param r a row
     * @param c a column
     *
     * @return boolean
     */
    public boolean isValidLoc(int r, int c){
        // FILL IN
        //return true; // THIS LINE IS JUST SO CODE COMPILES
        if (r >= 0 && c >= 0 && r < numRows+1 && c < numCols+1) {
			return true;
		} else {
			return false;
		}
    }

    /**
     * Returns a specific location based on the given row and column
     *
     * @param r the row
     * @param c the column
     *
     * @return GridLocation
     */
    public GridLocation getLocation(int r, int c){
		if (this.isValidLoc(r,c)== true) {
			return world[r][c];
		} else {
			return null;
		}
    }

    /**
     * @return FlyWorldLocation, the location of the fly in the world
     */
    public GridLocation getFlyLocation(){
        return mosca.getLocation();
    }
    
    /**
     * Moves the fly in the given direction (if possible)
     * Checks if the fly got home or was eaten
     *
     * @param direction the direction, N,S,E,W to move
     *
     * @return int, determines the outcome of moving fly<br>
     *              there are three possibilities<br>
     *              1. fly is at home, return ATHOME (defined in FlyWorldGUI)<br>
     *              2. fly is eaten, return EATEN (defined in FlyWorldGUI)<br>
     *              3. fly not at home or eaten, return NOACTION (defined in FlyWorldGUI)
     */
    public int moveFly(int direction){
        // FILL IN
        //return FlyWorldGUI.NOACTION; // THIS LINE IS JUST SO CODE COMPILES
		mosca.update(direction);
		int result = 0;
		
		//System.out.println("fly:" + mosca.getLocation());
		
		if ((mosca.getLocation()).equals(goal)) { //if mosca got home
			
			result = FlyWorldGUI.ATHOME;
			
		} else {
			for (int m=0; m < frogs.length; m++) { //loop through all frogs to see if they ate fly
			
				if (frogs[m].eatsFly() == true) {
					result = FlyWorldGUI.EATEN;
					break;
				} else { //if a frog didn't eat the fly
					result = FlyWorldGUI.NOACTION;
				}		
			}	
		}
		return result;
    }

    /**
     * Moves all predators. After it moves a predator, checks if it eats fly
     *
     * @return boolean, return true if any predator eats fly, false otherwise
     */
	public boolean movePredators(){
        // FILL IN
        //return false; // THIS LINE IS JUST SO CODE COMPILES
        
        boolean x = false;
        for (int m=0; m < frogs.length; m++) { //go through all frogs to update each of them
			frogs[m].update();
			
			if (frogs[m].eatsFly() == true) { //check if any of them ate the fly
				
				x = true;
				break;
				
			} else {
				x = false;
			}	
		}
		return x;
    }
}
