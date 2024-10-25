package main.java.classes;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Report implements Serializable {

    public enum reportStatus{OPEN,ASSIGNED,CLOSED}

    private final int reportId;
    private final int userId;
    private String title;
    private String description;
    private int assignmentWorkerID;
    private reportStatus status;
    private final String date;

    private static int reportIdCounter = 1;

    private static String getCurrentDate(){
        SimpleDateFormat dayMonthYear = new SimpleDateFormat("dd/MM/yyyy");
        return dayMonthYear.format(LocalDate.now());
    }

    public Report(int userID, String title, String description) {
        this.reportId = reportIdCounter++;
        this.userId = userID;
        this.title = title;
        this.description = description;
        this.assignmentWorkerID = -1;
        this.status = reportStatus.OPEN;
        this.date = getCurrentDate();
    }

    public int getReportId() {
        return reportId;
    }

    public int getUserId() {
        return userId;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAssignmentWorkerID() {
        return assignmentWorkerID;
    }

    public void setAssignmentWorkerID(int assignmentWorkerID) {
        this.assignmentWorkerID = assignmentWorkerID;
    }

    public reportStatus getStatus() {
        return status;
    }

    public void setStatus(reportStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return reportId + " - " + userId + " - " + title + " - " + description;
    }
}
