package com.ashok.quizapp.model;

import java.util.List;

public class QuestionModel {
    private String title;
    private String question;
    private List<String> answers;
    private int correctAnswerIndex;

    public QuestionModel(String title, String question, List<String> answers, int correctAnswerIndex) {
        this.title = title;
        this.question = question;
        this.answers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getTitle() {
        return title;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}