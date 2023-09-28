/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author janch
 */
public class DatabaseManager
{
    public static DatabaseManager INSTANCE;
    
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://102.130.115.69:3306/jcjDB";
    private static final String USER = "jcj";
    private static final String PASS = "Reddam2021";
    
    private PreparedStatement statement;
    private ResultSet resultSet;
    private Connection conn;
    
    private DatabaseManager()
    {
        try
        {
            Class.forName(DRIVER);
            System.out.println("DB: Driver found");
        } catch (ClassNotFoundException e)
        {
            System.out.println("DB: Unable to load driver");
        }

        try
        {
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("DB: Database connected");
        } catch (Exception e)
        {
            System.out.println("DB: Unable to load database");
        }
    }
    
    public static void init()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new DatabaseManager();
        }
        
    }
    
    //INSERT, UPDATE or DELETE
    public void update(String update) throws SQLException
    {
        statement = conn.prepareStatement(update);
        statement.executeUpdate();
        statement.close();
    }
    public ResultSet query(String stmt) throws SQLException
    {
        statement = conn.prepareStatement(stmt);
        resultSet = statement.executeQuery();


        return resultSet;
    }
}
