package gamelogic;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Randomisation--> shuffling qBank, we can mess with qBank any way we want
 * since practice module will use different qBank instance.
 */
public class QuestionBank implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Category> _categories = new ArrayList<Category>();
	
	public void saveState() {}
	public void loadState() {}
	
	public QuestionBank() {
		File directory = new File("categories");
		   File categoryList[] = directory.listFiles();
		   for (File category: categoryList)
		   {
			   _categories.add(new Category(category));
		   }
	}
	
	public String ask(int CatIndex, int QstnIndex) {
		//access categorylist, ask question
		return _categories.get(CatIndex).ask(QstnIndex);
	}
	
	public boolean answer(int CatIndex, int qstnindex, String answer)
	{
		boolean correct = _categories.get(CatIndex).answer(qstnindex, answer);
		return correct;
	}
	
	public void shuffle() {
		Collections.shuffle(_categories);
		for (Category cat: _categories) {
			cat.shuffle();
		}
	}
	
	public void shuffleCat(String category) {
		for (Category cat: _categories) {
			if (cat.getName().equals(category))
			{
				cat.shuffle();
			}
		}
	}
	
	public List<String> getFirst5Cat(){
		List<String> output = new ArrayList<String>();
		for (int i=0; i<=4;i++) {
			output.add(_categories.get(i).getName());
		}
		return output;
	}
}