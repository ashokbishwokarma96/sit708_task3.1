package com.ashok.quizapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashok.quizapp.adapter.QuestionAdapter;
import com.ashok.quizapp.model.QuestionModel;
import com.google.gson.Gson;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    List<QuestionModel> questionList;
    private int currentQuestionIndex = 0;
    private QuestionAdapter QSadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        questionList = ParseJSON("{\n" +
                "  \"questions\": [\n" +
                "    {\n" +
                "      \"title\": \"Australian History\",\n" +
                "      \"question\": \"What year did Australia become a federation?\",\n" +
                "      \"answers\": [\"1900\", \"1901\", \"1902\", \"1903\"],\n" +
                "      \"correctAnswerIndex\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"Australian Geography\",\n" +
                "      \"question\": \"What is the capital city of Australia?\",\n" +
                "      \"answers\": [\"Melbourne\", \"Sydney\", \"Canberra\", \"Brisbane\"],\n" +
                "      \"correctAnswerIndex\": 2\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"Australian Wildlife\",\n" +
                "      \"question\": \"What is the largest living marsupial?\",\n" +
                "      \"answers\": [\"Kangaroo\", \"Koala\", \"Wombat\", \"Tasmanian Devil\"],\n" +
                "      \"correctAnswerIndex\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"Australian Sports\",\n" +
                "      \"question\": \"What is the national sport of Australia?\",\n" +
                "      \"answers\": [\"Cricket\", \"Rugby\", \"Soccer\", \"Australian Rules Football\"],\n" +
                "      \"correctAnswerIndex\": 3\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"Australian Culture\",\n" +
                "      \"question\": \"What is the most popular food in Australia?\",\n" +
                "      \"answers\": [\"Hamburger\", \"Pizza\", \"Meat Pie\", \"Fish and Chips\"],\n" +
                "      \"correctAnswerIndex\": 2\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"Australian History\",\n" +
                "      \"question\": \"Who was the first Prime Minister of Australia?\",\n" +
                "      \"answers\": [\n" +
                "        \"John Curtin\",\n" +
                "        \"Robert Menzies\",\n" +
                "        \"William Morris Hughes\",\n" +
                "        \"Edmund Barton\"\n" +
                "      ],\n" +
                "      \"correctAnswerIndex\": 3\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"Australian Geography\",\n" +
                "      \"question\": \"What is the name of the longest river in Australia?\",\n" +
                "      \"answers\": [\n" +
                "        \"Murray River\",\n" +
                "        \"Darling River\",\n" +
                "        \"Lachlan River\",\n" +
                "        \"Macquarie River\"\n" +
                "      ],\n" +
                "      \"correctAnswerIndex\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"Australian Wildlife\",\n" +
                "      \"question\": \"What is the name of the largest reptile in Australia?\",\n" +
                "      \"answers\": [\"Crocodile\", \"Python\", \"Lizard\", \"Turtle\"],\n" +
                "      \"correctAnswerIndex\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"Australian Sports\",\n" +
                "      \"question\": \"What is the most watched sporting event in Australia?\",\n" +
                "      \"answers\": [\n" +
                "        \"Australian Open (tennis)\",\n" +
                "        \"Melbourne Cup (horse racing)\",\n" +
                "        \"AFL Grand Final (football)\",\n" +
                "        \"State of Origin (rugby league)\"\n" +
                "      ],\n" +
                "      \"correctAnswerIndex\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"Australian Landmarks\",\n" +
                "      \"question\": \"What is the name of the world's largest coral reef system, located off the coast of Queensland?\",\n" +
                "      \"answers\": [\n" +
                "        \"The Coral Triangle\",\n" +
                "        \"The Maldives\",\n" +
                "        \"The Great Barrier Reef\",\n" +
                "        \"The Red Sea\"\n" +
                "      ],\n" +
                "      \"correctAnswerIndex\": 3\n" +
                "    }\n" +
                "  ]\n" +
                "}\n");// create list of Question objects
        Log.d("TAG", "onCreate: "+questionList.get(0).getQuestion());

        recyclerView  = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setVisibility(View.GONE);
        QSadapter = new QuestionAdapter(questionList,recyclerView,this);
        recyclerView.setAdapter(QSadapter);
        loadQuestion(currentQuestionIndex);



    }
    public List<QuestionModel> ParseJSON(String json){

        Gson gson = new Gson();
        QuizModel quiz = gson.fromJson(json, QuizModel.class);
        return quiz.getQuestions();

    }
    private static class QuizModel {
        private List<QuestionModel> questions;

        public List<QuestionModel> getQuestions() {
            return questions;
        }


    }
    private void loadQuestion(int questionIndex) {
        if (questionIndex >= questionList.size()) {
            // no more questions left, show score or navigate to next activity
            return;
        }
        Log.d("questionIndex", "loadQuestion: "+questionIndex);
        QSadapter.setCurrentQuestionIndex(questionIndex); // update the current question index in the adapter
        QSadapter.notifyDataSetChanged(); // notify the adapter that the data has changed

        if (recyclerView.getVisibility() == View.GONE) {
            recyclerView.setVisibility(View.VISIBLE);
        }
//     onAnswerSubmitted();
    }

    public void onAnswerSubmitted() {
        currentQuestionIndex++;
        recyclerView.setVisibility(View.GONE); // hide the current question

        // load the next question
        loadQuestion(currentQuestionIndex);
    }

}