package com.kassandra.repository;


public interface IQuestion {

    public String getContent();

    public int checkAnswer();

    public int getAnswer();

    public int getCorrectAnswer();

    public void setAnswer(int answer);
}
