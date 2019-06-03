package ex001;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *  Class for accessing the DataBase implemented as Singleton
 * @author franz
 */
public class DataBase {
    
    /**
     * The one and only instance of the DataBase class.
     */
    
    private static DataBase theInstance;
    private Connection conn;
    
    /**
     * Constructor is private for singleton implementation
     */
    
    DataBase() throws SQLException
    {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cherry-db", "postgres","postgres");
    }
    /**
     * If the instance hasn't been created before, it gets created.
     * 
     * @return 
     */
    
    public synchronized static DataBase getInstance() throws SQLException
    {
        if(theInstance == null)
        {
            theInstance = new DataBase();
        }
        return theInstance;
    }
    
    public void createTablePlayer() throws Exception 
    {
//        String sql = "DROP TABLE IF EXISTS Player;"
//                + ""
//                + "CREATE TABLE Player"
//                + "("
//                + "     ID SERIAL PRIMARY KEY,"
//                + "     Name character varying NOT NULL,"
//                + "     Highscore character varying NOT NULL"
//                + ")";
        
        String sql = "CREATE TABLE IF NOT EXISTS Player"
                + "("
                + "     ID SERIAL PRIMARY KEY,"
                + "     Name character varying NOT NULL,"
                + "     Highscore character varying NOT NULL"
                + ")";
        
        Statement stat = conn.createStatement();
        stat.executeUpdate(sql);
    }
    
    public void insertTableData(String name, int score) throws Exception
    {        
        String sql = String.format("INSERT INTO Player(Name, Highscore) VALUES('%s', %d)", name, score);
        Statement stat = conn.createStatement();
        stat.executeUpdate(sql);
    }
    
    public ArrayList<Player> getPlayers() throws Exception
    {
        ArrayList<Player> players = new ArrayList<>();
        
        String sql = "SELECT * FROM Player";
        
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery(sql);
        
        while(rs.next())
        {
            Player p = new Player(rs.getInt("id"),
                                  rs.getString("name"),
                                  rs.getInt("highscore"));
            players.add(p);
        }
        return players;
    }
}
