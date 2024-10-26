package main.java.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public interface FileManager<T> {

    default List<String> importFilesList(Path folderPath){
        try {
            return Files.list(folderPath)
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .filter(name -> name.endsWith(".dat"))
                    .toList();
        } catch (IOException e) {
            //logi - jakiś błąd
            return null;
        }
    }

    List<T> importDatabase();

    void exportDatabase(List<T> data);

    void clearDatabase();

    void exportItem(T item);

    T importItem(String path);

    void deleteItem(String path);


}
