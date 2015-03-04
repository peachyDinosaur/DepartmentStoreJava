package com.store.app;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection sConnection;

    public static Connection getInstance() throws ClassNotFoundException, SQLException {
        String host, db, user, password;

      host = "localhost";
      db = "n00120430";
      user = "N00120430";
      password = "N00120430";
//      login details for local database        
        
//        host = "daneel";
//        db = "n00120430";
//        user = "N00120430";
//        password = "N00120430";
//      login details for iadt database        
        
        if (sConnection == null || sConnection.isClosed()) {
            String url = "jdbc:mysql://" + host + "/" + db;
            Class.forName("com.mysql.jdbc.Driver");
            sConnection = DriverManager.getConnection(url, user, password);
        }

        return sConnection;
    }
}
