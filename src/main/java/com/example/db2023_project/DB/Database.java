package com.example.db2023_project.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final Database db = new Database();
    private final DatabaseAuthInformation dbAuthInfo = new DatabaseAuthInformation();
    final String authFileName="C:\\Users\\vladk\\Desktop\\db2023_project\\src\\main\\java\\com\\example\\db2023_project\\DB\\auth\\mysql.auth";

    private Database(){};

    public static Database getDb(){
        return db;
    }


    public Connection getConnection() {
        if(!dbAuthInfo.parseAuthInfo(authFileName)){
            System.out.println("Error in parsing Auth Info");
            return null;
        }
        System.out.println("dbAuthInfo.getDatabaseName() = " + dbAuthInfo.getDatabaseName());
        System.out.println("dbAuthInfo.getHost() = " + dbAuthInfo.getHost());

        String dbConnectionUrl = String.format("jdbc:mysql://%s:%s/%s", dbAuthInfo.getHost(), dbAuthInfo.getPort(), dbAuthInfo.getDatabaseName());

        System.out.println("dbConnectionUrl = " + dbConnectionUrl);

        Connection dbConnection;
        try {
            dbConnection = DriverManager.getConnection(dbConnectionUrl, dbAuthInfo.getUserName(), dbAuthInfo.getPassword());
        } catch (SQLException e) {
            System.out.println("Error in getConnection()");
            System.out.println("e = " + e);
            throw new RuntimeException(e);
        }
        return dbConnection;
    }
}
