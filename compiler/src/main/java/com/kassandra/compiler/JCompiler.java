package com.kassandra.compiler;

import java.util.Arrays;
import java.util.List;

import javax.tools.*;

import com.kassandra.compiler.utils.StringToJavaSource;

public class JCompiler {

    private List<String> options = null;
    private JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    private DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
    private StringBuffer msg = new StringBuffer();

    public JCompiler(String... options) {
        if (options != null && options.length > 0) {
            this.options = Arrays.asList(options);
        }
    }

    public boolean compile(String className, String code) {
        JavaFileObject sourceFile = new StringToJavaSource(className, code);
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(sourceFile);
        JavaCompiler.CompilationTask task = compiler
                .getTask(null, null, diagnostics, options, null, compilationUnits);
        boolean result = task.call();

        for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics()) {
            msg.append(diagnostic.getMessage(null)).append("\n");
            msg.append(String.format("Error on line %d in %s%n", diagnostic.getLineNumber(),
                    ((FileObject) diagnostic.getSource()).toUri()));
        }

        return result;
    }

    public String getErrMsg() {
        return msg.toString();
    }
}
