package com.kassandra.repository;

public class RepositoryException extends Exception {
    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(Throwable ex) {
        super(ex);
    }
}
