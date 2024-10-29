package main.java;

import main.java.classes.Report;
import main.java.database.DatabaseOperations;
import main.java.database.FilterMethods;
import main.java.database.ReportsDatabase;
import main.java.database.SortMethods;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String folderPath = "src/main/resources/reports/";
        DatabaseOperations<Report> reportsDatabase = new ReportsDatabase(folderPath);

        reportsDatabase.addItemToDatabase(12, new Report(42, "COSIK", "DASASD"));
        reportsDatabase.addItemToDatabase(32, new Report(21, "BOSIK", "DASASD"));
        reportsDatabase.addItemToDatabase(15, new Report(56, "HOSIK", "DASASD"));
        reportsDatabase.addItemToDatabase(66, new Report(12, "COSIK", "DASASD"));
        reportsDatabase.addItemToDatabase(54, new Report(12, "GOSIK", "DASASD"));
        reportsDatabase.addItemToDatabase(13, new Report(12, "GOSIK", "DASASD"));
        reportsDatabase.addItemToDatabase(99, new Report(12, "COSIK", "DASASD"));
        reportsDatabase.addItemToDatabase(90, new Report(12, "COSIK", "DASASD"));

        Map<Integer, Report> filtered = reportsDatabase.
                getFiltered(FilterMethods.combinedFilter(
                        FilterMethods.filterUserId(12),
                        FilterMethods.filterReportTitleFirstLetter('C')
                ));
        System.out.println("-".repeat(20));

        filtered.forEach((k, v) -> System.out.println(v + " - " + k.toString()));
        System.out.println("-".repeat(20));

        var sorted = reportsDatabase.getSorted(SortMethods.sortByUserId());
        sorted.forEach((k, v) -> System.out.println(k + " - " + v.toString()));
        System.out.println("-".repeat(20));

        reportsDatabase.getAll().forEach((k, v) -> System.out.println(v + " - " + k.toString()));

        reportsDatabase.clearDatabase();
        //reportsFileManager.exportDatabase(map);


        //reportsFileManager.refreshFilesList();
        //reportsFileManager.clearDatabase();


        //reportsFileManager.clearDatabase();

        //reportsFileManager.exportDatabase(reports);

        //reports.forEach(System.out::println);


        //TUTAJ CHYBA WSZYSTKO DZIAŁA
    }
}
