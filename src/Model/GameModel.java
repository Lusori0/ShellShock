package Model;

import Panzer.Panzer;
import Views.GameLoop;
import Views.GameUiView;
import Views.GameView;


import Weapons.Weapon;
import Window.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;


public class GameModel {

    private GameView gameView;
    private GameLoop gameLoop;
    private LinkedList<Player>  players;
    private LinkedList<Player>  deadPlayers;
    private CopyOnWriteArrayList<Weapon> currentWeapons;
    private GameMap map;
    private int height;
    private Player lastLocalHuman;
    private LinkedList<Player> currentPlayer;
    private boolean shot;
    private int weaponsShowedTime = 0;
    private int spielmode = 0,teamanzahl = 0;


    private int highId = 0;

    private double redTriTimer = 0;
    private boolean sandbox;
    private GameUiView gameUiView;

    private LinkedList<Drop> drops = new LinkedList<>();
    private boolean dropping;


    public GameModel(){
        //map = Var.map;

        //collisionMap = new CollisionMap(map,this);

        height = (int) (GameLoop.imgH);

        gameView = new GameView();

        gameView.addKeyListener(new MyKeys());



        players = new LinkedList<>();
        currentPlayer = new LinkedList<>();

        //Erster spieler ist ein lokaler mensch

        teamanzahl = 2;

        spielmode = 3;

        currentWeapons = new CopyOnWriteArrayList<>();
        deadPlayers = new LinkedList<>();
    }

    public void start(LinkedList<Player> players,boolean sandbox,Color background,Color foreground,int mapArt,int spielmode){

        map = new GameMap(this,mapArt);

        this.sandbox = sandbox;

        this.players = players;

        this.spielmode = spielmode;

        if(spielmode == 1){
            currentPlayer.add(players.getFirst());
        }else if(spielmode == 2){
            int team = players.getFirst().getTeam();

            for(Player player : players){
                if(player.getTeam() == team){
                    currentPlayer.add(player);
                }
            }
        }else{
            currentPlayer.addAll(players);
        }

        for(Player player : currentPlayer) {
            player.setOnTurn(true);
            if(player.isLocalHuman()){
                lastLocalHuman = player;

            }
        }

        for(Player player : players){
            if(sandbox){
                player.setSandboxWeapons(this);
                player.setSelectedWeapon(player.getWeapons().get(0));
            }else{
                player.restoreWeapons(this);
                player.setSelectedWeapon(player.getWeapons().get(0));
            }
        }

        gameLoop = new GameLoop(this,background,foreground);

        gameView.add(gameLoop,BorderLayout.CENTER);
        gameUiView = new GameUiView(this);
        gameUiView.erzeugenOverlay();
        gameView.add(gameUiView,BorderLayout.PAGE_END);
        MyWindow.setContent(gameView);
        gameLoop.createStrategy();
        gameLoop.mainGameLoop();





        for(Player player : players){
            player.getPanzer().moveNotTurn(map);
            if(sandbox){
                player.setSandboxWeapons(this);
                player.setSelectedWeapon(player.getWeapons().get(0));
            }else{
                player.restoreWeapons(this);
                player.setSelectedWeapon(player.getWeapons().get(0));
            }
        }
    }



    public GameMap getMap(){
        return map;
    }

    public void drawPanzer(Graphics2D g2d){
        for(Player player : players){
            if(player == lastLocalHuman) {
                player.getPanzer().draw(g2d,0);

            }else if(player.getTeam() == lastLocalHuman.getTeam()){
                player.getPanzer().draw(g2d,1);
            }else{
                player.getPanzer().draw(g2d,2);
            }

            if(currentPlayer.contains(player)){
                if(!shot) {
                    drawRedRect(player, g2d);
                }
            }
        }

        for(Player player : deadPlayers){
            player.getPanzer().draw(g2d,3);
        }
    }

    private void drawRedRect(Player player,Graphics2D g2d){

        int timerAdd = (int) Math.abs(redTriTimer);

        int triSize = 10;
        int[] xPos = new int[]{(int) (player.getPanzer().getBulletspawn().getX() - triSize), (int) player.getPanzer().getBulletspawn().getX(), (int) (player.getPanzer().getBulletspawn().getX() + triSize)};
        int ytemp = (int) (player.getPanzer().getCenter().getY() - 110 - timerAdd);
        int[] yPos = new int[]{ytemp - triSize,ytemp,ytemp - triSize};

        g2d.setColor(Color.RED);
        g2d.fill(new Polygon(xPos,yPos,3));

        if(redTriTimer > -10){
            redTriTimer -= 0.2;
        }else{
            redTriTimer = 10;
        }
    }

    public void movePanzer(){
        for(Player player : players){

            if(!shot) {

                player.move(this,map);
            }
            if(allLockedIn()){
                shoot();
            }
        }
        for(Player player : deadPlayers){
            player.getPanzer().moveNotTurn(map);
        }
    }

