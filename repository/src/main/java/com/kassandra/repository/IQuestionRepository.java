package com.kassandra.repository;

import java.util.List;

/**
 * Created by madatoia on 10/13/2015.
 */
public interface IQuestionRepository {

    IQuestion getQuestion(String id) throws RepositoryException;

    List<String> getAll(String type) throws RepositoryException;

    boolean createQuestion(IQuestion question) throws RepositoryException;
}
