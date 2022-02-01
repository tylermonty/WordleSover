package wordleSolver;

public class WordScore implements Comparable<WordScore>{
	private String word;
	private float score;
	
	public WordScore(String word, float score) {
		this.word = word;
		this.score = score;
	}
	
	public String getWord() {
		return this.word;
	}
	
	public float getScore() {
		return this.score;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
	public void setScore(float score){
		this.score = score;
	}
	
	public String toString() {
		return this.word + ": " + this.score*100 + "\n";
	}
	
	@Override
	public int compareTo(WordScore o) {
		return (int) Float.compare(this.score, o.getScore());
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof WordScore) && !(o instanceof WordScore)) {
			return false;
		}
		else if(o instanceof WordScore){
			return ((WordScore) o).getScore() == this.score;
		}
		else {
			return (Character) o == this.score;
		}
	}
}