    public boolean allLockedIn(){
        for(Player player : currentPlayer){
            if(!player.isLockedIn()){
                return false;
            }
        }
        return true;
    }



    public int getHeight() {
        return height;
    }

    public void setShot(boolean shot) {
        this.shot = shot;
    }

    public void nextPlayer(){

       switch(spielmode){
           case 1:
               for(Player player : players){
                   if(player == currentPlayer.getFirst()){
                       currentPlayer.remove(player);

                       if(players.indexOf(player) == players.indexOf(players.getLast())){
                           currentPlayer.add(players.getFirst());
                           break;
                       }else{
                           currentPlayer.add(players.get(players.indexOf(player) + 1));
                           break;
                       }
                   }
               }
               break;
           case 2:
               if(currentPlayer.getFirst().getTeam() == teamanzahl){
                   currentPlayer = new LinkedList<>();
                   for(Player player : players){
                       if(player.getTeam() == 1){
                           currentPlayer.add(player);
                       }
                   }
               }else{
                   int t = currentPlayer.getFirst().getTeam();
                   for(Player player : players){
                       if(player.getTeam() == t+1){
                           currentPlayer.add(player);
                       }
                   }
               }

               break;
           case 3:
               break;
       }


    }

    public void removeWeapon(Weapon weapon){
        currentWeapons.remove(weapon);

    }

    public void changeGui(){

    }

    public void nextTurn(){
        if(currentWeapons.size() == 0){

            for (Player player : players) {

                if (player.getPanzer().getLeben() <= 0) {

                    player.setDead(true);
                    explosion((int)player.getPanzer().getBulletspawn().getX(), (int) player.getPanzer().getBulletspawn().getY(),80,20,player.getPanzer());
                    deadPlayers.add(player);
                }
            }

            players.removeAll(deadPlayers);


            boolean temp = false;
            for(Player player : players){
                if(player.getWeaponCount() <= 2){
                    temp = true;
                }
            }

            if(temp){
                for(Player player : players){
                    drops.add(new Drop(player,this));
                    dropping = true;
                }

            }else{
                nextTurnAction();
            }
        }
    }

    public void drawDrops(Graphics2D g2d){
        if(dropping) {
            boolean temp = false;
            for (Drop drop : drops) {
                if (!drop.isDone()) {
                    drop.draw(g2d);
                    temp = true;
                }
            }
            if (!temp) {
                dropping = false;
                drops.removeAll(drops);
                nextTurnAction();
            }
        }
    }

    public void nextTurnAction(){



        if(currentWeapons.size() == 0) {


            if (!battleEnd()) {
                for (Player player : currentPlayer) {
                    player.setOnTurn(false);
                    player.setLockedIn(false);
                    player.getPanzer().resetSprit();

                }

                nextPlayer();

                for (Player player : currentPlayer) {
                    player.setOnTurn(true);

                }

                changeGui();

                for (Player player : currentPlayer) {
                    if (player.isLocalHuman()) {
                        lastLocalHuman = player;
                    } else if (player.isKi()) {
                        player.prepare(this);
                    }
                }

                shot = false;
            } else {
                endGame();
            }
        }
    }

    public boolean battleEnd(){
        if(players.size() == 0){
            return true;
        }
        int t = players.getFirst().getTeam();
        for(Player player : players){
            if(player.getTeam() != t){
                return false;
            }
        }

        return true;
    }

    public void endGame(){
        if(players.size() == 0){
            endingScreen(1);
        }else {
            int t = players.getFirst().getTeam();
            for (Player player : players) {
                player.getPanzer().addXP(50);
            }

            for (Player player : deadPlayers) {
                if (player.getTeam() == t) {
                    player.getPanzer().addXP(25);
                }
            }

            deadPlayers.addAll(players);

            endingScreen(2);
        }
    }

    public void endingScreen(int art){
        String text;
        if(art == 1){
            text = "DRAW";
            gameUiView.removeAll();
            gameUiView.revalidate();
            gameUiView.repaint();
            gameUiView.erzeugenOverlay_End();
            gameLoop.drawEndScreen(text,-1);
        }else{
            text = "TEAM " + players.getFirst().getTeam() + " WINS";
            gameUiView.removeAll();
            gameUiView.revalidate();
            gameUiView.repaint();
            gameLoop.drawEndScreen(text,players.getFirst().getTeam());
            gameUiView.erzeugenOverlay_End();

        }


    }

    public void handleMousePressed(int x,int y){
        if(!shot) {

            for(Player player : currentPlayer){
                if (player.isLocalHuman()) {
                    if (player.getPanzer().isSelected()) {


                        player.getPanzer().setTemps();

                        player.getPanzer().changeRohr(x,y,this);

                        player.getPanzer().setShootready(true);


                    }
                }
            }


        }
    }

