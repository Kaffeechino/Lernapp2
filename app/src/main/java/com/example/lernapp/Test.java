package com.example.lernapp;

import static com.example.lernapp.TestActivity.test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Test {
    private static int anzahlFragen = 10;
    private Question[] questions;
    private int[] chosenAnswers;
    private int ergebnis;
    private int CQI;
    private Question CurrentQuestion;
    private boolean isFinished;


    public Test(String testData) throws JSONException {

        this.questions = new Question[10];
        JSONArray jsonArrayQuestions = new JSONArray(testData);
        for (int i = 0; i < jsonArrayQuestions.length(); i++) {
            JSONObject jsonObjectQuestion = jsonArrayQuestions.getJSONObject(i);
            JSONObject questionObject = jsonObjectQuestion.getJSONObject("question");
            String questionText = (String) questionObject.get("questionText");
            int questionID = (int) questionObject.get("questionID");
            JSONArray answersArray = jsonObjectQuestion.getJSONArray("answers");
            int amountAnswers = answersArray.length();
            Answer[] answers = new Answer[4];
            Boolean x;
            for (int j = 0; j < amountAnswers; j++) {
                int answerID = (int) answersArray.getJSONArray(j).getInt(0);
                String answerText = (String) answersArray.getJSONArray(j).getString(1);
                int isCorrect = answersArray.getJSONArray(j).getInt(2);
                if (isCorrect == 0) {
                    x = false;
                } else {
                    x = true;
                }
                Answer answer = new Answer(answerText, answerID, x);
                answers[j] = answer;
            }
            this.questions[i] = new Question(questionText, questionID, answers);
        }
        this.CQI = 0;
        this.CurrentQuestion = this.questions[0];
        this.isFinished = false;
        this.ergebnis = -1;


    }


    public static int getAnzahlFragen() {
        return anzahlFragen;
    }

    public static void setAnzahlFragen(int anzahlFragen) {
        Test.anzahlFragen = anzahlFragen;
    }

    public Question[] getQuestions() {
        return questions;
    }

    public void setQuestions(Question[] questions) {
        this.questions = questions;
    }

    public int[] getChosenAnswers() {
        return chosenAnswers;
    }

    public void setChosenAnswers(int[] chosenAnswers) {
        this.chosenAnswers = chosenAnswers;
    }

    public int getErgebnis() {
        if (this.isFinished) {
            ergebnis = 0;
            for (Question question : this.getQuestions()
            ) {
                if (question.getRightAnswer() == question.getSelectedAnswer()) {
                    ergebnis++;
                }
            }
        }
        return ergebnis;
    }

    public void setErgebnis(int ergebnis) {
        this.ergebnis = ergebnis;
    }

    public int getCQI() {
        return CQI;
    }

    public void setCQI(int CQI) {
        this.CQI = CQI;
    }


    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public Question getCurrentQuestion() {
        return this.getQuestions()[CQI];
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.CurrentQuestion = currentQuestion;
    }

    public boolean[] getCheckedQuestions() {
        boolean[] result = new boolean[10];
        for (int i = 0; i < 10; i++) {
            result[i] = this.getQuestions()[i].isChecked();
        }
        return result;
    }


}