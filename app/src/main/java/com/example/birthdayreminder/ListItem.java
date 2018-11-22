package com.example.birthdayreminder;

public abstract class ListItem {

    public static final int TYPE_DATE = 0;
    public static final int TYPE_BIRTHDAY = 1;

    public abstract int getType();

}
