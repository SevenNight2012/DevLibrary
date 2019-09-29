package com.xxc.dev.main.test.domain;

public class Bird extends Animal {

    public Bird() {
        name = "鸟";
        type = ANIMAL_BIRD;
    }

    public String feather;
    public String color;

    @Override
    public String toString() {
        return "Bird{" + "feather='" + feather + '\'' + ", color='" + color + '\'' + ", type='" + type + '\'' + ", name='" + name + '\'' + ", age=" + age + ", image='" + image + '\'' + '}';
    }
}
