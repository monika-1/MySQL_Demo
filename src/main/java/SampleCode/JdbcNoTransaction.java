package SampleCode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcNoTransaction {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/testdb?useSSL=false";
        String user = "root";
        String password = "lotrlegolas";

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement()) {

            st.executeUpdate("UPDATE Authors SET Name = 'Leo Tolstoy'"
                    + "WHERE Id = 1");
            st.executeUpdate("UPDATE Books SET Title = 'War and Peace'"
                    + "WHERE Id = 1");
            st.executeUpdate("UPDATE Books SET Title = 'Anna Karenina'"
                    + "WHERE Id = 2");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(JdbcNoTransaction.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}