package com.example.docs.mixin_docs_examples.introduction;
// Sanitized target class to act as a very intuitive and "simple" target for introductory docs.

//#region mixin_introduction_person_example_target_class
public class Person {
    private final String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    protected void setAge(int newAge) {
        this.age = newAge;
    }

    public boolean canLegallyDrink() {
        return this.age >= 21;
    }
}
//#endregion mixin_introduction_person_example_target_class
