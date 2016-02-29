package by.dayslar.sample.Utilites;

import by.dayslar.sample.Interface.impl.RecordDAODatabase;
import by.dayslar.sample.Model.Record;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ChartUtil {

    private static ObservableList<Record> records;
    private static Map<String, String> managers;
    private static Map<String, Integer> subdivisions;
    private static List<Double> days;

    public static void initializeRecords(long oldTime, long newTime, String subdivision){
        records = new RecordDAODatabase().getRecords(oldTime, newTime, subdivision);

        managers = calculateManagers(records);
        subdivisions = calculateSubdivision(records);
        days = calculateDays(records);
    }

    private static List<Double> calculateDays(ObservableList<Record> records) {
        Map<Double, Integer> mapDay = new HashMap<>();

        for (Record record : records){
            String day = new SimpleDateFormat("MM.dd").format(new Date(record.getCallTime()));
            mapDay.put(Double.parseDouble(day), 0);
        }

        List<Double> listDay = new ArrayList<>();
        listDay.addAll(mapDay.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList()));
        Collections.sort(listDay, Double::compareTo);

        return listDay;
    }

    private static Map<String, Integer> calculateSubdivision(ObservableList<Record> records) {
        HashMap<String, Integer> map = new HashMap<>();
        for (Record record : records){
            if (record.getSubdivision().equals("")) continue;
            map.put(record.getSubdivision(), 0);
        }
        return map;
    }

    private static Map<String, String> calculateManagers(ObservableList<Record> records) {
        HashMap<String, String> map = new HashMap<>();
        for (Record record : records){
            if (record.getManagerName().equals("")) continue;
            map.put(record.getManagerName(),record.getSubdivision());
        }
        return map;
    }


    /** Графики для распределения по звонкам*/
    public static ObservableList<XYChart.Series<String, Number>> getDistributionCallSubdivision(){

        XYChart.Series<String, Number> seriesMissed = new XYChart.Series<>();
        seriesMissed.setName("Пропущеные");

        XYChart.Series<String, Number> seriesIncoming = new XYChart.Series<>();
        seriesIncoming.setName("Входящие");

        XYChart.Series<String, Number> seriesOutgoing = new XYChart.Series<>();
        seriesOutgoing.setName("Исходящие");


        for (Map.Entry<String, Integer> subItem : subdivisions.entrySet()){
            int countMissed = 0;
            int countIncoming = 0;
            int countOutgoing = 0;

            String subdivision = subItem.getKey();
            for (Record record: records){
                if (record.getSubdivision().equals(subdivision)){
                    if (record.isCall())
                        countOutgoing++;
                    else
                        countIncoming++;

                    if (!record.isCallAnswer())
                        countMissed++;
                }
            }

            seriesIncoming.getData().add(new XYChart.Data<>(subdivision, countIncoming));
            seriesOutgoing.getData().add(new XYChart.Data<>(subdivision, countOutgoing));
            seriesMissed.getData().add(new XYChart.Data<>(subdivision, countMissed));

        }

        return FXCollections.observableArrayList(seriesMissed, seriesOutgoing, seriesIncoming);
    }

    public static ObservableList<XYChart.Series<String, Number>> getDistributionCallDay(){

        XYChart.Series<String, Number> seriesMissed = new XYChart.Series<>();
        seriesMissed.setName("Пропущеные");

        XYChart.Series<String, Number> seriesIncoming = new XYChart.Series<>();
        seriesIncoming.setName("Входящие");

        XYChart.Series<String, Number> seriesOutgoing = new XYChart.Series<>();
        seriesOutgoing.setName("Исходящие");

        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd");
        Date date = new Date();

        for (Double day: days){
            int countMissed = 0;
            int countIncoming = 0;
            int countOutgoing = 0;

            for (Record record: records){
                date.setTime(record.getCallTime());
                Double checkDay = Double.parseDouble(sdf.format(date));

                if (checkDay.equals(day)){
                    if (record.isCall())
                        countOutgoing++;
                    else
                        countIncoming++;

                    if (!record.isCallAnswer())
                        countMissed++;
                }
            }

            seriesIncoming.getData().add(new XYChart.Data<>(day + "", countIncoming));
            seriesOutgoing.getData().add(new XYChart.Data<>(day + "", countOutgoing));
            seriesMissed.getData().add(new XYChart.Data<>(day + "", countMissed));

        }

        return FXCollections.observableArrayList(seriesMissed, seriesOutgoing, seriesIncoming);
    }

    public static ObservableList<PieChart.Data> getDistributionCallType() {

        int dataMissedCount = 0;
        int dataOutgoingCount = 0;
        int dataIncomingCount = 0;

        for (Record record: records){
            if (!record.isCallAnswer())
                dataMissedCount++;
            if (record.isCall()){
                dataOutgoingCount++;
            }

            else dataIncomingCount++;
        }

        return FXCollections.observableArrayList(
                new PieChart.Data("Пропущенные - " + dataMissedCount, dataMissedCount),
                new PieChart.Data("Входящие - " + dataIncomingCount, dataIncomingCount),
                new PieChart.Data("Исходящие - " + dataOutgoingCount, dataOutgoingCount)
        );
    }


    /** Графики для распределения по менеджерам*/
    public static ObservableList<XYChart.Series<String, Number>> getDistributionManagersSubdivision(){
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Менеджер");

        for (Map.Entry<String, Integer> subItem : subdivisions.entrySet()){
            int count = 0;

            for (Map.Entry<String, String> managerItem : managers.entrySet()){
                if (subItem.getKey().equals(managerItem.getValue()))
                    count++;
            }

            if (count > 0)
            series.getData().add(new XYChart.Data<>(subItem.getKey() + " - " + count, count));
        }

        return FXCollections.observableArrayList(series);
    }

    public static ObservableList<XYChart.Series<String, Number>> getDistributionManagersDay(){
        ObservableList<XYChart.Series<String, Number>> list = FXCollections.observableArrayList();
        ObservableList<XYChart.Data<String, Number>> listData = FXCollections.observableArrayList();

        Date data = new Date();
        Double localDay;
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd");
        String managerName = "";

        for (Map.Entry<String, String> itemManager: managers.entrySet()){
            managerName = itemManager.getKey();
            listData = FXCollections.observableArrayList();

            for (Double day: days){
                int count = 0;
                for (Record record : records){
                    data.setTime(record.getCallTime());
                    localDay = Double.parseDouble(sdf.format(data));

                    if (localDay.equals(day) && record.getManagerNameProperty().get().equals(managerName)){
                        count++;
                    }
                }

                listData.add(new XYChart.Data<>(day + "", count));
            }

            XYChart.Series<String, Number> series= new XYChart.Series<>();
            series.setName(managerName);
            series.setData(FXCollections.observableArrayList(listData));

            list.add(series);
        }

        return list;
    }

    public static ObservableList<PieChart.Data> getDistributionManagersType() {
        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
        int localCount = 0;
        String localManager;

        for (Map.Entry<String, String> managerItem : managers.entrySet()){
            localManager = managerItem.getKey();

            for (Record record: records){
                if (record.getManagerName().equals(localManager))
                   localCount++;
            }

            chartData.add(new PieChart.Data(localManager + " - " + localCount, localCount));
            localCount = 0;
        }

        return chartData;
    }



    /**Графики для распредления по ответам*/
    public static ObservableList<XYChart.Series<String, Number>> getDistributionAnswerSubdivision(){

        XYChart.Series<String, Number> seriesAnswer = new XYChart.Series<>();
        seriesAnswer.setName("C ответом");

        XYChart.Series<String, Number> seriesNotAnswer = new XYChart.Series<>();
        seriesNotAnswer.setName("Без ответа");

        String subdivision;


        for (Map.Entry<String, Integer> mapSubdivision : subdivisions.entrySet()){
            subdivision = mapSubdivision.getKey();

            int localAnswer = 0;
            int localNotAnswer = 0;

            for (Record record: records){
                if (record.getSubdivision().equals(subdivision)){
                    if (record.isCallAnswer())
                        localAnswer++;
                    else
                        localNotAnswer++;

                }
            }

            seriesAnswer.getData().add(new XYChart.Data<>(subdivision, localAnswer));
            seriesNotAnswer.getData().add(new XYChart.Data<>(subdivision, localNotAnswer));

        }

        return FXCollections.observableArrayList(seriesAnswer, seriesNotAnswer);
    }

    public static ObservableList<XYChart.Series<String, Number>> getDistributionAnswerDays(){

        XYChart.Series<String, Number> seriesAnswer = new XYChart.Series<>();
        seriesAnswer.setName("C Ответом");

        XYChart.Series<String, Number> seriesNotAnswer = new XYChart.Series<>();
        seriesNotAnswer.setName("Без ответа");

        for (Double day: days){
            int countAnswer = 0;
            int countNotAnswer = 0;

            for (Record record: records){
                Double checkDay = Double.parseDouble(new SimpleDateFormat("MM.dd").format(new Date(record.getCallTime())));

                if (checkDay.equals(day)){
                    if (record.isCallAnswer())
                        countAnswer++;
                    else countNotAnswer++;
                }
            }

            seriesNotAnswer.getData().add(new XYChart.Data<>(day + "", countNotAnswer));
            seriesAnswer.getData().add(new XYChart.Data<>(day + "", countAnswer));
        }


        return FXCollections.observableArrayList(seriesAnswer, seriesNotAnswer);
    }

    public static ObservableList<PieChart.Data> getDistributionAnswerManager() {
        ObservableList<PieChart.Data> listSubdivisionData = FXCollections.observableArrayList();
        int count = 0;

        for (Map.Entry<String, Integer> item_subdivision : subdivisions.entrySet()){
            String local_subdivision = item_subdivision.getKey();

            for (Record record: records){
                if (local_subdivision.equals(record.getSubdivision()) && record.isCallAnswer()){
                    count++;
                }
            }
            listSubdivisionData.add(new PieChart.Data(local_subdivision + " - " + count, count));
            count = 0;
        }
        return listSubdivisionData;
    }



    /**Дополнительный класс для определения какие графики подгружать*/
    public class ChartType {
        public static final String DISTRIBUTION_CALL = "Распределение по звонков";
        public static final String DISTRIBUTION_MANAGER = "Распределение по менеджерам";
        public static final String DISTRIBUTION_ANSWER = "Распределение по ответам";
    }
}
