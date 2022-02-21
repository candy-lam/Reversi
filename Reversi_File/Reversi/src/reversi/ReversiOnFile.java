package reversi;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * ReversiOnFile is a subclass of Reversi, adding File I/O capabilities
 * for loading and saving game.
 *
 * I declare that the work here submitted is original
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
 */
public class ReversiOnFile extends Reversi {
    
    public static final char UNICODE_BLACK_CIRCLE = '\u25CF';
    public static final char UNICODE_WHITE_CIRCLE = '\u25CB';
    public static final char UNICODE_WHITE_SMALL_SQUARE = '\u25AB';
    
    // constructor to give a new look to new subclass game
    public ReversiOnFile()
    {
        window.setTitle("ReversiOnFile");
        gameBoard.setBoardColor(Color.BLUE);
    }


    // STUDENTS' WORK HERE 
    /**
     * To load a board from file given by player
     * @param filename which input by player from main()
     * @throws Exception  
     */
    public void loadBoard(String filename) throws Exception
    {
        // 1) prepare an empty board
        for (int i = 0; i <= 8; i++){
            for (int j = 0; j <= 8; j++){
                pieces[i][j] = EMPTY;
            }
        } 
        
        //situation when player does not input anything on filename
        if (filename == null){
            gameBoard.addText("Cannot load board from " + filename);
            System.out.println("Cannot load board from " + filename);
            setupBoardDefaultGame(); 
            return;
        }
        
        // 2) load board and current player from file in UTF-8 Charset encoding
        try{
            String path = "C:" + File.separator + "Users" + File.separator + "candy" + File.separator + "Desktop" + File.separator + "CUHK" + File.separator + "CSCI1130" + File.separator + "Reversi_File" + File.separator + "Reversi" + File.separator + filename;
            System.out.println(path);
            Scanner reader;
            reader = new Scanner(new File(path));                            
            String[] line = new String[9];
            int i = 0;
            // read file line by line first
            while (reader.hasNextLine()){
                line[i] = reader.nextLine();
                for (int j = 0; j < 8; j++){
                    if (i != 8){            
                        // break the line by char and check which symbol it is
                        if ((line[i].substring(j,j+1)).equals("\u25CB"))
                            // change the value of pieces according to the file
                            pieces[i+1][j+1] = WHITE;
                        else if ((line[i].substring(j,j+1)).equals("\u25CF"))
                            pieces[i+1][j+1] = BLACK;
                        else if ((line[i].substring(j,j+1)).equals("\u25AB"))
                            pieces[i+1][j+1] = EMPTY;
                    }
                    else{
                        // check who is the current player in the file
                        if ((line[i].substring(j,j+1)).equals("\u25CB"))
                            currentPlayer = WHITE;
                        else if ((line[i].substring(j,j+1)).equals("\u25CF"))
                            currentPlayer = BLACK;
                        // 3) display successful messages and update game status on screen
                        gameBoard.addText("Loaded board from " + filename);
                        System.out.println("Loaded board from " + filename);
                        gameBoard.updateStatus(pieces, currentPlayer);
                        
                    }                    
                }
                i++;
            }
        }
        // 4) in case of any Exception:
        catch(FileNotFoundException e){
            gameBoard.addText("Cannot load board from " + filename);
            System.out.println("Cannot load board from " + filename);
            // you may implement a method to setupBoardDefaultGame();
            setupBoardDefaultGame();            
        }
    }

    

    // STUDENTS' WORK HERE    
    /**
     * To save the current board into a file stated by player
     * @param filename which input by player from main()
     * @throws Exception 
     */
    public void saveBoard(String filename) throws Exception
    {
        // 1) open/overwrite a file for writing text in UTF-8 Charset encoding
        try{
            PrintStream saveFile;
            String path = "C:" + File.separator + "Users" + File.separator + "candy" + File.separator + "Desktop" + File.separator + "CUHK" + File.separator + "CSCI1130" + File.separator + "Reversi_File" + File.separator + "Reversi" + File.separator + filename; 
            saveFile = new PrintStream(path);

        // 2) save board to the file on 8 lines of 8 Unicode char on each line
            for (int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++){
                    if (pieces[i+1][j+1] == WHITE)
                        saveFile.print("\u25CB");
                    else if (pieces[i+1][j+1] == BLACK)
                        saveFile.print("\u25CF");
                    else
                        saveFile.print("\u25AB");
            }
                saveFile.println();
            }
        
        // 3) save current player on line 9 and display successful messages
            if (currentPlayer == WHITE)
                saveFile.print("\u25CB");
            else if (currentPlayer == BLACK)
                saveFile.print("\u25CF");
            else{
                gameBoard.addText("Saved board to " + filename);
                System.out.println("Saved board to " + filename);
            }            
        }
        catch (Exception e){
        // 4) in case of any Exception:
            gameBoard.addText("Cannot save board to " + filename);
            System.out.println("Cannot save board to " + filename);
        }
    }
    
    
    /**
     * setupBoardDefaultGame for exception
     */
    public void setupBoardDefaultGame(){
        
        pieces[4][4] = WHITE;
        pieces[4][5] = BLACK;
        pieces[5][4] = BLACK;
        pieces[5][5] = WHITE;

        currentPlayer = BLACK;  // black plays first
        
        gameBoard.updateStatus(pieces, currentPlayer);
    }

    
    // STUDENTS' WORK HERE    
    /**
     * Override sayGoodbye method of super class, to save board
     */
    @Override
    public void sayGoodbye()
    {   
        // as usual, sayGoodbye..
        System.out.println("Goodbye!");
        
        // ask for save filename
        try{
            String filename = JOptionPane.showInputDialog("Save board filename");
        // save board to file
            saveBoard(filename);
            // ...
        }
        catch (Exception e){
            System.out.println("Invalid input.");
        }
        // ...
    }  

            
    // STUDENTS' WORK HERE    
    // main() method, starting point of subclass ReversiOnFile
    public static void main(String[] args){
        ReversiOnFile game = new ReversiOnFile();
        
        
        // comment or remove the following statements for real game play
//        game.setupDebugBoardEndGame();
//        game.saveBoard("game4.txt");
//        game.setupDebugBoardMidGame();
//        game.saveBoard("game8.txt");
        // end of sample/ debugging code     
        
        try{
            // ask for load filename
            String filename = JOptionPane.showInputDialog("Load board filename");
            // load board from file
            game.loadBoard(filename);
            // ...
        }
        catch (Exception e){
            System.out.print("");
        }
    }
}
