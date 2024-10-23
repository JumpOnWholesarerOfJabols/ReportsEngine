package main.java.database;

import main.java.classes.Report;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;

public class DatabaseManager {

    private static final String reportsFilePath = "src/main/resources/reports.txt";
    ReportsDatabase reportsDatabase = new ReportsDatabase(reportsFilePath);


    /*Tak ja to przynajmniej widzę. Dostęp do bazy danych zrobić pośredni, bo:
        a) Nie wydaje mi się, że będzie dobrze jak będzie bezpośrednio dostęp do bazy danych, tak chyba bezpieczniej
        b) Co z tego wynika - klasa baza danych nie musi być publiczna, bo i tak nie będzie do niej dostępu
        c) NAJWAŻNIEJSZE - spełnimy wymaganie klasy zagnieżdżonej :)
   */
    private interface DatabaseOperations<T> {
        List<T> importDataFromFile(String path);

        boolean exportDataToFile(String path);

        boolean addItemToDatabase(T item);

        boolean removeItemFromDatabase(T item);

        T getItemFromDatabase(int id);

        List<T> getAll();

        List<T> getSorted(Comparator<T> comparator);

        List<T> getFiltered(Predicate<T> filter);

    }

    private static class ReportsDatabase implements DatabaseOperations<Report> {

        private List<Report> data;
        private String path;

        public ReportsDatabase(String path) {
            this.path = path;
            data = importDataFromFile(path);
        }

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
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(path))) {
                outputStream.writeObject(data);
                return true;
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }

        @Override
        public boolean addItemToDatabase(Report item) {
            return data.add(item);
        }

        @Override
        public boolean removeItemFromDatabase(Report item) {
            return data.remove(item);
        }

        @Override
        public Report getItemFromDatabase(int id) {
            return data.stream().filter(s -> s.getReportId() == id).findFirst().orElse(null);
        }

        @Override
        public List<Report> getAll() {
            return data;
        }

        @Override
        public List<Report> getSorted(Comparator<Report> comparator) {
            return data.stream().sorted(comparator).toList();
        }

        @Override
        public List<Report> getFiltered(Predicate<Report> filter) {
            return data.stream().filter(filter).toList();
        }


    }


    public static void main(String[] args) {
        
    }
}