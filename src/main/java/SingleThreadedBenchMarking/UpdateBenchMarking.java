package SingleThreadedBenchMarking;

import Properties.PropertiesLoaderClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateBenchMarking {

    final static int NO_OF_HITS = 1000;

    public static void main(String[] args) {

        Properties props = PropertiesLoaderClass.getConnectionData();
        String url = props.getProperty("db.url.pets");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        String sql = "Update cats Set name = ? where id = 1 ;";

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            long startTime = System.currentTimeMillis();
            String name = "Kitty";

            PreparedStatement pst = con.prepareStatement(sql);
            for (int i = 1; i <= NO_OF_HITS; i++) {
                pst.setString(1, name+i);
                pst.executeUpdate();
            }

            long endTime = System.currentTimeMillis();

            long totalTime = endTime - startTime;
            System.out.println("Total time: " + totalTime);

            long qps = (NO_OF_HITS * 1000l)/totalTime;
            System.out.println("QPS: " + qps);

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(UpdateBenchMarking.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}

//NO_OF_HITS = 1000
//Total time: 2139
//QPS: 467

//NO_OF_HITS = 10000
//Total time: 14160
//QPS: 706
