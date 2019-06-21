package Model;

import Panzer.Panzer;
import Views.GameLoop;
import Views.GameUiView;
import Views.GameView;
import Weapons.Shot.NormalShot;
import Weapons.Weapon;
import Window.MyKeys;
import Window.MyWindow;
import Window.Var;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;


public class GameModel {

    private GameView gameView;
    private GameLoop gameLoop;
    private GameMap map;
    private GameUiView gameUiView;

    private Player lastLocalHuman;

    private LinkedList<Player> players;
    private LinkedList<Player> deadPlayers;
    private LinkedList<Player> currentPlayer;
    private CopyOnWriteArrayList<Weapon> currentWeapons;
    private LinkedList<Drop> drops = new LinkedList<>();

    private boolean shot;
    private boolean sandbox;
    private boolean dropping;
    private boolean ended;

    private int height;
    private int weaponsShowedTime = 0;
    private int spielmode,teamanzahl;
    private int highId = 0;

    private double redTriTimer = 0;



    public GameModel(){


        height = GameLoop.imgH;

        gameView = new GameView();

        gameView.addKeyListener(new MyKeys());



        players = new LinkedList<>();
        currentPlayer = new LinkedList<>();

        currentWeapons = new CopyOnWriteArrayList<>();
        deadPlayers = new LinkedList<>();
    }

    //ANDERE METHODEN

    public void start(LinkedList<Player> players,boolean sandbox,Color background,Color foreground,int mapArt,int spielmode){

        //startet das Spiel

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
            if(player.getTeam() > teamanzahl){
                teamanzahl++;
            }
        }

