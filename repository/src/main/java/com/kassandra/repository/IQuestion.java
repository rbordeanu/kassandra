package com.kassandra.repository;

import java.util.List;

public interface IQuestion {

    public String getContent();

    public int getCorrectAnswer();

    public List<String> getAllAnswers();
}
