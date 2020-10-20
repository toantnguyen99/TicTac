
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class TicTacToeGUImain {

	public static void main(String[] args)
	{	
		SwingUtilities.invokeLater(new Runnable() { //helper: take care of any update of the GUI
			
			@Override
			public void run() {
				
				//display player vs player mode
				TicTacToeGUIClass game2 = new TicTacToeGUIClass();
				game2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				game2.displayBoard2();
				
				//display player vs computer mode
				TicTacToeGUIClass game = new TicTacToeGUIClass();
				game.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				game.displayBoard();		
			}
		});
	}
}
