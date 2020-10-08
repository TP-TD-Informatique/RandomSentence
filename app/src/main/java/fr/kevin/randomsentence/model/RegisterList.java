package fr.kevin.randomsentence.model;

import java.io.Serializable;
import java.util.ArrayList;

public class RegisterList extends ArrayList<Register> implements Serializable {
    private int register;

    public RegisterList() {
        super();
        this.register = 0;
    }

    public Register getActualRegister() {
        return get(register);
    }

    public void choose(String name) {
        for (int i = 0; i < size(); i++) {
            if (get(i).getName().equals(name)) register = i;
        }
    }

    public Register get(String name) {
        for (int i = 0; i < size(); i++) {
            if (get(i).getName().equals(name)) return get(i);
        }
        return null;
    }

    public void remove(String name) {
        remove(get(name));
    }
}