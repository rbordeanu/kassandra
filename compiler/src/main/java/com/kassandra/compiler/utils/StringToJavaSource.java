package com.kassandra.compiler.utils;

import java.io.IOException;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

public class StringToJavaSource extends SimpleJavaFileObject {

    private String code;

    public StringToJavaSource(String className, String code) {
        super(URI.create(className + Kind.SOURCE.extension), Kind.SOURCE);
        this.code = code;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return code;
    }
}
