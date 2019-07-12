package by.popovich.last;


import by.popovich.last.dao.pool.ConnectionPool;
import by.popovich.last.entity.*;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.*;

import java.sql.Date;

public class Main {
    public static final String DB_DRIVER_CLASS = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/sport_club?useUnicode=true&characterEncoding=UTF-8";
    public static final String DB_USER = "club_user";
    public static final String DB_PASSWORD = "club_password";
    public static final int DB_POOL_START_SIZE = 10;
    public static final int DB_POOL_MAX_SIZE = 1000;
    public static final int DB_POOL_CHECK_CONNECTION_TIMEOUT = 0;

    public static void main(String[] args) {
        try {
            ConnectionPool.getInstance().init(DB_DRIVER_CLASS, DB_URL, DB_USER, DB_PASSWORD, DB_POOL_START_SIZE, DB_POOL_MAX_SIZE, DB_POOL_CHECK_CONNECTION_TIMEOUT);
        } catch (PersistentException e) {
            e.printStackTrace();
        }
        UserService service = new UserServiceImpl();
        try {
            System.out.println(service.readAll());
            service.delete(16);
            System.out.println(service.readAll());
        } catch (PersistentException e) {
            e.printStackTrace();
        }
    }
}
//
//        System.out.println(((ExerciseServiceImpl) service).readAll());
//        System.out.println(((ExerciseServiceImpl) service).readById(2));
//        System.out.println(((ExerciseServiceImpl) service).readIdByName("Йога"));
//        Exercise ex = new Exercise();
//        ex.setIdentity(1);
//        ex.setTypeOfExercises("Тренажёрка");
//        ((ExerciseServiceImpl) service).save(ex);
//        System.out.println(((ExerciseServiceImpl) service).readAll());




//        System.out.println(service.readAll());
//        System.out.println(service.readById(5));
//        System.out.println(service.readByGroupId(3));
//        System.out.println(service.readSubscriptionsByClientId(5));
//        Date date = new Date(119, 5, 20);
//        System.out.println(date);
//        System.out.println(service.readByLastDay(date));
//        Subscription subscription = new Subscription();
//        subscription.setIdentity(15);
//        subscription.setClientId(4);
//        subscription.setLastDay(date);
//        subscription.setLeftVisits(4);
//        subscription.setIdOfGroup(2);
//        service.save(subscription);
//        service.deleteSubscription(4,2);
//        System.out.println(service.readAll());
