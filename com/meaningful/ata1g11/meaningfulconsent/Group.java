package com.meaningful.ata1g11.meaningfulconsent;

import java.util.ArrayList;

public class Group {
    private ArrayList<Child> Items;
    private String Name;

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public ArrayList<Child> getItems() {
        return this.Items;
    }

    public void setItems(ArrayList<Child> Items) {
        this.Items = Items;
    }
}
