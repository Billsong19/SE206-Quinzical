package gamelogic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/*
 * This class includes all the game-related methods that we need to
 * call from ui elements for the games module.
 * HOW TO USE:
 * 	shuffle shuffles entire questionbank, so that first 5 categories are random,
 * 	and first 5 questions from each category is random.
 * 
 * 	Then buttons will call ask(0-4, 0,4) and answer(0-4,0-4)
 * 
 * Here's some basic code i was using to test
 *  //TESTING
            GameBoard gBoard = new GameBoard();
            System.out.println(gBoard.getCategoryNames());
            gBoard.ask(0,0);
            
            if (gBoard.answer(0,0,"bumbum")) {
            	System.out.println("GOOD");
            }
            else System.out.println("BAD");
            
 */
public class GameBoard implements Serializable {
	int _winnings = 0;
	QuestionBank _qBank;
	// randomly selected categories/questions
	// first row is category indices
	// second-6th row is question indices
	boolean[][] _completed = new boolean[5][5]; // 0=incomplete.1=completed.

	public GameBoard() {
		_qBank = new QuestionBank();
		_qBank.shuffle();
		loadState();
	}

	public void ask(int qIndex, int catindex) {
		_qBank.ask(catindex, qIndex);
	}

	public boolean answer(int qIndex, int catIndex, String answer) {
		boolean wasCorrect = _qBank.answer(catIndex, qIndex, answer);
		if (wasCorrect) {
			_winnings += qIndex * 100;
		}
		_completed[qIndex][catIndex] = true;
		return wasCorrect;
	}

	
	/*
	 * if fresh instance, do nothing. if saved state exists, update _winnings,
	 * update _completed,
	 */
	public void loadState() {
		File f = new File("/tmp/game-state.ser");
		if (f.exists() && !f.isDirectory()) {
			// do something
			try {
				FileInputStream fileIn = new FileInputStream("/tmp/game-state.ser");
				ObjectInputStream in = new ObjectInputStream(fileIn);
				GameBoard loadedGBoard = (GameBoard) in.readObject();
				_winnings = loadedGBoard.getWinnings();
				_completed = loadedGBoard.getCompleted();
				_qBank = loadedGBoard.getQBank();
				in.close();
				fileIn.close();
			} catch (IOException i) {
				i.printStackTrace();
				return;
			} catch (ClassNotFoundException c) {
				System.out.println("game-state not found");
				c.printStackTrace();
				return;
			}

		}
		else
			return;
	}

	public void saveState() {
		try {
			FileOutputStream fileOut = new FileOutputStream("/tmp/game-state.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();

			System.out.println("data saved to game-state.ser");

		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public void reset() {
		File file = new File("/tmp/game-state.ser");
		file.delete();
		_winnings=0;
		_completed = new boolean[5][5];
		_qBank.shuffle();
	}
	
//GETTERS
	public int getWinnings() {
		return _winnings;
	}

	/*
	 * returns 2d boolean matrix, where 1=completed and 0=incomplete
	 */
	public boolean[][] getCompleted() {
		return _completed;
	}

	public List<String> getCategoryNames() {
		return _qBank.getFirst5Cat();
	}
	
	private QuestionBank getQBank() {
		return _qBank;
	}
//TESTING, IGNORE.
//	 GameBoard gBoard = new GameBoard();
//     System.out.println(gBoard.getCategoryNames());
//     gBoard.ask(0,0);
//     
//     if (gBoard.answer(0,0,"bumbum")) {
//     	System.out.println("GOOD");
//     }
//     else System.out.println("BAD");
//     
//     System.out.println(Arrays.deepToString(gBoard.getCompleted()));
//     
////     gBoard.saveState();
//     gBoard.getQBank().shuffle();
//     System.out.println(gBoard.getCategoryNames());
//     System.out.println(Arrays.deepToString(gBoard.getCompleted()));
//     gBoard.loadState();
//     System.out.println(gBoard.getCategoryNames());
//     System.out.println(Arrays.deepToString(gBoard.getCompleted()));
//     
//     System.out.println(Arrays.deepToString(gBoard.getCompleted()));
//     System.out.println(gBoard.getCategoryNames());
}
