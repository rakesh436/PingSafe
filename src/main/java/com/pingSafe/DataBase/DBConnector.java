package com.pingSafe.DataBase;

import com.pingSafe.Helper.PropertiesManager;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Singelton class
 */
public class DBConnector {

    private DBConnector() {

    }
    private static DBConnector dbConnector = null;



    public static DBConnector getInstance() {
        if (Objects.isNull(dbConnector))
            return new DBConnector();
        else
            return dbConnector;
    }

    public Connection dbConnectionEstablisher() {
        Connection conn = null;
        try {
            String dbPath = PropertiesManager.getInstance().getProperty("DB_Path");
            String url = String.format("jdbc:sqlite:%s", dbPath);
            conn = DriverManager.getConnection(url);

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return conn;
    }

    private void closeConnestion(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
    }

    public List<Map<String, ?>> getResultSetAsListOfMap(String sqlStatement) {
        Connection conn = dbConnectionEstablisher();
        List<Map<String, ?>> list = null;
        ResultSet rs = null;
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(sqlStatement);
            rs = statement.executeQuery();
            list = resultSetToList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
            closePreparedStatement(statement);
            closeConnestion(conn);
        }
        return list;
    }

    public List<Map<String, ?>> resultSetToList(ResultSet rs)
            throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<Map<String, ?>> results = new ArrayList<>();
        while (rs.next()) {
            Map<String, Object> row = new HashMap<>();
            for (int i = 1; i <= columns; i++) {
                row.put(md.getColumnLabel(i), rs.getObject(i));
            }
            results.add(row);
        }
        return results;
    }

    private void closeResultSet(ResultSet rs){
        if(!Objects.isNull(rs)) {
            try {
                rs.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
        }
    }

    private void closePreparedStatement(PreparedStatement statement){
        if(!Objects.isNull(statement)) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        DBConnector dbConnector1 = DBConnector.getInstance();
        Connection conn = dbConnector1.dbConnectionEstablisher();
       // var li = dbConnector1.executeQuery(conn,"SELECT * FROM  customers WHERE id = '9653'");

//        System.out.println( li.get(0).get("SMS_SENT"));
//        System.out.println(li);
      //  var dd = dbConnector1.getResultSetAsListOfMap("SELECT id FROM  customers");
       // System.out.println(dd);

        var li = dbConnector1.getListOfIds();
        System.out.println(li);
    }

    public List<Map<String, ?>> getCustomerByID(String id){
        DBConnector dbConn = DBConnector.getInstance();
        String query = String.format("SELECT * FROM  customers WHERE id = '%s'",id);
        return  dbConn.getResultSetAsListOfMap(query);
    }

    public List<String> getListOfIds(){
        DBConnector dbConn = DBConnector.getInstance();
        String query = String.format("SELECT id FROM  customers");
        List<Map<String, ?>> data = dbConn.getResultSetAsListOfMap(query);
        return data.stream()
                .map(i -> i.get("id").toString())
                .collect(Collectors.toList());
    }

}
