package fr.kevin.randomsentence.storage;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import fr.kevin.randomsentence.model.Register;
import fr.kevin.randomsentence.storage.utils.JsonFileStorage;

/**
 * Implémentation de la class JsonFileStorage pour la classe Register
 *
 * @see JsonFileStorage
 */
public class RegistersJsonFileStorage extends JsonFileStorage<Register> {

    /**
     * Constructeur, on lui donne le contexte de l'application et le nom du fichier
     *
     * @param context Context
     * @param name    String
     */
    public RegistersJsonFileStorage(Context context, String name) {
        super(context, name);
    }

    /**
     * @param id     int
     * @param object T
     * @return JSONObject
     * @see JsonFileStorage
     */
    @Override
    protected JSONObject objectToJsonObject(int id, Register object) {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject assoc = new JSONObject();
            for (String word : object.getWords()) {
                JSONObject words = new JSONObject();
                int i = 0;
                for (String w : object.getAssoc(word)) {
                    words.put(String.valueOf(i), w);
                    i++;
                }
                assoc.put(word, words);
            }
            jsonObject.put(object.getName(), assoc);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    /**
     * @param jsonObject JSONObject
     * @return Register
     * @see JsonFileStorage
     */
    @Override
    protected Register jsonObjectToObject(JSONObject jsonObject) {
        Iterator<String> keys = jsonObject.keys();
        String name = keys.hasNext() ? keys.next() : "";

        HashMap<String, ArrayList<String>> assoc = new HashMap<>();

        try {
            Iterator<String> words = jsonObject.getJSONObject(name).keys();
            while (words.hasNext()) {
                String word = words.next();
                Iterator<String> w = jsonObject.getJSONObject(name).getJSONObject(word).keys();
                ArrayList<String> a = new ArrayList<>();
                while (w.hasNext()) {
                    a.add(w.next());
                }
                assoc.put(word, a);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new Register(name, assoc);
    }
}
