package com.xxc.dev.main.test.domain;

public class Dog extends Animal {

    public Dog() {
        name = "狗";
        type = ANIMAL_DOG;
    }

    public String hobby;
    public int weight;

    @Override
    public String toString() {
        return "Dog{" + "hobby='" + hobby + '\'' + ", weight=" + weight + ", type='" + type + '\'' + ", name='" + name + '\'' + ", age=" + age + ", image='" + image + '\'' + '}';
    }
}
