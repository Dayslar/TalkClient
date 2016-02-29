package by.dayslar.sample.Utilites;

import by.dayslar.sample.Model.User;

public class UserUtil {

    static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user){
        UserUtil.user = user;
    }
}
