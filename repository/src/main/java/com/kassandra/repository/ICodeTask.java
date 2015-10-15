package com.kassandra.repository;

import java.util.List;

public interface ICodeTask {

    String getStatement();
    List<String> getInput();
    List<String> getOutput();
}
