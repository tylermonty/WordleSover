package wordleSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Dictionary {
	//dictionary by alphabetical indexed word length
	private ArrayList<ArrayList<String>> lenDictionary = new ArrayList<ArrayList<String>>();
	//full dictionary alphabetical
	private ArrayList<String> dictionary = new ArrayList<String>();
	//count of total letters in dictionary
	private int[] letterCounts = new int[26];
	//sorted arrayList of character and % frequency
	private ArrayList<CharacterFreq> sortedFreq = new ArrayList<CharacterFreq>();
	//longest word in dictionary
	private int maxLength = 0;
	private String legalAlphabet = "abcdefghijklmnopqrstuvwxyz";
	//Used to Record Scores of all words
	private ArrayList<WordScore> wordScores = new ArrayList<WordScore>();
	
	public String getAlphabet() {
		return legalAlphabet;
	}
	public ArrayList<String> getFullDictionary() {
		return dictionary;
	}
	public int getMaxWordLength() {
		return maxLength;
	}
	public ArrayList<ArrayList<String>> getDictionary() {
		return lenDictionary;
	}
	public int[] getLetterCounts() {
		return letterCounts;
	}
	public ArrayList<CharacterFreq> getLetterFrequency() {
		return sortedFreq;
	}
	
	//full path constructor
	public Dictionary(String dictionary_file_name) {
		try {
			String cwd = System.getProperty("user.dir");
			File full_dictionary = new File(cwd + "/inputs/", dictionary_file_name);
			Scanner reader = new Scanner(full_dictionary);
			
			
			while (reader.hasNextLine()) {
				String word = reader.nextLine();
				if(word.length() > maxLength) {
					maxLength = word.length();
				}
				//only accept lowercase alphabetic characters
				if(word.matches("[a-z]+")) {
					dictionary.add(word);
					addLetterCounts(word);
				}
			}
			reader.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i <= maxLength; i++) {
			lenDictionary.add(new ArrayList<String>());
		}
		
		//add to length indexed arrayList
		dictionary.forEach((word) -> lengthAdd(word, lenDictionary));
		
		//calculate letter frequency percents
		int totalLetters = Arrays.stream(letterCounts).sum();
		for(int i = 0; i < letterCounts.length; i++) {
			float freq = (float) letterCounts[i]/(float) totalLetters;
			char c = (char) ((char) i+97);
			sortedFreq.add(new CharacterFreq(c, freq));
		}
		
		//sort based on highest frequency
		Collections.sort(sortedFreq, Collections.reverseOrder());
		
		optimalFirstWord(sortedFreq, dictionary);
	}
	
	//add words to length indexed ArrayList
	private static void lengthAdd(String word, ArrayList<ArrayList<String>> dict) {
		dict.get(word.length()).add(word);
	}
	
	//show example outputs of dictionary (testing method)
	public void testOutput(int minWordLength, int maxWordLength, int maxWordCount) {
		if(minWordLength < 1)
			minWordLength = 1;
		if(maxWordLength > maxLength)
			maxWordLength = maxLength;
		for(int j = minWordLength; j <= maxWordLength; j++) {
			int maxWordCountTemp = maxWordCount; 
			if(lenDictionary.get(j).size() < maxWordCount) {
				maxWordCountTemp = lenDictionary.get(j).size();
			}
			for(int i = 0; i < maxWordCountTemp; i++) {
				System.out.println(lenDictionary.get(j).get(i));
			}
		}
	}
	
	//add to letter array to get count of letters
	private void addLetterCounts(String word) {
		for(int i = 0; i < word.length(); i++) {
			int index = (int) (word.charAt(i)-97);
			letterCounts[index]++;
		}
	}
	
	//calculate most optimal first word based on wordle game
	private void optimalFirstWord(ArrayList<CharacterFreq> letterFreqs, ArrayList<String> dictionary) {
		for (String word: dictionary) {
			ArrayList<Character> lettersInWord = new ArrayList<Character>();
			float score = 0;
			for (char c : word.toCharArray()) {
				lettersInWord.add(c);
			}
			HashSet<Character> lettersSet = new HashSet<Character>(lettersInWord);
			lettersInWord.clear();
			lettersInWord.addAll(lettersSet);
			
			//Calculate score
			for (char letter : lettersInWord) {
				for (CharacterFreq c : letterFreqs) {
					if (c.getCharacter() == letter) {
						score += c.getFrequency();
						break;
					}
				}
			}
			wordScores.add(new WordScore(word, score));
		}
		Collections.sort(wordScores);
		System.out.println(wordScores.toString());
	}
}