/**
 * @author duamanan
 * @author GeldelaMohit
 */

import javax.swing.*;
import java.awt.*;

public class ShipLength implements shipClass{


    //instance variables
    private JButton buttonArray[][];
    private int shipLength;
    

    /**
  * Constructor that creates a ship given its length and the board it will be placed on
  *
  * @param JButton button[][], array of buttons
  * @param int shipLength, length of the ship being placed
  */
    ShipLength(JButton button[][], int shipLength) {
        this.buttonArray = button;
        this.shipLength = shipLength;
    }


    /**
   * add the hover effect on the buttons depending on the length of the ship, also return whether the ship can be placed there or not
   *
   * @param int rotate, the rotation of the ship being placed, vertical or horzintal
   * @param Color shipColour, the colour of the ship that needs to be placed based on whos turn it is
   * @param xCoord, the x location of the button
   * @param yCoord, the y location of the button
   * @return boolean, if the ship can be placed without any errors, or not
   */
    public boolean shipHover (int rotate, Color shipColour, int xCoord, int yCoord) {
        if (rotate % 2 == 0) { //if the ship is being placed vertically
            int start = Math.max(0, yCoord - (rotate % 2 == 1 ? shipLength - 1 : 0)); // determine where to start and end changing the colors of th ebuttons based on if the ship is being placed vertically or horizontally
            int end = Math.min(buttonArray.length - 1, yCoord + (rotate % 2 == 0 ? shipLength - 1 : 0));
            boolean canPlace = true;
            if (yCoord > buttonArray.length - shipLength) { // if the ship is trying to be placed outside the board vertically, adjust the start and end variables
                start = buttonArray.length - 1;
                end = buttonArray.length - shipLength;
                int temp = end;
                end = start;
                start = temp;
            }
            for (int i = start; i <= end; i++) {
                if (!buttonArray[xCoord][i].getText().equals("")) { // if there is already a ship there, set canPlace to false
                    canPlace = false;
                }
            }
            if (canPlace && buttonArray[xCoord][start].getBackground() != Color.RED) { // if the ship can be placed, set it to the ship's colour (based on whos turn it is)
                for (int i = start; i <= end; i++) {
                    buttonArray[xCoord][i].setBackground(shipColour);
                }
                return true;
            } else {
                for (int i = start; i <= end; i++) { // if the ship can't be placed there set the ship's colour to red
                    buttonArray[xCoord][i].setBackground(Color.RED);
                }
                return false;
            }
        }
        else { //if the ship is being placed horizontally
            int start = Math.max(0, xCoord + (rotate % 2 == 1 ? shipLength - 1 : 0)); // determine where to start and end changing the colors of th ebuttons based on if the ship is being placed vertically or horizontally
            int end = Math.min(buttonArray.length - 1, xCoord + (rotate % 2 == 0 ? shipLength - 1 : 0));
            boolean canPlace = true;
            if (xCoord > buttonArray.length - shipLength) { // if the ship is trying to be placed outside the board horizontally, adjust the start and end variables
                start = buttonArray.length - 1;
                end = buttonArray.length - shipLength;
            }
            for (int i = start; i >= end; i--) {
                if (!buttonArray[i][yCoord].getText().equals("")) {  // if there is already a ship there, set canPlace to false
                    canPlace = false;
                }
            }
            if (canPlace && buttonArray[start][yCoord].getBackground() != Color.RED) { // if the ship can be placed, set it to the ship's colour (based on whos turn it is)
                for (int i = start; i >= end; i--) {
                    buttonArray[i][yCoord].setBackground(shipColour);
                }
                return true;
            } else {
                for (int i = start; i >= end; i--) { // if the ship can't be placed there set the ship's colour to red
                    buttonArray[i][yCoord].setBackground(Color.RED);
                }
                return false;
            }
        }
    }


    /**
   * if the ship can be placed(returned from shipHover method), place the ship depending on its length and rotation
   *
   * @param int rotate, the rotation of the ship being placed, vertical or horzintal
   * @param Color shipColour, the colour of the ship that needs to be placed based on whos turn it is
   * @param int xCoord, the x location of the button
   * @param int yCoord, the y location of the button
   * @param int shipCount, the number of ships that have been placed
   * @return void
   */
    public void confirmShip (int rotate, Color shipColour, int xCoord, int yCoord, int shipCount) {
        if (rotate % 2 == 0) { //if the ship is being placed vertically
            int start = Math.max(0, yCoord - (rotate % 2 == 1 ? shipLength - 1 : 0));// determine where to start and end changing the colors of th ebuttons based on if the ship is being placed vertically or horizontally
            int end = Math.min(buttonArray.length - 1, yCoord + (rotate % 2 == 0 ? shipLength - 1 : 0));
            if (yCoord > buttonArray.length - shipLength) { // if the ship is trying to be placed outside the board vertically, adjust the start and end variables
                start = buttonArray.length - 1;
                end = buttonArray.length - shipLength;
                int temp = end;
                end = start;
                start = temp;
            }
            for (int i = start; i <= end; i++) {
                buttonArray[xCoord][i].setBackground(shipColour); //confirm the ship's placement by changing the colour permenently and assigning the button with the shipCount value
                buttonArray[xCoord][i].setText(String.valueOf(shipCount));
            }
        }
        else { //if the ship is being placed horizontally
            int start = Math.max(0, xCoord + (rotate % 2 == 1 ? shipLength - 1 : 0));// determine where to start and end changing the colors of th ebuttons based on if the ship is being placed vertically or horizontally
            int end = Math.min(buttonArray.length - 1, xCoord + (rotate % 2 == 0 ? shipLength - 1 : 0));
            if (xCoord > buttonArray.length - shipLength) { // if the ship is trying to be placed outside the board horizontally, adjust the start and end variables
                start = buttonArray.length - 1;
                end = buttonArray.length - shipLength;
            }
            for (int i = start; i >= end; i--) {
                buttonArray[i][yCoord].setBackground(shipColour); //confirm the ship's placement by changing the colour permenently and assigning the button with the shipCount value
                buttonArray[i][yCoord].setText(String.valueOf(shipCount));
            }
        }
    }

}
