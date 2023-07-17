package com.example.lernapp;

import static com.example.lernapp.TestActivity.radioGroup;
import static com.example.lernapp.TestActivity.test;

import android.widget.RadioButton;

public class TestHandler {
    public static void markQuestion() {
        test.getCurrentQuestion().setMarked(!test.getCurrentQuestion().isMarked());
    }

    public static void nextQuestion() {
        updateChecked();
        changeCqiBy(1);
    }
    public static void previousQuestion() {
        updateChecked();
        changeCqiBy(-1);

    }

    public static void updateChecked() {
        if (radioGroup.getCheckedRadioButtonId() != -1) {
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                if (radioButton.getId() == radioGroup.getCheckedRadioButtonId()) {
                    test.getCurrentQuestion().disselectAnswers();
                    test.getCurrentQuestion().setSelectedAnswer(i);
                    test.getCurrentQuestion().setChecked(true);
                }
            }
        } else {
            test.getCurrentQuestion().disselectAnswers();
            test.getCurrentQuestion().setChecked(false);
        }

    }
    public static void changeCqiBy(int step) {
        int nextCQI = test.getCQI() + step;
        test.setCQI(nextCQI);
    }
}
