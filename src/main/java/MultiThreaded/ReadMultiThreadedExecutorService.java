package MultiThreaded;

import Properties.PropertiesLoaderClass;

import java.sql.*;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadMultiThreadedExecutorService {

    final static int NO_OF_HITS = 1000;
    final static int NO_OF_THREADS = 10;
    private static Properties props;
    private static String url;
    private static String user;
    private static String password;
    private static String query;
    private static Connection[] connections;
    private static Random random;

    public static void main(String[] args) throws InterruptedException, SQLException {

        props = PropertiesLoaderClass.getConnectionData();
        url = props.getProperty("db.url.pets");
        user = props.getProperty("db.user");
        password = props.getProperty("db.password");
        query = "SELECT * FROM cats";
        random = new Random();

        //Connection pool
        connections = new Connection[NO_OF_THREADS];
        for (int i=0; i<NO_OF_THREADS; i++) connections[i] = DriverManager.getConnection(url, user, password);

        ExecutorService executors = Executors.newFixedThreadPool(NO_OF_THREADS);

        long startTime = System.currentTimeMillis();

        for (int i=0; i<NO_OF_HITS; i++) {
            executors.submit(new Runner());
        }
        executors.shutdown();
        executors.awaitTermination(1, TimeUnit.HOURS);

        long endTime = System.currentTimeMillis();

        long totalTime = endTime - startTime;
        System.out.println("Total time: " + totalTime);

        long qps = (NO_OF_HITS * 1000l)/totalTime;
        System.out.println("QPS: " + qps);
    }

    static class Runner implements Runnable {

        @Override
        public void run() {
            try {
                PreparedStatement pst = connections[random.nextInt(NO_OF_THREADS)].prepareStatement(query);
                ResultSet rs = pst.executeQuery();
//                while (rs.next()) {
//                    System.out.print(rs.getInt(1));
//                    System.out.print(": ");
//                    System.out.println(rs.getString(2));
//                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}

//NO_OF_HITS = 1000
//Total time: 558
//QPS: 1792

//NO_OF_HITS = 10000
//Total time: 2959
//QPS: 3379

//NO_OF_HITS = 100000
//Total time: 15505
//QPS: 6449
