package SingleThreadedBenchMarking;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import Properties.PropertiesLoaderClass;

public class ReadBenchmarking {

    final static int NO_OF_HITS = 1000;

    public static void main(String[] args) {

        Properties props = PropertiesLoaderClass.getConnectionData();

        String url = props.getProperty("db.url.pets");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        String query = "SELECT * FROM cats";

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            long startTime = System.currentTimeMillis();

            for (int i=0; i<NO_OF_HITS; i++) {
                PreparedStatement pst = con.prepareStatement(query);
                ResultSet rs = pst.executeQuery();

//                while (rs.next()) {
//                    System.out.print(rs.getInt(1));
//                    System.out.print(": ");
//                    System.out.println(rs.getString(2));
//                }
            }

            long endTime = System.currentTimeMillis();

            long totalTime = endTime - startTime;
            System.out.println("Total time: " + totalTime);

            long qps = (NO_OF_HITS * 1000l)/totalTime;
            System.out.println("QPS: " + qps);


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(ReadBenchmarking.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}

//NO_OF_HITS = 1000
//Total time: 1042
//QPS: 959

//NO_OF_HITS = 10000
//Total time: 3512
//QPS: 2847

//NO_OF_HITS = 100000
//Total time: 31160
//QPS: 3209
