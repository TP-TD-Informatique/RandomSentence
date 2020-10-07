package fr.kevin.randomsentence.storage.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Implémentation de l'interface Storage pour le stockage dans un fichier
 *
 * @param <T>
 * @see Storage
 */
public abstract class FileStorage<T> implements Storage<T> {
    /**
     * Le prefixe pour le fichier de stockage
     */
    private final static String PREFIX = "storage_";

    /**
     * Le contexte de l'application
     */
    private Context context;
    /**
     * Le nom du fichier
     */
    private String filename;

    /**
     * Constructeur, il faut lui donner le contexte de l'application, le nom du fichier, et l'extension du fichier
     *
     * @param context   Context
     * @param name      String
     * @param extension String
     */
    public FileStorage(Context context, String name, String extension) {
        this.context = context;
        this.filename = PREFIX + name + extension;
        read();
    }

    /**
     * Méthode abstraite pour créer la structure de donnée
     */
    protected abstract void create();

    /**
     * Méthode abstraite pour initialiser la structure de données
     *
     * @param value String
     */
    protected abstract void initialize(String value);

    /**
     * Méthode abstraire qui renvoie la valeur de la structure de données
     *
     * @return String
     */
    protected abstract String getValue();

    /**
     * Lit le fichier et initialise la structure de données
     */
    protected void read() {
        try {
            FileInputStream in = context.openFileInput(filename);
            System.out.println(context.getFilesDir());
            if (in != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                String temp;
                StringBuilder builder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    builder.append(temp);
                }
                in.close();
                initialize(builder.toString());
            }
        } catch (FileNotFoundException e) {
            create();
            write();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ecrit la structure données dans le fichier
     */
    protected void write() {
        try {
            FileOutputStream out = context.openFileOutput(filename, Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(out);
            writer.write(getValue());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
