package com.example.lernapp;

public class Answer {
    private String answerText;
    private int answerID;
    private boolean isSelected, isCorrect;

    public Answer(String answerText, int answerID, boolean isCorrect) {
        this.answerText = answerText;
        this.answerID = answerID;
        this.isCorrect = isCorrect;
        this.isSelected = false;
    }



    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public int getAnswerID() {
        return answerID;
    }

    public void setAnswerID(int answerID) {
        this.answerID = answerID;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
