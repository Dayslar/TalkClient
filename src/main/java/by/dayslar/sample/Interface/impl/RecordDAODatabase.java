package by.dayslar.sample.Interface.impl;

import by.dayslar.sample.Interface.RecordDAO;
import by.dayslar.sample.Model.Record;
import by.dayslar.sample.Utilites.DialogUtil;
import by.dayslar.sample.Utilites.RecordUtil;
import com.mysql.fabric.jdbc.FabricMySQLDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;

import java.sql.*;

public class RecordDAODatabase implements RecordDAO {

    private static Logger LogArea = Logger.getLogger(RecordDAODatabase.class);

    private final String DB_SELECT_DATA = "SELECT *FROM record WHERE call_time BETWEEN ? and ?";
    private final String DB_SELECT_DATA_SUBDIVISION = "SELECT * FROM record WHERE call_time BETWEEN ? and ? and subdivision = ?";
    private final String DB_DELETE = "DELETE record WHERE _id = ?";

//    private static final String URL = "jdbc:mysql://localhost/mydb";
    private static final String URL = "jdbc:mysql://192.168.21.61/mydb";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";

    private static Connection connection;

    public RecordDAODatabase(){
        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            initializeError("Ошибка регистрации драйвера","***SQL EXCEPTION***  Не удалось зарегистрировать драйвер: \n" + e);
        }
    }

    public Connection getConnection(){
        if (connection == null)
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            initializeError("Ошибка подключения к базе","***SQL EXCEPTION***  Не удалось подключиться к базе - " + e);
        }

        return connection;
    }

    @Override
    public ObservableList<Record> getRecords(long oldDate, long newDate, String subdivision) {
        Record localRecord;
        ObservableList<Record> records = FXCollections.observableArrayList();
        getConnection();

        ResultSet resultSet = null;

        boolean isSubdivision = subdivision.equals(RecordUtil.FilterType.TYPE_ALL_SUBDIVISION);
        String finalSql = isSubdivision?DB_SELECT_DATA: DB_SELECT_DATA_SUBDIVISION;

//        System.out.println(finalSql);

        try (PreparedStatement preparedStatement = connection.prepareStatement(finalSql)) {

            preparedStatement.setLong(1, oldDate);
            preparedStatement.setLong(2, newDate);

            if (!isSubdivision)
                preparedStatement.setString(3, subdivision);

            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();

            while (resultSet.next()){
                localRecord = readRecord(resultSet);
                records.add(localRecord);
            }

            return records;

        } catch (SQLException e) {
           initializeError("Ошибка получения данных","***SQL EXCEPTION***   Не удалось получить данные из базы: \n" + e);
        }

        finally {
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    //NOT
                }
        }

        return records;
    }

    @Override
    public boolean deleteRecord(int id) {
        Connection connection = getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(DB_DELETE)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.execute();
        }
        catch (SQLException e){
            initializeError("Ошибка удаления записи", "***SQL EXCEPTION***   Не удалось удалить запись: \n " + e);
            return false;
        }
    }

    private Record readRecord(ResultSet resultSet) throws SQLException {

        Record record = new Record();
        record.setId(resultSet.getInt(RecordItems.RECORD_ID));
        record.setFilePatch(resultSet.getString(RecordItems.RECORD_FILE_PATCH));
        record.setPhoneNumber(resultSet.getString(RecordItems.RECORD_PHONE_NUMBER));
        record.setCallNumber(resultSet.getString(RecordItems.RECORD_CALL_NUMBER));
        record.setCallDuration(resultSet.getInt(RecordItems.RECORD_CALL_DURATION));
        record.setIsCall(resultSet.getBoolean(RecordItems.RECORD_IS_CALL));
        record.setCallTime(resultSet.getLong(RecordItems.RECORD_CALL_TIME));
        record.setContactName(resultSet.getString(RecordItems.RECORD_CONTACT_NAME));
        record.setIsCallAnswer(resultSet.getBoolean(RecordItems.RECORD_IS_CALL_ANSWER));
        record.setManagerName(resultSet.getString(RecordItems.RECORD_MANAGER_NAME));
        record.setSubdivision(resultSet.getString(RecordItems.RECORD_SUBDIVISION));

        return record;
    }

    private void initializeError(String title, String info) {
        LogArea.fatal(info);
        DialogUtil.showErrorDialog(title, info);
    }

    //описывает константы для доступа к полям resultSet
    class RecordItems{
        public static final int RECORD_ID = 1;
        public static final int RECORD_FILE_PATCH = 2;
        public static final int RECORD_PHONE_NUMBER = 3;
        public static final int RECORD_CALL_NUMBER = 4;
        public static final int RECORD_CALL_DURATION = 5;
        public static final int RECORD_IS_CALL = 6;
        public static final int RECORD_CALL_TIME = 7;
        public static final int RECORD_CONTACT_NAME = 8;
        public static final int RECORD_IS_CALL_ANSWER = 9;
        public static final int RECORD_MANAGER_NAME = 10;
        public static final int RECORD_SUBDIVISION = 11;


    }

}
