package com.xxc.dev.main.test.domain;

import com.xxc.dev.main.test.adapter.MyAdapter;

public class Animal {

    public static final String TAG = "Animal";

    public static final String ANIMAL_DOG = "[dog]";
    public static final String ANIMAL_CAT = "[cat]";
    public static final String ANIMAL_BIRD = "[bird]";

    public String type;
    public String name;
    public int age;
    public String image;

    public int getAnimalType() {
        if (ANIMAL_CAT.equals(type)) {
            return MyAdapter.TYPE_CAT;
        } else if (ANIMAL_BIRD.equals(type)) {
            return MyAdapter.TYPE_BIRD;
        } else {
            return MyAdapter.TYPE_DOG;
        }
    }

}
