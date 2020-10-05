package gamelogic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

/*
 * This class includes all the game-related methods that we need to
 * call from ui elements for the games module.
 * HOW TO USE:
 * 	shuffle shuffles entire questionbank, so that first 5 categories are random,
 * 	and first 5 questions from each category is random.
 * 
 * 	Then buttons from GUI will call ask(0-4, 0,4) and answer(0-4,0-4)
 * 
 *  //TESTING - USE CASE
            GameBoard gBoard = new GameBoard();
            System.out.println(gBoard.getCategoryNames());
            gBoard.ask(0,0);
            
            if (gBoard.answer(0,0,"bumbum")) {
            	System.out.println("GOOD");
            }
            else System.out.println("BAD");
            
 */
public class GameBoard implements Serializable {
	private static final long serialVersionUID = 1L;
	int _winnings = 0;
	QuestionBank _qBank;
	// randomly selected categories/questions
	// first row is category indices
	// second-6th row is question indices
	boolean[][] _completed = new boolean[5][5]; // 0=incomplete.1=completed.

	/**
	 * Gets a shuffled question bank of all questions on file
	 */
	public GameBoard() {
		_qBank = new QuestionBank();
		// Shuffles everything
		_qBank.shuffle();
		// Load any saved fields
		loadState();
	}

	/**
	 *  Passes call to question bank, to ask a question
	 * @param qIndex - desire question index
	 * @param catIndex - desired category index
	 * @return - string of the clue for user
	 */
	public String ask(int qIndex, int catIndex) {
		return _qBank.ask(catIndex, qIndex);
	}

	/**
	 *  Checks if the supplied answer is correct, and changes winnings accordingly
	 */
	public boolean answer(int qIndex, int catIndex, String answer) {
		boolean wasCorrect = _qBank.answer(catIndex, qIndex, answer);
		if (wasCorrect) {
			_winnings += qIndex * 100;
		}
		_completed[qIndex][catIndex] = true;
		return wasCorrect;
	}

	/**
	 * Reads out a desired string message to user
	 * @param message - string they want read out
	 */
	public void say(String message) {
		TtsHandler speaker = new TtsHandler();
		speaker.say(message);
	}
	
	/*
	 * if fresh instance, do nothing. if saved state exists, update _winnings,
	 * update _completed, to be used to remember previous games
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
				c.printStackTrace();
				return;
			}

		}
		else
			return;
	}

	/**
	 * Saves all fields to be used later if user exits to menu and returns to game
	 */
	public void saveState() {
		try {
			FileOutputStream fileOut = new FileOutputStream("/tmp/game-state.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();

		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	/**
	 * Resets all information and deletes any save data
	 */
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
	 * returns 2d boolean matrix, where true=completed and false=incomplete
	 */
	public boolean[][] getCompleted() {
		return _completed;
	}

	/**
	 * Checks if a specific question has already been answered
	 * @param question - desired question index
	 * @param category - desired category index
	 * @return boolean, true if answer has been completed
	 */
	public boolean isCompleted(int question, int category) {
		return _completed[question][category];
	}
	
	/**
	 * Gets the prompt for user to see. Both params are indices.
	 * @param question
	 * @param category
	 * @return
	 */
	public String getPrompt(int question, int category) {
		return _qBank.getPrompt(question, category);
	}

	/**
	 * Sets a question as having been attempted
	 * @param question - desired question
	 * @param category - of desired category
	 */
	public void makeCompleted(int question, int category) { _completed[question][category] = true; }

	/**
	 * Returns just the first 5 categories on the list to display on the board
	 * @return a list of the names of 5 categories
	 */
	public List<String> getCategoryNames() {
		return _qBank.getFirst5Cat();
	}
	
	/**
	 * Getter method
	 * @return questionBank
	 */
	private QuestionBank getQBank() {
		return _qBank;
	}

}
