package by.dayslar.sample.Interface.impl;

import by.dayslar.sample.Interface.RecordDAO;
import by.dayslar.sample.Model.Record;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;

public class RecordDAOSocket implements RecordDAO {

    private static Logger LogArea = Logger.getLogger(RecordDAOSocket.class);

    private Socket socket;
    private static InputStream inputStream;
    private static OutputStream outputStream;

    public RecordDAOSocket(){
        try {
//            socket = new Socket(InetAddress.getByName(SettingUtil.getIp()), Integer.parseInt(SettingUtil.getPort()));
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

        } catch (IOException e) {
            LogArea.error("***IOException***   При создании сокета произошла ошибка - " + e);
        }

    }

    @Override
    public ObservableList<Record> getRecords(long oldDate, long newDate, String sql) {
        ObservableList<Record> records = FXCollections.observableArrayList();

        try (DataInputStream dataInputStream = new DataInputStream(inputStream);
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream)){

            dataOutputStream.writeLong(oldDate);
            dataOutputStream.writeLong(newDate);
            dataOutputStream.flush();

            int count = dataInputStream.readInt();

            for (int i =0; i < count; i++ ){
                records.add(readRecord(dataInputStream));
            }
        }
        catch (IOException e) {
            LogArea.error("***IOException***   При чтении записей из потока произошла ошибка - " + e);
        }

        finally {
            if (socket != null)
            try {
                socket.close();
            } catch (IOException e) {
                //not
            }
        }

        return records;
    }

    @Override
    public boolean deleteRecord(int id) {
        return true;
    }

    private Record readRecord(DataInputStream dataInputStream) {
        Record record = new Record();

        try {
            record.setId(dataInputStream.readInt());
            record.setFilePatch(dataInputStream.readUTF());
            record.setPhoneNumber(dataInputStream.readUTF());
            record.setCallNumber(dataInputStream.readUTF());
            record.setCallDuration(dataInputStream.readLong());
            record.setCallTime(dataInputStream.readLong());
            record.setContactName(dataInputStream.readUTF());
            record.setManagerName(dataInputStream.readUTF());
            record.setIsCall(dataInputStream.readBoolean());
            record.setIsCallAnswer(dataInputStream.readBoolean());
            record.setSubdivision(dataInputStream.readUTF());

        } catch (IOException e) {
           LogArea.error("***IOException***   При чтении записи произошла ошибка - " + e);
        }

        return record;
    }
}
