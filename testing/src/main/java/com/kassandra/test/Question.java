package com.kassandra.test;

import com.kassandra.repository.IQuestion;

public class Question implements IQuestion {

    private final String id;
    private final byte[] content;
    private final int score;
    private int answer;
    private final int correctAnswer;

    public Question(String id, byte[] content, int score, int correctAnswer) {
        this.id = id;
        this.content = content;
        this.score = score;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String getId() {
        return id;
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

    public byte[] getContent() {
        return content;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
