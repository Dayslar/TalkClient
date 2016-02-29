package by.dayslar.sample.Interface;


import by.dayslar.sample.Model.User;

public interface UserDAO {

    //проверяет загерестрирован ли пользователь в системе
    boolean checkUser(User user);

    //возвращает подразделения для пользователя
    String getSubdivisionUser();
}
