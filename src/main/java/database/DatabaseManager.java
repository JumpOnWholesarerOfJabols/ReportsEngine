package main.java.database;

import main.java.classes.Report;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {



    private static final String reportsFilePath = "src/main/resources/reports.txt";
    private Database database;


    public DatabaseManager() {
        this.database = new Database(reportsFilePath);
    }




    //     Tak ja to przynajmniej widzę. Dostęp do bazy danych zrobić pośredni, bo:
//         a) Nie wydaje mi się, że będzie dobrze jak będzie bezpośrednio dostęp do bazy danych, tak chyba bezpieczniej
//         b) Co z tego wynika - klasa baza danych nie musi być publiczna, bo i tak nie będzie do niej dostępu
//         c) NAJWAŻNIEJSZE - spełnimy wymaganie klasy zagnieżdżonej :)
    private static class Database<T>{
        private final String filePath;
        private List<T> data;

        private Database(String filePath) {
            this.filePath = filePath;
            this.data = importData(this.filePath);
        }

        private List<T> importData(String path)  {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(path))) {
                var object = inputStream.readObject();
                if (object instanceof List<?>) {
                    return (List<T>) object;
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
            return new ArrayList<>();
        }

        private void exportReports(String path){
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(path))) {
                outputStream.writeObject(data);
            } catch (IOException e) {
                e.getMessage();
            }
        }

    }
}
