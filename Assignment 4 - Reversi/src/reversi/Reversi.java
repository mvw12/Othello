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
 * Student Name: Maanav Vijay WADHWA
 * Student ID  : 1155138112
 * Date        : 26/11/2020
 * 
 * @author based on skeleton code provided by Michael FUNG
 */
public class Reversi 
{

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
    
    
    
    /**
     * The only constructor for initializing a new board in this app
     */
    public Reversi() 
    {
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
            
        // check and handle forced pass
            // gameBoard.addText("Forced Pass");
            
        // check and handle double forced pass
            // gameBoard.addText("Double Forced Pass");
            // gameBoard.addText("End game!");

        // check for invalid move, i.e., a cell is occupied or
        // a move that cannot take any opponent pieces
            // gameBoard.addText("Invalid move");
        
        // sample statements for students' reference and self-learning
        boolean valid, checker, possible_move, full_board = false;
        // boolean variables checking various conditions before a move can be made
        int i, j, l, m; // loop variables
        int c = 0, d = 0; // loop variables
        int counter = 0; // to determine the direction in which pieces can be flipped
        int opp_colour = FLIP * currentPlayer; // opponent's colour
        valid = captureCheck(row, col);
        if(valid == true)
        {
            pieces[row][col] = currentPlayer;
            for(i = row - 1; i <= row + 1; i++)
            {
                for(j = col - 1; j <= col + 1; j++)
                {
                    l = i;
                    m = j;
                    checker = false;
                    counter++;
                    if((i == row) && (j == col))
                        continue;
                    if(pieces[i][j] == opp_colour)
                    {
                        switch (counter)
                        {
                            case 1: 
                                while((l > 0) && (m > 0))
                                {
                                    if(pieces[l-1][m-1] == EMPTY)
                                        break;
                                    else if(pieces[l-1][m-1] == currentPlayer)
                                    {
                                        c = l - 1;
                                        d = m - 1;
                                        checker = true;
                                        break;
                                    }
                                    else
                                    {
                                        l--;
                                        m--;
                                    }
                                }
                                if(checker == true)
                                {
                                    l = i;
                                    m = j;
                                    while((l > c) && (m > d))
                                    {
                                        pieces[l][m] = currentPlayer;
                                        l--;
                                        m--;
                                    }
                                }
                                break;
                                
                            case 2:
                                while(l > 0)
                                {
                                    if(pieces[l-1][m] == EMPTY)
                                        break;
                                    else if(pieces[l-1][m] == currentPlayer)
                                    {
                                        c = l - 1;
                                        checker = true;
                                        break;
                                    }
                                    else
                                        l--;
                                }
                                if(checker == true)
                                {
                                    l = i;
                                    while(l > c)
                                    {
                                        pieces[l][m] = currentPlayer;
                                        l--;
                                    }
                                }
                                break;
                                
                            case 3:
                                while((l > 0) && (m <= 7))
                                {
                                    if(pieces[l-1][m+1] == EMPTY)
                                        break;
                                    else if(pieces[l-1][m+1] == currentPlayer)
                                    {
                                        c = l - 1;
                                        d = m + 1;
                                        checker = true;
                                        break;
                                    }
                                    else
                                    {
                                        l--;
                                        m++;
                                    }
                                }
                                if(checker == true)
                                {
                                    l = i;
                                    m = j;
                                    while((l > c) && (m < d))
                                    {
                                        pieces[l][m] = currentPlayer;
                                        l--;
                                        m++;
                                    }
                                }
                                break;
                                
                            case 4:
                                while(m > 0)
                                {
                                    if(pieces[l][m-1] == EMPTY)
                                        break;
                                    else if(pieces[l][m-1] == currentPlayer)
                                    {
                                        d = m - 1;
                                        checker = true;
                                        break;
                                    }
                                    else
                                        m--;
                                }
                                if(checker == true)
                                {
                                    m = j;
                                    while(m > d)
                                    {
                                        pieces[l][m] = currentPlayer;
                                        m--;
                                    }
                                }
                                break;
                                
                            case 6:
                                while(m <= 7)
                                {
                                    if(pieces[l][m+1] == EMPTY)
                                        break;
                                    else if(pieces[l][m+1] == currentPlayer)
                                    {
                                        d = m + 1;
                                        checker = true;
                                        break;
                                    }
                                    else
                                        m++;
                                }
                                if(checker == true)
                                {
                                    m = j;
                                    while(m < d)
                                    {
                                        pieces[l][m] = currentPlayer;
                                        m++;
                                    }
                                }
                                break;
                                
                            case 7:
                                while((l <= 7) && (m > 0))
                                {
                                    if(pieces[l+1][m-1] == EMPTY)
                                        break;
                                    else if(pieces[l+1][m-1] == currentPlayer)
                                    {
                                        c = l + 1;
                                        d = m - 1;
                                        checker = true;
                                        break;
                                    }
                                    else
                                    {
                                        l++;
                                        m--;
                                    }
                                }
                                if(checker == true)
                                {
                                    l = i;
                                    m = j;
                                    while((l < c) && (m > d))
                                    {
                                        pieces[l][m] = currentPlayer;
                                        l++;
                                        m--;
                                    }
                                }
                                break;
                                
                            case 8:
                                while(l <= 7)
                                {
                                    if(pieces[l+1][m] == EMPTY)
                                        break;
                                    else if(pieces[l+1][m] == currentPlayer)
                                    {
                                        c = l + 1;
                                        checker = true;
                                        break;
                                    }
                                    else
                                        l++;
                                }
                                if(checker == true)
                                {
                                    l = i;
                                    while(l < c)
                                    {
                                        pieces[l][m] = currentPlayer;
                                        l++;
                                    }
                                }
                                break;
                                
                            case 9:
                                while((l <= 7) && (m <= 7))
                                {
                                    if(pieces[l+1][m+1] == EMPTY)
                                        break;
                                    else if(pieces[l+1][m+1] == currentPlayer)
                                    {
                                        c = l + 1;
                                        d = m + 1;
                                        checker = true;
                                        break;
                                    }
                                    else
                                    {
                                        l++;
                                        m++;
                                    }
                                }
                                if(checker == true)
                                {
                                    l = i;
                                    m = j;
                                    while((l < c) && (m < d))
                                    {
                                        pieces[l][m] = currentPlayer;
                                        l++;
                                        m++;
                                    }
                                }
                                break;
                        }
                    }
                        
                }
            }
            for(int x = 1; x <= 8; x++)
            {
                for(int y = 1; y <= 8; y++)
                {
                    if(pieces[x][y] != EMPTY)
                        full_board = true;
                    else
                    {
                        full_board = false;
                        break;
                    }
                }
                if(full_board == false)
                    break;
            }
            if(full_board == true)
            {
                gameBoard.updateStatus(pieces, currentPlayer);
                gameBoard.addText("End game!");
            }
            else
            {   
                currentPlayer = FLIP * currentPlayer;
                gameBoard.updateStatus(pieces, currentPlayer);
                possible_move = forcedPassCheck();
                if(possible_move == false)
                {
                    gameBoard.addText("Forced Pass");
                    currentPlayer = FLIP * currentPlayer;
                    gameBoard.updateStatus(pieces, currentPlayer);
                    possible_move = forcedPassCheck();
                    if(possible_move == false)
                    {
                        gameBoard.addText("Double Forced Pass");
                        gameBoard.addText("End game!");
                    }
                }
            }
        }
        else
            gameBoard.addText("Invalid move");
    }

