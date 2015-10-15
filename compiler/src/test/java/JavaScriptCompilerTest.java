import javax.script.*;

public class JavaScriptCompilerTest {

    public static final String MY_JS
            = "print(\"Hello world!\\n\");"
            + "var myFunction = function(x) { return x+3; };";

    public static void main(String[] args)
            throws ScriptException, NoSuchMethodException {

        ScriptEngine se = new ScriptEngineManager()
                .getEngineByName("javascript");

        if ( Compilable.class.isAssignableFrom(se.getClass()) ) {

            Compilable c = (Compilable) se;
            CompiledScript cs = c.compile(MY_JS);

            System.out.println("From compiled JS:");
            cs.eval();

        } else {
            System.out.println("From interpreted JS:");
            se.eval(MY_JS);

        }

        if ( Invocable.class.isAssignableFrom(se.getClass()) ) {

            Invocable i = (Invocable) se;

            System.out.println("myFunction(2) returns: "
                    + i.invokeFunction("myFunction", 2));

        } else {

            System.out.println(
                    "Method invocation not supported!");

        }

    }

}
