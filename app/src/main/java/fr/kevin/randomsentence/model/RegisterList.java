package fr.kevin.randomsentence.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Liste et gère tout les registres existant
 */
public class RegisterList {
    /**
     * La liste des registres
     */
    private HashMap<String, Register> registers;
    /**
     * Le registre actuel (utilisé)
     */
    private String actualRegister;

    /**
     * Constructeur de la liste des registre via une HashMap<> existante
     * @param registers HashMap<String, Register>
     * @param actualRegister String
     */
    public RegisterList(HashMap<String, Register> registers, String actualRegister) {
        this.registers = registers;
        this.actualRegister = actualRegister;
    }

    /**
     * Constructeur par défaut pour créer une nouvelle liste
     */
    public RegisterList() {
        this(new HashMap<String, Register>(), "");
    }

    /**
     * Créé un nouveau registre
     * @param name String
     */
    public void create(String name) {
        registers.put(name, new Register());
    }

    /**
     * Supprime un registre
     * @param name String
     */
    public void remove(String name) {
        registers.remove(name);
    }

    /**
     * Permet de choisir le registre à utiliser
     * Renvoie false si le registre n'existe pas
     * @param name String
     * @return boolean
     */
    public boolean choose(String name) {
        if (registers.containsKey(name)) {
            actualRegister = name;
            return true;
        }
        return false;
    }

    /**
     * Renvoie le registre en cours d'utilisation
     * Renvoie null si le registre n'existe pas ou si aucun registre n'a été choisis
     * @return Register
     */
    public Register getRegister() {
        if (actualRegister.equals("")) {
            return null;
        } else {
            return registers.get(actualRegister);
        }
    }

    /**
     * Renvoie la liste des noms des registres
     * @return ArrayList<String>
     */
    public ArrayList<String> getRegistersName() {
        return new ArrayList<>(registers.keySet());
    }

    /**
     * Renvoie la liste des registres
     * @return HashMap<String, Register>
     */
    public HashMap<String, Register> getRegisters() {
        return registers;
    }

    /**
     * Renvoie le nom du registre utilisé
     * @return String
     */
    public String getActualRegister() {
        return actualRegister;
    }
}
