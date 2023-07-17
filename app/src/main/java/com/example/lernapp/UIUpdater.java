package com.example.lernapp;

import static com.example.lernapp.TestActivity.radioGroup;
import static com.example.lernapp.TestActivity.test;
import static com.example.lernapp.TestActivity.textViewFrage;
import static com.example.lernapp.TestActivity.nextQuestionView;
import static com.example.lernapp.TestActivity.previousQuestionView;
import static com.example.lernapp.TestActivity.textViewTitel;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;



public class UIUpdater {

  public static void nextQuestion() {
        updateFrageText();
        updateRadioGroup();
        updateQuestionNavigation();
        updateTable(TestActivity.tableLayout);
      for (int i = 0; i < 10; i++) {
          System.out.println(test.getCheckedQuestions()[i]);
      }
  }

  public static void previousQuestion() {
        updateFrageText();
        updateRadioGroup();
        updateQuestionNavigation();
        updateTable(TestActivity.tableLayout);
  }
  public static void updateFrageText() {
      textViewTitel.setText("Frage " + (test.getCQI()+1));
        textViewFrage.setText(test.getCurrentQuestion().getQuestionText());
  }
  public static void updateRadioGroup() {
      for (int i = 0; i < radioGroup.getChildCount(); i++) {
          RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
          radioButton.setText(test.getCurrentQuestion().getAnswers()[i].getAnswerText());
      }
      radioGroup.clearCheck();
      if (test.getCurrentQuestion().isChecked()) {
          radioGroup.check(radioGroup.getChildAt(test.getCurrentQuestion().getSelectedAnswer()).getId());
      }

  }
  public static void updateQuestionNavigation() {
      if (test.getCQI() + 1 == 10) {
          nextQuestionView.setVisibility(View.GONE);
      } else nextQuestionView.setVisibility(View.VISIBLE);
      if (test.getCQI() == 0) {
          previousQuestionView.setVisibility(View.GONE);
      } else previousQuestionView.setVisibility(View.VISIBLE);
  }
  


    public static void generateTable() {
        int numRows = 1; // Replace with the actual number of rows
        int numColumns = 10; // Replace with the actual number of columns
        for (int i = 0; i < numRows; i++) {
            TableRow row = new TableRow(TestActivity.context);
            row.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            ));

            for (int j = 0; j < numColumns; j++) {
                // Create a new cell (ImageView in this example)
                ImageView cell = createCell(j);

                // Set cell properties and update its state based on the question status
                updateCell(cell, test.getCheckedQuestions()[j]);

                // Add the cell to the row
                row.addView(cell);
            }

            // Add the row to the table
            TestActivity.tableLayout.addView(row);
        }
    }

    private static ImageView createCell(int j) {
        ImageView cell = new ImageView(TestActivity.context);
        cell.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));
        // Set the cell image or drawable here
        cell.setImageResource(R.drawable.baseline_check_box_outline_blank_24);
        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToQuestion(j);
            }
        });
        return cell;
    }

    private static void updateCell(ImageView cell, boolean isAnswered) {
        if (isAnswered) {
            // Set the cell as answered (marked, selected, etc.)
            cell.setImageResource(R.drawable.baseline_check_box_24);
        } else {
            // Set the cell as not answered
            cell.setImageResource(R.drawable.baseline_check_box_outline_blank_24);
        }


    }
    public static void updateTable(TableLayout tableLayout) {
        TableRow row = (TableRow) tableLayout.getChildAt(0);
        for (int i = 0;  i < 10; i++) {
            updateCell((ImageView) ((TableRow) tableLayout.getChildAt(0)).getChildAt(i), test.getCheckedQuestions()[i]);
        }
    }

    public static void jumpToQuestion(int questionIndex) {
      TestHandler.updateChecked();
      test.setCQI(questionIndex);
        updateFrageText();
        updateRadioGroup();
        updateQuestionNavigation();
        updateTable(TestActivity.tableLayout);
    }
}