    public void handleMouseClicked(int x,int y){
        if(!shot) {

            for(Player player : currentPlayer){
                if (player.isLocalHuman()) {
                    if (player.getPanzer().isSelected()) {

                        player.getPanzer().setTemps();

                        player.getPanzer().changeRohr(x,y,this);

                        player.getPanzer().setShootready(true);




                    }
                    if (player.getPanzer().isHit(x, y)) {
                        player.getPanzer().setSelected(true);
                    }

                }
            }

        }
    }

    public void drawUi(Graphics2D g2d,int x,int y) {
        if (currentPlayer != null) {
            if (!shot) {
                for (Player player : currentPlayer) {
                    if(player.isLocalHuman()) {
                        player.getPanzer().drawUi(g2d, x, y, this);
                    }
                }
            }
        }
    }


    public int getWeaponsShowedTime() {
        return weaponsShowedTime;
    }

    public boolean showWeapons(Graphics2D g2d, int mousex, int mousey, boolean clicked,int art){

        if(art == 1) {
            if (weaponsShowedTime <= GameLoop.imgH / 20) {
                g2d.fillRect(0, (int) (GameLoop.imgH - 20 * weaponsShowedTime), GameLoop.imgW, (int) 20 * weaponsShowedTime);
                weaponsShowedTime += 2;
            } else {
                g2d.fillRect(0, 0, GameLoop.imgW, GameLoop.imgH);

            }
        }else{
            if (weaponsShowedTime > 0) {
                g2d.fillRect(0, (int) (GameLoop.imgH - 20 * weaponsShowedTime), GameLoop.imgW, (int) 20 * weaponsShowedTime);
                weaponsShowedTime -= 4;
            }
        }

        int xtemp = GameLoop.imgW/5;
        int ytemp = GameLoop.imgH/8;

        int size = lastLocalHuman.getWeapons().size();



        int h= size/5;

        if(size%5 != 0){
            h++;
        }

        int t = 0;

        boolean temp = false,handeld = false;

        for(Weapon weapon : lastLocalHuman.getWeapons()){
            if(weaponsShowedTime/5 > t) {

                if (new Rectangle2D.Double(t % 5 * xtemp, (t / 5) * ytemp + height - h * ytemp, xtemp, ytemp).contains(mousex, mousey)) {

                    if(mousey < (int) ((t / 5) * ytemp + height - h * ytemp - ytemp * 0.1) + (int) (ytemp * 1.2 * (2.0/9.0))  && clicked && sandbox) {

                        System.out.println((int)((mousex - ((xtemp) * 0.1) - (int) (t % 5 * xtemp - xtemp * 0.1))/((xtemp) * (1/5.6))) );

                        if((int)((mousex - ((xtemp) * 0.1) - (int) (t % 5 * xtemp - xtemp * 0.1))/((xtemp) * (1.2/5.6))) < weapon.getLevelAnzhal()){

                            System.out.println(22);

                            Weapon weapon1 = weapon.getLevelWeapon((int)((mousex - ((xtemp) * 0.1) - (int) (t % 5 * xtemp - xtemp * 0.1))/((xtemp) * (1.2/5.6))) + 1,this);
                            int anzahl = weapon.getAnzahl();
                            System.out.println(weapon1.getName());
                            weapon1.setAnzahl(anzahl);
                            weapon1.createImage();




                            int index = lastLocalHuman.getWeapons().indexOf(weapon);
                            lastLocalHuman.getWeapons().remove(weapon);
                            lastLocalHuman.subWaffenanzahl();
                            lastLocalHuman.getWeapons().add(index,weapon1);


                            handeld = true;

                        }

                        weapon.drawImage((int) (t % 5 * xtemp - xtemp * 0.1), (int) ((t / 5) * ytemp + height - h * ytemp - ytemp * 0.1), (int) (xtemp * 1.2), (int) (ytemp * 1.2), g2d);


                    }else if (clicked) {
                        MyKeys.weapon = false;
                        lastLocalHuman.setSelectedWeapon(weapon);
                        handeld = true;
                        weapon.drawImage((int) (t % 5 * xtemp - xtemp * 0.1), (int) ((t / 5) * ytemp + height - h * ytemp - ytemp * 0.1), (int) (xtemp * 1.2), (int) (ytemp * 1.2), g2d);
                    } else {
                        weapon.drawImage((int) (t % 5 * xtemp - xtemp * 0.1), (int) ((t / 5) * ytemp + height - h * ytemp - ytemp * 0.1), (int) (xtemp * 1.2), (int) (ytemp * 1.2), g2d);
                    }
                } else {
                    weapon.drawImage(t % 5 * xtemp, (t / 5) * ytemp + height - h * ytemp, xtemp, ytemp, g2d);
                }

                t++;
            }else{
                temp = true;
            }
        }

        if(clicked && !handeld){
            MyKeys.weapon = false;
        }

        return clicked;


    }

