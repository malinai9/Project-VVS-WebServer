import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;


class uiClass implements Runnable{

    static boolean runServer=true;
    static String  statusServer;
    static int port;
    static boolean connectionOk=false;
    static WebServer server;

    public static void main(String[] args){

        server = new WebServer();
        setPortInterface();


        Thread interfaceThread = new Thread(new uiClass());
        interfaceThread.start();


        try {
            readComands();
        } catch (IOException e) {
            System.out.println("\n Invalid command !");
        }
    }

    @Override
    public void run() {
        while(runServer) {
            server.handleClient();
        }
    }


    public static void readComands() throws IOException {
        while(true)
        {
            System.out.print("+ Enter command :");
            BufferedReader readerCommand =  new BufferedReader(new InputStreamReader(System.in));
            String comandaLinie = readerCommand.readLine();
            verifCommand(comandaLinie);
        }
    }

    public static void verifCommand(String cmd) throws IOException {
        if (cmd.equals("status")) {
            System.out.println("Status WebServer is :"+statusServer);
        }else if (cmd.equals("systeminfo")) {
            System.out.println("Status server : "+statusServer);
            System.out.println("Port : "+port);
            InetAddress IP = InetAddress.getLocalHost();
            System.out.println("Host : "+IP.getHostAddress());
        }else if (cmd.equals("srv_pause")){
            server.setStateServer(2);
            statusServer="MAINTENANCE";
            System.out.println("Server is now in maintenance mode!");
        }else if (cmd.equals("srv_start")) {
            server.setStateServer(1);
            statusServer="RUNNING";
            System.out.println("Server start with initial port : "+port);
        }else if (cmd.equals("srv_stop")){
            server.setStateServer(3);
            statusServer="STOP";
            System.out.println("Server is stop!");
        }else{
            System.out.println("This command is not defined!");
        }
    }

    public static void setPortInterface() {
        int goNext=0;
        while(!connectionOk)
        {
            System.out.print("Enter Server Port : ");
            BufferedReader readerCommand =  new BufferedReader(new InputStreamReader(System.in));
            try {
                port = Integer.parseInt(readerCommand.readLine());
                goNext=1;
            } catch (NumberFormatException e) {
                goNext=0;
                System.out.println("Invalid port");
            } catch (IOException e) {
                goNext=0;
                System.out.println("Invalid port");
            }

            if(goNext==1){
                if(server.setPort(port))
                {
                    if(!server.acceptServerPort())
                    {
                        System.out.println("Cannot connect on this port!\n");
                    }else {
                        System.out.println("Port : "+port+" was accepted! Enter 'srv_start' command in order to start server l\n");
                        connectionOk=true;
                        statusServer="STOP";
                    }
                }
            }
        }
    }

}