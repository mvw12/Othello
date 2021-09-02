package reversi;

import java.awt.Color;
import java.io.File;
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
 * Student Name: Maanav Vijay WADHWA
 * Student ID  : 1155138112
 * Date        : 26/11/2020
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
     * loadBoard method used to read the file
     * @param filename - name of the file
     * @throws Exception - in case file name is invalid
     */
    public void loadBoard(String filename) throws Exception
    {
        try
        {
            String s;
            Scanner keyboard = new Scanner(new File(filename));
            gameBoard.addText("Loaded board from " + filename);
            System.out.println("Loaded board from " + filename);
            for(int i = 0; i <= 8; i++)
            {
                int j = 0;
                s = keyboard.nextLine();
                if(i == 8)
                {
                    if(s.charAt(0) == UNICODE_BLACK_CIRCLE)
                        currentPlayer = BLACK;
                    else if(s.charAt(0) == UNICODE_WHITE_CIRCLE)
                        currentPlayer = WHITE;
                }
                else
                {
                    while(j < 8)
                    {
                            if(s.charAt(j) == UNICODE_BLACK_CIRCLE)
                                pieces[i+1][j+1] = BLACK;
                            else if(s.charAt(j) == UNICODE_WHITE_CIRCLE)
                                pieces[i+1][j+1] = WHITE;
                            else
                                pieces[i+1][j+1] = EMPTY;
                        j++;
                    }
                }
            }
            gameBoard.updateStatus(pieces, currentPlayer);
        }
        catch(Exception e)
        {
            gameBoard.addText("Cannot load board from " + filename);
            System.out.println("Cannot load board from " + filename);
            setUpBoardGame();
        }
        // 1) prepare an empty board
        
        // 2) load board and current player from file in UTF-8 Charset encoding
            
        // 3) display successful messages and update game status on screen
           // gameBoard.addText("Loaded board from " + filename);
           // System.out.println("Loaded board from " + filename);
           // gameBoard.updateStatus(pieces, currentPlayer);

        // 4) in case of any Exception:
          //  gameBoard.addText("Cannot load board from " + filename);
           // System.out.println("Cannot load board from " + filename);
            // you may implement a method to setupBoardDefaultGame();
    }

    
    /**
     * saveBoard method used to update and save the file
     * with current settings of the game
     * @param filename - name of the file
     * @throws Exception - in case file name is invalid
     */
    // STUDENTS' WORK HERE    
    public void saveBoard(String filename)
    {
        try
        {
            PrintStream newFile = new PrintStream(filename, "UTF-8");
            for(int i = 1; i <= 8; i++)
            {
                for(int j = 1; j <= 8; j++)
                {
                    if(pieces[i][j] == BLACK)
                        newFile.print(UNICODE_BLACK_CIRCLE);
                    else if(pieces[i][j] == WHITE)
                        newFile.print(UNICODE_WHITE_CIRCLE);
                    else
                        newFile.print(UNICODE_WHITE_SMALL_SQUARE);
                }
                newFile.println();
            }
            if(currentPlayer == BLACK)
                newFile.print(UNICODE_BLACK_CIRCLE);
            else
                newFile.print(UNICODE_WHITE_CIRCLE);
            
            gameBoard.addText("Saved board to " + filename);
            System.out.println("Saved board to " + filename);
            newFile.close();
        }
        catch(Exception e)
        {
            gameBoard.addText("Cannot save board to " + filename);
            System.out.println("Cannot save board to " + filename);
        }
        // 1) open/overwrite a file for writing text in UTF-8 Charset encoding

        // 2) save board to the file on 8 lines of 8 Unicode char on each line

        // 3) save current player on line 9 and display successful messages

           // gameBoard.addText("Saved board to " + filename);
            // System.out.println("Saved board to " + filename);

        // 4) in case of any Exception:
           // gameBoard.addText("Cannot save board to " + filename);
            //System.out.println("Cannot save board to " + filename);
    }
    
    /**
     * method used to set up a default game when 
     * an invalid file name is provided
     */
    public void setUpBoardGame()
    {
        for(int i = 1; i <= 8; i++)
        {
            for(int j = 1; j <= 8; j++)
            {
                pieces[i][j] = EMPTY;
            }
        }
        pieces[4][4] = WHITE;
        pieces[4][5] = BLACK;
        pieces[5][4] = BLACK;
        pieces[5][5] = WHITE;
        currentPlayer = BLACK;
        gameBoard.updateStatus(pieces, currentPlayer);
    }
    
    
    // STUDENTS' WORK HERE    
    /**
     * Override sayGoodbye method of super class, to save board
     */
    // ...
    // {
        // as usual, sayGoodbye...
        
        // ask for save filename
        // String filename = JOptionPane.showInputDialog("Save board filename");
        
        // save board to file
        // ...
    // }

    @Override 
    protected void sayGoodbye()
    {
        System.out.println("Goodbye!");
        String filename = JOptionPane.showInputDialog("Save board filename");
        if(filename != null)
            saveBoard(filename);
    }
    
    // STUDENTS' WORK HERE    
    // main() method, starting point of subclass ReversiOnFile
    public static void main(String[] args) 
    {
        ReversiOnFile game = new ReversiOnFile();
        
        
        // comment or remove the following statements for real game play
//        game.setupDebugBoardEndGame();
//        game.saveBoard("game4.txt");
//        game.setupDebugBoardMidGame();
//        game.saveBoard("game8.txt");
        // end of sample/ debugging code
        
        
        
        // ask for load filename
        String filename = JOptionPane.showInputDialog("Load board filename");
        
        try
        {
            game.loadBoard(filename);
        }
        catch (Exception e)
        {
            game.setUpBoardGame();
        }
        // load board from file
        // ...
    }
}
