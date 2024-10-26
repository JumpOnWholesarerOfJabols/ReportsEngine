package main.java.database;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

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

    Map<Integer,T> importDatabase();

    void exportDatabase(Map<Integer,T> data);

    void clearDatabase();

    void exportItem(int key,T item);

    AbstractMap.SimpleEntry<Integer, T> importItem(String path);

    void deleteItem(int id);


}
