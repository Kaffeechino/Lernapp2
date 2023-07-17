package com.example.lernapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

public class TestResultAdapter extends RecyclerView.Adapter<TestResultAdapter.ViewHolder> {
    private List<Question> testResults;

    public TestResultAdapter(List<Question> testResults) {
        this.testResults = testResults;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_test_result, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question testResult = testResults.get(position);
        // Bind the data to the views in the ViewHolder
        holder.textViewQuestion.setText(testResult.getQuestionText());
        holder.textViewAnswer1.setText(testResult.getAnswers()[0].getAnswerText());
        holder.textViewAnswer2.setText(testResult.getAnswers()[1].getAnswerText());
        holder.textViewAnswer3.setText(testResult.getAnswers()[2].getAnswerText());
        holder.textViewAnswer4.setText(testResult.getAnswers()[3].getAnswerText());

        int rightAnswerIndex = testResult.getRightAnswer();
        int selectedAnswerIndex = testResult.getSelectedAnswer();

        if (selectedAnswerIndex == -1) {
            holder.textViewHinweisSelektion.setVisibility(View.VISIBLE);
            for (int i = 0; i < holder.textViewArray.length; i++) {
                if (i == rightAnswerIndex) {
                    holder.textViewArray[i].setTextColor(Color.rgb(1, 238, 144));
                } else {
                    holder.textViewArray[i].setTextColor(Color.BLACK);
                }
            }
        } else {
            holder.textViewHinweisSelektion.setVisibility(View.GONE);
            for (int i = 0; i < holder.textViewArray.length; i++) {
                if (i == rightAnswerIndex) {
                    holder.textViewArray[i].setTextColor(Color.GREEN);
                } else if (i == selectedAnswerIndex) {
                    holder.textViewArray[i].setTextColor(Color.RED);
                } else {
                    holder.textViewArray[i].setTextColor(Color.BLACK);
                }
            }
        }



    }

    @Override
    public int getItemCount() {
        return testResults.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewQuestion, textViewAnswer1, textViewAnswer2, textViewAnswer3, textViewAnswer4, textViewHinweisSelektion;
        TextView[] textViewArray;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewQuestion = itemView.findViewById(R.id.textViewResult);
            textViewHinweisSelektion = itemView.findViewById(R.id.textViewHinweisSelektion);
            textViewAnswer1 = itemView.findViewById(R.id.textViewAnswer1);
            textViewAnswer2 = itemView.findViewById(R.id.textViewAnswer2);
            textViewAnswer3 = itemView.findViewById(R.id.textViewAnswer3);
            textViewAnswer4 = itemView.findViewById(R.id.textViewAnswer4);
            // Declare a Map to store the TextViews with their indices
            textViewArray = new TextView[4];

            // Add TextViews to the map with their indices
            textViewArray[0] = textViewAnswer1;
            textViewArray[1] = textViewAnswer2;
            textViewArray[2] = textViewAnswer3;
            textViewArray[3] = textViewAnswer4;


            // Access a TextView by its index

        }
    }
}