    public void drawCurrentWeapons(Graphics2D g2d){
        for(Weapon weapon : currentWeapons){
            weapon.draw(g2d);
        }
    }

    public boolean isCollisionPanzer(int x, int y, Panzer herkunft){
        for(Player player : players){
            if(player.getPanzer() != herkunft) {
                if (player.getPanzer().isHit(x, y)) {
                    return true;
                }
            }

        }
        return map.isCollision(x,y);
    }

    public void shoot(){
        if(!shot) {

            for(Player player : currentPlayer) {

                player.shoot(this);


                currentWeapons.add(player.getSelectedWeapon());



                if(!sandbox) {
                    if (player.isLocalHuman() || player.isKi()) {
                        if (player.getSelectedWeapon().getAnzahl() == 1) {
                            player.getWeapons().remove(player.getSelectedWeapon());
                            player.subWaffenanzahl();
                            player.setSelectedWeapon(player.getWeapons().get(0));
                        } else {
                            player.getSelectedWeapon().subAnzahl();
                            player.getSelectedWeapon().reset();
                        }
                    }
                }

            }

            shot = true;
        }
    }



    public void feuerButtonAction(){
        for(Player player : currentPlayer) {
            if(player.isLocalHuman()) {
                player.setLockedIn(true);
            }
        }

    }

    public void addWeapon(Weapon weapon){
        currentWeapons.add(weapon);
    }


    public void explosion(int x,int y,int size,int damage,Panzer herkunft){
        map.explosion(x,y,size,damage,herkunft);
    }

    public void noImpactExplosion(Shape shape,int damage,Panzer herkunft){
        map.noImpactExplosion(shape,damage,herkunft);
    }



    public void movePanzerDown(int x,int y,int size,int damage,Panzer herkunft) {

        int team = 0;

        for(Player player : players){
            if(player.getPanzer() == herkunft){
                team = player.getTeam();
            }
        }


        for(Player player : players){

            player.getPanzer().createHitbox();

            player.getPanzer().moveNotTurn(map);
            player.getPanzer().setPolyPoints(this);



            if(new Ellipse2D.Double(x - size/(double)2,y -size/(double)2,size,size).intersects(player.getPanzer().getHitbox().getBounds2D()) || new Ellipse2D.Double(x - size/(double)2,y -size/(double)2,size,size).contains(player.getPanzer().getBulletspawn())){



                if(player.getTeam() != team) {

                    player.getPanzer().schaden(damage, 0,sandbox);

                    herkunft.addXP(damage);
                }else{

                    player.getPanzer().schaden(damage, 1,sandbox);
                }
            }

        }
    }

    public void movePanzerDown(Shape shape,int damage,Panzer herkunft) {

        int team = 0;

        for(Player player : players){
            if(player.getPanzer() == herkunft){
                team = player.getTeam();
            }
        }


        for(Player player : players){

            player.getPanzer().createHitbox();

            player.getPanzer().moveNotTurn(map);
            player.getPanzer().setPolyPoints(this);

            System.out.println(shape.getBounds());
            if(shape.intersects(player.getPanzer().getHitbox().getBounds2D()) ||shape.contains(player.getPanzer().getBulletspawn())){



                if(player.getTeam() != team) {

                    player.getPanzer().schaden(damage, 0,sandbox);

                    herkunft.addXP(damage);
                }
            }

        }
    }

    public Player getGegener(Player playert){
        for(Player player : players){
            if(player.getTeam() != playert.getTeam()){
                return player;
            }
        }
        return null;
    }

    public LinkedList<Player> getPlayer() {
        return players;
    }

    public boolean isCollision(int x,int y){
        return map.isCollision(x,y);
    }

    public int getNextId(){
        highId++;
        return highId;
    }

    public boolean isSandbox() {
        return sandbox;
    }

    public Player getLastLocalHuman() {
        return lastLocalHuman;
    }

    public LinkedList<Player> getDeadPlayer() {
        return deadPlayers;
    }

    private class Drop {
        private Player player;
        private int x,y;
        private boolean done;
        private GameModel model;

        public Drop(Player player,GameModel model){
            this.player = player;
            y = -30;
            x = (int) player.getPanzer().getBulletspawn().getX();
            this.model = model;
        }

        public void draw(Graphics2D g2d){
            if(!done) {
                if (y < player.getPanzer().getBulletspawn().getY() - 15) {
                    y+=3;
                    g2d.drawImage(Var.shotIcon,x-15,y,30,30,null);
                } else {
                    player.restoreWeapons(model);
                    done = true;
                }
            }
        }

        public boolean isDone() {
            return done;
        }
    }
}
