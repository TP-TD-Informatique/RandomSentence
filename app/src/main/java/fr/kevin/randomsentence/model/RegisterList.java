package fr.kevin.randomsentence.model;

import java.io.Serializable;
import java.util.HashMap;

public class RegisterList extends HashMap<Integer, Register> implements Serializable {
    private int register;

    public RegisterList() {
        super();
        this.register = 0;
    }

    public RegisterList(HashMap<Integer, Register> map) {
        this();
        for (int key : map.keySet()) {
            put(key, map.get(key));
        }
    }

    public Register getActualRegister() {
        return get(register);
    }

    public void choose(String name) {
        for (int i = 0; i < size(); i++) {
            if (get(i).getName().equals(name)) register = i;
        }
    }

    public void choose(int id) {
        if (get(id) != null) {
            register = id;
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

    public int getRegister() {
        return register;
    }
}