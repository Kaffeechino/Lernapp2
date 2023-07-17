package com.example.lernapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;


import org.json.JSONException;

public class TestActivity extends AppCompatActivity {
    public static Test test;
    public static TextView textViewTitel, textViewFrage, textViewTimer;
    public static View nextQuestionView, previousQuestionView, markQuestionView;
    public static RadioGroup radioGroup;
    public static RadioButton radioButton1, radioButton2, radioButton3, radioButton4;
    public static Button buttonAbgeben, buttonShowResults;
    public static int x, checkedQuestions, progress;
    public static TableLayout tableLayout;
    public static Context context;
    CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        System.out.println("Start");
        context = this;
        startTimer();
        tableLayout = findViewById(R.id.tableLayout);
        textViewTitel = findViewById(R.id.textViewTitel);
        textViewFrage = findViewById(R.id.textViewFrage);
        nextQuestionView = findViewById(R.id.nextQuestionView);
        previousQuestionView = findViewById(R.id.previousQuestionView);
        markQuestionView = findViewById(R.id.markQuestionView);
        textViewTimer = findViewById(R.id.textViewTimer);
        radioGroup = findViewById(R.id.radioGroup);
        checkedQuestions = 0;
        progress = 0;
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton4 = findViewById(R.id.radioButton4);
        buttonAbgeben = findViewById(R.id.buttonAbgeben);
        buttonShowResults = findViewById(R.id.buttonShowResults);

        try {
            test = new Test(Main.testData);
            System.out.println(test);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        UIUpdater.updateFrageText();
        UIUpdater.updateRadioGroup();
        UIUpdater.updateQuestionNavigation();
        UIUpdater.generateTable();



        nextQuestionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestHandler.nextQuestion();
                UIUpdater.nextQuestion();
            }
        });
        previousQuestionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cqi = test.getCQI();
                TestHandler.previousQuestion();
                UIUpdater.previousQuestion();
            }
        });
        markQuestionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestHandler.markQuestion();
                TableRow childAt = (TableRow) tableLayout.getChildAt(0);
                if (test.getCurrentQuestion().isMarked()) {
                    childAt.getChildAt(test.getCQI()).setBackgroundColor(Color.YELLOW);
                } else {
                childAt.getChildAt(test.getCQI()).setBackgroundColor(Color.WHITE);}

            }
        });
        buttonShowResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonAbgeben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity.this);
                builder.setTitle("Bestätigung")
                        .setMessage("Sind Sie sicher, dass Sie den Test abgeben möchten?")
                        .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TestHandler.updateChecked();
                                test.setFinished(true);
                                countDownTimer.cancel();
                                // ++ Test übergeben
                                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Cancel the action or do nothing
                            }
                        })
                        .show();
            }
        });

    }
    private void startTimer() {
        countDownTimer = new CountDownTimer(300000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                textViewTimer.setText(String.format("Zeit: %02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), "Zeit ist abgelaufen", Toast.LENGTH_LONG).show();
                textViewTimer.setText("Zeit ist abgelaufen");
                textViewFrage.setVisibility(View.GONE);
                textViewTitel.setText("Test ist fertig");
                radioGroup.setVisibility(View.GONE);

                markQuestionView.setVisibility(View.GONE);
                nextQuestionView.setVisibility(View.GONE);
                previousQuestionView.setVisibility(View.GONE);
                buttonAbgeben.setVisibility(View.GONE);
                buttonShowResults.setVisibility(View.VISIBLE);
                tableLayout.setVisibility(View.GONE);
                TestHandler.updateChecked();
                test.setFinished(true);
            }
        };
        countDownTimer.start();
    }

    }
