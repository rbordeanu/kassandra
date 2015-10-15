package com.kassandra.compiler.java;

import java.lang.reflect.InvocationTargetException;

import com.kassandra.compiler.java.JCompiler;
import com.kassandra.compiler.java.utils.ResolveClassName;

public class CompileRunTest {

    public static void main(String[] args) {
        StringBuffer str = new StringBuffer();

        str.append("import java.util.List;");
        str.append("import java.util.ArrayList;");
        str.append("public class HelloWorld {");
        str.append("  public static void main(String args[]) {");
        str.append(" List<String> list = new ArrayList<String>(); ");
        str.append("list.add(\"A\");");
        str.append("    System.out.println(\"This is in another java file\");");
        str.append("    System.out.println(list.get(0));");
        str.append("  }");
        str. append(" public static String f1(){ return \"A \"; } ");
        str.append("}");
        String code = str.toString();

        String className = ResolveClassName.getClassName(code);
        String fullClassName = ResolveClassName.getFullClassName(code);
        System.out.println("" + ClassLoader.getSystemResource("."));
        if("D://hackathon//kassandra//compiler//target//classes".equals(ClassLoader.getSystemResource(".").toString())) {
            System.out.println("blabla");
        }
        JCompiler compiler = new JCompiler("-d", ClassLoader.getSystemResource(".").toString());

        boolean success = compiler.compile(className, code);

        if (success) {
            try {
                String s = (String) Class.forName(fullClassName)
                        .getDeclaredMethod("f1")
                        .invoke(null);
                System.out.println(s);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                System.err.println("Load class error: " + e);
            }
        }
        else {
            System.out.println(compiler.getErrMsg());
        }
    }
}
