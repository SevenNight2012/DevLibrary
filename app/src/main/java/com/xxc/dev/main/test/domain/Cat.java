package com.xxc.dev.main.test.domain;

public class Cat extends Animal {

    public Cat() {
        name = "猫";
        type = ANIMAL_CAT;
    }

    public String hobby;

    @Override
    public String toString() {
        return "Cat{" + "hobby='" + hobby + '\'' + ", type='" + type + '\'' + ", name='" + name + '\'' + ", age=" + age + ", image='" + image + '\'' + '}';
    }
}
