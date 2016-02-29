package by.dayslar.sample.Interface;

import javafx.collections.ObservableList;
import by.dayslar.sample.Model.Record;

public interface RecordDAO {

    //возвращает список записей
    ObservableList<Record> getRecords(long oldDate, long newDate, String sql);

    //удаляет запись по ид
    boolean deleteRecord(int id);

}
