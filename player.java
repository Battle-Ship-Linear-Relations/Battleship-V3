import javax.swing.*;

//Create player object to get values inputted by the user and computer
public class player {

    private JTextField coordinateField;
    private JTextField riseTextField;
    private JTextField runTextField;
    private JTextField yIntTextField;
    int xCoord;
    int yCoord;
    int rise, run, yInt;
    
    /**
    * Constructor that creates a new player to obtain the text fields 
    * 
    * @param JTextField c, the coordinate field
    * @param JTextField y, the yInt field
    * @param JTextField rise, the rise field
    * @param JTextField run, the run field
    */
    player(JTextField c,JTextField y, JTextField rise, JTextField run) {
        this.coordinateField = c;
        this.riseTextField = rise;
        this.runTextField = run;
        this.yIntTextField = y;
    }

    /**
    * get the coordinates from what the user/computer inputted
    *
    * @param none
    * @return int[], the x and y coordinates
    *
    */
    public int[] level1Values() {
        String parts[] = String.valueOf(coordinateField.getText()).split(","); // split the contents of the coordinate and 
        xCoord = Integer.valueOf(parts[0].trim().substring(1).trim());
        yCoord = Integer.valueOf(parts[1].trim().substring(0, parts[1].trim().length() - 1).trim()); // set the variables to the x and y values inputted
        coordinateField.setText("");
        return new int[] {xCoord, yCoord};
    }

    /**
    * get the slope and y-int from what the user/computer inputted
    *
    * @param none
    * @return int[], the rise, run and yInt
    *
    */
    public int[] level2Values() {
        if (yIntTextField.getText().equals("")) yIntTextField.setText("0"); // if a text field is empty, set the value to 0
        if (riseTextField.getText().equals("")) riseTextField.setText("0");
        if (runTextField.getText().equals("")) runTextField.setText("0");
        rise = Integer.valueOf(riseTextField.getText()); //set the integers to the values in the text fields
        run = Integer.valueOf(runTextField.getText());
        yInt = Integer.valueOf(yIntTextField.getText());
        int simplifyFraction = greatestCommonDenominator(rise, run); // find the gcd from the rise and run values
        rise /= simplifyFraction; //divide the integers with the gcd found
        run /= simplifyFraction;
        riseTextField.setText("");
        runTextField.setText("");
        yIntTextField.setText("");
        return new int[] {rise, run, yInt};
    }

    /**
     * find the greatest common denominator between 2 numbers to simplify a fraction
     * @param int numberator, the numerator of the fraction
     * @param int demoninator, the denominator of the fraction
     * @return int, the gcd between the 2 numbers
     */
    public int greatestCommonDenominator(int numerator, int denominator) {
        if (denominator == 0)
            return numerator;
        return greatestCommonDenominator(denominator, numerator % denominator); //recurssion to keep finding the gcd
         
    }
}