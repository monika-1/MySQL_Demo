package SampleCode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcNotPreparedTesting {

    public static void main(String[] args) {

        String cs = "jdbc:mysql://localhost:3306/testdb?useSSL=false";
        String user = "root";
        String password = "lotrlegolas";

        try (Connection con = DriverManager.getConnection(cs, user, password);
             Statement st = con.createStatement()) {

            for (int i = 1; i <= 5000; i++) {

                String sql = "INSERT INTO Testing(Id) VALUES(" + 2 * i + ")";
                st.executeUpdate(sql);
            }

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(JdbcNotPreparedTesting.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}