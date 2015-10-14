package com.kassandra.rest.bean;

public class TestBean  implements ITestBean{
    public String test() {
        return "Fuck Guice! Go Spring";
    }
}
