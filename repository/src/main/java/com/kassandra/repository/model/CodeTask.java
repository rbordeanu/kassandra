package com.kassandra.repository.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kassandra.repository.ICodeTask;

public class CodeTask implements ICodeTask {

    private final String statement;
    private final String template;
    private final String lang;
    private final List<String> input;
    private final List<String> output;

    @JsonCreator
    public CodeTask(@JsonProperty("statement") String statement, @JsonProperty("lang") String lang,
            @JsonProperty("template") String template,
        @JsonProperty("input") List<String> input, @JsonProperty("output") List<String> output) {
        this.statement = statement;
        this.lang = lang;
        this.template = template;
        this.input = input;
        this.output = output;
    }

    @Override
    public String getStatement() {
        return statement;
    }

    @Override
    public List<String> getInput() {
        return input;
    }

    @Override
    public List<String> getOutput() {
        return output;
    }

    @Override
    public String getTemplate() {
        return template;
    }

    @Override
    public String getLang() {
        return lang;
    }
}
