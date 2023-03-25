package dev.levi.data;

import dev.levi.domain.Files;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FileDaoImpl implements FileDao{
    private static Connection conn ;

    static {
        try {
            conn=   DriverManager.getConnection ("jdbc:h2:./src/main/resources/db", "sa","");
            Statement stmt = null;
            stmt = conn.createStatement();
            String sql =  "SET MODE PostgreSQL; " +
                    "CREATE  TABLE IF NOT EXISTS FILES" +
                    " (id int  PRIMARY KEY auto_increment, " +
                    " name VARCHAR(255), " +
                    " path VARCHAR(255) , " +
                    " time LONG );";
            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");

            // STEP 4: Clean-up environment
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void createFile(Files file) {
        try {
            PreparedStatement statement = conn.prepareStatement( "INSERT INTO FILES (name,path,time) VALUES (?, ?,?)");
//            statement.setInt(1, 0);
           statement.setString(1, file.getName());
           statement.setString(2,file.getPath());
           statement.setLong(3,file.getTime());
           statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Files> findAllFiles() {
        List<Files> list = new ArrayList<Files>();
        try {

            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM FILES order by time DESC LIMIT 8;";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String path = resultSet.getString("path");
                Long time = resultSet.getLong("time");
                list.add(new Files(id,name,path,time));
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void updateFile(Files file) {
        try {
            PreparedStatement statement = conn.prepareStatement(
                    "UPDATE FILES SET name=? ,time =? " +
                    " WHERE path = ?");
//            statement.setInt(1, 0);
            statement.setString(1, file.getName());
            statement.setLong(2,file.getTime());
            statement.setString(3, file.getPath());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
