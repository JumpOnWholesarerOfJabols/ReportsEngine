package main.java.database;

import main.java.classes.Report;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface DatabaseOperations<T> {
    Map<Integer, T> importDataFromFile();

    void exportDataToFile();

    void addItemToDatabase(T item);

    void addItemToDatabase(int id,T item);

    void removeItemFromDatabase(int id);

    T getItemFromDatabase(int id);

    void updateItemInDatabase(int id,T item);

    void clearDatabase();

    Map<Integer, T> getAll();

    LinkedHashMap<Integer, T> getSorted(Comparator<T> comparator, Map<Integer, T> map);

    Map<Integer, T>  getFiltered(Predicate<T> filter, Map<Integer, T> map);

    boolean checkDuplicate(int id);
}
