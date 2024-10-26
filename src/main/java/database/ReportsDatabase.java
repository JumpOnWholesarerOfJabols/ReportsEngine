package main.java.database;

import main.java.classes.Report;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ReportsDatabase /*implements DatabaseOperations<Report>*/ {

    private static final ReportsFileManager reportsFileManager = new ReportsFileManager();

    private final List<Report> data = new ArrayList<>(reportsFileManager.importDatabase());






    private static class ReportsFileManager implements FileManager<Report> {

        private static final String folderPath = "src/main/resources/reports/";

        private final Path path = Paths.get(folderPath);
        private List<String> filesList = new ArrayList<>(importFilesList(path));


        private boolean containsId(int id){
            return false;
        }

        @Override
        public List<Report> importDatabase() {
            List<Report> reports = new ArrayList<>();
            reports = filesList.stream().map(this::importItem).filter(Objects::nonNull).toList();
            return reports;
        }

        @Override
        public void exportDatabase(List<Report> data) {
            data.forEach(this::exportItem);
        }

        @Override
        public void clearDatabase() {
            filesList.forEach(this::deleteItem);
        }

        @Override
        public void exportItem(Report item) {
            int id = item.getReportId();
            Path itemPath = Paths.get(folderPath + id + ".dat");

            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(itemPath.toFile()))){
                out.writeObject(item);
                //logi - wyeksportowano item
            } catch (IOException e) {
                //logi - blad podczas eksportu itemu
            }
            
        }


        @Override
        public Report importItem(String path) {

            Path reportPath = Paths.get(path);

            if (!filesList.contains(path)){
                //logi że brak pliku
                return null;
            }

            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(reportPath.toFile()))) {
                return (Report) in.readObject();
                //logi - wczytano item
            } catch (IOException | ClassNotFoundException e) {
                //logi - blad podczas wczytywania itemu
                return null;
            }
        }

        @Override
        public void deleteItem(String path) {
            if (!filesList.contains(path)){
                //logi że brak pliku
            }

            try {
                Files.delete(Path.of(path));
                //logi ze usuwamy plik
            } catch (IOException e) {
                // logi ze blad
            }

        }
    }


    public static void main(String[] args) {

        String folderPath = "src/main/resources/reports";
        Path path = Paths.get(folderPath);

        ReportsFileManager reportsFileManager = new ReportsFileManager();


        reportsFileManager.deleteItem("src/main/resources/reports/21.dat");
        reportsFileManager.exportItem(new Report(42,21,"COSIK", "DASASD"));
        reportsFileManager.exportItem(new Report(21,21,"COSIK", "DASASD"));
        reportsFileManager.exportItem(new Report(56,21,"COSIK", "DASASD"));
        reportsFileManager.exportItem(new Report(12,21,"COSIK", "DASASD"));

        try {
            List<String> nazwy = Files.list(path).map(Path::toString).toList();
            nazwy.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Report> reports = reportsFileManager.importDatabase();

        reportsFileManager.clearDatabase();

        //reportsFileManager.exportDatabase(reports);

        reports.forEach(System.out::println);



    }
}