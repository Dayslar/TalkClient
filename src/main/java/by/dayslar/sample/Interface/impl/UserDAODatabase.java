package by.dayslar.sample.Interface.impl;

import by.dayslar.sample.Interface.UserDAO;
import by.dayslar.sample.Model.User;
import by.dayslar.sample.Utilites.DialogUtil;
import com.mysql.fabric.jdbc.FabricMySQLDriver;
import org.apache.log4j.Logger;

import java.sql.*;

public class UserDAODatabase implements UserDAO {

    private static final String URL = "jdbc:mysql://192.168.21.61:3306/mydb";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";

    private static final String DB_SELECT_USER = "SELECT *FROM users WHERE name = ? and password = ?";
    private String subdivision;

    private static Logger LogArea = Logger.getLogger(UserDAODatabase.class);

    Connection connection;

    public UserDAODatabase(){
        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            initializeError("Ошибка регистрации драйвера", "Не удалось зарегестрировать драйвер: \n" + e);
        }
    }

    public Connection getConnection(){
        if (connection == null)
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            initializeError("Ошибка подключения к базе","***SQLException***   Не удалось подключиться к базе: " + e);
        }

        return connection;
    }

    @Override
    public boolean checkUser(User user) {
        getConnection();
        ResultSet resultSet = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(DB_SELECT_USER)){

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());

            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();

            if (resultSet.next()){
                subdivision = resultSet.getString(4);
                return true;
            }

        } catch (SQLException e) {
            initializeError("Ошибка проверки пользователя", "***SQLException***   При проверке пользователя произошла ошибка - " + e);
        }

        finally {
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException e) {
                   //not
                }
        }

        return false;
    }

    @Override
    public String getSubdivisionUser() {
        return subdivision;
    }

    private void initializeError(String title, String info) {
        LogArea.fatal(info);
        DialogUtil.showErrorDialog(title, info);
    }
}
