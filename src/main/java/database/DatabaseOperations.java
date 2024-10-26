package main.java.database;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public interface DatabaseOperations<T> {
    List<T> importDataFromFile();

    void exportDataToFile();

    void addItemToDatabase(T item);

    void removeItemFromDatabase(T item);

    T getItemFromDatabase(int id);

    void updateItemInDatabase(T item);

    void clearDatabase();

    List<T> getAll();

    List<T> getSorted(Comparator<T> comparator);

    List<T> getFiltered(Predicate<T> filter);

    boolean checkDuplicate(int id);
}
