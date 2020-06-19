package SampleCode;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcReadImage {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/testdb?useSSL=false";
        String user = "root";
        String password = "lotrlegolas";

        String query = "SELECT Data FROM Images LIMIT 1";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet result = pst.executeQuery()) {

            if (result.next()) {

                String fileName = "src/main/resources/tweety.jpg";

                try (FileOutputStream fos = new FileOutputStream(fileName)) {

                    Blob blob = result.getBlob("Data");
                    int len = (int) blob.length();

                    byte[] buf = blob.getBytes(1, len);

                    fos.write(buf, 0, len);

                } catch (IOException ex) {

                    Logger lgr = Logger.getLogger(JdbcReadImage.class.getName());
                    lgr.log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(JdbcReadImage.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}