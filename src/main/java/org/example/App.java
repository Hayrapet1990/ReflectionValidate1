package org.example;


import java.util.List;

public class App {
    public static void main(String[] args) throws IllegalAccessException {

        Student student = new Student();
        List<String> errors = Validator.validate(student);
        for (String p : errors) {
            System.out.println(p);
        }

    }
}