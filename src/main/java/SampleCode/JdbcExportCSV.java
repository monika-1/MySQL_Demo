package SampleCode;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcExportCSV {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/testdb?useSSL=false";
        String user = "root";
        String password = "lotrlegolas";

        String query = "SELECT Name, Title INTO OUTFILE "
                + "'/Users/monikakumari/Desktop/Java/mysql_jdbc_example/src/main/resources/authors_books.csv' "
                + "FIELDS TERMINATED BY ',' "
                + "FROM Authors, Books WHERE "
                + "Authors.Id=Books.AuthorId";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.execute();
        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(JdbcExportCSV.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}