/* Fly.java
 * A program for display and movement of Mosca aka the player
 * Part of HW1
 */


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Contains several methods that aid in the<br>
 * display and movement of Mosca
 */
public class Fly
{
    protected static final String imgFile = "fly.png";
    
    protected GridLocation location;

    protected FlyWorld world;

    protected BufferedImage image;

    
    /**
     * Creates a new Fly object.<br>
     * The image file for a fly is fly.jpg<br>
     *
     * @param loc a GridLocation
     * @param fw the FlyWorld the fly is in
     */
    public Fly(GridLocation loc, FlyWorld fw)
    {
        location = loc;
        world = fw;

        try
        {
            image = ImageIO.read(new File(imgFile));
        }
        catch (IOException ioe)
        {
            System.out.println("Unable to read image file: " + imgFile);
            System.exit(0);
        }
        location.setFly(this);
    }

    /**
     * @return BufferedImage, the image of the fly
     */
    public BufferedImage getImage()
    {
    return image;
    }

    /**
     * @return GridLocation, the location of the fly
     */
    public GridLocation getLocation()
    {
    return location;
    }

    /**
     * @return boolean, always false, Mosca is not a predator
     */
    public boolean isPredator()
    {
    return false;
    }

    /**
    * Returns a string representation of this Fly showing
    * the location coordinates and the world.
    *
    * @return the string representation
    */
    public String toString(){
        String s = "Fly in world:  " + this.world + "  at location (" + this.location.getRow() + ", " + this.location.getCol() + ")";
        return s;
    }
    
    /**
     * This method <strong>updates</strong> the fly's location in<br>
     * the <strong>world</strong><br>
     * The fly can move in one of the four cardinal (N, S, E, W) directions.<br>
     * You need to make sure that the <strong>updated</strong> location<br>
     * is within the bounds of the world before<br>
     * changing the location of the fly<br>
     *
     * @param direction one of the four cardinal directions<br>
     *        Given as constants in FlyWorldGUI<br><br>
     *        They are:<br>
     *        FlyWorldGUI.NORTH<br>
     *        FlyWorldGUI.SOUTH<br>
     *        FlyWorldGUI.EAST<br>
     *        FlyWorldGUI.WEST<br>
     */
    public void update(int direction)
    {
    // FILL IN
    
    if (direction == FlyWorldGUI.NORTH) { //if move North, go up 1 row
		//checking validity of new location
		if (world.isValidLoc(this.location.getRow()-1, this.location.getCol()) == true) {
			
			//removing the fly from old location
			this.world.getLocation(this.location.getRow(), this.location.getCol()).removeFly();
			
			//creating the new location
			this.location = new GridLocation(this.location.getRow()-1, this.location.getCol());
			
			//setting the fly to new location
			this.world.getLocation(this.location.getRow(), this.location.getCol()).setFly(this);
			
		} else {
			System.out.println("Invalid move! Cannot move North.");
			
		}	
	} else if (direction == FlyWorldGUI.SOUTH) { //if move South, go down 1 row
		if (world.isValidLoc(((this.getLocation().getRow())+1), (this.getLocation().getCol())) == true) {
			
			this.world.getLocation(this.location.getRow(), this.location.getCol()).removeFly();
			
			this.location = new GridLocation(this.location.getRow()+1, this.location.getCol());
			
			this.world.getLocation(this.location.getRow(), this.location.getCol()).setFly(this);
			
		} else {
			System.out.println("Invalid move! Cannot move South.");
	
		}	
	} else if (direction == FlyWorldGUI.EAST) { // If move East, go right 1 column
		if (world.isValidLoc((this.getLocation().getRow()), ((this.getLocation().getCol())+1)) == true) {
			
			this.world.getLocation(this.location.getRow(), this.location.getCol()).removeFly();
			
			this.location = new GridLocation(this.location.getRow(), this.location.getCol()+1);
			
			this.world.getLocation(this.location.getRow(), this.location.getCol()).setFly(this);
			
			
		} else {
			System.out.println("Invalid move! Cannot move East");
			
		}	
	} else if (direction == FlyWorldGUI.WEST) { //if go West, go left 1 column
		if (world.isValidLoc((this.getLocation().getRow()), ((this.getLocation().getCol())-1)) == true) {
			
			this.world.getLocation(this.location.getRow(), this.location.getCol()).removeFly();
			
			this.location = new GridLocation(this.location.getRow(), this.location.getCol()-1);
			
			this.world.getLocation(this.location.getRow(), this.location.getCol()).setFly(this);
			
		} else {
			System.out.println("Invalid move! Cannot move West");
			
		}	
	} else {
		System.out.println("Invalid move! Can only move North, South, East, or West!");	
	}		
    }
}
