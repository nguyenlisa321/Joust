//Lisa Nguyen (lyn5nw) + Jiawen Lin (jl9dc)

import java.io.File;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Scoreboard {
	private int score1;
	private int score2;
	private boolean gameOver;
	private ArrayList<Integer> highScores;
	private int highScoresCapacity;
	
	public Scoreboard() {
		this.score1 = 0;
		this.score2 = 0;
		this.gameOver = false;
		this.highScores = new ArrayList<Integer>();
		this.highScoresCapacity = 3;
	}
	public  Scoreboard(int highScoresCapacity) {
		this.score1 = 0;
		this.score2 = 0;
		this.gameOver = false;
		this.highScores = new ArrayList<Integer>();
		this.highScoresCapacity = highScoresCapacity;
	}
	
	public void incrementScore1(){
		this.score1++;
	}
	public void incrementScore2() {
		this.score2++;
	}
	public int getScore1() {
		return this.score1;
	}
	public int getScore2() {
		return this.score2;
	}
	public boolean isGameOver() {
		return this.gameOver;
	}
	public void endGame() {
		this.gameOver = true;
	}
	public ArrayList<Integer>  getHighScores()  {
		return this.highScores;
	}
	public void  addHighScore(int newScore) {
		highScores.add(newScore);
		Collections.sort(highScores, Collections.reverseOrder());
	
	
		while(highScores.size() > this.highScoresCapacity){
			highScores.remove(highScores.get(highScores.size()-1));
		}
	}
	public void  loadHighScores(String filename ) throws Exception {
		Scanner  input = new Scanner(new File(filename));
		while(input.hasNextLine()){
			String line = input.nextLine();
			String [] brokenLine = line.split(",");
	
			for (int i=0; i < brokenLine.length; i++){
				this.highScores.add(Integer.parseInt(brokenLine[i]));
				
				}	
				
			}
		Collections.sort(highScores, Collections.reverseOrder());
		
		while(this.highScores.size() > this.highScoresCapacity){
			highScores.remove(highScores.get(highScores.size()-1));
		}
		}
		
	
	
	public void  saveHighScores(String filename ) throws Exception {
		File text = new File(filename);
		PrintWriter  outputFile = new PrintWriter(text);
		for (int i =0; i < highScores.size(); i++){
			if(i< highScores.size()-1){
			outputFile.write(highScores.get(i) +",");
			
			}
			else {
				outputFile.write(highScores.get(i).toString());
		
			}
		}
		outputFile.close();
		
		
	}
	public String  toString(){
		String highscore1 = highScores.toString().replace("[", "");
		highscore1 = highscore1.replace("]", "");
		highscore1 = highscore1.replace(", ", ",");
	
		if (gameOver == true)
			return score1+" - "+score2+". Game is over. High scores: " + highscore1;
		else
			return score1+" - "+score2+". Game is not over. High scores: " + highscore1;
	}
}
