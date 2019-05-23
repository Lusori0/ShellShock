package Network;

import Model.*;
import Window.MyWindow;
import Window.Var;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Networktest{

    JPanel chosePanel,hostPanel,joinPanel;

    JButton hostBtn,joinBtn;

    JButton start;

    JButton refresh;

    Server server;

    Client client;

    GameModel model;

    private boolean isServer = false;

    private List<ServerSave> serverSavesList = new ArrayList<>();

    private List<PlayerData> player = new ArrayList<>();

    private LinkedList<Player> players = new LinkedList<>();

    public Networktest(){



        chosePanel = new JPanel();

        hostBtn = new JButton("Host");
        hostBtn.addActionListener(e -> startHost());
        hostBtn.setVisible(true);

        joinBtn = new JButton("Join");
        joinBtn.addActionListener(e -> startJoin());
        joinBtn.setVisible(true);

        chosePanel.add(hostBtn);
        chosePanel.add(joinBtn);

        hostPanel = new JPanel();

        start = new JButton("Start");

        start.addActionListener(e -> startGame());

        hostPanel.add(start);

        joinPanel = new JPanel();

        refresh = new JButton("Refresh");
        refresh.addActionListener(e -> refreshJoin());

        joinPanel.add(refresh);

    }

    public void startGame() {
        if(isServer) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    server.sendToAllTCP("start:start");
                    model.start(players,client,server,false,new Color(0,195,255),new Color(150,150,0));
                }
            }).start();





        }else{

            new Thread(new Runnable() {
                @Override
                public void run() {
                    model.start(players,client,server,false,new Color(0,195,255),new Color(150,150,0));
                }
            }).start();


        }
    }

    public void startHost(){
        isServer = true;
        MyWindow.getFrame().setTitle("server");
        MyWindow.setContent(hostPanel);
        model = new GameModel();
        server = new Server(model,this);
        server.start();
        addPlayer(new HumanPlayer(model,1,model.getNextId(),"hosttestttaanasgkjh"));
        addPlayer(new KiPlayer(model,2,model.getNextId(),3));
        hostPanel.updateUI();
    }

    public void addPlayer(Player playerToJoin){

        for(Player player : players){
            if(player.equals(playerToJoin)){
                return;
            }
        }

        players.add(playerToJoin);
        hostPanel.add(new JButton(playerToJoin.getName() + ":"+ playerToJoin.getId() + ":" + playerToJoin.getTeam()));
        hostPanel.updateUI();
    }



    public List<Player> getPlayer() {
        return players;
    }

    public void setHostView(){
        MyWindow.setContent(hostPanel);
    }

    public void startJoin(){
        MyWindow.setContent(joinPanel);
        model = new GameModel();
        try {
            client = new Client(model,this, InetAddress.getLocalHost().toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        client.start();

        refreshJoin();
    }

    private void refreshJoin(){
        client.searchServers("search");
        joinPanel.updateUI();
    }

    public void addServer(String name,InetAddress address){
        serverSavesList.add(new ServerSave(address,name,this));
        joinPanel.updateUI();
    }

    public void joinServer(InetAddress address){
        client.setIpAdress(address);
        client.sendData("join:name".getBytes());
    }

    public JPanel getPanel(){
        return chosePanel;
    }

    private class ServerSave implements ActionListener {
        private InetAddress address;
        private String name;
        private JButton btn;
        private Networktest networktest;

        public ServerSave(InetAddress address, String name, Networktest networktest) {
            this.networktest = networktest;
            this.address = address;
            this.name = name;
            btn = new JButton(name);
            btn.addActionListener(this);
            btn.setVisible(true);
            joinPanel.add(btn);

        }

        public InetAddress getAddress() {
            return address;
        }

        public String getName() {
            return name;
        }

        public JButton getBtn() {
            return btn;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == btn){
                networktest.joinServer(address);
            }
        }
    }

    public class PlayerData implements ActionListener {
        String name;
        JButton btn;
        int port;
        InetAddress ipAdress;
        int id;
        int team;

        public PlayerData(String name,int team,int id) {
            this.name = name;
            this.team = team;
            this.id = id;
            btn = new JButton(name + ":" + id + ":" + team);
            btn.addActionListener(this);
            btn.setVisible(true);
            hostPanel.add(btn);
        }

        public PlayerData(String name,int team,int id,InetAddress ipAdress,int port) {
            this.name = name;
            this.team = team;
            this.id = id;
            this.ipAdress = ipAdress;
            this.port = port;
            btn = new JButton(name + ":" + id);
            btn.addActionListener(this);
            btn.setVisible(true);
            hostPanel.add(btn);
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == btn){

            }
        }
    }
}
