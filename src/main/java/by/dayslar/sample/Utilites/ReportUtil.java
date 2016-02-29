package by.dayslar.sample.Utilites;

import by.dayslar.sample.Interface.impl.RecordDAODatabase;
import by.dayslar.sample.Model.Record;
import by.dayslar.sample.Model.Report;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportUtil {

    private static ObservableList<Record> records;

    public static ObservableList<Record> getRecords() {
        return records;
    }

    public static void initialize(long oldDate, long newDate, String subdivision){
        records =  new RecordDAODatabase().getRecords(oldDate, newDate, subdivision);
    }

    public static ObservableList<String> getListSubdivision(ObservableList<Record> list) {
        ObservableList<String> listBoxManager = FXCollections.observableArrayList();
        Map<String, Boolean> map = new HashMap<>();

        for (Record record: list) {
            if (record.getSubdivision().equals("")) continue;
            map.put(record.getSubdivision(), true);
        }
        listBoxManager.addAll(map.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList()));

        return listBoxManager;
    }

    public static Report getReport(String subdivisionName){
        Report report = new Report();

        for (Record record: records) {
            if (subdivisionName.equals(RecordUtil.FilterType.TYPE_ALL_SUBDIVISION)) {
                addInfoReport(report, record);
            }

            else if (record.getSubdivision().equals(subdivisionName)){
                addInfoReport(report, record);
            }
        }

        return report;
    }

    private static void addInfoReport(Report report, Record record){

        if (record.isCall()){
            report.setOutgoingCount(report.getOutgoingCount() + 1);
            report.setOutgoingDuration(report.getOutgoingDuration() + record.getCallDuration());

            if (record.isCallAnswer())
                report.setOutgoingAnswerCount(report.getOutgoingAnswerCount() + 1);
            else
                report.setOutgoingNotAnswerCount(report.getOutgoingNotAnswerCount() + 1);
        }

        else {
            report.setIncomingCount(report.getIncomingCount() + 1);
            report.setIncomingDuration(report.getIncomingDuration() + record.getCallDuration());

            if (record.isCallAnswer())
                report.setIncomingAnswerCount(report.getIncomingAnswerCount() + 1);
            else
                report.setIncomingNotAnswerCount(report.getIncomingNotAnswerCount() + 1);
        }

        report.setAnswerCount(report.getOutgoingAnswerCount() + report.getIncomingAnswerCount());
        report.setNotAnswerCount(report.getOutgoingNotAnswerCount() + report.getIncomingNotAnswerCount());
        report.setCount(report.getIncomingCount() + report.getOutgoingCount());
        report.setDuration(report.getIncomingDuration() + report.getOutgoingDuration());

    }
}
