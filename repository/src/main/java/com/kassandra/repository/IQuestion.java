package com.kassandra.repository;


public interface IQuestion {

    public byte[] getContent();

    public String getId();

    public int checkAnswer();

    public int getAnswer();

    public int getCorrectAnswer();

    public void setAnswer(int answer);
}
