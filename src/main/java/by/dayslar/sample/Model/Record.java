package by.dayslar.sample.Model;

import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;

public class Record {

    private int id;
    private String filePatch;
    private String phoneNumber;
    private String callNumber;
    private String subdivision;
    private long callDuration;
    private long callTime;
    private String contactName;
    private String managerName;
    private boolean bCall;
    private boolean bCallAnswer;

    private SimpleStringProperty contactNameProperty = new SimpleStringProperty("Неизвестный контакт");
    private SimpleStringProperty managerNameProperty = new SimpleStringProperty("");
    private SimpleStringProperty callDurationProperty = new SimpleStringProperty("0");
    private SimpleStringProperty callTimeProperty = new SimpleStringProperty("0");

    public void setId(int id) {
        this.id = id;
    }

    public void setFilePatch(String filePatch) {
        this.filePatch = filePatch;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public void setCallDuration(long callDuration) {

        long hours = callDuration / 3600;
        long second = callDuration % 60;
        long minutes = callDuration / 60;

        String formatSecond = (second / 10 < 1)?("0"+second): second + "";
        String formatMinutes = (minutes / 10 < 1)?("0" + minutes):minutes + "";
        String formatHours = (hours / 10 < 1)?("0"+hours): hours + "";

        this.callDurationProperty.set(formatHours + ":" + formatMinutes + ":" + formatSecond);
        this.callDuration = callDuration;
    }

    public void setIsCall(boolean isCall) {
        this.bCall = isCall;
    }

    public void setCallTime(long callTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        this.callTimeProperty.set(sdf.format(callTime));

        this.callTime = callTime;
    }

    public void setIsCallAnswer(boolean isCallAnswer) {
        this.bCallAnswer = isCallAnswer;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public void setContactName(String contactName) {
        if (contactName.equals("Неизвестный контакт"))
            this.contactNameProperty.set(getCallNumber());
        else
            this.contactNameProperty.set(contactName);

        this.contactName = contactName;

    }

    public void setSubdivision(String subdivision){
        if (managerName.equals("") && !subdivision.equals(""))
            this.managerNameProperty.set(subdivision);
        else
            this.managerNameProperty.set(managerName);
        this.subdivision = subdivision;
    }

    public int getId() {
        return id;
    }

    public String getFilePatch() {
        return filePatch != null?filePatch : "";
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public long getCallDuration() {
        return callDuration;
    }

    public boolean isCall() {
        return bCall;
    }

    public long getCallTime() {
        return callTime;
    }

    public String getContactName() {
        return contactName;
    }

    public boolean isCallAnswer() {
        return bCallAnswer;
    }

    public String getManagerName() {
        return managerName;
    }

    public String getSubdivision() {
        return subdivision;
    }

    public SimpleStringProperty getContactNameProperty() {
        return contactNameProperty;
    }

    public SimpleStringProperty getCallDurationProperty() {
        return callDurationProperty;
    }

    public SimpleStringProperty getManagerNameProperty() {
        return managerNameProperty;
    }

    public SimpleStringProperty getCallTimeProperty() {
        return callTimeProperty;
    }

    @Override
    public String toString() {
        return id + "\n"
                + filePatch + "\n"
                + phoneNumber + "\n"
                + callNumber + "\n"
                + callDuration + "\n"
                + callTime + "\n"
                + contactName + "\n"
                + managerName + "\n"
                + bCall + "\n"
                + bCallAnswer + "\n";
    }
}
