package MultiThreaded;

import Properties.PropertiesLoaderClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class UpdateMultiThreadedThreadClass {

    final static int NO_OF_HITS = 100;
    final static int NO_OF_THREADS = 10;
    private static Properties props;
    private static String url;
    private static String user;
    private static String password;
    private static String query;
    private static String name = "Kitty";

    public static void main(String[] args) throws InterruptedException, SQLException {

        props = PropertiesLoaderClass.getConnectionData();
        url = props.getProperty("db.url.pets");
        user = props.getProperty("db.user");
        password = props.getProperty("db.password");
        query = "Update cats Set name = ? where id = 1 ;";

        Thread[] threads = new Thread[NO_OF_THREADS];
        for (int i=0; i<NO_OF_THREADS; i++) threads[i] = new Thread(new Runner());

        long startTime = System.currentTimeMillis();

        for (int i=0; i<NO_OF_THREADS; i++) threads[i].start();
        for (int i=0; i<NO_OF_THREADS; i++) threads[i].join();

        long endTime = System.currentTimeMillis();

        long totalTime = endTime - startTime;
        System.out.println("Total time: " + totalTime);

        long qps = (NO_OF_HITS * NO_OF_THREADS * 1000l)/totalTime;
        System.out.println("QPS: " + qps);
    }

    static class Runner implements Runnable {

        Connection connection;
        Runner() throws SQLException {
            connection = DriverManager.getConnection(url, user, password);
        }

        @Override
        public void run() {
            try {
                for (int i=1; i<=NO_OF_HITS; i++) {
                    PreparedStatement pst = connection.prepareStatement(query);
                    pst.setString(1, name+i);
                    pst.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}

//NO_OF_HITS = 1000
//Total time: 984
//QPS: 1016

//NO_OF_HITS = 10000
//Total time: 6111
//QPS: 1636


