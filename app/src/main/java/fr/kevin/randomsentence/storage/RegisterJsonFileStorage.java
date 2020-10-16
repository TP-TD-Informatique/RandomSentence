package fr.kevin.randomsentence.storage;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import fr.kevin.randomsentence.model.Register;
import fr.kevin.randomsentence.storage.utils.JsonFileStorage;

public class RegisterJsonFileStorage extends JsonFileStorage<Register> {
    public static final String APP_NAME = "randomSentence";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String ASSOCS = "assocs";

    private static RegisterJsonFileStorage STORAGE;

    public static RegisterJsonFileStorage get(Context context) {
        if (STORAGE == null) STORAGE = new RegisterJsonFileStorage(context);

        return STORAGE;
    }

    private RegisterJsonFileStorage(Context context) {
        super(context, APP_NAME);
    }

    @Override
    protected JSONObject objectToJsonObject(int id, Register object) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(NAME, object.getName());
            JSONObject assocs = new JSONObject();
            for (String word : object.getWords().keySet()) {
                assocs.put(word, new JSONArray(object.getWords().get(word)));
            }
            jsonObject.put(ASSOCS, assocs);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    protected Register jsonObjectToObject(JSONObject jsonObject) {
        try {
            String name = jsonObject.getString(NAME);

            HashMap<String, ArrayList<String>> assocs = new HashMap<>();
            JSONObject a = jsonObject.getJSONObject(ASSOCS);
            for (Iterator<String> it = a.keys(); it.hasNext(); ) {
                String word = it.next();
                ArrayList<String> words = new ArrayList<>();
                for (int i = 0; i < a.getJSONArray(word).length(); i++) {
                    words.add(a.getJSONArray(word).getString(i));
                }

                assocs.put(word, words);
            }

            return new Register(name, assocs);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
