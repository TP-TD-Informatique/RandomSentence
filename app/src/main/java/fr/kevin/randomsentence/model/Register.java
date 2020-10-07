package fr.kevin.randomsentence.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Le registre qui contient les associations de mots vers des ArrayList<> de mots
 */
public class Register {
    /**
     * Cette HashMap<> associe à chaque mot une ArrayList<> de mots qui sont les mots qui peuvent suivre
     */
    private HashMap<String, ArrayList<String>> words;

    /**
     * Constructeur du registre à partir d'une HashMap<> existante
     * @param words HashMap<String, ArrayList<String>>
     */
    public Register(HashMap<String, ArrayList<String>> words) {
        this.words = words;
    }

    /**
     * Constructeur par défaut pour créer un nouveau registre
     */
    public Register() {
        this(new HashMap<String, ArrayList<String>>());
    }

    /**
     * Ajoute une nouvelle association au mot 'word'
     * @param word String
     * @param nextWord String
     */
    private void add(String word, String nextWord) {
        if (words.get(word) == null) words.put(word, new ArrayList<String>());

        Objects.requireNonNull(words.get(word)).add(nextWord);
    }

    /**
     * Découpe la chaine 'sentence' autour des espaces et créé les associations pour remplir le registre
     * @param sentence String
     */
    public void add(String sentence) {
        ArrayList<String> w = cut(sentence);

        String prec = "";
        for (String word : w) {
            add(prec, word);
            prec = word;
        }
    }

    /**
     * Renvoie 'quantity' mots généré aléatoirement
     * @param quantity int
     * @return String
     */
    public String generate(int quantity) {
        StringBuilder builder = new StringBuilder(getRandom(""));
        String prec = builder.toString();

        for (int i = 0; i < quantity; i++) {
            prec = getRandom(prec);
            builder.append(" ");
            builder.append(prec);
        }

        return builder.toString();
    }

    /**
     * Renvoie le nombre d'associations existantes dans ce registre
     * @return int
     */
    public int size() {
        int result = 0;

        for (ArrayList<String> assoc : words.values()) {
            result += assoc.size();
        }

        return result;
    }

    /**
     * Renvoie un mot aléatoire dans l'ArrayList<> de 'word'
     * @param word String
     * @return String
     */
    private String getRandom(String word) {
        if (words.get(word) != null) {
            return Objects.requireNonNull(words.get(word)).get(random(Objects.requireNonNull(words.get(word)).size() - 1));
        } else {
            return "";
        }
    }

    /**
     * Renvoie un entier aléatoire en 0 et 'end'
     * @param end int
     * @return int
     */
    private int random(int end) {
        return (int) (Math.random() * end);
    }

    /**
     * Découpe sentence autour des espaces en prenant en compte la ponctuation
     * @param sentence String
     * @return ArrayList<String>
     */
    private ArrayList<String> cut(String sentence) {
        final String delimiters = ",?;.:!";

        ArrayList<String> result = new ArrayList<>();

        StringBuilder word = new StringBuilder();
        for (int i = 0; i < sentence.length(); i++) {
            char c = sentence.charAt(i);
            if (c == ' ') {
                if (word.length() > 0)
                    result.add(word.toString());
                word = new StringBuilder();
            } else if (delimiters.contains(String.valueOf(c))) {
                if (word.length() > 0)
                    result.add(word.toString());
                word = new StringBuilder();
                result.add(String.valueOf(c));
            } else
                word.append(c);
        }
        if (word.length() > 0)
            result.add(word.toString());

        return result;
    }
}
