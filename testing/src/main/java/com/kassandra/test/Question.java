package com.kassandra.test;

import com.kassandra.repository.IQuestion;

public class Question implements IQuestion {

    private final String content;
    private final int score;
    private int answer;
    private final int correctAnswer;

    public Question(String content, int score, int correctAnswer) {
        this.content = content;
        this.score = score;
        this.correctAnswer = correctAnswer;
    }


    @Override
    public int getAnswer() {
        return answer;
    }

    @Override
    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public int checkAnswer() {
        return correctAnswer == answer ? score : 0;
    }

    public String getContent() {
        return content;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
