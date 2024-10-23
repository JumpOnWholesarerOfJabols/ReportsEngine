package main;

import main.java.classes.Report;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Report report = new Report(2137, "test", "tegsgfsgsfgst");
        Report report2 = new Report(2137, "tescik", "adsasdasads");
        Report report3 = new Report(1244, "test", "zxzx");
        Report report4 = new Report(2517, "test", "fsgssasd");

        List<Report> reports = new ArrayList<>(List.of(report, report2, report3, report4));

        OutputStream fileOut = new FileOutputStream("src/main/resources/reports.txt");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);

        out.writeObject(reports);

        InputStream fileIn = new FileInputStream("src/main/resources/reports.txt");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        List<Report> reports2 = (List<Report>) in.readObject();

        reports2.forEach(s->System.out.println(s.toString()));
    }
}