        for(Player player : players){
            System.out.println(player.getTeam());
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

    public void movePanzer(){



        // bewegen der Panzer

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

    public void updateUI(){
        gameUiView.drawBar();
    }

    public void removeWeapon(Weapon weapon){
        currentWeapons.remove(weapon);
    }

    //NÄCHSTER ZUG

    private void nextPlayer(){

        //wählt aus wer als nächstes an der Reihe ist

        switch(spielmode){
            case 1:
                int id = currentPlayer.getFirst().getId();
                currentPlayer.remove(currentPlayer.getFirst());
                currentPlayer.add(getNextAlivePlayer(id));
                break;
            case 2:
                int team = currentPlayer.getFirst().getTeam();
                currentPlayer = getNextAliveTeam(team);
                break;
            case 3:
                break;
        }


    }

    private Player getNextAlivePlayer(int id){
        if(id == highId){
            return getNextAlivePlayer(0);
        }else{
            for(Player player : players){
                if(player.getId() == id + 1){
                    return player;
                }
            }

            return getNextAlivePlayer(id + 1);
        }
    }

    private LinkedList<Player> getNextAliveTeam(int team){

        LinkedList<Player> returnValue = new LinkedList<>();

        if(team == teamanzahl){
            return getNextAliveTeam(0);
        }else{
            for(Player player : players){
                if(player.getTeam() == team + 1){
                    returnValue.add(player);
                }
            }

            if(returnValue.size() > 0){
                return returnValue;
            }else{
                return getNextAliveTeam(team + 1);
            }
        }
    }

    public void nextTurn(){

        //beendet den vorherigen Zug

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

    private void nextTurnAction(){


        //startet den nächsten Zug

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


                for (Player player : currentPlayer) {
                    if (player.isLocalHuman()) {
                        lastLocalHuman = player;
                    } else if (player.isKi()) {
                        player.prepare(this);
                    }
                }

                updateUI();

                shot = false;
            } else {
                endGame();
            }
        }
    }

    //ENDE DES SPIELS

    private boolean battleEnd(){

        //überprüft ob der Kampf vorbei ist

        if(players.size() == 0){
            return true;
        }
        int t = players.getFirst().getTeam();
        for(Player player : players){
            if(player.getTeam() != t){
                return false;
            }
        }

        ended = true;
        return true;
    }

    private void endGame(){

        //beendet das Spiel

        if(players.size() == 0){
            endingScreen(1);
        }else {
            if(!sandbox) {
                int t = players.getFirst().getTeam();
                for (Player player : players) {
                    player.getPanzer().addXP(50);
                }

                for (Player player : deadPlayers) {
                    if (player.getTeam() == t) {
                        player.getPanzer().addXP(25);
                    }
                }
            }

            deadPlayers.addAll(players);

            endingScreen(2);
        }
    }

    private void endingScreen(int art){

        //zeichnet den Abschlussbildschirm

        String text;
        if(art == 1){
            text = "DRAW";
            gameUiView.removeAll();
            gameUiView.revalidate();
            gameUiView.repaint();
            gameUiView.erzeugenOverlay_End();
            gameUiView.repaint();
            gameLoop.drawEndScreen(text,-1);
        }else{
            text = "TEAM " + players.getFirst().getTeam() + " WINS";
            gameUiView.removeAll();
            gameUiView.revalidate();
            gameUiView.repaint();
            gameUiView.erzeugenOverlay_End();
            gameUiView.repaint();
            gameLoop.drawEndScreen(text,players.getFirst().getTeam());
        }
    }

    //HANDLER

    public void handleMousePressed(int x,int y){
        if(!shot) {

            for(Player player : currentPlayer){
                if (player.equals(lastLocalHuman)) {
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
                if (player.equals(lastLocalHuman)) {
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

    //ZEICHENMETHODEN

    public boolean showWeapons(Graphics2D g2d, int mousex, int mousey, boolean clicked,int art){

        //zeichnet das Waffenauswahlmenü



        if(art == 1) {
            if (weaponsShowedTime <= GameLoop.imgH / 20) {
                g2d.fillRect(0, (GameLoop.imgH - 20 * weaponsShowedTime), GameLoop.imgW, 20 * weaponsShowedTime);
                weaponsShowedTime += 2;
            } else {
                g2d.fillRect(0, 0, GameLoop.imgW, GameLoop.imgH);

            }
        }else{
            if (weaponsShowedTime > 0) {
                g2d.fillRect(0, (GameLoop.imgH - 20 * weaponsShowedTime), GameLoop.imgW, 20 * weaponsShowedTime);
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

                        if((int)((mousex - ((xtemp) * 0.1) - (int) (t % 5 * xtemp - xtemp * 0.1))/((xtemp) * (1.2/5.6))) < weapon.getLevelAnzhal()){



                            Weapon weapon1 = weapon.getLevelWeapon((int)((mousex - ((xtemp) * 0.1) - (int) (t % 5 * xtemp - xtemp * 0.1))/((xtemp) * (1.2/5.6))) + 1,this);
                            int anzahl = weapon.getAnzahl();
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
            }
        }

        if(clicked && !handeld){
            MyKeys.weapon = false;
        }

        return clicked;


    }

    public void drawUi(Graphics2D g2d,int x,int y) {

        //Zeichnet Steuerungselemente um den Panzer (z.B. weißer Kreis)

        if (currentPlayer != null) {
            if (!shot) {
                for (Player player : currentPlayer) {
                    if(player.equals(lastLocalHuman)) {
                        player.getPanzer().drawUi(g2d, this);
                    }
                }
            }
        }
    }

    public void drawCurrentWeapons(Graphics2D g2d){
        for(Weapon weapon : currentWeapons){
            weapon.draw(g2d);
        }
    }

    public void drawDrops(Graphics2D g2d){

        //zeichnet Versorgungsdrops wenn die Waffen leer sind

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

    public void drawPanzer(Graphics2D g2d){

        //zeichnen der Panzer

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

        //zeichnen des roten dreiecks über den Panzern die am Zug sind

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

    //SCHUSS

    private void shoot(){
        if(!shot) {


            boolean temp = false;

            for(Player player : currentPlayer) {

                if(player.getPanzer().getLeben() > 0) {

                    player.shoot(this);


                    currentWeapons.add(player.getSelectedWeapon());


                    if (!sandbox) {

                        if (player.getSelectedWeapon().getAnzahl() == 1) {
                            player.getWeapons().remove(player.getSelectedWeapon());
                            player.subWaffenanzahl();
                            player.setSelectedWeapon(player.getWeapons().get(0));
                        } else {
                            player.getSelectedWeapon().subAnzahl();
                            player.getSelectedWeapon().reset();
                            player.subWaffenanzahl();
                        }

                    }
                }else{
                    NormalShot shot =  new NormalShot(this);
                    shot.create((int)player.getPanzer().getBulletspawn().getX(), (int) player.getPanzer().getBulletspawn().getY(),player.getPanzer().getWinkel() - Math.PI/2,1,false,player.getPanzer());
                    currentWeapons.add(shot);
                }

            }

            shot = true;
        }
    }

    public void feuerButtonAction(){
        for(Player player : currentPlayer) {
            if(player.equals(lastLocalHuman)) {
                player.setLockedIn(true);
                for(Player player1 : currentPlayer){
                    if(!player1.equals(player) && !player1.isLockedIn()){
                        lastLocalHuman = player1;
                        updateUI();
                    }
                }
                return;
            }
        }

    }

    //EXPLOSION

    public void explosion(int x,int y,int size,int damage,Panzer herkunft){
        map.explosion(x,y,size,damage,herkunft);
    }

    public void noImpactExplosion(Shape shape,int damage,Panzer herkunft){
        map.noImpactExplosion(shape,damage,herkunft);
    }

    public void movePanzerDown(int x,int y,int size,int damage,Panzer herkunft) {

        //führt alle actionen nach einer Explosion aus (Panzer bewegen, schaden hinzufügen)

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

                    player.getPanzer().schaden(damage, 0,sandbox,this);

                    herkunft.addXP(damage);
                }else{

                    player.getPanzer().schaden(damage, 1,sandbox,this);
                }
            }

        }
    }

    public void movePanzerDown(Shape shape,int damage,Panzer herkunft) {

        //führt alle actionen nach einer "NoImpactExplosion" aus (Panzer bewegen, schaden hinzufügen)


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


            if(shape.intersects(player.getPanzer().getHitbox().getBounds2D()) ||shape.contains(player.getPanzer().getBulletspawn())){



                if(player.getTeam() != team) {

                    player.getPanzer().schaden(damage, 0,sandbox,this);

                    if(!sandbox) {
                        herkunft.addXP(damage);
                    }
                }
            }

        }
    }

    //COLLISION DETECTION

    public boolean isCollision(int x,int y){
        return map.isCollision(x,y);
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

    //SETTER

    public void setUiView() {
        gameUiView.removeAll();
        gameUiView.revalidate();
        gameUiView.repaint();
        gameUiView.erzeugenOverlay_End();

    }

    public void setShot(boolean shot) {
        this.shot = shot;
    }

    //GETTER

    public GameMap getMap(){
        return map;
    }

    private boolean allLockedIn(){

        //überprüft ob all spieler bereit sind zu schießen

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

    public boolean isEnded() {
        return ended;
    }

    public int getWeaponsShowedTime() {
        return weaponsShowedTime;
    }

    public LinkedList<Player> getPlayer() {
        return players;
    }

    public int getNextId(){
        highId++;
        return highId;
    }

    public Player getLastLocalHuman() {
        return lastLocalHuman;
    }

    public LinkedList<Player> getDeadPlayer() {
        return deadPlayers;
    }

    public GameView getGameView() {
        return gameView;
    }

    //DROP

    private class Drop {

        //private Klasse für Versorgungsdrops

        private Player player;
        private int x,y;
        private boolean done;
        private GameModel model;

        public Drop(Player player,GameModel model){
            this.player = player;
            y = -70;
            x = (int) player.getPanzer().getBulletspawn().getX();
            this.model = model;
        }

        public void draw(Graphics2D g2d){
            if(!done) {
                if (y < player.getPanzer().getBulletspawn().getY() - 50) {
                    y+=3;
                    g2d.drawImage(Var.drop,x-25,y,50,100,null);
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
