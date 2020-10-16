package fr.kevin.randomsentence.storage.utils;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Implémentation de la classe abstraite FileStorage pour les fichier json
 *
 * @param <T>
 * @see FileStorage
 */
public abstract class JsonFileStorage<T> extends FileStorage<T> {
    /**
     * L'extension des fichiers
     */
    private final static String EXTENSION = ".json";
    /**
     * Le nom pour la liste
     */
    private final static String LIST = "list";
    /**
     * Le nom pour le stockage de l'id suivant
     */
    private final static String NEXT_ID = "next_id";
    /**
     * L'id utilisé actuellement
     */
    private final static String USE_ID = "use_id";

    /**
     * L'objet json pour manipuler la structure de données
     */
    protected JSONObject json;

    /**
     * Constructeur, on lui donne le contexte de l'application, et le nom du fichier
     *
     * @param context Context
     * @param name    String
     */
    public JsonFileStorage(Context context, String name) {
        super(context, name, EXTENSION);
    }

    /**
     * Convertis l'objet en objet json
     *
     * @param id     int
     * @param object T
     * @return JSONObject
     */
    protected abstract JSONObject objectToJsonObject(int id, T object);

    /**
     * Convertis l'objet json en l'objet T
     *
     * @param jsonObject JSONObject
     * @return T
     */
    protected abstract T jsonObjectToObject(JSONObject jsonObject);

    /**
     * @see Storage
     */
    @Override
    protected void create() {
        json = new JSONObject();
        try {
            json.put(LIST, new JSONObject());
            json.put(NEXT_ID, 1);
            json.put(USE_ID, 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param value String
     * @see Storage
     */
    @Override
    protected void initialize(String value) {
        try {
            json = new JSONObject(value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return String
     * @see Storage
     */
    @Override
    protected String getValue() {
        return json.toString();
    }

    /**
     * @param object T
     * @see Storage
     */
    @Override
    public void insert(T object) {
        int nextId = json.optInt(NEXT_ID);
        try {
            json.getJSONObject(LIST).put(String.valueOf(nextId), objectToJsonObject(nextId, object));
            json.put(NEXT_ID, nextId + 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        write();
    }

    /**
     * @return List<T>
     * @see Storage
     */
    @Override
    public HashMap<Integer, T> findAll() {
        HashMap<Integer, T> result = new HashMap<>();
        try {
            Iterator<String> iterator = json.getJSONObject(LIST).keys();
            while (iterator.hasNext()) {
                String id = iterator.next();
                result.put(Integer.valueOf(id), jsonObjectToObject(json.getJSONObject(LIST).getJSONObject(id)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param id int
     * @return T
     * @see Storage
     */
    @Override
    public T find(int id) {
        T object = null;
        try {
            object = jsonObjectToObject(json.getJSONObject(LIST).getJSONObject(String.valueOf(id)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * @return int
     * @see Storage
     */
    @Override
    public int size() {
        int size = 0;
        try {
            size = json.getJSONObject(LIST).length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * @param id     int
     * @param object T
     * @see Storage
     */
    @Override
    public void update(int id, T object) {
        try {
            json.getJSONObject(LIST).put(String.valueOf(id), objectToJsonObject(id, object));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        write();
    }

    /**
     * @param id int
     * @see Storage
     */
    @Override
    public void delete(int id) {
        try {
            json.getJSONObject(LIST).remove(String.valueOf(id));

            if (id == json.optInt(USE_ID)) json.put(USE_ID, 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        write();
    }

    @Override
    public void use(int id) {
        try {
            json.put(USE_ID, id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        write();
    }

    @Override
    public int getUsed() {
        return json.optInt(USE_ID);
    }
}