    /**
     * forcedPassCheck is a method to determine if there are no more
     * moves possible for a player
     * In this case, the player is forced to pass the turn
     * @return - true/false is returned based on whether the player is forced 
     * to pass or not
     */
    
    public boolean forcedPassCheck()
    {
        boolean valid;
        int i, j;
        for(i = 1; i <= 8; i++)
        {
            for(j = 1; j <= 8; j++)
            {
                if(pieces[i][j] != EMPTY)
                    continue;
                else
                {
                    valid = captureCheck(i, j);
                    if(valid == false)
                        continue;
                    else
                    {
                        valid = true;
                        return valid;
                    }
                }
            }
        }
        valid = false;
        return valid;
    }
    
    /**
     * captureCheck is a method to determine if a move is valid or now
     * @param row - row of the button selected by the user
     * @param col - column of the button selected by the user
     * @return - true/false is returned based on whether a move is valid or not
     */
    
    public boolean captureCheck(int row, int col)
    {
        boolean valid;
        int opp_colour = FLIP * currentPlayer;
        int counter = 0;
        int l, m;
        if(pieces[row][col] != EMPTY)
        {
            System.out.println("Invalid move!");
            valid = false;
            return valid;
        }
        
        for(int i = row - 1; i <= row + 1; i++)
        {
            for(int j = col - 1; j <= col + 1; j++)
            {
                l = i;
                m = j;
                counter++;
                if(pieces[i][j] == opp_colour)
                {
                    switch (counter)
                    {
                        case 1:
                            while((l > 0) && (m > 0))
                            {
                                if(pieces[l-1][m-1] == EMPTY)
                                    break;
                                else if(pieces[l-1][m-1] == currentPlayer)
                                {
                                    valid = true;
                                    return valid;
                                }
                                else
                                {
                                    l--;
                                    m--;
                                }
                            }
                            break;
                    
                        case 2:
                            while(l > 0)
                            {
                                if(pieces[l-1][m] == EMPTY)
                                    break;
                                else if(pieces[l-1][m] == currentPlayer)
                                {
                                    valid = true;
                                    return valid;
                                }
                                else
                                    l--;
                            }
                            break;
                    
                        case 3:
                            while((l > 0) && (m <= 7))
                            {
                                if(pieces[l-1][m+1] == EMPTY)
                                    break;
                                else if(pieces[l-1][m+1] == currentPlayer)
                                {
                                    valid = true;
                                    return valid;
                                }
                                else
                                {
                                    l--;
                                    m++;
                                }
                            }
                            break;
                    
                        case 4:
                            while(m > 0)
                            {
                                if(pieces[l][m-1] == EMPTY)
                                    break;
                                else if(pieces[l][m-1] == currentPlayer)
                                {
                                    valid = true;
                                    return valid;
                                }
                                else
                                    m--;
                            }
                            break;
                    
                        case 6:
                            while(m <= 7)
                            {
                                if(pieces[l][m+1] == EMPTY)
                                    break;
                                else if(pieces[l][m+1] == currentPlayer)
                                {
                                    valid = true;
                                    return valid;
                                }
                                else
                                    m++;
                            }
                            break;
                    
                        case 7:
                            while((l <= 7) && (m > 0))
                            {
                                if(pieces[l+1][m-1] == EMPTY)
                                    break;
                                else if(pieces[l+1][m-1] == currentPlayer)
                                {
                                    valid = true;
                                    return valid;
                                }
                                else
                                {
                                    l++;
                                    m--;
                                }
                            }
                            break;
                    
                        case 8:
                            while(l <= 7)
                            {
                                if(pieces[l+1][m] == EMPTY)
                                    break;
                                else if(pieces[l+1][m] == currentPlayer)
                                {
                                    valid = true;
                                    return valid;
                                }
                                else
                                    l++;
                            }
                            break;
                    
                        case 9:
                            while((l <= 7) && (m <= 7))
                            {
                                if(pieces[l+1][m+1] == EMPTY)
                                    break;
                                else if(pieces[l+1][m+1] == currentPlayer)
                                {
                                    valid = true;
                                    return valid;
                                }
                                else
                                {
                                    l++;
                                    m++;
                                }
                            }
                            break;
                    }
                }
            }
        }
        valid = false;
        return valid;
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
  //      game.setupDebugBoardEndGame();
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
