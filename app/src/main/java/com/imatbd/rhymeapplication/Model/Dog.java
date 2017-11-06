package com.imatbd.rhymeapplication.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Genius 03 on 11/6/2017.
 */

public class Dog extends RealmObject{

    @PrimaryKey
    private int id;

    @Required
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*@Override
    public String toString() {
        return this.name;
    }*/
}
