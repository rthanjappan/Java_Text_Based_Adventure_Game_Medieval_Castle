package com.company;

import java.util.ArrayList;
import java.util.Objects;

public class Puzzle {
    private String puzzleID;
    private String puzzleName;
    private ArrayList<String> puzzleDescription;
    private ArrayList<String> solvedDescription;
    private ArrayList<String> requiredItems;
    private String requiredString;
    private String answer;
    private int noOfAttempts;
    private boolean solved;
    //PuzzleID, PuzzleName, Description,----,solvedDescription,****,requiredItem, requiredString,NoOfAttempts,isSolved
//    public Puzzle() {
//        this.solved=true;
//    }

    public Puzzle(){
        this.puzzleID = "0";
        this.puzzleName = "";
        this.puzzleDescription = new ArrayList<>();
        this.solvedDescription= new ArrayList<>();
        this.requiredItems=new ArrayList<>();
        this.requiredString="";
        this.answer = "";
        this.noOfAttempts = 0;
        this.solved = true;
    }

    public String getPuzzleID() {
        return puzzleID;
    }

    public void setPuzzleID(String puzzleID) {
        this.puzzleID = puzzleID;
    }

    public String getPuzzleName() {
        return puzzleName;
    }

    public void setPuzzleName(String puzzleName) {
        this.puzzleName = puzzleName;
    }

    public ArrayList<String> getPuzzleDescription() {
        return puzzleDescription;
    }

    public void setPuzzleDescription(ArrayList<String> puzzleDescription) {
        this.puzzleDescription = puzzleDescription;
    }

    public ArrayList<String> getSolvedDescription() {
        return solvedDescription;
    }

    public void setSolvedDescription(ArrayList<String> solvedDescription) {
        this.solvedDescription = solvedDescription;
    }

    public ArrayList<String> getRequiredItems() {
        return requiredItems;
    }

    public void setRequiredItems(ArrayList<String> requiredItems) {
        this.requiredItems = requiredItems;
    }

    public String getRequiredString() {
        return requiredString;
    }

    public void setRequiredString(String requiredString) {
        this.requiredString = requiredString;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getNoOfAttempts() {
        return noOfAttempts;
    }

    public void setNoOfAttempts(int noOfAttempts) {
        this.noOfAttempts = noOfAttempts;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    @Override
    public String toString() {
        return "Puzzle{" +
                "puzzleID='" + puzzleID + '\'' +
                "puzzleName='" + puzzleName + '\'' +
                ", puzzleDescription='" + puzzleDescription + '\'' +
                ",\n solvedDescription='" + solvedDescription + '\'' +
                ",\n requiredItems='" + requiredItems + '\'' +
                ",\n requiredString='" + requiredString + '\'' +
                ", noOfAttempts=" + noOfAttempts +
                ", solved=" + solved +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Puzzle puzzle = (Puzzle) o;
        return noOfAttempts == puzzle.noOfAttempts && solved == puzzle.solved && Objects.equals(puzzleID, puzzle.puzzleID) && Objects.equals(puzzleName, puzzle.puzzleName) && Objects.equals(puzzleDescription, puzzle.puzzleDescription) && Objects.equals(solvedDescription, puzzle.solvedDescription) && Objects.equals(requiredItems, puzzle.requiredItems) && Objects.equals(requiredString, puzzle.requiredString) && Objects.equals(answer, puzzle.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(puzzleID, puzzleName, puzzleDescription, solvedDescription, requiredItems, requiredString, answer, noOfAttempts, solved);
    }
}
