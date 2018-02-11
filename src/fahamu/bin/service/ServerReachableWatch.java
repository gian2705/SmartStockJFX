package fahamu.bin.service;

//import static fahamu.bin.Ui.Main.password;
//import static fahamu.bin.Ui.Main.username;

public class ServerReachableWatch implements Runnable {
    /**
     * This class construct an object which
     * is the service to watch if database
     * is reachable when a program is running
     */

    public ServerReachableWatch() {
        Thread thread = new Thread(this, "Check Server Availability");
        thread.start();
    }

    /*
    private static void startService() {
        for (; ; ) {

            try {
                InetAddress inetAddress = InetAddress.getByAddress(new byte[]{10, 42, 0, 1});
                boolean reachable = inetAddress.isReachable(1000);
                if (!reachable) {
                    //boolean check for availability of local server
                    boolean isLocalServerAvailable;
                    //check availability of local databases
                    MysqlDataSource mysqlDataSource = new MysqlDataSource();
                    mysqlDataSource.setUser(username);
                    mysqlDataSource.setPassword(password);
                    mysqlDataSource.setServerName("localhost");

                    Connection connection = null;
                    try {
                        connection = mysqlDataSource.getConnection();

                        //if connection success, change a server address to local
                        isLocalServerAvailable = true;
                        Main.serverAddress = "localhost";

                    } catch (SQLException e) {
                        isLocalServerAvailable = false;
                    } finally {
                        if (connection != null) try {
                            connection.close();
                        } catch (SQLException ignore) {
                        }
                    }

                    if (!isLocalServerAvailable) {
                        if (connection != null) try {
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Server is not reachable");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void stopService(boolean stop) {
        if (stop) {
            System.out.println("Service Exit");
            System.exit(0);
        }
    }


    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        //startService();
    }

}
