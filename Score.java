/**
 * @author duamanan
 * @author GeldelaMohit
 */

import javax.swing.*;

//create score object
public class Score {

    //instance variables
    private JLabel scoresLabel;
	int userScore = 0;
	int computerScore = 0;
	
    /** 
    * Constructor that keeps track of the scores throughout the game
    * 
    * @param JLabel s, the score label
    */
	Score(JLabel s){
		this.scoresLabel = s;
	}

    /** 
    * When the user wins, update the score label
    *
    * @return void 
    */
    public void userWins() {
        userScore++; // increase the user's score
        scoresLabel.setText(userScore + " :Scores: " + computerScore);
    }

    /** 
    * When the computer wins, update the score label
    * 
    * @return void
    */
    public void computerWins() {
        computerScore++; //increase the computer's score
        scoresLabel.setText(userScore + " :Scores: " + computerScore);
    }
}