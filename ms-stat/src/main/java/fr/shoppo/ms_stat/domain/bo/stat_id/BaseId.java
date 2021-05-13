package fr.shoppo.ms_stat.domain.bo.stat_id;

import fr.shoppo.ms_stat.domain.bo.UserType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseId extends StatId {
    final static DateFormat DATE = new SimpleDateFormat("yyyyMMdd");

    String date;
    UserType user;
    String name;


    public static BaseId of(UserType user,String name){
        return of(user,name,new Date());
    }
    public static BaseId of(UserType user){
        return of(user,"_");
    }

    public static BaseId of(UserType user,String name,Date from){
        var id = new BaseId();
        id.setUser(user);
        id.setName(name);
        id.setDate(DATE.format(from));
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public UserType getUser() {
        return user;
    }

    public void setUser(UserType user) {
        this.user = user;
    }
}
