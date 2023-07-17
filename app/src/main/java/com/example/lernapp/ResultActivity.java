package com.example.lernapp;

import static com.example.lernapp.TestActivity.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    private RecyclerView recyclerViewTestResults;
    private TestResultAdapter adapter;
    private List<Question> testResults;
    public static TextView textViewResult;
    private TextView textViewBackhome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        textViewBackhome = findViewById(R.id.textViewBackHome);
        textViewResult = findViewById(R.id.textViewResult);
        textViewResult.setText(test.getErgebnis() + "/" + test.getQuestions().length);
        recyclerViewTestResults = findViewById(R.id.recyclerViewTestResults);
        // Assuming you have a list of TestResult objects

            testResults = getTestResults();
            adapter = new TestResultAdapter(testResults);

            recyclerViewTestResults.setLayoutManager(new LinearLayoutManager(this));
            recyclerViewTestResults.setAdapter(adapter);

        textViewBackhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main.class);
                startActivity(intent);
                finish();
            }
        });

    }

    // Dummy method to generate test results
    private List<Question> getTestResults() {
        List<Question> results = new ArrayList<>();
        for (int i = 0; i < test.getQuestions().length; i++) {
            results.add(test.getQuestions()[i]);
        }
        return results;
    }
}