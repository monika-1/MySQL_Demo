package SingleThreadedBenchMarking;

import Properties.PropertiesLoaderClass;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteBenchMarking {

    final static int NO_OF_HITS = 10000;

    public static void main(String[] args) {

        Properties props = PropertiesLoaderClass.getConnectionData();
        String url = props.getProperty("db.url.testdb");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        String sql = "Delete from Testing where id = ? ;";

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

            Logger lgr = Logger.getLogger(DeleteBenchMarking.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}

//NO_OF_HITS = 1000
//Total time: 2375
//QPS: 421

//NO_OF_HITS = 10000
//Total time: 47238
//QPS: 211

