package by.popovich.last;

import by.popovich.last.domain.Role;
import by.popovich.last.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final String DB_DRIVER_CLASS = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/library_db?useUnicode=true&characterEncoding=UTF-8";
    public static final String DB_USER = "library_user";
    public static final String DB_PASSWORD = "library_password";

    public static final String SELECT_ALL = "select `id`, `login`, `password`,";


    public static void main(String[] args) {
        Connection cn;
        try {
            cn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(SELECT_ALL);
            List<User> users = new ArrayList<>();
            User user;
            while (rs.next()) {
                user = new User();
                user.setIdentity(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setRole(Role.getByIdentity(rs.getInt("role")));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
