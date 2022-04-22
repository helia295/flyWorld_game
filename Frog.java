/* Frog.java
 * A program for display, movement and capabilities of frogs
 * Part of HW1
 */


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Random;

/**
 * Handles display, movement, and fly eating capabalities for frogs
 */
public class Frog
{
    protected static final String imgFile = "frog.png";

    protected GridLocation location;

    protected FlyWorld world;

    protected BufferedImage image;

    /**
     * Creates a new Frog object.<br>
     * The image file for a frog is frog.jpg<br>
     *
     * @param loc a GridLocation
     * @param fw the FlyWorld the frog is in
     */
    public Frog(GridLocation loc, FlyWorld fw)
    {
    // FILL IN
    //to assign parameters' values to the instance variables to be able to work with these values later
    location = loc;
    world = fw;
    
    try
        { //reading the image of a frog
            image = ImageIO.read(new File(imgFile));
        }
        catch (IOException ioe)
        { //if unable to read the image, print out warning and exit
            System.out.println("Unable to read image file: " + imgFile);
            System.exit(0);
        }
        //set the frog at the location
        location.setFrog(this);
        
    
    }

    /**
     * @return BufferedImage the image of the frog
     */
    public BufferedImage getImage()
    {
    return image;
    }

    /**
     * @return GridLocation the location of the frog
     */
    public GridLocation getLocation()
    {
    return location;
    }

    /**
     * @return boolean, always true
     */
    public boolean isPredator()
    {
    return true;
    }

    /**
    * Returns a string representation of this Frog showing
    * the location coordinates and the world.
    *
    * @return the string representation
    */
    public String toString(){
        String s = "Frog in world:  " + this.world + "  at location (" + this.location.getRow() + ", " + this.location.getCol() + ")";
        return s;
    }

    /**
     * Generates a list of <strong>ALL</strong> possible legal moves<br>
     * for a frog.<br>
     * You should select all possible grid locations from<br>
     * the <strong>world</strong> based on the following restrictions<br>
     * Frogs can move one space in any of the four cardinal directions but<br>
     * 1. Can not move off the grid<br>
     * 2. Can not move onto a square that already has frog on it<br>
     * GridLocation has a method to help you determine if there is a frog<br>
     * on a location or not.<br>
     *
     * @return GridLocation[] a collection of legal grid locations from<br>
     * the <strong>world</strong> that the frog can move to
     */
    public GridLocation[] generateLegalMoves()
    {
        // FILL IN
        //return null; // THIS LINE IS JUST SO CODE COMPILES
        //maximum number of possible moves would be total number of squares on the grid minus total number of frogs
        //since they cannot step on each other
        int numFrogsmove = (this.world.numCols)*(this.world.numRows)-(this.world.frogs.length);
        
        //create an array to store the collection of possible moves
        GridLocation[] gridCollection = new GridLocation[numFrogsmove];
       
        int q =0;
        while (q < numFrogsmove) { //while loop to make sure the for loop doesn't go out of bound for the array
			for (int i=0; i < world.getNumRows(); i++) {
				for (int j=0; j < world.getNumCols(); j++) {
					
					if (world.getLocation(i, j).hasPredator() == false && world.isValidLoc(i, j) == true) {
						//checking the conditions for the moves to add the qualified ones to the array
						gridCollection[q] = new GridLocation(i, j); 
						q++;
				
					}
					if (q >= numFrogsmove) {
						break;
					}	
				}
			}
		}
		return (gridCollection);
    }

    /**
     * This method updates the frog's position.<br>
     * It should randomly select one of the legal locations(if there any)<br>
     * and set the frog's location to the chosen updated location.
     */
    public void update()
    {
        // FILL IN
        //total number of possible moves
        int numFrogsmove = (world.getNumCols())*(world.getNumRows())-(world.frogs.length);
        
        if (numFrogsmove > 0) { 
			
			GridLocation[] gridColl = this.generateLegalMoves();
			for (int k=0; k < gridColl.length; k++) {
				
			}	
			Random gen = new Random();
			int moveIdx = gen.nextInt(numFrogsmove);
			
			//initialize new with the new randomlized index from the array
			GridLocation move = new GridLocation(gridColl[moveIdx].getRow(), gridColl[moveIdx].getCol());
			
			//remove frog from old location
			this.world.getLocation(this.location.getRow(), this.location.getCol()).removeFrog();
			
			//create new location	
			this.location = new GridLocation(move.getRow(), move.getCol());
			
			//set the frog to the new location
			this.world.getLocation(this.location.getRow(), this.location.getCol()).setFrog(this);
			
		} else {
			System.out.println("A frog cannot move");
		}
    }

    /**
     * This method helps determine if a frog is in a location<br>
     * where it can eat a fly or not. A frog can eat the fly if it<br>
     * is on the same square as the fly or 1 spaces away in<br>
     * one of the cardinal directions
     *
     * @return boolean true if the fly can be eaten, false otherwise
     */ 
    public boolean eatsFly()
    {
        // FILL IN
        //return false; // THIS LINE IS JUST SO CODE COMPILES
        GridLocation above = new GridLocation(this.location.getRow()-1, this.location.getCol());
        GridLocation below = new GridLocation(this.location.getRow()+1, this.location.getCol());
        GridLocation left = new GridLocation(this.location.getRow(), this.location.getCol()-1);
        GridLocation right = new GridLocation(this.location.getRow(), this.location.getCol()+1);
        
        if (this.location.equals(world.getFlyLocation()) == true || above.equals(world.getFlyLocation()) == true
			|| below.equals(world.getFlyLocation()) == true || left.equals(world.getFlyLocation()) == true
			|| right.equals(world.getFlyLocation()) == true) {
			return true;
		} else {
			return false;
		}
    }   
}
