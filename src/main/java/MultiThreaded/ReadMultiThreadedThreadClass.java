package MultiThreaded;

import Properties.PropertiesLoaderClass;

import java.sql.*;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadMultiThreadedThreadClass {

    final static int NO_OF_HITS = 10000;
    final static int NO_OF_THREADS = 10;
    private static Properties props;
    private static String url;
    private static String user;
    private static String password;
    private static String query;

    public static void main(String[] args) throws InterruptedException, SQLException {

        props = PropertiesLoaderClass.getConnectionData();
        url = props.getProperty("db.url.pets");
        user = props.getProperty("db.user");
        password = props.getProperty("db.password");
        query = "SELECT * FROM cats";

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

                for (int i=0; i<NO_OF_HITS; i++) {
                    PreparedStatement pst = connection.prepareStatement(query);
                    ResultSet rs = pst.executeQuery();
//                    while (rs.next()) {
//                        System.out.print(rs.getInt(1));
//                        System.out.print(": ");
//                        System.out.println(rs.getString(2));
//                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}

//NO_OF_HITS = 1000
//Total time: 1019
//QPS: 981

//NO_OF_HITS = 10000
//Total time: 3318
//QPS: 3013

//NO_OF_HITS = 100000
//Total time: 11909
//QPS: 8397

