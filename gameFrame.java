import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

/**
   * new class that implements the JFrame and creates and adds all components of the game
*/

public class gameFrame extends JFrame{ //extends JFrame so this class can just be treated as the JFrame
    ImageIcon logo = new ImageIcon(new ImageIcon("logo.png").getImage().getScaledInstance(350, 100, Image.SCALE_SMOOTH)); // scale the image to be the right size
    ImageIcon background = new ImageIcon("Background.jpg");
    ImageIcon gridImage = new ImageIcon(new ImageIcon("grid.png").getImage().getScaledInstance(555, 558, Image.SCALE_SMOOTH)); // scale the image to be the right size
    JButton userBoard[][] = new JButton[11][11]; //size of the game
    JButton computerBoard[][] = new JButton[userBoard.length][userBoard[0].length];
    JLabel inequalityCheck[][] = new JLabel[userBoard.length][userBoard[0].length];
    JLabel logoLabel = new JLabel(logo);
    JLabel equationLabel = new JLabel("y = — x + o");
    JTextField riseTextField = new JTextField();
    JTextField runTextField = new JTextField();
    JTextField yIntTextField = new JTextField();
    JLabel errorLabel = new JLabel("Error, please try again");
    String[] ships = {"Battleship", "Destroyer", "Submarine", "Battleship", "Destroyer", "Submarine"}; //names of all the ships that can be placed
    String[] equations = {"y < — x + o", "y > — x + o", "y = — x + o", "y ≤ — x + o", "y ≥ — x + o"};
    JLabel shipType = new JLabel("Ship: " + ships[0]); // displays the ship type
    JLabel grid = new JLabel(gridImage);
    JLabel grid2 = new JLabel(gridImage);
    JLabel turnJLabel = new JLabel("Your Turn");
    JLabel rotateLabel = new JLabel("Press 'r' to rotate");
    JLabel coordinateLabel = new JLabel("Coordinate:");
    JLabel computerBoardLabel = new JLabel("Computer's baord:");
    JLabel userBoardLabel = new JLabel("Your board:");
    JLabel userGuessedLabel = new JLabel("Your guesses:");
    JLabel computerGuessedLabel = new JLabel("Computer's guesses:");
    JTextField coordinateField = new JTextField();
    JTextArea computerGuessedArea = new JTextArea();
    JScrollPane computerGuessedScroller = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    JTextArea userGuessedArea = new JTextArea();
    JScrollPane userGuessedScroller = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    JLabel helpLabel = new JLabel();
    JButton resetButton = new JButton("Play Again");
    JLabel difficultyLabel = new JLabel("Select a difficulty");
    JLabel scoresLabel = new JLabel("0 :Scores: 0");
    JButton level1Button = new JButton("1"); // buttons for the difficulty of the game
    JButton level2Button = new JButton("2");
    JButton level3Button = new JButton("3");
    JButton lessThanButton = new JButton("<");
    JButton greaterThanButton = new JButton(">");
    JButton equalButton = new JButton("=");
    JButton lessThanEqualButton = new JButton("≤");
    JButton greaterThanEqualButton = new JButton("≥");
    JButton okButton = new JButton("OK");
    JLabel instructions = new JLabel("<html>Instructions<br/><br/>- You will be asked to choose a difficulty<br/>- 1 -> coordinate version<br/>- 2 -> slope version<br/>- 3 -> inequality version<br/>- The grid increments up by 1 (1 box = 1 unit)<br/>- First, you must place your ships on your own board, ships can be rotated by pressing 'r'<br/>- Then, the computer will place its ships on its board, you will not be able to see the computer's ships<br/>- You will then input a value depending on the difficulty<br/>- Guesses on the board will be shown as black X's and hits will be shown as red X's<br/>- If a ship has sunk on either side, it will indicate at the bottom of the screen<br/>- Once a player has sunk all the other player's ships, they win the game!<html/>");
    ArrayList<String> userGuesses = new ArrayList<String>();
    ArrayList<String> computerGuesses = new ArrayList<String>();
    int equationTurn = 0;
    int rotate = 0;
    int rise, run, yInt;
    int shipCount;
    int shipLength = 4;
    int playerTurn = 0;
    int difficulty = 0;
    int mouseX, mouseY;
    Color shipColour = Color.GREEN;
    java.util.Timer timer = new java.util.Timer();
    boolean shipCheck1 = true, shipCheck2 = true, shipCheck3 = true;
    boolean p2ShipCheck1 = true, p2ShipCheck2 = true, p2ShipCheck3 = true;
    int counter1 = 0, counter2 = 0, counter3 = 0; // these count the number of ships on the board after every turn
    int p2Counter1 = 0, p2Counter2 = 0, p2Counter3 = 0;
    Score score;
    File userFile = new File("userHistory.txt");
    File computerFile = new File("computerHistory.txt");
    FileWriter userHistory = null; //text files that hold the history of both players including the board of each player
    FileWriter computerHistory = null;    
    Ship ship = new Ship(userBoard, shipLength); //create a ship
    /**
     * parameter initializes everything in the game needed
     */
    gameFrame() { // initialize everything that is going to be on the GUI

        setVisible(true); 
        setSize(1300, 750); // set the bounds of the instructions
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        getContentPane().setLayout(null);
        setTitle("Instructions");

        instructions.setFont(new Font("Verdana", Font.BOLD, 25)); // the frame shows these instructions before the game starts
        instructions.setBounds(20, 0, 1250, 600);
        instructions.setForeground(Color.WHITE);
        instructions.setVisible(true);
        add(instructions);

        okButton.setFont(new Font("Verdana", Font.BOLD, 25)); // button to userBoard again after someone has won the game
        okButton.setBounds(550, 570, 200, 75);
        okButton.setForeground(Color.BLACK);
        okButton.setBackground(Color.WHITE);
        okButton.setVisible(true);
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { // once ok button is pressed, start the actual game
                setFocusable(true);
                setSize(1550, 1000); // set the bounds of the game
                setTitle("Battleship");
                score = new Score(scoresLabel);
                setVisible(true);
                setResizable(false);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                okButton.setVisible(false);
                instructions.setVisible(false);

                logoLabel.setBounds(600, 0, 350, 100);
                setContentPane(new JLabel(background)); // set the background of the game
                add(logoLabel);        

                resetButton.setFont(new Font("Verdana", Font.BOLD, 25)); // button to userBoard again after someone has won the game
                resetButton.setBounds(670, 800, 200, 75);
                resetButton.setForeground(Color.BLACK);
                resetButton.setFocusable(false);
                resetButton.setBackground(Color.WHITE);
                resetButton.setVisible(false);
                resetButton.addActionListener(f -> playAgain()); // if reset button is pressed
                add(resetButton);

                scoresLabel.setFont(new Font("Verdana", Font.BOLD, 50)); // button to userBoard again after someone has won the game
                scoresLabel.setBounds(610, 880, 400, 75);
                scoresLabel.setForeground(Color.WHITE);
                add(scoresLabel);
                

                grid.setBounds(28, 98, 555, 558); // the grid image in from of the buttons
                grid.setVisible(true);
                add(grid);

                grid2.setBounds(978, 98, 555, 558); // the grid image in from of the buttons
                grid2.setVisible(true);
                add(grid2);
                
                computerBoardLabel.setFont(new Font("Verdana", Font.BOLD, 30)); // the label if there is an error with something the user inputted (out of range or not peoper coordinate notation)
                computerBoardLabel.setBounds(1100, 10, 400, 150);
                computerBoardLabel.setForeground(Color.BLACK);
                add(computerBoardLabel);

                userBoardLabel.setFont(new Font("Verdana", Font.BOLD, 30)); // the label if there is an error with something the user inputted (out of range or not peoper coordinate notation)
                userBoardLabel.setBounds(210, 10, 400, 150);
                userBoardLabel.setForeground(Color.BLACK);
                add(userBoardLabel);

                errorLabel.setFont(new Font("Verdana", Font.BOLD, 30)); // the label if there is an error with something the user inputted (out of range or not peoper coordinate notation)
                errorLabel.setBounds(575, 100, 400, 150);
                errorLabel.setForeground(Color.RED);
                errorLabel.setVisible(false);
                add(errorLabel);

                rotateLabel.setFont(new Font("Verdana", Font.BOLD, 30)); // instructions displaying you can rotate the ships with 'r'
                rotateLabel.setBounds(620, 100, 400, 40);
                rotateLabel.setForeground(Color.BLACK);
                rotateLabel.setVisible(false);
                add(rotateLabel);

                difficultyLabel.setFont(new Font("Verdana", Font.BOLD, 30)); // label asking the user what difficulty they want
                difficultyLabel.setBounds(630, 250, 300, 150);
                difficultyLabel.setForeground(Color.WHITE);
                add(difficultyLabel);
                
                lessThanButton.setFont(new Font("Verdana", Font.BOLD, 18)); // less than button for level 3
                lessThanButton.setBounds(620, 200, 50, 50);
                lessThanButton.setForeground(Color.BLACK);
                lessThanButton.setBackground(Color.WHITE);
                lessThanButton.setFocusable(false);
                lessThanButton.setVisible(false);
                lessThanButton.setEnabled(false);
                lessThanButton.addActionListener(f -> equationLabel.setText("y < — x + o"));
                add(lessThanButton);

                greaterThanButton.setFont(new Font("Verdana", Font.BOLD, 18)); // greater than button for level 3
                greaterThanButton.setBounds(680, 200, 50, 50);
                greaterThanButton.setForeground(Color.BLACK);
                greaterThanButton.setBackground(Color.WHITE);
                greaterThanButton.setFocusable(false);
                greaterThanButton.setVisible(false);
                greaterThanButton.setEnabled(false);
                greaterThanButton.addActionListener(f -> equationLabel.setText("y > — x + o"));
                add(greaterThanButton);

                equalButton.setFont(new Font("Verdana", Font.BOLD, 18)); // equal button for level 3
                equalButton.setBounds(740, 200, 50, 50);
                equalButton.setForeground(Color.BLACK);
                equalButton.setBackground(Color.WHITE);
                equalButton.setFocusable(false);
                equalButton.setVisible(false);
                equalButton.setEnabled(false);
                equalButton.addActionListener(f -> equationLabel.setText("y = — x + o"));
                add(equalButton);

                lessThanEqualButton.setFont(new Font("Verdana", Font.BOLD, 18)); // less than or equal to button for level 3
                lessThanEqualButton.setBounds(800, 200, 50, 50);
                lessThanEqualButton.setForeground(Color.BLACK);
                lessThanEqualButton.setBackground(Color.WHITE);
                lessThanEqualButton.setFocusable(false);
                lessThanEqualButton.setVisible(false);
                lessThanEqualButton.setEnabled(false);
                lessThanEqualButton.addActionListener(f -> equationLabel.setText("y ≤ — x + o"));
                add(lessThanEqualButton);

                greaterThanEqualButton.setFont(new Font("Verdana", Font.BOLD, 18)); // greater than or equal to button for level 3
                greaterThanEqualButton.setBounds(860, 200, 50, 50);
                greaterThanEqualButton.setForeground(Color.BLACK);
                greaterThanEqualButton.setBackground(Color.WHITE);
                greaterThanEqualButton.setFocusable(false);
                greaterThanEqualButton.setVisible(false);
                greaterThanEqualButton.setEnabled(false);
                greaterThanEqualButton.addActionListener(f -> equationLabel.setText("y ≥ — x + o"));
                add(greaterThanEqualButton);

                turnJLabel.setFont(new Font("Verdana", Font.BOLD, 40)); // label telling you whos turn it is
                turnJLabel.setBounds(640, 450, 300, 150);
                turnJLabel.setForeground(Color.WHITE);
                turnJLabel.setVisible(false);
                add(turnJLabel);

                shipType.setBounds(625, 650, 300, 50); // tells you what kind of ship you are placing depending on size
                shipType.setFont(new Font("Verdana", Font.BOLD, 30));
                shipType.setForeground(Color.WHITE);
                shipType.setVisible(false);
                add(shipType);

                computerGuessedLabel.setFont(new Font("Verdana", Font.BOLD, 30));  // the label for the things the computer guesses
                computerGuessedLabel.setBounds(130, 600, 400, 150);
                computerGuessedLabel.setForeground(Color.WHITE);
                add(computerGuessedLabel);

                computerGuessedArea.setBounds(125, 700, 350, 200); // the text area for the things the computer guesses
                computerGuessedArea.setFont(new Font("Dialog", Font.PLAIN, 20));
                computerGuessedArea.setEditable(false);
                computerGuessedScroller.setBounds(125, 700, 350, 200);
                computerGuessedScroller.setViewportView(computerGuessedArea);
                add(computerGuessedScroller);

                userGuessedLabel.setFont(new Font("Verdana", Font.BOLD, 30));  // the label for the things the user guesses
                userGuessedLabel.setBounds(1125, 600, 400, 150);
                userGuessedLabel.setForeground(Color.WHITE);
                add(userGuessedLabel);

                userGuessedArea.setBounds(1075, 700, 350, 200); // the text area for the things the user guesses
                userGuessedArea.setFont(new Font("Dialog", Font.PLAIN, 20));
                userGuessedArea.setEditable(false);
                userGuessedScroller.setBounds(1075, 700, 350, 200);
                userGuessedScroller.setViewportView(userGuessedArea);
                add(userGuessedScroller);

                coordinateField.setBounds(650, 270, 200, 75); // Text field for the coordinate to be inputted
                coordinateField.setFont(new Font("Verdana", Font.BOLD, 40));
                coordinateField.setFocusable(false);
                coordinateField.setBackground(Color.lightGray);
                coordinateField.setVisible(false);
                coordinateField.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if(e.getKeyCode() == KeyEvent.VK_ENTER){ // add key listener, so if enter is pressed, go to the confirm equation method
                            confirmEquation();
                        }
                    }
                });
                add(coordinateField);

                coordinateLabel.setFont(new Font("Verdana", Font.BOLD, 30)); // label that said Coordinate: above the input field
                coordinateLabel.setBounds(650, 230, 400, 40);
                coordinateLabel.setForeground(Color.BLACK);
                coordinateLabel.setVisible(false);
                add(coordinateLabel);

                riseTextField.setBounds(710, 270, 50, 50); //text field for the rise value of the equation
                riseTextField.setFocusable(false);
                riseTextField.setVisible(false);
                riseTextField.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if(e.getKeyCode() == KeyEvent.VK_ENTER){ //add key listener, when enter is pressed, go to the confirm equation method
                            confirmEquation();
                        }
                    }
                });
                add(riseTextField);

                runTextField.setBounds(710, 340, 50, 50); //text field for the run value of the equation
                runTextField.setFocusable(false);
                runTextField.setVisible(false);
                runTextField.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if(e.getKeyCode() == KeyEvent.VK_ENTER){ //add key listener, when enter is pressed, go to the confirm equation method
                            confirmEquation();
                        }
                    }
                });
                add(runTextField);

                yIntTextField.setBounds(880, 305, 50, 50); //text field for the y-intercept value of the equation
                yIntTextField.setFocusable(false);
                yIntTextField.setVisible(false);
                yIntTextField.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if(e.getKeyCode() == KeyEvent.VK_ENTER){ //add key listener, when enter is pressed, go to the confirm equation method
                            confirmEquation();
                        }
                    }
                });
                add(yIntTextField);

                equationLabel.setBounds(600, 250, 400, 150); //label that says "y = — x + b"
                equationLabel.setFont(new Font("Verdana", Font.BOLD, 50));
                equationLabel.setForeground(Color.WHITE);
                equationLabel.setVisible(false);
                add(equationLabel);

                riseTextField.setBackground(Color.lightGray);
                runTextField.setBackground(Color.lightGray);
                yIntTextField.setBackground(Color.lightGray);
                riseTextField.setFont(new Font("Verdana", Font.BOLD, 40)); //set font and colours of the text fields
                runTextField.setFont(new Font("Verdana", Font.BOLD, 40));
                yIntTextField.setFont(new Font("Verdana", Font.BOLD, 40));

                
                for (int i = 0; i < userBoard.length; i ++) { //initializes both set of buttons (boards for the game)
                    for (int j = 0; j < userBoard[0].length; j++) {
                        
                        inequalityCheck[i][j] = new JLabel(); // 2D array of Jlabels used with inequalities function
                        inequalityCheck[i][j].setBounds(i * 46 + 50, j * 46 + 125, 46, 46); // set the location and size of the labels
                        inequalityCheck[i][j].setFocusable(false);
                        inequalityCheck[i][j].setVisible(true);
                        inequalityCheck[i][j].setFont(new Font("Verdana", Font.BOLD, 0));      
                        inequalityCheck[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 0)); //remove the outlines of the labels      
                        inequalityCheck[i][j].setBackground(Color.PINK); //the background is pink when an inequality is revealed
                        inequalityCheck[i][j].setText("");
                        inequalityCheck[i][j].setOpaque(false);
                        add(inequalityCheck[i][j]); //add the inequality check labels to the game

                        userBoard[i][j] = new JButton(); // userBoard if the array of buttons the user will be playing on
                        userBoard[i][j].setBounds(i * 46 + 50, j * 46 + 125, 46, 46); // set the location and size of the buttons
                        userBoard[i][j].setFocusable(false);
                        userBoard[i][j].setVisible(true);
                        userBoard[i][j].setFont(new Font("Verdana", Font.BOLD, 0));      
                        userBoard[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 0)); //remove the outlines of the buttons           
                        userBoard[i][j].setBackground(Color.lightGray);// initialize what happens when the button is pressed
                        userBoard[i][j].setText("");
                        add(userBoard[i][j]);  //add the user's buttons to the game 

                        computerBoard[i][j] = new JButton(); //computerBoard is the array of buttons the comupter will be playing on
                        computerBoard[i][j].setBounds(i * 46 + 1000, j * 46 + 125, 46, 46); // set the location and size of the buttons
                        computerBoard[i][j].setFocusable(false);
                        computerBoard[i][j].setVisible(true);
                        computerBoard[i][j].setFont(new Font("Verdana", Font.BOLD, 0));               
                        computerBoard[i][j].setBackground(Color.lightGray);
                        computerBoard[i][j].setText("");
                        computerBoard[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 0)); //remove the outlines of the buttons
                        add(computerBoard[i][j]);  //add the computer's buttons to the game 
                    }
                }
                KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.addKeyEventPostProcessor(new KeyEventPostProcessor() { //this listens for key presses
                    @Override
                    public boolean postProcessKeyEvent(KeyEvent e) {
                        if (e.getID() == KeyEvent.KEY_PRESSED) {
                            if(e.getKeyCode() == KeyEvent.VK_R){ // if r is pressed increase rotate variable to say the ship is rotated
                                rotate++;
                                for (int i = 0; i < userBoard.length; i ++) {
                                    for (int j = 0; j < userBoard[0].length; j++) {
                                        if(userBoard[i][j].getText().equals("")) userBoard[i][j].setBackground(Color.lightGray); // reset the colour of the board to the ship before it was rotate is no longer visible
                                        else if(userBoard[i][j].getText().equals("0") || userBoard[i][j].getText().equals("1") || userBoard[i][j].getText().equals("2") && playerTurn % 2 == 0) userBoard[i][j].setBackground(Color.GREEN); //reset the colours, but if a ship is present make the colour green
                                    }
                                }
                                ship.shipHover(rotate, shipColour, mouseX, mouseY); //create a new ship immediately with new rotation
                                repaint();
                            }
                        }
                        return false;
                    }
                });
                
                    level1Button.setFont(new Font("Verdana", Font.BOLD, 30)); // level 1 button
                    level1Button.setBounds(620, 360, 75, 75);
                    level1Button.setForeground(Color.BLACK);
                    level1Button.setBackground(Color.WHITE);
                    level1Button.setFocusable(false);
                    level1Button.addActionListener(f -> startGame(1)); // if level 1 is pressed, the difficuly is set to 1 (the coordinate version)
                    add(level1Button);

                    level2Button.setFont(new Font("Verdana", Font.BOLD, 30)); //level 2 button
                    level2Button.setBounds(740, 360, 75, 75);
                    level2Button.setForeground(Color.BLACK);
                    level2Button.setBackground(Color.WHITE);
                    level2Button.setFocusable(false);
                    level2Button.addActionListener(f -> startGame(2)); // if level 1 is pressed, the difficuly is set to 2 (the slope version)
                    add(level2Button);

                    level3Button.setFont(new Font("Verdana", Font.BOLD, 30)); //level 2 button
                    level3Button.setBounds(860, 360, 75, 75);
                    level3Button.setForeground(Color.BLACK);
                    level3Button.setBackground(Color.WHITE);
                    level3Button.setFocusable(false);
                    level3Button.addActionListener(f -> startGame(3)); // if level 1 is pressed, the difficuly is set to 2 (the slope version)
                    add(level3Button);
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        repaint(); // refresh the JFrame every millisecond, this removes lag from the user input
                    }
                };
                timer.scheduleAtFixedRate(task, 0, 1);
            }
        });
        add(okButton);
        repaint();
    }


    /**
     * initailize the functions of the game and display the needed buttons/textfeild depending on the difficulty
     * @param int level, thee difficulty of the game
     * @return void
     */
    public void startGame(int level) {
        setFocusable(true);
        difficultyLabel.setVisible(false);
        level1Button.setVisible(false);
        level2Button.setVisible(false);
        level3Button.setVisible(false);
        turnJLabel.setVisible(true);
        rotateLabel.setVisible(true);
        shipType.setVisible(true);
        
        //addKeyListener(rPressed);
        try {
            userHistory = new FileWriter(userFile); //declare the location of each text file
            computerHistory = new FileWriter(computerFile);
            userHistory.flush();
            computerHistory.flush();
        } catch (Exception err) {}
        shipColour = Color.GREEN;

            for (int i = 0; i < userBoard.length; i++) {
                for (int j = 0; j < userBoard.length; j++) {
                    buttonPressed(i, j); // initialize what happes to the buttons over hover and when pressed
                    buttonHover(i, j);
                }
            }

        difficulty = level;
        if (difficulty == 1) { // if difficulty is 1 (coordinate version)
            coordinateField.setVisible(true); //set the coordiate stuff visible
            coordinateLabel.setVisible(true);
        }

        else if (difficulty == 2) { // if difficulty is 2 (slope version)
            equationLabel.setVisible(true);// set the slope stuff visible
            yIntTextField.setVisible(true);
            riseTextField.setVisible(true);
            runTextField.setVisible(true);
        }

        else if (difficulty == 3) { // if difficulty is 3 (inequality version)
            equationLabel.setVisible(true);// set the inequality stuff visible
            yIntTextField.setVisible(true);
            riseTextField.setVisible(true);
            runTextField.setVisible(true);
            lessThanButton.setVisible(true);
            greaterThanButton.setVisible(true);
            equalButton.setVisible(true);
            lessThanEqualButton.setVisible(true);
            greaterThanEqualButton.setVisible(true);
        }
    }

    /**
     * if the userBoard again button is pressed, create a new game
     * @return void
     */
    public void playAgain() { // if userBoard again is pressed, detroy the current JFrame and create a new one.       
        shipCount = 0;
        turnJLabel.setText("Your Turn");
        turnJLabel.setVisible(true);
        resetButton.setVisible(false);
        for (int i = 0; i < userBoard.length; i++) {
            for (int j = 0; j < userBoard.length; j++) {
                userBoard[i][j].setText("");
                userBoard[i][j].setBackground(Color.lightGray);
                userBoard[i][j].setFont(new Font("Verdana", Font.BOLD, 0));
                computerBoard[i][j].setText("");
                computerBoard[i][j].setBackground(Color.lightGray);
                computerBoard[i][j].setFont(new Font("Verdana", Font.BOLD, 0));
            }
        }
        shipCheck1 = true;//reset all the ship check values
        shipCheck2 = true;
        shipCheck3 = true;
        p2ShipCheck1 = true;
        p2ShipCheck2 = true;
        p2ShipCheck3 = true;
        rotate = 0;
        userGuesses.clear();
        computerGuesses.clear(); // clear all the guesses made
        userGuessedArea.setText("");
        computerGuessedArea.setText("");
        shipType.setText("Ship: " + ships[shipCount]);
        shipType.setBounds(625, 650, 300, 50);
        ship = new Ship(userBoard, 4); // create a new ship with length of 4
        startGame(difficulty);
    }


    /**
     * set what happens to the buttons when the mouse hovers over them, and exxCoordts
     * @param int xCoord, the x vakue of the button
     * @param int yCoord, the y value of the button
     * @return void
     */
    public void buttonHover(int xCoord, int yCoord) {
        
        userBoard[xCoord][yCoord].addMouseListener(new java.awt.event.MouseAdapter() {
            /**
             * if the mouse hovers over a button
             * @param MouseEvent evt
             * @return void
             */
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (shipCount < 3) { // while the ship count is not equal to 3 (it is the user's turn), if not do nothing on hover afterwards
                    ship.shipHover(rotate, shipColour, xCoord, yCoord); // when on hover, depending on the length of the ship being placed, change the colour of the JButtons to represent a ship
                    mouseX = xCoord; // set the location of the mouse to the x and y values of the buttons
                    mouseY = yCoord;

                }
            }
        
            /**
             * if the mouse is no longer hovering over the JButton, reset its colour, this will depend on the length of the ship, what is the orientation of the ship, and the location of the ship
             * @param MouseEvent evt
             * @return void
             */
            public void mouseExited(java.awt.event.MouseEvent evt) {
                for (int i = 0; i < userBoard.length; i ++) { // when the mouse exits a button, reset the colours of all th ebuttons except if there is a ship there
                    for (int j = 0; j < userBoard[0].length; j++) {
                        if(userBoard[i][j].getText().equals("")) userBoard[i][j].setBackground(Color.lightGray); // reset the colour of the board to the ship before it was rotate is no longer visible
                        else if(userBoard[i][j].getText().equals("0") || userBoard[i][j].getText().equals("1") || userBoard[i][j].getText().equals("2") && playerTurn % 2 == 0) userBoard[i][j].setBackground(Color.GREEN); //reset the colours, but if a ship is present make the colour green
                    }
                }
            }
        });
    }

    /**
     * when the computer selects a location for thier ship
     * @param int xCoord, the x value of the button
     * @param int yCoord, the y value of the button
     * @return void
     */
    public void computerButtonPressed(int xCoord, int yCoord) { // if the robot is confirming its ships

        if (ship.shipHover(rotate, shipColour, xCoord, yCoord)) { // if the ship can be placed without any errors, place it
            ship.confirmShip(rotate, shipColour, xCoord, yCoord, shipCount);
            shipCount++;
        }
        if (shipCount == 0 || shipCount == 3) { // determine thie ship length based on how many ships havce been placed
            shipLength = 4;
        } else if (shipCount == 1 || shipCount == 4) {
            shipLength = 3;
        } else if (shipCount == 2 || shipCount == 5) {
            shipLength = 2;
        }
        ship = new Ship(computerBoard, shipLength); // create a new ship for placingo n the computer's board
        
        for (int a = 0; a < computerBoard.length; a++) { // after every placement make sure there are only 3 ships and no errors with the placement
            for (int b = 0; b < computerBoard.length; b++) {
                if (computerBoard[a][b].getText().equals("")) {
                    computerBoard[a][b].setBackground(Color.lightGray); // this avoids there being multiple of the same ship if there is an error with its placement
                }
                else {
                    computerBoard[a][b].setBackground(shipColour);
                }
            }
        }

        try {
        shipType.setText("Ship: " + ships[shipCount]); // display the type of ship being placed based on length
        } catch (ArrayIndexOutOfBoundsException e) {
            try {
                computerHistory.write("Computer's board:\n");
            } catch (IOException err){}
            for (int i = 0; i < computerBoard.length; i++) {
                for (int j = 0; j < computerBoard.length; j++) {
                    try {
                        if (computerBoard[j][i].getText().equals("")) computerHistory.write("●  ");
                        else computerHistory.write("X  ");
                        computerHistory.flush();
                    }catch (IOException er){}
                }
                try {
                    computerHistory.write("\n");
                }catch (IOException er){}
            }
            try{
                computerHistory.write("\n\n\nComputer's Guesses:\n");
                computerHistory.flush();
            } catch(Exception er){}
            if (difficulty == 1) { //when difficulty is 1, set the coordinate stuff focusable
                coordinateField.setBackground(Color.WHITE);
                coordinateField.setFocusable(true);
            }
            if (difficulty == 2) { //when difficulty is 2, set the slope stuff focusable
                riseTextField.setBackground(Color.WHITE);
                riseTextField.setFocusable(true);
                runTextField.setBackground(Color.WHITE);
                runTextField.setFocusable(true);
                yIntTextField.setBackground(Color.WHITE);
                yIntTextField.setFocusable(true);
            }
            if (difficulty == 3) { //when difficulty is 3, set the inequality stuff focusable
                riseTextField.setBackground(Color.WHITE);
                riseTextField.setFocusable(true);
                runTextField.setBackground(Color.WHITE);
                runTextField.setFocusable(true);
                yIntTextField.setBackground(Color.WHITE);
                yIntTextField.setFocusable(true);
                lessThanButton.setEnabled(true);
                greaterThanButton.setEnabled(true);
                equalButton.setEnabled(true);
                lessThanEqualButton.setEnabled(true);
                greaterThanEqualButton.setEnabled(true);
            }
            shipType.setVisible(false);
            repaint();
            turnJLabel.setText("Your Turn"); // indicate its the user's turn
        }
        
    }



    /**
     * if the user is selecting the location of thier ships (button is pressed)
     * @param int xCoord, the x value of the button
     * @param int yCoord, the y value of the button
     * @return void
    */
    public void buttonPressed(int xCoord, int yCoord) { // if the user if confirming a ship
        
        userBoard[xCoord][yCoord].addActionListener(
        new ActionListener() {
            /**
             * action listener if a button is pressed
             * @return void
             */
            public void actionPerformed(ActionEvent e) {
                if (shipCount < 3) {

                    if (ship.shipHover(rotate, shipColour, xCoord, yCoord)) {
                        ship.confirmShip(rotate, shipColour, xCoord, yCoord, shipCount);
                        shipCount++;
                    }

                    if (shipCount == 0 || shipCount == 3) { // determine the ship's length based on how many ships have been placed
                        shipLength = 4;
                    } else if (shipCount == 1 || shipCount == 4) {
                        shipLength = 3;
                    } else if (shipCount == 2 || shipCount == 5) {
                        shipLength = 2;
                    }
                    ship = new Ship(userBoard, shipLength); //create a new ship with the shipLength

                    if (shipCount <= 3) shipType.setText("Ship: " + ships[shipCount]); // display the type of ship being placed based on length
                    if (shipCount == 3) {
                        
                        shipColour = Color.BLUE; // when shipCount is equal to 3 (the user has placed all thier ships), set the color to light gray
                        repaint();
                        playerTurn++;
                        turnJLabel.setText("<html>Computer's<br/>Turn</html>"); // once the user has placed all of their ships, indicate its the coputer's turn and allow the bot to userBoard its ship
                        rotateLabel.setVisible(false);
                        if (shipCount == 0 || shipCount == 3) {
                            shipLength = 4;
                        } else if (shipCount == 1 || shipCount == 4) {
                            shipLength = 3;
                        } else if (shipCount == 2 || shipCount == 5) {
                            shipLength = 2;
                        }
                        ship = new Ship(computerBoard, shipLength);// create a new ship based on its length
                        botPlaceShips(); // tell the computer to place its ships
                    }
                }
            }
        });
    }

    /**
     * when it is the robot's turn to place ships, it will select random coordinates
     * @return void
     */
    public void botPlaceShips() {
        if (shipCount == 0 || shipCount == 3) {
            shipLength = 4;
        } else if (shipCount == 1 || shipCount == 4) {
            shipLength = 3;
        } else if (shipCount == 2 || shipCount == 5) {
            shipLength = 2;
        }
        ship = new Ship(computerBoard, shipLength); // create a new ship based on its length
        try {
            userHistory.write("User's board:\n");
        } catch (IOException er){}
        for (int i = 0; i < userBoard.length; i++) {
            for (int j = 0; j < userBoard.length; j++) {
                try{
                    if (userBoard[j][i].getText().equals("")) userHistory.write("●  ");
                    else userHistory.write("X  ");
                    userHistory.flush();
                } catch(Exception e){}
            }
            try{
            userHistory.write("\n");
            } catch(Exception e){}
        }
        try{
            userHistory.write("\n\n\nUser's Guesses:\n");
            userHistory.flush();
        } catch(Exception e){}
        if (shipCount < 6) {
            java.util.Timer timer2 = new java.util.Timer();
            TimerTask task2 = new TimerTask() {
                @Override
                public void run() {
                    rotate += (int)Math.floor(Math.random()*(10));
                    int xCoord = (int)Math.floor(Math.random()*(computerBoard.length));
                    int yCoord = (int)Math.floor(Math.random()*(computerBoard.length)); // select a random location for the bot's ships
                    computerButtonPressed(xCoord, yCoord);
                    if (shipCount >= 6)  {
                        timer2.cancel();
                    }
                }
            };
            timer2.scheduleAtFixedRate(task2, 3000, 3000); // set a timer, so the placement seems realitic as if a person is placing the sips
        }
        if (difficulty == 3) {
            equationLabel.setText("y = — x + o");
        }
        playerTurn++; // increase player turn 
    }

    /**
     * when the user is inputting a coordinate, obtain the value the user inputted and apply it to the game 
     * @return void
     */
    public void confirmEquation() { // when enter is pressed on the coordinate field
        player player = new player(coordinateField, yIntTextField, riseTextField, runTextField); //create a new player

        if (difficulty == 1) { // if the difficulty is the coordinate version
            try {
                errorLabel.setVisible(false);
                errorLabel.setText("Error, please try again");
                int coordiates[] = player.level1Values();
                int xCoord = coordiates[0];
                int yCoord = coordiates[1];
                if (xCoord > userBoard.length/2 || xCoord < -userBoard.length/2 || yCoord > userBoard.length/2 || yCoord < -userBoard.length/2) { // if the coordinates are out of the range
                    errorLabel.setVisible(true);
                    errorLabel.setFont(new Font("Verdana", Font.BOLD, 30));
                    errorLabel.setText("Error, please try again"); //show an error and do not apply to the game
                }
                else {
                    int coordinatePlaced = guessSpot(xCoord, yCoord);
                    if (coordinatePlaced == 1) { // if the coordinate is valid 
                        coordinateField.setBackground(Color.lightGray);
                        coordinateField.setFocusable(false);
                        userGuessedArea.setText(userGuessedArea.getText() + "\n(" + xCoord + ", " + yCoord + ")");
                        try {
                            userHistory.write("(" + xCoord + ", " + yCoord + ")\n");
                            userHistory.flush();
                        } catch (Exception e){}
                        playerTurn++;
                        botGuessShip();
                    }
                    else if(coordinatePlaced == 2){ // if the coordinate is valid and you win the game
                        coordinateField.setBackground(Color.lightGray);
                        coordinateField.setFocusable(false);
                        turnJLabel.setVisible(true);
                        resetButton.setVisible(true);
                        try {
                            boolean startCounter = false;
                            int numberOfGuesses = 0;
                            Scanner userScanner = new Scanner(userFile);
                            while (userScanner.hasNextLine()) { // count the number of guesses by reading the userHistory text file and display it when you win the game
                                String data = userScanner.nextLine();
                                if (startCounter) {
                                    numberOfGuesses++;
                                }
                                if (data.equals("User's Guesses:")) {
                                    startCounter = true;
                                }
                            }
                            numberOfGuesses++;
                            turnJLabel.setText("<html>You Win!<br/>Guesses: " + numberOfGuesses + "<html/>");
                            userScanner.close();
                        } catch (Exception e){}
                        score.userWins();
                    }
                    else { // if you have laready guessed that previously
                        errorLabel.setVisible(true);
                        errorLabel.setFont(new Font("Verdana", Font.BOLD, 25));
                        errorLabel.setText("You already guessed that");
                    }
                }
            } catch (Exception e) { // if there is any of the following errors (not proper notation, not numerical values, out of bounds of the game)
                coordinateField.setText("");
                errorLabel.setVisible(true);
                errorLabel.setFont(new Font("Verdana", Font.BOLD, 30));
                errorLabel.setText("Error, please try again");
            }
        }

        if (difficulty == 2) { // if the difficulty is the slope version
            try {
                errorLabel.setVisible(false);
                boolean winner = false; // winner checks if there has been a winner after an input

                int slopeValues[] = player.level2Values();
                rise = slopeValues[0];
                run = slopeValues[1];
                yInt = slopeValues[2];
                
                if (rise == 0 || run == 0) { // make sure the values are not 0, so it isnt an error or a stright line
                    errorLabel.setVisible(true);
                    errorLabel.setFont(new Font("Verdana", Font.BOLD, 30));
                    errorLabel.setText("Error, please try again");
                } else {
                    int xCoord = 0;
                    int yCoord = yInt; // set the begining x and y coordinates
                    if (!userGuesses.contains("y = " + rise + "/" + run + " x + " + yInt)) {
                        boolean occurance = false;
                        for (int i = -5; i <= 5; i++) {
                            int endPoint = (int)Math.floor((double)rise * (double)i / (double)run + (double)yInt); //solve for the y-values with the x-values throughout the board
                            if (endPoint <= 5 && endPoint >= -5) {
                                occurance = true;
                                break;
                            }
                        }
                        if (occurance) { // checks if the equation inputted actually occurs on the board
                            userGuesses.add("y = " + rise + "/" + run + " x + " + yInt);
                            userGuessedArea.setText(userGuessedArea.getText() + "\n" + (equationLabel.getText().replace("—", rise + "/" + run)).replace("o", String.valueOf(yInt)));
                            try {
                                userHistory.write("y = " + rise + "/" + run + " x + " + yInt + "\n");
                                userHistory.flush();
                            } catch (Exception e){}
                            xCoord = 0;
                            yCoord = yInt; // set the begining x and y coordinates
                            while (xCoord <= userBoard.length/2 && xCoord >= -userBoard.length/2) { // continue the line until it reaches the end of the graph
                                if (yCoord <= userBoard.length/2 && yCoord >= -userBoard.length/2) {
                                    int coordinatePlaced = guessSpot(xCoord, yCoord); //set a variable temporarily to check if it successfully places the coordinate and if there is a win
                                    if (coordinatePlaced == 1) {
                                        riseTextField.setBackground(Color.lightGray);
                                        riseTextField.setFocusable(false);
                                        runTextField.setBackground(Color.lightGray);
                                        runTextField.setFocusable(false);
                                        yIntTextField.setBackground(Color.lightGray);
                                        yIntTextField.setFocusable(false);
                                        
                                    }
                                    else if(coordinatePlaced == 2){ // if there is a winner set the boolean winner to true
                                        winner = true;
                                    }
                                }
                                xCoord += run;
                                yCoord += rise; // increment the x and y values with the rise and run values, increasing
                            }
                            xCoord = 0;
                            yCoord = yInt; // set the begining x and y coordinates
                            while (xCoord <= userBoard.length/2 && xCoord >= -userBoard.length/2) {
                                if (yCoord <= userBoard.length/2 && yCoord >= -userBoard.length/2) {
                                    int coordinatePlaced = guessSpot(xCoord, yCoord); //set a variable temporarily to check if it successfully places the coordinate and if there is a win
                                    if (coordinatePlaced == 1) {
                                        riseTextField.setBackground(Color.lightGray);
                                        riseTextField.setFocusable(false);
                                        runTextField.setBackground(Color.lightGray);
                                        runTextField.setFocusable(false);
                                        yIntTextField.setBackground(Color.lightGray);
                                        yIntTextField.setFocusable(false);
                                        
                                    }
                                    else if(coordinatePlaced == 2){  // if there is a winner set the boolean winner to true
                                        winner = true;
                                    }
                                }
                                xCoord -= run;
                                yCoord -= rise; // increment the x and y values with the rise and run values, decreasing
                            }
                            if (winner) { // if there is a winner
                                riseTextField.setBackground(Color.lightGray);
                                riseTextField.setFocusable(false);
                                runTextField.setBackground(Color.lightGray);
                                runTextField.setFocusable(false);
                                yIntTextField.setBackground(Color.lightGray);
                                yIntTextField.setFocusable(false);
                                turnJLabel.setVisible(true);
                                resetButton.setVisible(true);
                                try {
                                    boolean startCounter = false;
                                    int numberOfGuesses = 0;
                                    Scanner userScanner = new Scanner(userFile);
                                    while (userScanner.hasNextLine()) { // count the number of guesses by reading the userHistory text file and display it when you win the game
                                        String data = userScanner.nextLine();
                                        if (startCounter) {
                                            numberOfGuesses++;
                                        }
                                        if (data.equals("User's Guesses:")) {
                                            startCounter = true;
                                        }
                                    }
                                    numberOfGuesses++;
                                    turnJLabel.setText("<html>You Win!<br/>Guesses: " + numberOfGuesses + "<html/>");
                                    userScanner.close();
                                } catch (Exception e){}
                                score.userWins();
                            }
                            else { // if there is not a repeating slope
                                playerTurn++; //increment player turn
                                equationLabel.setText("y = — x + o");
                                botGuessShip(); // tell the computer to guess a slope
                            }
                        }
                        else {
                            riseTextField.setText("");
                            runTextField.setText("");
                            yIntTextField.setText("");
                            errorLabel.setVisible(true);
                            errorLabel.setFont(new Font("Verdana", Font.BOLD, 30));
                            errorLabel.setText("Error, please try again");
                        }
                    }
                    else { // if the slope has already been guessed
                        errorLabel.setVisible(true);
                        errorLabel.setFont(new Font("Verdana", Font.BOLD, 25));
                        errorLabel.setText("You already guessed that");
                    }
                }
            }
            catch (Exception e) { // if there is any of the following errors (not proper notation, not numerical values, out of bounds of the game)
                riseTextField.setText("");
                runTextField.setText("");
                yIntTextField.setText("");
                errorLabel.setVisible(true);
                errorLabel.setFont(new Font("Verdana", Font.BOLD, 30));
                errorLabel.setText("Error, please try again");
            }
        }
        if (difficulty == 3) { // if the difficulty is the inequality version
            try {
                errorLabel.setVisible(false);
                boolean winner = false; // winner checks if there has been a winner after an input
                int slopeValues[] = player.level2Values();
                rise = slopeValues[0];
                run = slopeValues[1];
                yInt = slopeValues[2];
                
                if (rise == 0 || run == 0) { // make sure the values are not 0, so it isnt an error or a stright line
                    errorLabel.setVisible(true);
                    errorLabel.setFont(new Font("Verdana", Font.BOLD, 30));
                    errorLabel.setText("Error, please try again");
                } else {
                    int xCoord = 0;
                    int yCoord = yInt; // set the begining x and y coordinate
                    if (!userGuesses.contains((equationLabel.getText().replace("—", rise + "/" + run)).replace("o", String.valueOf(yInt)))) {
                        boolean occurance = false;
                        for (int i = -5; i <= 5; i++) {
                            int endPoint = (int)Math.floor((double)rise * (double)i / (double)run + (double)yInt); //solve for the y-values with the x-values throughout the board
                            if (endPoint <= 5 && endPoint >= -5) {
                                occurance = true;
                                break;
                            }
                        }
                        if (occurance) { // checks if the equation inputted actually occurs on the board
                            userGuesses.add((equationLabel.getText().replace("—", rise + "/" + run)).replace("o", String.valueOf(yInt)));
                            
                            if (equationLabel.getText().equals("y = — x + o")) {
                                userGuessedArea.setText(userGuessedArea.getText() + "\n" + (equationLabel.getText().replace("—", rise + "/" + run)).replace("o", String.valueOf(yInt)));
                                try {
                                    userHistory.write((equationLabel.getText().replace("—", rise + "/" + run)).replace("o", String.valueOf(yInt))+ "\n");
                                    userHistory.flush();
                                } catch (Exception e){}
                                xCoord = 0;
                                yCoord = yInt; // set the begining x and y coordinates
                                while (xCoord <= userBoard.length/2 && xCoord >= -userBoard.length/2) { // continue the line until it reaches the end of the graph
                                    if (yCoord <= userBoard.length/2 && yCoord >= -userBoard.length/2) {
                                        int coordinatePlaced = guessSpot(xCoord, yCoord); //set a variable temporarily to check if it successfully places the coordinate and if there is a win
                                        if (coordinatePlaced == 1) {
                                            riseTextField.setBackground(Color.lightGray);
                                            riseTextField.setFocusable(false);
                                            runTextField.setBackground(Color.lightGray);
                                            runTextField.setFocusable(false);
                                            yIntTextField.setBackground(Color.lightGray);
                                            yIntTextField.setFocusable(false);
                                            
                                        }
                                        else if(coordinatePlaced == 2 ){ // if there is a winner set the boolean winner to true
                                            winner = true;
                                        }
                                    }
                                    xCoord += run;
                                    yCoord += rise; // increment the x and y values with the rise and run values, increasing
                                }
                                xCoord = 0;
                                yCoord = yInt; // set the begining x and y coordinates
                                while (xCoord <= userBoard.length/2 && xCoord >= -userBoard.length/2) {
                                    if (yCoord <= userBoard.length/2 && yCoord >= -userBoard.length/2) {
                                        int coordinatePlaced = guessSpot(xCoord, yCoord); //set a variable temporarily to check if it successfully places the coordinate and if there is a win
                                        if (coordinatePlaced == 1) {
                                            riseTextField.setBackground(Color.lightGray);
                                            riseTextField.setFocusable(false);
                                            runTextField.setBackground(Color.lightGray);
                                            runTextField.setFocusable(false);
                                            yIntTextField.setBackground(Color.lightGray);
                                            yIntTextField.setFocusable(false);
                                            
                                        }
                                        else if(coordinatePlaced == 2){  // if there is a winner set the boolean winner to true
                                            winner = true;
                                        }
                                    }
                                    xCoord -= run;
                                    yCoord -= rise; // increment the x and y values with the rise and run values, decreasing
                                }
                            }
                            else guessInequality();
                            if (winner) { // if there is a winner
                                riseTextField.setBackground(Color.lightGray);
                                riseTextField.setFocusable(false);
                                runTextField.setBackground(Color.lightGray);
                                runTextField.setFocusable(false);
                                yIntTextField.setBackground(Color.lightGray);
                                yIntTextField.setFocusable(false);
                                turnJLabel.setVisible(true);
                                resetButton.setVisible(true);
                                try {
                                    boolean startCounter = false;
                                    int numberOfGuesses = 0;
                                    Scanner userScanner = new Scanner(userFile);
                                    while (userScanner.hasNextLine()) { // count the number of guesses by reading the userHistory text file and display it when you win the game
                                        String data = userScanner.nextLine();
                                        if (startCounter) {
                                            numberOfGuesses++;
                                        }
                                        if (data.equals("User's Guesses:")) {
                                            startCounter = true;
                                        }
                                    }
                                    numberOfGuesses++;
                                    turnJLabel.setText("<html>You Win!<br/>Guesses: " + numberOfGuesses + "<html/>");
                                    userScanner.close();
                                } catch (Exception e){}
                                score.userWins();
                            }
                            else { // if there is not a repeating slope
                                playerTurn++; //increment player turn
                                equationLabel.setText("y = — x + o");
                                botGuessShip(); // tell the computer to guess a slope
                            }
                        }
                    }
                    else { // if the slope has already been guessed
                        errorLabel.setVisible(true);
                        errorLabel.setFont(new Font("Verdana", Font.BOLD, 25));
                        errorLabel.setText("You already guessed that");
                    }
                }
            }
            catch (Exception e) { // if there is any of the following errors (not proper notation, not numerical values, out of bounds of the game)
                riseTextField.setText("");
                runTextField.setText("");
                yIntTextField.setText("");
                errorLabel.setVisible(true);
                errorLabel.setFont(new Font("Verdana", Font.BOLD, 30));
                errorLabel.setText("Error, please try again");
            }
        }
    }

    /**
     * when eiither player is trying to guess a spot on the game
     * @param int x, the x coordinate passed
     * @param int y, the y coordinate passed
     * @return void
     */
    public int guessSpot(int x, int y) {
        java.util.Timer timer4 = new java.util.Timer();
        int middle = userBoard.length/2; // middle of the game (0, 0)
        y = -y; // y value will be inverted because of how the JButtons are arranged
        if (playerTurn % 2 == 0) { // if it is the user's turn
            if (!computerBoard[middle + x][middle + y].getText().equals("X")) { // if the guessed coordinate isnt already guessed
                if (!computerBoard[middle + x][middle + y].getText().equals(""))  { // if there is a ship, mark a red X
                    computerBoard[middle + x][middle + y].setForeground(Color.RED);
                    computerBoard[middle + x][middle + y].setText("X");
                    computerBoard[middle + x][middle + y].setFont(new Font("Verdana", Font.BOLD, 50));
                }
                else { // if there is no ship, mark a black X
                    computerBoard[middle + x][middle + y].setForeground(Color.BLACK);
                    computerBoard[middle + x][middle + y].setText("X");
                    computerBoard[middle + x][middle + y].setFont(new Font("Verdana", Font.BOLD, 50));
                }
                //Counting the number of ships after every turn to determine if there is a sink and if you win
                if (shipCheck1) counter3 = 0; // only reset the value if the ship has not already sunk
                if (shipCheck2) counter2 = 0;
                if (shipCheck3) counter1 = 0;
                for (int i = 0 ; i < computerBoard.length; i++) {
                    for (int j = 0; j < computerBoard.length; j++) {
                        if (shipCheck1 && computerBoard[i][j].getText().equals("3")) counter3++; // count the number of each boat for the user
                        if (shipCheck2 && computerBoard[i][j].getText().equals("4")) counter2++;
                        if (shipCheck3 && computerBoard[i][j].getText().equals("5")) counter1++;
                    }
                }
                if (counter1 == 0 || counter2 == 0 || counter3 == 0) {
                    shipType.setVisible(true);
                    shipType.setBounds(685, 650, 300, 50);
                    shipType.setText("SINK!");
                    if (counter1 == 0) { // if any boats sink, set them to 1 and don't reset their value to avoid saying sink after every turn
                        shipCheck3 = false;
                        counter1 = 1;
                    }
                    if (counter2 == 0) {
                        shipCheck2 = false;
                        counter2 = 1;
                    }
                    if (counter3 == 0) {
                        shipCheck1 = false;
                        counter3 = 1;
                    }
                    TimerTask task4 = new TimerTask() { // timer to show sink text and remove after 2 seconds
                        @Override
                        public void run() {
                            shipType.setVisible(false);
                            timer4.cancel();
                        }
                    };
                    timer4.scheduleAtFixedRate(task4, 2000, 1000); // create a timer with a delay of 2 seconds that only runs once
                }
                if (!shipCheck1 && !shipCheck2 && !shipCheck3) { // if all ships are sunk, return 2 indicating a win
                    return 2;
                }
                return 1; // if everything is valid and is not a win, return 1
            }
            else {
                return 0; // if there is an error, if it has already been guessed (only for difficulty == 1), return 0
            }
        }
        else { // if it is the computer's turn
            if (!userBoard[middle + x][middle + y].getText().equals("X")) { // if the guessed coordinate isnt already guessed
                if (!userBoard[middle + x][middle + y].getText().equals(""))  { // if there is a ship, mark a red X
                    userBoard[middle + x][middle + y].setForeground(Color.RED);
                    userBoard[middle + x][middle + y].setText("X");
                    userBoard[middle + x][middle + y].setFont(new Font("Verdana", Font.BOLD, 50));
                }
                else { // if there is no ship, mark a black X
                    userBoard[middle + x][middle + y].setForeground(Color.BLACK);
                    userBoard[middle + x][middle + y].setText("X");
                    userBoard[middle + x][middle + y].setFont(new Font("Verdana", Font.BOLD, 50));
                }
                //Counting the number of ships after every turn to determine if there is a sink and if you win
                if (p2ShipCheck1) p2Counter3 = 0; // only reset the value if the ship has not already sunk
                if (p2ShipCheck2) p2Counter2 = 0;
                if (p2ShipCheck3) p2Counter1 = 0;
                for (int i = 0 ; i < userBoard.length; i++) {
                    for (int j = 0; j < userBoard.length; j++) {
                        if (p2ShipCheck1 && userBoard[i][j].getText().equals("0")) p2Counter3++; // count the number of each boat for the computer
                        if (p2ShipCheck2 && userBoard[i][j].getText().equals("2")) p2Counter2++;
                        if (p2ShipCheck3 && userBoard[i][j].getText().equals("1")) p2Counter1++;
                    }
                }
                if (p2Counter1 == 0 || p2Counter2 == 0 || p2Counter3 == 0) {
                    shipType.setVisible(true);
                    shipType.setBounds(685, 650, 300, 50);
                    shipType.setText("SINK!");
                    if (p2Counter1 == 0) { // if any  of the computer's boats sink, set them to 1 and don't reset their value to avoid saying sink after every turn
                        p2ShipCheck3 = false;
                        p2Counter1 = 1;
                    }
                    if (p2Counter2 == 0) {
                        p2ShipCheck2 = false;
                        p2Counter2 = 1;
                    }
                    if (p2Counter3 == 0) {
                        p2ShipCheck1 = false;
                        p2Counter3 = 1;
                    }
                    TimerTask task4 = new TimerTask() { // timer to show sink text and remove after 2 seconds
                        @Override
                        public void run() {
                            shipType.setVisible(false);
                            timer4.cancel();
                        }
                    };
                    timer4.scheduleAtFixedRate(task4, 2000, 1000); // create a timer with a delay of 2 seconds that only runs once
                }
                if (!p2ShipCheck1 && !p2ShipCheck2 && !p2ShipCheck3) {
                    return 2; // if all ships are sunk, return 2 indicating a win for the computer
                }
                return 1; // if everything is valid and is not a win, return 1
            }
            else {
                return 0; // if there is an error, if it has already been guessed (only for difficulty == 1), return 0
            }
        }
    }

    public void guessInequality() {
            riseTextField.setBackground(Color.lightGray);
            riseTextField.setFocusable(false);
            runTextField.setBackground(Color.lightGray);
            runTextField.setFocusable(false);
            yIntTextField.setBackground(Color.lightGray);
            yIntTextField.setFocusable(false);
            int middle = userBoard.length/2; // middle of the game (0, 0)
            if (equationLabel.getText().equals("y < — x + o")) { // if the inequality is less than
                for (int i = -5; i <= 5; i++) {
                    int endPoint = (int)Math.floor((double)rise * (double)i / (double)run + (double)yInt); //solve for the y-values with the x-values throughout the board
                    for (int j = -5; j < endPoint; j++) {
                        try {
                            if (playerTurn % 2 == 0) {
                                computerBoard[middle + i][middle - j].setBackground(Color.PINK);// show the inequality on the boards by setting the color to pink temporarily
                            }
                            else {
                                inequalityCheck[middle + i][middle - j].setOpaque(true);
                            }
                        } catch (ArrayIndexOutOfBoundsException e){}
                    }
                }
            }
            else if (equationLabel.getText().equals("y > — x + o")) { // if the inequality is greater than
                for (int i = -5; i <= 5; i++) {
                    int endPoint = (int)Math.floor((double)rise * (double)i / (double)run + (double)yInt); //solve for the y-values with the x-values throughout the board
                    for (int j = 5; j > endPoint; j--) {
                        try {
                            if (playerTurn % 2 == 0) {
                                computerBoard[middle + i][middle - j].setBackground(Color.PINK);// show the inequality on the boards by setting the color to pink temporarily
                            }
                            else {
                                inequalityCheck[middle + i][middle - j].setOpaque(true);
                            }
                        } catch (ArrayIndexOutOfBoundsException e){}
                    }
                }
            }
            else if (equationLabel.getText().equals("y ≤ — x + o")) { // if the inequality is less than equal to 
                for (int i = -5; i <= 5; i++) {
                    int endPoint = (int)Math.floor((double)rise * (double)i / (double)run + (double)yInt); //solve for the y-values with the x-values throughout the board
                    for (int j = -5; j <= endPoint; j++) {
                        try {
                            if (playerTurn % 2 == 0) {
                                computerBoard[middle + i][middle - j].setBackground(Color.PINK);// show the inequality on the boards by setting the color to pink temporarily
                            }
                            else {
                                inequalityCheck[middle + i][middle - j].setOpaque(true);
                            }
                        } catch (ArrayIndexOutOfBoundsException e){}
                    }
                }
            }
            else if (equationLabel.getText().equals("y ≥ — x + o")) { // if the inequality is greater than equal to
                for (int i = -5; i <= 5; i++) {
                    int endPoint = (int)Math.floor((double)rise * (double)i / (double)run + (double)yInt); //solve for the y-values with the x-values throughout the board
                    for (int j = 5; j >= endPoint; j--) {
                        try {
                            if (playerTurn % 2 == 0) {
                                computerBoard[middle + i][middle - j].setBackground(Color.PINK); // show the inequality on the boards by setting the color to pink temporarily
                            }
                            else {
                                inequalityCheck[middle + i][middle - j].setOpaque(true);
                            }
                        } catch (ArrayIndexOutOfBoundsException e){}
                    }
                    
                }
            }

            int computerShipCount = 0, userShipCount = 0;
            for (int i = 0; i < userBoard.length; i++) { //count how many ships fall under the inequality inputted
                for (int j = 0; j < userBoard.length; j++) {
                    if ((userBoard[i][j].getText().equals("0") || userBoard[i][j].getText().equals("1") || userBoard[i][j].getText().equals("2")) && inequalityCheck[i][j].isOpaque()) {
                        computerShipCount++;
                    }
                    if ((computerBoard[i][j].getText().equals("3") || computerBoard[i][j].getText().equals("4") || computerBoard[i][j].getText().equals("5")) && computerBoard[i][j].getBackground().equals(Color.PINK)) {
                        userShipCount++;
                    }
                }
            }
            if (playerTurn % 2 == 0) { //add the inequality with the number of ships to the arraylists and the text files
                userGuessedArea.setText(userGuessedArea.getText() + "\n" + (equationLabel.getText().replace("—", rise + "/" + run)).replace("o", String.valueOf(yInt)) + ",   new ships found: " + userShipCount);
                try {
                    userHistory.write((equationLabel.getText().replace("—", rise + "/" + run)).replace("o", String.valueOf(yInt)) + ",   new ships found: " + userShipCount + "\n");
                    userHistory.flush();
                } catch (Exception e){}
            }
            else {
                computerGuessedArea.setText(computerGuessedArea.getText() + "\n" + (equationLabel.getText().replace("—", rise + "/" + run)).replace("o", String.valueOf(yInt)) + ",   new ships found: " + computerShipCount);
                try {
                    computerHistory.write((equationLabel.getText().replace("—", rise + "/" + run)).replace("o", String.valueOf(yInt)) + ",   new ships found: " + userShipCount + "\n");
                    computerHistory.flush();
                } catch (Exception e){}
            }
    }
    /**
     * the computer selects random spots to guess on the user's ships
     * @return void
     */
    public void botGuessShip() { // the computer's turn
        
    java.util.Timer timer3 = new java.util.Timer();
        TimerTask task3 = new TimerTask() { // starts a timer to immatate the time a person would take 
            int count = 0;
            @Override
            public void run() {
                if (count == 0) { // first iteration of the timer
                    for (int i = 0; i < userBoard.length; i++) {
                        for (int j = 0; j < userBoard.length;j++) {
                            computerBoard[i][j].setBackground(Color.lightGray);
                        }
                    }
                    turnJLabel.setText("<html>Computer's<br/>Turn</html>"); // indicate its the computer's turn
                    count++; // increment counter
                }
                else if (count == 1) {// second interation
                    int xValue = (int)Math.floor(Math.random()*((userBoard.length/2)-(-userBoard.length/2)+1)+(-userBoard.length/2));
                    int yValue = (int)Math.floor(Math.random()*((userBoard.length/2)-(-userBoard.length/2)+1)+(-userBoard.length/2));
                    coordinateField.setText("(" + xValue + ", " + yValue + ")");
                    rise = 0;
                    run = 0;
                    yInt = (int)Math.floor(Math.random()*((userBoard.length)-(-userBoard.length)+1)+(-userBoard.length)); // randomize nubers within the range of the board
                    while (rise == 0 || run == 0) { //make sure neither of the values are equal to 0
                        rise = (int)Math.floor(Math.random()*((userBoard.length/2)-(-userBoard.length/2)+1)+(-userBoard.length/2));
                        run = (int)Math.floor(Math.random()*((userBoard.length/2)-(-userBoard.length/2)+1)+(-userBoard.length/2));
                    }
                    riseTextField.setText(String.valueOf(rise)); // display the values that the computer is guessing on the text feilds, so the user knows what the computer guessed
                    runTextField.setText(String.valueOf(run));
                    yIntTextField.setText(String.valueOf(yInt));
                    player computer = new player(coordinateField, yIntTextField, riseTextField, runTextField);
                    if (difficulty == 1) { // if difficulty is coordinate version
                        int coordinates[] = computer.level1Values();
                        int xCoord = coordinates[0];
                        int yCoord = coordinates[1];
                        coordinateField.setText("(" + xCoord + ", " + yCoord + ")");
                        int coordinatePlaced = guessSpot(xCoord, yCoord);
                        if (coordinatePlaced == 1)  { // if the coordiate is valid increment the counter
                            computerGuessedArea.setText(computerGuessedArea.getText() + "\n(" + xCoord + ", " + yCoord + ")");
                            try {
                                computerHistory.write("(" + xCoord + ", " + yCoord + ")\n");
                                computerHistory.flush();
                            } catch (Exception e){}
                            count++; 
                        }
                        else if(coordinatePlaced == 2){  // if the computer wins the game
                            coordinateField.setBackground(Color.lightGray); // dont allow any more inputs
                            coordinateField.setFocusable(false);
                            turnJLabel.setVisible(true);
                            resetButton.setVisible(true);
                            try {
                                boolean startCounter = false;
                                int numberOfGuesses = 0;
                                Scanner computerScanner = new Scanner(computerFile);
                                while (computerScanner.hasNextLine()) { // count the number of guesses by reading the computerHistory text file and display it when the computer wins the game
                                    String data = computerScanner.nextLine();
                                    if (startCounter) {
                                        numberOfGuesses++;
                                    }
                                    if (data.equals("Computer's Guesses:")) {
                                        startCounter = true;
                                    }
                                }
                                numberOfGuesses++;
                                turnJLabel.setText("<html>Computer Wins!<br/>Guesses: " + numberOfGuesses + "<html/>");
                                computerScanner.close();
                            } catch (Exception e){}
                            score.computerWins();
                            timer3.cancel(); // stop the timer
                        }
                    }

                    if (difficulty == 2) { // if difficulty is slope version
                        boolean winner = false;
                        int slopeValues[] = computer.level2Values();
                        rise = slopeValues[0];
                        run = slopeValues[1];
                        yInt = slopeValues[2];
                        int xCoord = 0;
                        int yCoord = yInt; //set the beginning values of the x and y coordinates

                        if (!computerGuesses.contains("y = " + rise + "/" + run + " x + " + yInt)) {
                            boolean occurance = false;
                            for (int i = -5; i <= 5; i++) {
                                int endPoint = (int)Math.floor((double)rise * (double)i / (double)run + (double)yInt); //solve for the y-values with the x-values throughout the board
                                if (endPoint <= 5 && endPoint >= -5) {
                                    occurance = true;
                                    break;
                                }
                            }
                            if (occurance) { // checks if the equation inputted actually occurs on the board
                                computerGuesses.add("y = " + rise + "/" + run + " x + " + yInt );
                                try {
                                    computerHistory.write("y = " + rise + "/" + run + " x + " + yInt+ "\n");
                                    computerHistory.flush();
                                } catch (Exception e){}
                                riseTextField.setText(String.valueOf(rise)); // display the values that the computer is guessing on the text feilds, so the user knows what the computer guessed
                                runTextField.setText(String.valueOf(run));
                                yIntTextField.setText(String.valueOf(yInt));
                                if (equationLabel.getText().equals("y = — x + o")) {
                                    xCoord = 0;
                                    yCoord = yInt; //set the beginning values of the x and y coordinates
                                    while (xCoord <= userBoard.length/2  && xCoord >= -userBoard.length/2) {
                                        if (yCoord <= userBoard.length/2 && yCoord >= -userBoard.length/2) {
                                            int coordinatePlaced = guessSpot(xCoord, yCoord); //set a variable temporarily to check if it successfully places the coordinate and if there is a win
                                            if(coordinatePlaced == 2){ // if there is a winner, set winner to true
                                                winner = true;
                                            }
                                        }
                                        xCoord += run;
                                        yCoord += rise;
                                    }
                                    xCoord = 0;
                                    yCoord = yInt; //set the beginning values of the x and y coordinates
                                    while (xCoord <= userBoard.length/2 && xCoord >= -userBoard.length/2) {
                                        if (yCoord <= userBoard.length/2 && yCoord >= -userBoard.length/2) {
                                            int coordinatePlaced = guessSpot(xCoord, yCoord); //set a variable temporarily to check if it successfully places the coordinate and if there is a win
                                            if(coordinatePlaced == 2){ // if there is a winner, set winner to true
                                                winner = true;
                                            }
                                        }
                                        xCoord -= run;
                                        yCoord -= rise;
                                    }
                                }
                                else guessInequality();
                                if (winner) {
                                    turnJLabel.setVisible(true);
                                    resetButton.setVisible(true);
                                    try {
                                        boolean startCounter = false;
                                        int numberOfGuesses = 0;
                                        Scanner computerScanner = new Scanner(computerFile);
                                        while (computerScanner.hasNextLine()) { // count the number of guesses by reading the computerHistory text file and display it when the computer wins the game
                                            String data = computerScanner.nextLine();
                                            if (startCounter) {
                                                numberOfGuesses++;
                                            }
                                            if (data.equals("Computer's Guesses:")) {
                                                startCounter = true;
                                            }
                                        }
                                        numberOfGuesses++;
                                        turnJLabel.setText("<html>Computer Wins!<br/>Guesses: " + numberOfGuesses + "<html/>");
                                        computerScanner.close();
                                    } catch (Exception e){}
                                    score.computerWins();
                                    timer3.cancel(); //if there is a winner stop the timer prevent the game from continuing
                                }
                                else { // if there is no error, increment counter
                                    computerGuessedArea.setText(computerGuessedArea.getText() + "\n" + (equationLabel.getText().replace("—", rise + "/" + run)).replace("o", String.valueOf(yInt)));
                                    count++; 
                                }
                            }
                        }
                    }
                    if (difficulty == 3) { // if difficulty is inequality version
                        
                        if (equationTurn % 2 == 0) { // this cycles through the different equations so the robot will userBoard an inequality first, then an equation and the vice versa
                            int chooseEquation = 2;
                            while (chooseEquation == 2) {
                                chooseEquation = (int)Math.floor(Math.random()*(5));
                            }
                            equationLabel.setText(equations[chooseEquation]);
                            equationTurn++;
                        }
                        else {
                            equationLabel.setText(equations[2]);
                            equationTurn++;
                        }
                        boolean winner = false;
                        int slopeValues[] = computer.level2Values();
                        rise = slopeValues[0];
                        run = slopeValues[1];
                        yInt = slopeValues[2];
                        int xCoord = 0;
                        int yCoord = yInt; //set the beginning values of the x and y coordinates
                        if (!computerGuesses.contains((equationLabel.getText().replace("—", rise + "/" + run)).replace("o", String.valueOf(yInt)))) {
                            boolean occurance = false;
                            for (int i = -5; i <= 5; i++) {
                                int endPoint = (int)Math.floor((double)rise * (double)i / (double)run + (double)yInt); //solve for the y-values with the x-values throughout the board
                                if (endPoint <= 5 && endPoint >= -
                                5) {
                                    occurance = true;
                                    break;
                                }
                            }
                            if (occurance) { // checks if the equation inputted actually occurs on the board
                                computerGuesses.add((equationLabel.getText().replace("—", rise + "/" + run)).replace("o", String.valueOf(yInt)));
                            
                                riseTextField.setText(String.valueOf(rise)); // display the values that the computer is guessing on the text feilds, so the user knows what the computer guessed
                                runTextField.setText(String.valueOf(run));
                                yIntTextField.setText(String.valueOf(yInt));
                                if (equationLabel.getText().equals("y = — x + o")) {
                                    computerGuessedArea.setText(computerGuessedArea.getText() + "\n" + (equationLabel.getText().replace("—", rise + "/" + run)).replace("o", String.valueOf(yInt)));
                                    try {
                                        computerHistory.write((equationLabel.getText().replace("—", rise + "/" + run)).replace("o", String.valueOf(yInt)) + "\n");
                                        computerHistory.flush();
                                    } catch (Exception e){}
                                    xCoord = 0;
                                    yCoord = yInt; //set the beginning values of the x and y coordinates
                                    while (xCoord <= userBoard.length/2  && xCoord >= -userBoard.length/2) {
                                        if (yCoord <= userBoard.length/2 && yCoord >= -userBoard.length/2) {
                                            int coordinatePlaced = guessSpot(xCoord, yCoord); //set a variable temporarily to check if it successfully places the coordinate and if there is a win
                                            if(coordinatePlaced == 2){ // if there is a winner, set winner to true
                                                winner = true;
                                            }
                                        }
                                        xCoord += run;
                                        yCoord += rise;
                                    }
                                    xCoord = 0;
                                    yCoord = yInt; //set the beginning values of the x and y coordinates
                                    while (xCoord <= userBoard.length/2 && xCoord >= -userBoard.length/2) {
                                        if (yCoord <= userBoard.length/2 && yCoord >= -userBoard.length/2) {
                                            int coordinatePlaced = guessSpot(xCoord, yCoord); //set a variable temporarily to check if it successfully places the coordinate and if there is a win
                                            if(coordinatePlaced == 2){ // if there is a winner, set winner to true
                                                winner = true;
                                            }
                                        }
                                        xCoord -= run;
                                        yCoord -= rise;
                                    }
                                }
                                else guessInequality();
                                if (winner) {
                                    turnJLabel.setVisible(true);
                                    resetButton.setVisible(true);
                                    try {
                                        boolean startCounter = false;
                                        int numberOfGuesses = 0;
                                        Scanner computerScanner = new Scanner(computerFile);
                                        while (computerScanner.hasNextLine()) { // count the number of guesses by reading the computerHistory text file and display it when the computer wins the game
                                            String data = computerScanner.nextLine();
                                            if (startCounter) {
                                                numberOfGuesses++;
                                            }
                                            if (data.equals("Computer's Guesses:")) {
                                                startCounter = true;
                                            }
                                        }
                                        numberOfGuesses++;
                                        turnJLabel.setText("<html>Computer Wins!<br/>Guesses: " + numberOfGuesses + "<html/>");
                                        computerScanner.close();
                                    } catch (Exception e){}
                                    score.computerWins();
                                    timer3.cancel(); //if there is a winner stop the timer prevent the game from continuing
                                }
                                else { // if there is no error, increment counter
                                    count++; 
                                }
                            }
                        }
                    }
                }
                else { // last iteration
                    if (difficulty == 3) {
                        for (int i = 0; i < userBoard.length; i++) {
                            for (int j = 0; j < userBoard.length;j++) {
                                inequalityCheck[i][j].setOpaque(false);
                            }
                        }
                    }
                    playerTurn++; // increment player turn
                    turnJLabel.setText("Your Turn");
                    repaint();
                    if (difficulty == 1) { // if the difficulty is the coordinate version
                        coordinateField.setText(""); // set the text fields need to focusable
                        coordinateField.setBackground(Color.WHITE);
                        coordinateField.setFocusable(true);
                    }
                    if (difficulty == 2) { // if the difficulty is the slope version
                        riseTextField.setText(""); // set the text fields need to focusable
                        runTextField.setText("");
                        yIntTextField.setText("");
                        riseTextField.setBackground(Color.WHITE);
                        riseTextField.setFocusable(true);
                        runTextField.setBackground(Color.WHITE);
                        runTextField.setFocusable(true);
                        yIntTextField.setBackground(Color.WHITE);
                        yIntTextField.setFocusable(true);
                    }
                    if (difficulty == 3) {
                        equationLabel.setText("y = — x + o");
                        riseTextField.setText(""); // set the text fields need to focusable
                        runTextField.setText("");
                        yIntTextField.setText("");
                        riseTextField.setBackground(Color.WHITE);
                        riseTextField.setFocusable(true);
                        runTextField.setBackground(Color.WHITE);
                        runTextField.setFocusable(true);
                        yIntTextField.setBackground(Color.WHITE);
                        yIntTextField.setFocusable(true);
                        lessThanButton.setEnabled(true);
                        greaterThanButton.setEnabled(true);
                        equalButton.setEnabled(true);
                        lessThanEqualButton.setEnabled(true);
                        greaterThanEqualButton.setEnabled(true);
                    }
                    timer3.cancel();
                }
                
            }
        };
        timer3.scheduleAtFixedRate(task3, 4000, 3000); // create a timer with a delay of 4 seconds, and will run every 3 seconds
    }
}