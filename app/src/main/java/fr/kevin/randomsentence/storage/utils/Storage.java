package fr.kevin.randomsentence.storage.utils;

import java.util.List;

/**
 * Interface pour gerer le stockage de données
 * @param <T>
 */
public interface Storage<T> {

    /**
     * Permet d'insérer l'objet
     * @param object T
     */
    void insert(T object);

    /**
     * Renvoie la liste de tout les objets qui ont été inserrés
     * @return List<T>
     */
    List<T> findAll();

    /**
     * Renvoie l'objet à l'id donné
     * Null si non existant
     * @param id int
     * @return T
     */
    T find(int id);

    /**
     * Renvoie la taille de liste des objets
     * @return int
     */
    int size();

    /**
     * Met à l'objet à l'id donné
     * @param id int
     * @param object T
     */
    void update(int id, T object);

    /**
     * Supprime l'objet à l'id donné
     * @param id int
     */
    void delete(int id);
}
