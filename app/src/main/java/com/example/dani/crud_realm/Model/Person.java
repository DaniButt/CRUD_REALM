package com.example.dani.crud_realm.Model;

import android.widget.EditText;

import io.realm.RealmObject;

/**
 * Created by Dani on 3/22/2018.
 */

public class Person extends RealmObject {

    String name;
    int age;

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

    @Override
    public String toString() {

        return
                "name='" + name + '\'' +
                ", age=" + age +"\n"
                ;
    }
}
