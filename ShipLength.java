/**
 * @author duamanan
 * @author GeldelaMohit
 */

import javax.swing.JButton;

public class ShipLength extends Ship {

    /**
     * create a new ship based on its length and what board its being placed on
     * 
     * @param JButton b[][]
     * @param int shipLength
     */
    ShipLength(JButton b[][], int shipLength) { 
        super(b, shipLength);
    }

}