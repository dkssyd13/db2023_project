package com.example.db2023_project.DB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DatabaseAuthInformation {
    private String host;
    private String userName;
    private String databaseName;
    private String port;
    private String password;

    public DatabaseAuthInformation() {
        this.host=null;
        this.userName=null;
        this.databaseName=null;
        this.password=null;
        this.port=null;
    }

    public boolean parseAuthInfo(String authPath) {
        String host = null;
        String port=null;
        String database=null;
        String userName=null;
        String password=null;

        try {
            File file=new File(authPath);
            BufferedReader br= new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                if(line.isEmpty()) continue;
                if(line.charAt(0)== '#') continue;

                int lineLength=line.length();
                if (line.startsWith("host")) host=line.substring(5,lineLength);
                else if (line.startsWith("port")) port = line.substring(5, lineLength);
                else if (line.startsWith("database")) database = line.substring(9, lineLength);
                else if (line.startsWith("username")) userName = line.substring(9, lineLength);
                else if (line.startsWith("password")) password = line.substring(9, lineLength);

            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


        boolean flag=true;
        if (host == null) {
            flag=false;
        }
        if (port==null) {
            flag=false;
        }
        if (database == null) {
            flag=false;
        }
        if (userName == null) {
            flag=false;
        }
        if (password == null) {
            flag=false;
        }
        if (!flag) {
            return false;
        }


        this.host=host;
        this.port = port;
        this.userName = userName;
        this.password = password;
        this.databaseName = database;
        return true;
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void debug_print() {
        System.out.println("Host: " + this.host + ":" + this.port + "/" + this.databaseName + "@" + this.userName + ":" + this.password);
    }
}