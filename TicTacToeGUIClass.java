import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class TicTacToeGUIClass extends JFrame{

	private JButton[][] board;
	private int[][] value = new int[3][3]; //determine if slot is contain X(value of 1) or O(value of 4) or empty(value of 0).
	private static String winner;
	private String player, computer;
	private Container pane; //make rectangle
	private boolean hasWinner;//check for winner
	private boolean full; //check for Draw
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem quit;
	private JMenuItem newGame;
	private int playerValue = 1; // player is X and has a value of 1
	private final int COMPUTERVALUE = 4; // computer is O and has a value of 4
	private final int PLAYER2VALUE = 13; //player 2 is O and has a value of 13
	private final int PLAYERWIN = 1*3; //3 X in a line will add up to 3
	private final int COMPUTERWIN = 4*3;// 3 O in a line will add up to 12
	private final int PLAYER2WIN = 13*3;// 3 O in a line will add up to 39
	private final int DRAW = 5*1 + 4*4;// when the game is full/DRAW (5 X, 4 O)
	private final int DRAW2 = 5*1 + 4*13;
	private final int DRAW1 = 4*1 + 5*4;// when the game is full/DRAW (4 X, 5 O)
	private final int DRAW3 = 4*1 + 5*13;

	
	//Constructor
	public TicTacToeGUIClass()
	{
		super();
		pane = getContentPane();
		pane.setLayout(new GridLayout(3,3));	
		setSize(600,600);
		setResizable(false);
		setVisible(true);
		player = "X";
		computer = "O";
		board = new JButton[3][3];
		hasWinner = false;
		full = false;		
		menuBar();//initialize menu bar
	}
	
	//initialize Board
	public void displayBoard()
	{			
		setTitle("Tic Tac Toe(Player vs. Computer)");
		for (int i = 0; i < 3; i++)	
			for (int j = 0; j < 3; j++){
				int k = i;
				int l = j;
				JButton button = new JButton();
				button.setFont(new Font(Font.MONOSPACED, Font.BOLD, 110));
				board[i][j] = button;
				//Display X when click
				button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if(((JButton)e.getSource()).getText().equals("") && hasWinner == false && full == false)
						{
							button.setText(player);
							value[k][l] = playerValue;
							checkWin();
							checkDraw();
							if (hasWinner != true && full != true)
							{
								computerMove();
								displayWinner();
							}
							else
								displayWinner();
						}
					}
				});
				pane.add(button);
		}
		
	}
	
	//Player vs. Player
	public void displayBoard2()
	{
		setTitle("Tic Tac Toe (Player vs Player)");
		for (int i = 0; i < 3; i++)	
			for (int j = 0; j < 3; j++){
				int k = i;
				int l = j;
				JButton button = new JButton();
				button.setFont(new Font(Font.MONOSPACED, Font.BOLD, 110));
				board[i][j] = button;
				//Display X or O when click
				button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if(((JButton)e.getSource()).getText().equals("") && hasWinner == false && full == false)
						{
							button.setText(player);
							value[k][l] = playerValue;
							checkWin();
							checkDraw();
						
							switchPlayer();
							checkWin();
							checkDraw();
							displayWinner();
						}
					}
				});
				pane.add(button);
			}
	}
	
	//Initialize menu bar
	private void menuBar() {
		menuBar = new JMenuBar();
		menu = new JMenu("Options");
		newGame = new JMenuItem("New Game");
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetBoard();				
			}
		});

		quit = new JMenuItem("Quit");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 dispose();
			}
		});
		menu.add(newGame);//put options in menu
		menu.add(quit);
		menuBar.add(menu);//put menu in menu bar
		setJMenuBar(menuBar);//set this menu bar to be the menu bar
	}

	//reset board (change winner and DRAW to false and all square to blank)
	private void resetBoard()
	{
		hasWinner = false;
		full = false;
		for(int i = 0; i < 3; i++)	
			for (int j = 0; j < 3; j++){ 
				board[i][j].setText("");
				value[i][j] = 0;
			}
		
	}
	

	
	//switch X to O or O to X
	private void switchPlayer()
	{
		if (player.equals("X"))
		{
			player = "O";
			playerValue = 13; 
		}
		else
		{
			player = "X";
			playerValue = 1;
		}
	}
	//Computer's move
	private void computerMove()
	{
		boolean notSame = false;
		while (notSame == false)
		{
			int row = (int) (Math.random() * (board.length));
			int col = (int) (Math.random() * (board.length));
			if (value[row][col] != playerValue && value[row][col] != COMPUTERVALUE && value[row][col] != PLAYER2VALUE )
			{
				board[row][col].setText(computer);
				value[row][col] = COMPUTERVALUE;	
				notSame = true;
			}
		}
		checkWin();
		checkDraw();
	}
	

	
	//Check winning  
	private void checkWin()
	{	
		if (checkColWin() || checkRowWin() || checkDiagonalWin())
			hasWinner = true;
	}
	
	//Check if there is a DRAW (board is full, no move left)
	private void checkDraw()
	{
		int total = 0;
		for (int row = 0; row < board.length; row++)
			for (int col = 0; col < board[0].length; col++)
				total += value[row][col];
		if (total == DRAW || total == DRAW1 || total == DRAW2 || total == DRAW3)
			full = true;	
	}
	
	//Display winner or Draw
	private void displayWinner()
	{
		if (hasWinner == true)
			JOptionPane.showMessageDialog(null,"Winner: " + winner ,"We have a winner" , JOptionPane.INFORMATION_MESSAGE);
		else if (full == true)
			JOptionPane.showMessageDialog(null,"Game Draws!" ,"Game Over" , JOptionPane.INFORMATION_MESSAGE);
	}
	
	//Win by column
	private boolean checkColWin()
	{
		boolean win = false;
		int sum;
		for (int col = 0; col < board[0].length; col++)
		{
			sum = 0;
			for (int row = 0; row < board.length; row++)
			{
				sum += value[row][col];
			}
			win = all3Match(win, sum);
		}
		return win;
	}
	
	//Win by row
	private boolean checkRowWin()
	{
		boolean win = false;
		int sum;
		for (int row = 0; row < board.length; row++)
		{
			sum = 0;
			for (int col = 0; col < board[0].length; col++)
			{
				sum += value[row][col];
			}
			win = all3Match(win, sum);
		}
		return win;
	}

	//Win by diagonal
	private boolean checkDiagonalWin()
	{
		boolean win = false;
		int sum = 0;
		//1st diagonal
		for (int rANDc = 0; rANDc < board.length; rANDc++)
			sum += value[rANDc][rANDc];
		win = all3Match(win, sum);
		
		//2nd diagonal
		sum = 0;
		for (int row = board.length-1 , col = 0; row >=0  && col < board.length; row--, col++)
			sum += value[row][col];
		win = all3Match(win, sum);	
		return win;
	}
	
	//all 3 in a line match and who win
	private boolean all3Match(boolean WiN, int SuM)
	{
		if (SuM == PLAYERWIN)
		{
			WiN = true;
			winner = "player X";
		}
		else if (SuM == COMPUTERWIN)
		{
			WiN = true;
			winner = "computer";
		}
		else if (SuM == PLAYER2WIN)
		{
			WiN = true;
			winner = "player O";
		}
		return WiN;
	}	
	
}

