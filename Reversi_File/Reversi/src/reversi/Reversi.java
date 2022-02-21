package reversi;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 * CSCI1130 Java Assignment
 * Reversi board game
 * 
 * Students shall complete this class to implement the game.
 * There are debugging, testing and demonstration code for your reference.
 * 
 * I declare that the assignment here submitted is original
 * except for source material explicitly acknowledged,
 * and that the same or closely related material has not been
 * previously submitted for another course.
 * I also acknowledge that I am aware of University policy and
 * regulations on honesty in academic work, and of the disciplinary
 * guidelines and procedures applicable to breaches of such
 * policy and regulations, as contained in the website.
 *
 * University Guideline on Academic Honesty:
 *   http://www.cuhk.edu.hk/policy/academichonesty
 * Faculty of Engineering Guidelines to Academic Honesty:
 *   https://www.erg.cuhk.edu.hk/erg/AcademicHonesty
 *
 * Student Name: Lam Hiu Ching
 * Student ID  : 1155129247
 * Date        : 2020/12/07
 * 
 * @author based on skeleton code provided by Michael FUNG
 */
public class Reversi {

    // pre-defined class constant fields used throughout this app
    public static final int BLACK = -1;
    public static final int WHITE = +1;
    public static final int EMPTY =  0;
    
    // a convenient constant field that can be used by students
    public final int FLIP  = -1;
    
    // GUI objects representing and displaying the game window and game board
    protected JFrame window;
    protected ReversiPanel gameBoard;
    protected Color boardColor = Color.GREEN;

    
    // a 2D array of pieces, each piece can be:
    //  0: EMPTY/ unoccupied/ out of bound
    // -1: BLACK
    // +1: WHITE
    protected int[][] pieces;
    
    
    // currentPlayer:
    // -1: BLACK
    // +1: WHITE
    protected int currentPlayer;

    
    
