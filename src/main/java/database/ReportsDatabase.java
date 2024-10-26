package main.java.database;

import main.java.classes.Report;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;

public class ReportsDatabase implements DatabaseOperations<Report> {

    private static final ReportsFileManager reportsFileManager = new ReportsFileManager();

    private final List<Report> data = new ArrayList<>(importDataFromFile());

    @Override
    public List<Report> importDataFromFile() {
        return reportsFileManager.importDatabase();
    }

    @Override
    public void exportDataToFile() {
        reportsFileManager.exportDatabase(data);
    }

    @Override
    public void addItemToDatabase(Report item) {

        reportsFileManager.exportItem(item);
    }

    @Override
    public void removeItemFromDatabase(Report item) {
        if (checkDuplicate(item.getReportId())) {
            data.remove(item);
            reportsFileManager.deleteItem(ReportsFileManager.makeFilePathFromId(item.getReportId()));
        }
    }

    @Override
    public Report getItemFromDatabase(int id) {
        return data.stream()
                .filter(report -> report.getReportId() == id).findFirst().orElse(null);
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

    @Override
    public boolean checkDuplicate(int id) {
        return data.stream().anyMatch(r -> r.getReportId() == id);
    }

    @Override
    public void updateItemInDatabase(Report item) {
        removeItemFromDatabase(item);
        addItemToDatabase(item);
    }

    @Override
    public void clearDatabase() {
        data.clear();
        clearDatabase();
    }



    private static class ReportsFileManager implements FileManager<Report> {

        private static final String folderPath = "src/main/resources/reports/";

        private final Path path = Paths.get(folderPath);
        private final List<String> filesList = new ArrayList<>(importFilesList(path));


        private static String makeFilePathFromId(int id){
            return folderPath+id+".dat";
        }

        private boolean containsId(int id){
            refreshFilesList();
            return filesList.contains(makeFilePathFromId(id));
        }

        private void refreshFilesList(){
            filesList.clear();
            filesList.addAll(importFilesList(path));
        }

        @Override
        public List<Report> importDatabase() {
            return filesList.stream().map(this::importItem).filter(Objects::nonNull).toList();
        }

        @Override
        public void exportDatabase(List<Report> data) {
            data.forEach(this::exportItem);
        }

        @Override
        public void clearDatabase() {
            List<String> filesToDelete = new ArrayList<>(filesList); //nie wiem dlaczego bez tego nie działa
            filesToDelete.forEach(this::deleteItem);
            refreshFilesList();
        }

        @Override
        public void exportItem(Report item) {
            int id = item.getReportId();
            String filename = makeFilePathFromId(id);
            Path itemPath = Paths.get(filename);

            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(itemPath.toFile()))){
                out.writeObject(item);
                filesList.add(filename);
                //logi - wyeksportowano item
            } catch (IOException e) {
                //logi - blad podczas eksportu itemu
            }
        }


        @Override
        public Report importItem(String path) {

            Path reportPath = Paths.get(path);

            if (!Files.exists(reportPath)) {
                //logi - że brak pliku
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
            Path reportPath = Paths.get(path);

            if (!Files.exists(reportPath)) {
                //logi - że brak pliku
                return;
            }

            try {
                Files.delete(reportPath);
                filesList.remove(path);
                //logi ze usuwamy plik
            } catch (IOException e) {
                // logi ze jakiś blad
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


        List<Report> reports = reportsFileManager.importDatabase();
        reports.forEach(System.out::println);


        reportsFileManager.refreshFilesList();
        reportsFileManager.clearDatabase();


        try {
            List<String> nazwy = Files.list(path).map(Path::toString).toList();
            nazwy.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




        reportsFileManager.clearDatabase();

        //reportsFileManager.exportDatabase(reports);

        reports.forEach(System.out::println);




        //TUTAJ CHYBA WSZYSTKO DZIAŁA
    }
}