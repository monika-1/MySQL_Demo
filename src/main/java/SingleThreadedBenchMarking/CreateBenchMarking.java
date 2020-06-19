package SingleThreadedBenchMarking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import Properties.PropertiesLoaderClass;

public class CreateBenchMarking {

    final static int NO_OF_HITS = 10000;

    public static void main(String[] args) {

        Properties props = PropertiesLoaderClass.getConnectionData();
        String url = props.getProperty("db.url.testdb");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        String sql = "INSERT INTO Testing(Id) VALUES(?)";

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            long startTime = System.currentTimeMillis();

            PreparedStatement pst = con.prepareStatement(sql);
            for (int i = 1; i <= NO_OF_HITS; i++) {
                pst.setInt(1, i);
                pst.executeUpdate();
            }

            long endTime = System.currentTimeMillis();

            long totalTime = endTime - startTime;
            System.out.println("Total time: " + totalTime);

            long qps = (NO_OF_HITS * 1000l)/totalTime;
            System.out.println("QPS: " + qps);

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(CreateBenchMarking.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}

//NO_OF_HITS = 1000
//Total time: 1817
//QPS: 550

//NO_OF_HITS = 10000
//Total time: 8348
//QPS: 1197