    // STUDENTS may declare other fields HERE
    protected int pass = 0;
    
    
    /**
     * The only constructor for initializing a new board in this app
     */
    public Reversi() {
        window = new JFrame("Reversi");
        gameBoard = new ReversiPanel(this);
        window.add(gameBoard);
        window.setSize(850, 700);
        window.setLocation(100, 50);
        window.setVisible(true);
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // use of implicitly extended inner class with overriden method, advanced stuff
        window.addWindowListener(
            new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e)
                {
                    sayGoodbye();
                }
            }
        );


        // a 8x8 board of pieces[1-8][1-8] surrounded by an EMPTY boundary of 10x10 
        pieces = new int[10][10];
        
        pieces[4][4] = WHITE;
        pieces[4][5] = BLACK;
        pieces[5][4] = BLACK;
        pieces[5][5] = WHITE;

        currentPlayer = BLACK;  // black plays first
        
        gameBoard.updateStatus(pieces, currentPlayer);
    }

    
    
    /**
     * setupDebugBoard for testing END-game condition
     * students can freely make any changes to this method for testing purpose
     * TEMPORARY testing case
     */
    protected void setupDebugBoardEndGame()
    {
        gameBoard.addText("setupDebugBoardEndGame():");

        for (int row = 1; row <= 8; row++)
            for (int col = 1; col <= 8; col++)
                pieces[row][col] = BLACK;
        pieces[5][8] = WHITE;
        pieces[6][8] = EMPTY;
        pieces[7][8] = EMPTY;
        pieces[8][8] = EMPTY;

        currentPlayer = BLACK;  // BLACK plays first
        
        gameBoard.updateStatus(pieces, currentPlayer);
    }


    
    /**
     * setupDebugBoard for testing MID-game condition
     * students can freely make any changes to this method for testing purpose
     * TEMPORARY testing case
     */
    protected void setupDebugBoardMidGame()
    {
        gameBoard.addText("setupDebugBoardMidGame():");

        int row, col, distance;
        
        // make all pieces EMPTY
        for (row = 1; row <= 8; row++)
            for (col = 1; col <= 8; col++)
                pieces[row][col] = EMPTY;
        
        // STUDENTS' TEST and EXPERIMENT
        // setup a star pattern as a demonstration, you may try other setups
        // relax, we will NOT encounter array index out of bounds, see below!!
        row = 5;
        col = 3;
        distance = 3;
        
        // beware of hitting the boundary or ArrayIndexOutOfBoundsException
        for (int y_dir = -1; y_dir <= +1; y_dir++)
            for (int x_dir = -1; x_dir <= +1; x_dir++)
            {
                try {
                    int move;
                    // setup some opponents
                    for (move = 1; move <= distance; move++)
                        pieces[row + y_dir * move][col + x_dir * move] = BLACK;

                    // far-end friend piece
                    pieces[row+y_dir * move][col + x_dir*move] = WHITE;
                }
                catch (ArrayIndexOutOfBoundsException e)
                {
                    // intentionally do nothing in this catch block
                    // this is simple and convenient in guarding array OOB
                }
            }
        // leave the center EMPTY for the player's enjoyment
        pieces[row][col] = EMPTY;
        
        // pieces[row][col] = 999;  // try an invalid piece

        
        // restore the fence of 10x10 EMPTY pieces around the 8x8 game board
        for (row = 1; row <= 8; row++)
            pieces[row][0] = pieces[row][9] = EMPTY;
        for (col = 1; col <= 8; col++)
            pieces[0][col] = pieces[9][col] = EMPTY;

        
        currentPlayer = WHITE;  // WHITE plays first
        // currentPlayer = 777;    // try an invalid player
        
        gameBoard.updateStatus(pieces, currentPlayer);
    }
    
    
    
    // STUDENTS are encouraged to define other instance methods here
    // to aid the work of the method userClicked(row, col)
    
    
    
    /**
     * STUDENTS' WORK HERE
     * 
     * As this is a GUI application, the gameBoard object (of class ReversiPanel)
     * actively listens to user's actionPerformed.  On user clicking of a
     * ReversiButton object, this callback method will be invoked to do some
     * game processing.
     * 
     * @param row is the row number of the clicked button
     * @param col is the col number of the clicked button
     */
    public void userClicked(int row, int col)
    {
        // major operation of this method:
        // make a valid move by placing a piece at [row][col]
        // AND flipping some opponent piece(s) in all available directions
        int check = 1;
        
        // check for invalid move, i.e., a cell is occupied or
        // a move that cannot take any opponent pieces
        if ((pieces[row][col] != EMPTY) || captureCheck(row, col, check) == false){
            // error message when the game is undergoing
            if (pass < 2)
                gameBoard.addText("Invalid move");
            // error message when the game ended after the double forced pass
            else
                gameBoard.addText("Invalid move. The game is ended.");
        }
        else{       
        // sample statements for students' reference and self-learning
            pieces[row][col] = currentPlayer;
            currentPlayer = FLIP * currentPlayer;
            gameBoard.updateStatus(pieces, currentPlayer);
        }
        
        // check and handle forced pass
        if (forcedPassCheck()){
            // check whether the first forced pass
            if (pass == 0){  
                pass = 1;
                gameBoard.addText("Forced Pass");
                currentPlayer = FLIP * currentPlayer;
                gameBoard.updateStatus(pieces, currentPlayer);
                // check and handle double forced pass
                // check whether the next move of opponent is double forced pass
                if (forcedPassCheck()){
                    gameBoard.addText("Double Forced Pass");
                    gameBoard.addText("End game!");
                    pass++;
                }
            }
            // check and handle double forced pass
            else if (pass == 1){
                gameBoard.addText("Double Forced Pass");
                gameBoard.addText("End game!");
                pass++;
            }
        }
            
        
    }
    
    /**
     * flip is method to filp opponent's pieces after the move
     * @param d the array stores number of opponent's pieces could be filped in 8 direction
     * @param r row number of the clicked button
     * @param c column number of the clicked button
     */
    public void flip(int d[], int r, int c){
        //variables for counting
        int a, b;
        
        //North
        // comfirm at least 1 pieces captured in this direction
        if (d[0] != 0){
            a = r - 1;
            b = c;
            //filp opponents' pieces
            for (int i = 1; i <= d[0]; i++){
                pieces[a][b] = currentPlayer;
                a--;
            }
        }
        //same flow for the following 7 if statement 
        
        //NorthEast
        if (d[1] != 0){
            a = r - 1;
            b = c + 1;
            for (int i = 1; i <= d[1]; i++){
                pieces[a][b] = currentPlayer;
                a--;
                b++;
            }
        }       
        
        //East
        if (d[2] != 0){
            a = r;
            b = c + 1;
            for (int i = 1; i <= d[2]; i++){
                pieces[a][b] = currentPlayer;
                b++;
            }
        }
        
        //SouthEast
        if (d[3] != 0){
            a = r + 1;
            b = c + 1;
            for (int i = 1; i <= d[3]; i++){
                pieces[a][b] = currentPlayer;
                a++;
                b++;
            }
        }
        
        //South
        if (d[4] != 0){
            a = r + 1;
            b = c;
            for (int i = 1; i <= d[4]; i++){
                pieces[a][b] = currentPlayer;
                a++;
            }
        }
        
        //South West
        if (d[5] != 0){
            a = r + 1;
            b = c - 1;
            for (int i = 1; i <= d[5]; i++){
                pieces[a][b] = currentPlayer;
                a++;
                b--;
            }
        }
        
        //West
        if (d[6] != 0){
            a = r;
            b = c - 1;
            for (int i = 1; i <= d[6]; i++){
                pieces[a][b] = currentPlayer;
                b--;
            }
        }
        
        //NorthWest
        if (d[7] != 0){
            a = r - 1;
            b = c - 1;
            for (int i = 1; i <= d[7]; i++){
                pieces[a][b] = currentPlayer;
                a--;
                b--;
            }
        }
    }
   
    /**
     * valid means capture at least one of the opponent's pieces in any direction among the 8 avaliable direction
     * would be called by 2 other method, so declare a variable called check to determine which method called
     * @param r row number of the clicked button
     * @param c column number of the clicked button
     * @param check
     * check = 1 mean called by userClicked()
     * check = 2 mean called by forcedPassCheck()
     * @return whether the move on the chosen pieces is valid
     */
    public boolean captureCheck(int r, int c, int check){
        // boolean variable for return
        boolean valid = false;
        // direction array
        int[] direction = new int[8];
        /**
         * direction[i]:
         * i = direction
         * 0 = North
         * 1 = NorthEast
         * 2 = East
         * 3 = SouthEast
         * 4 = South
         * 5 = South West
         * 6 = West
         * 7 = NorthWest
         * 
         * value of direction[i] = number of pieces flipped
         */
        // initial the direction array
        for (int i = 0; i < 8; i++) direction[i] = 0;
        // variables for counting
        int a, b, count;
        
        
               
        //North
        if (pieces[r-1][c] == currentPlayer *-1){
            count = 1;
            for (int i = r - 2; i >= 1; i--){
                // if there is empty pieces before encounter the player's pieces, invalid
                if (pieces[i][c] == EMPTY)
                    break;
                // find another player's pieces in this direction
                else if (pieces[i][c] == currentPlayer){
                    direction[0] = count;
                    valid = true;
                    break;
                }
                // count for how many pieces could be capture befor encounter the player's pieces
                // if no another player's pieces found in this direction, count number would not save in direction array
                else count++;
            }
        }
        
        // same flow for the following 7 if statement 
        
        //NorthEast
        if (pieces[r-1][c+1] == currentPlayer * -1){
            a = r - 2;
            b = c + 2;
            count = 1;
            while ((a >= 1) && (b <= 8)){
                if (pieces[a][b] == EMPTY)
                    break;
                else if (pieces[a][b] == currentPlayer){
                    direction[1] = count;
                    valid = true;
                    break;
                }
                else{
                    a--;
                    b++;
                    count++;
                }
            }
        }
        
        //East
        if (pieces[r][c+1] == currentPlayer * -1){
            count = 1;
            for (int i = c + 2; i <= 8; i++){
                if (pieces[r][i] == EMPTY)
                    break;
                else if (pieces[r][i] == currentPlayer){
                    direction[2] = count;
                    valid = true;
                    break;
                }
                else count++;
            }
        }
        
        //SouthEast
        if (pieces[r+1][c+1] == currentPlayer * -1){
            a = r + 2;
            b = c + 2;
            count = 1;
            while ((a <= 8) && (b <= 8)){
                if (pieces[a][b] == EMPTY)
                    break;
                else if (pieces[a][b] == currentPlayer){
                    direction[3] = count;
                    valid = true;
                    break;
                }
                else{
                    a++;
                    b++;
                    count++;
                }
            }
        }
        
        //South
        if (pieces[r+1][c] == currentPlayer * -1){
            count = 1;
            for (int i = r + 2; i <= 8; i++){
                if (pieces[i][c] == EMPTY)
                    break;
                else if (pieces[i][c] == currentPlayer){
                    direction[4] = count;
                    valid = true;
                    break;
                }
                else count++;
            }
        }
        
        //SouthWest
        if (pieces[r+1][c-1] == currentPlayer * -1){
            a = r + 2;
            b = c - 2;
            count = 1;
            while ((a <= 8) && (b >= 1)){
                if (pieces[a][b] == EMPTY)
                    break;
                else if (pieces[a][b] == currentPlayer){
                    direction[5] = count;
                    valid = true;
                    break;
                }
                else{
                    a++;
                    b--;
                    count++;
                }
            }
        }
        
        //West
        if (pieces[r][c-1] == currentPlayer * -1){
            count = 1;
            for (int i = c - 2; i >= 1; i--){
                if (pieces[r][i] == EMPTY)
                    break;
                else if (pieces[r][i] == currentPlayer){
                    direction[6] = count;
                    valid = true;
                    break;
                }
                else count++;
            }
        }
        
        //NorthWest
        if (pieces[r-1][c-1] == currentPlayer * -1){
            a = r - 2;
            b = c - 2;
            count = 1;
            while ((a >= 1) && (b >= 1)){
                if (pieces[a][b] == EMPTY)
                    break;
                else if (pieces[a][b] == currentPlayer){
                    direction[7] = count;
                    valid = true;
                    break;
                }
                else{
                    a--;
                    b--;
                    count++;
                }
            }
        }
        
        /**
         *   if this method is called by forcedPassCheck()(result = 2)
         *   ,do not required to filp opponent's pieces
         *   therefore, filp() would be skipped when result == 2
         */
        if (check == 1) flip(direction, r, c);              
        return valid;
    }
    
    /**
     * @return whether current player should be forced to pass
     */
    public boolean forcedPassCheck(){
        boolean p = true;
        // variable for captureCheck()
        int check = 2;
        System.out.println("forcedPassChecking...");
        // check whether there are valid move on the board
        for (int i = 1; i <= 8; i++){
            for (int j = 1; j <= 8; j++){
                if (pieces[i][j] == EMPTY){
                    // if there is at least one valid move, no forced pass
                    if (captureCheck(i, j, check)){
                        p = false;
                    }
                }
            }
        }
        return p;
    }

    
    
    /**
     * sayGoodbye on System.out, before program termination
     */
    protected void sayGoodbye()
    {
        System.out.println("Goodbye!");
    }

    
    
    // main() method, starting point of basic Reversi game
    public static void main(String[] args) {
        Reversi game = new Reversi();
        
        // comment or remove the following statements for real game play
//        game.setupDebugBoardEndGame();
//        game.setupDebugBoardMidGame();
        // end of sample/ debugging code

        
        // *** STUDENTS need NOT write anything here ***
        
        // this application still runs in the background!!
        // the gameBoard object (of class ReversiPanel) listens and handles
        // user interactions as well as invoking method userClicked(row, col)
        
        // although this is end of main() method
        // THIS IS NOT THE END OF THE APP!
    }
}
