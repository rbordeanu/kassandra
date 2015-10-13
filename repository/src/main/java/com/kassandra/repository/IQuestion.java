package com.kassandra.repository;

/**
 * Created by madatoia on 10/13/2015.
 */
public interface IQuestion {

    public byte[] getContent();

    public String getId();

    public int getScore();

    public int getAnswer();
}
