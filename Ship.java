import javax.swing.JButton;

public class Ship extends ShipLength{

    /**
     * create a new ship based on its length and what board its being placed on
     * 
     * @param JButton b[][]
     * @param int shipLength
     */
    Ship(JButton b[][], int shipLength) { 
        super(b, shipLength);
    }

}
