package main.java.database;

import main.java.classes.Report;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class DatabaseManager {

    private static final String reportsFilePath = "src/main/resources/reports.txt";






    private interface DatabaseOperations<T> {
        List<T> importDataFromFile(String path);
        boolean exportDataToFile(String path);
        boolean addItemToDatabase(T item);
        boolean removeItemFromDatabase(T item);
        T getItemFromDatabase(int id);
        List<T> getAll();
        List<T> getSorted(Comparator<T> comparator);
        List<T> getFiltered(String filter);

    }

    private static class ReportsDatabase implements DatabaseOperations<Report> {

        List<Report> data;


        @SuppressWarnings("unchecked")
        @Override
        public List<Report> importDataFromFile(String path) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(path))) {
                var object = inputStream.readObject();

                if (object instanceof List && !((List<?>) object).isEmpty() && ((List<?>) object).getFirst() instanceof Report) {
                    return (List<Report>) object;
                }
            } catch (ClassNotFoundException | IOException e) {
                System.out.println(e.getMessage());
            }
            return new ArrayList<>();
        }

        @Override
        public boolean exportDataToFile(String path) {
            return false;
        }

        @Override
        public boolean addItemToDatabase(Report item) {
            return false;
        }

        @Override
        public boolean removeItemFromDatabase(Report item) {
            return false;
        }

        @Override
        public Report getItemFromDatabase(int id) {
            return null;
        }

        @Override
        public List<Report> getAll() {
            return List.of();
        }

        @Override
        public List<Report> getSorted(Comparator<Report> comparator) {
            return List.of();
        }

        @Override
        public List<Report> getFiltered(String filter) {
            return List.of();
        }
    }


    public static void main(String[] args) {
        ReportsDatabase database = new ReportsDatabase();
        database.importDataFromFile(reportsFilePath);
    }



    //     Tak ja to przynajmniej widzę. Dostęp do bazy danych zrobić pośredni, bo:
//         a) Nie wydaje mi się, że będzie dobrze jak będzie bezpośrednio dostęp do bazy danych, tak chyba bezpieczniej
//         b) Co z tego wynika - klasa baza danych nie musi być publiczna, bo i tak nie będzie do niej dostępu
//         c) NAJWAŻNIEJSZE - spełnimy wymaganie klasy zagnieżdżonej :)
//    private static class Database<T>{
//        private final String filePath;
//        private List<T> data;
//
//        private Database(String filePath) {
//            this.filePath = filePath;
//            this.data = importData(this.filePath);
//        }
//
//        private List<T> importData(String path)  {
//            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(path))) {
//                var object = inputStream.readObject();
//                if (object instanceof List<?>) {
//                    return (List<T>) object;
//                }
//            } catch (IOException | ClassNotFoundException e) {
//                System.out.println(e.getMessage());
//            }
//            return new ArrayList<>();
//        }
//
//        private void exportReports(String path){
//            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(path))) {
//                outputStream.writeObject(data);
//            } catch (IOException e) {
//                e.getMessage();
//            }
//        }
//
//    }
}
