package SampleCode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import Properties.PropertiesLoaderClass;

public class JdbcProperties {

    public static void main(String[] args) {

        Properties props = PropertiesLoaderClass.getConnectionData();

        String url = props.getProperty("db.url.testdb");
        String user = props.getProperty("db.user");
        String passwd = props.getProperty("db.password");

        String query = "SELECT * FROM Authors";

        try (Connection con = DriverManager.getConnection(url, user, passwd);
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                System.out.print(rs.getInt(1));
                System.out.print(": ");
                System.out.println(rs.getString(2));
            }

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(JdbcProperties.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}