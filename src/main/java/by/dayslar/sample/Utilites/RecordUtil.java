package by.dayslar.sample.Utilites;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import by.dayslar.sample.Model.Record;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RecordUtil {

    private static Record selectRecord;
    private static ObservableList<Record> records;
    private static ObservableList<Record> filtered_records;

    public static ObservableList<Record> getRecords(){
        return records;
    }

    public static void setRecords(ObservableList<Record> records){
        RecordUtil.records = records;
    }

    public static Record getSelectRecord() {
       return selectRecord;
    }

    public static void setSelectRecord(Record selectRecord) {
        RecordUtil.selectRecord = selectRecord;
    }

//    private static ObservableList<Record> filterMissed(ObservableList<Record> list) {
//        ObservableList<Record> records = FXCollections.observableArrayList();
//
//        records.addAll(list.stream().filter(record -> !record.isCallAnswer()).collect(Collectors.toList()));
//        return records;
//    }
//
//    private static ObservableList<Record> filterIncoming(ObservableList<Record> list){
//        ObservableList<Record> records = FXCollections.observableArrayList();
//        records.addAll(list.stream().filter(record -> !record.isCall()).collect(Collectors.toList()));
//
//        return records;
//    }
//
//    private static ObservableList<Record> filterOutgoing(ObservableList<Record> list){
//        ObservableList<Record> records = FXCollections.observableArrayList();
//        records.addAll(list.stream().filter(Record::isCall).collect(Collectors.toList()));
//
//        return records;
//    }

    public static SortedList<Record> getSearchRecords(String oldValue){
        FilteredList<Record> records_filtered = new FilteredList<>(filtered_records, predicate -> true);
        records_filtered.setPredicate(record -> {

            if (oldValue.isEmpty()) return true;
            String newValueToLowerCase = oldValue.toLowerCase();

            return record.getManagerNameProperty().get().toLowerCase().contains((newValueToLowerCase))
                    || record.getContactNameProperty().get().toLowerCase().contains((newValueToLowerCase))
                    || record.getPhoneNumber().toLowerCase().contains((newValueToLowerCase))
                    || record.getCallNumber().toLowerCase().contains((newValueToLowerCase));

        });

        return new SortedList<>(records_filtered);
    }

    public static ObservableList<Record> getFilterRecord(ObservableList<Record> list, String typeCall, String managerName){

        ObservableList<Record> newList = FXCollections.observableArrayList();
        ObservableList<Record> filteredManagerNameList = getFilterRecordManager(list, managerName);
        ObservableList<Record> filteredTypeList = getFilteredType(filteredManagerNameList, typeCall);

        newList.addAll(filteredTypeList);

        return filtered_records = newList;

    }

    private static ObservableList<Record> getFilteredType(ObservableList<Record> list, String typeCall) {
        ObservableList<Record> newList = FXCollections.observableArrayList();

        if (!typeCall.equals("") && !typeCall.equals("Все записи")){

            boolean isCallAnswer = typeCall.equals(FilterType.TYPE_ANSWER);
            newList.addAll(list.stream().filter(record -> record.isCallAnswer() == isCallAnswer).collect(Collectors.toList()));

        }

        else
            newList.addAll(list);

        return newList;
    }

    public static ObservableList<Record> getFilterRecordManager(ObservableList<Record> list ,String suddivision){
        ObservableList<Record> newList = FXCollections.observableArrayList();

        if (!suddivision.equals("") && !suddivision.equals(FilterType.TYPE_ALL_SUBDIVISION))
            newList.addAll(list.stream().filter(record -> record.getSubdivision().equals(suddivision)).collect(Collectors.toList()));

        else newList.addAll(list);
        return newList;
    }

    public static ObservableList<String> getListBoxManager(ObservableList<Record> list) {
        ObservableList<String> listBoxManager = FXCollections.observableArrayList();
        Map<String, Integer> map = new HashMap<>();

        for (Record record: list) {
            if (record.getSubdivision().equals("")) continue;
            map.put(record.getSubdivision(), 0);
        }
        listBoxManager.addAll(map.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList()));

        return listBoxManager;
    }

    public static long getCalculationAllTime(ObservableList<Record> records){
        long time = 0;

        for (Record record: records){
            time = time + record.getCallDuration();
        }

        return time;
    }

    public class FilterType {
        public static final String TYPE_ANSWER = "Ответ";
        public static final String TYPE_MISSED = "Без ответа";
        public static final String TYPE_ALL_RECORD = "Все записи";
        public static final String TYPE_ALL_SUBDIVISION = "Все подразделения";
    }

}
