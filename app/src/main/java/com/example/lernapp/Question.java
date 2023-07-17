package com.example.lernapp;


public class Question {
    private String questionText;
    private int questionID, selectedAnswer;
    private Answer[] answers;
    private boolean isMarked, isChecked;

    public Question(String questionText, int questionID, Answer[] answers) {
        this.questionText = questionText;
        this.questionID = questionID;
        this.answers = answers;
        this.isMarked = false;
        this.isChecked = false;
        this.selectedAnswer = -1;
    }

    public void disselectAnswers() {
        for (Answer answer : this.getAnswers()) {
            answer.setSelected(false);
        }
    }

    public int getRightAnswer() {
        int result = 0;
        int i = 0;
        for (Answer answer:this.getAnswers()
             ) {
            if (answer.isCorrect()) {
                result = i;
                break;
            } else {
                i++;
            }
        }
        return result;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionString) {
        this.questionText = questionString;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public Answer[] getAnswers() {
        return answers;
    }

    public void setAnswers(Answer[] answers) {
        this.answers = answers;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(int selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }
}
