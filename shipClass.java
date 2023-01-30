/**
 * @author duamanan
 * @author GeldelaMohit
 */

import java.awt.*;

interface shipClass {
	

  /**
  * 
  *
  * Interface method that the subclass will have to overide to determine if a ship can be placed at a certain location
  *
  *
  * @param int rotate, the rotation of the ship being placed, vertical or horzintal
  * @param Color shipColour, the colour of the ship that needs to be placed based on whos turn it is
  * @param xCoord, the x location of the button
  * @param yCoord, the y location of the button
  * @return boolean, if the ship can be placed without any errors, or not
  *
  */
	public boolean shipHover(int rotate, Color shipColour, int xCoord, int yCoord);

	/**
  * 
  *
  * Interface method that the subclass will have to overide to place a ship at the desired location
  *
  *
  * @param int rotate, the rotation of the ship being placed, vertical or horzintal
  * @param Color shipColour, the colour of the ship that needs to be placed based on whos turn it is
  * @param int xCoord, the x location of the button
  * @param int yCoord, the y location of the button
  * @param int shipCount, the number of ships that have been placed
  * @return void
  *
  */
	public void confirmShip(int rotate, Color shipColour, int xCoord, int yCoord, int shipCount);
}
