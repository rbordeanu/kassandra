package com.kassandra.test;

import com.kassandra.repository.IQuestion;

/**
 * Created by madatoia on 10/12/2015.
 */
public class Question implements IQuestion {

    private final String id;
    private final byte[] content;
    private final int score;
    private final int answer;

    public Question(String id, byte[] content, int score, int answer) {
        this.id = id;
        this.content = content;
        this.score = score;
        this.answer = answer;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public int getAnswer() {
        return answer;
    }

    int answer(int ans) {
        return ans == answer ? score : 0;
    }

    public byte[] getContent() {
        return content;
    }
}
