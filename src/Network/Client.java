package Network;

import Model.GameModel;



import java.io.*;
import java.net.*;


public class Client extends Thread {

    private InetAddress ipAdress;
    private DatagramSocket socket;
    private GameModel model;
    private Networktest networktest;
    private ClientHandler inputHandler;
    private Socket socketTCP;
    private BufferedReader readerTCP;
    private PrintStream writerTCP;



    public Client(GameModel model,Networktest networktest,String ipAdress){
        this.model = model;
        this.networktest = networktest;

        this.inputHandler = new ClientHandler(networktest,this,model);


        try {
            this.socket = new DatagramSocket();
            this.ipAdress = InetAddress.getByName("255.255.255.255");
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }



    }

    public void run(){



        while(true){




            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data,data.length);

            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String message = new String(packet.getData());




            inputHandler.handle(message.trim(),packet);

        }
    }

    public void connectTCP(InetAddress ipAdress){
        try {
            socketTCP = new Socket(ipAdress,1331);
            readerTCP = new BufferedReader(new InputStreamReader(socketTCP.getInputStream()));
            writerTCP = new PrintStream(socketTCP.getOutputStream());
            readTCP();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readTCP(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(socketTCP.isConnected()){
                    String in;
                    try {
                        System.out.println("reading...");
                        in = readerTCP.readLine();
                        System.out.println("Client: " + in);
                        inputHandler.handle(in);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

    public void sendTCP(String message){
        writerTCP.println(message);
        writerTCP.flush();
    }



    public void sendData(byte[] data){


        DatagramPacket packet = new DatagramPacket(data,data.length,ipAdress,1331);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setIpAdress(InetAddress address){
        ipAdress = address;
    }



    public void searchServers(String message){

        try {
            broadcast("search:name",InetAddress.getByName("255.255.255.255"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void broadcast(String broadcastMessage, InetAddress address) throws IOException {
        socket = new DatagramSocket();
        socket.setBroadcast(true);

        byte[] buffer = broadcastMessage.getBytes();

        DatagramPacket packet
                = new DatagramPacket(buffer, buffer.length, address, 1331);

        socket.send(packet);
    }

}
