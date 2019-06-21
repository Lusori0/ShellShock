package Panzer;

import Model.GameMap;
import Model.GameModel;
import Views.GameLoop;
import Window.Var;

import javax.sound.sampled.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Panzer {

    private BufferedImage image,imageRed,imageBlue,rohrGreen,rohrRed,rohrBlue,imageDark;

    private double xPosition,yPosition;

    private double width,height,winkel,shotstrength,rohrwinkel,drawAncle,tempAncle;

    private AffineTransform affineTransform;

    private Shape hitbox;

    private boolean selected,shootready,orientationRight,moveRight,moveLeft;

    private int[] xPoints,yPoints,xPointsSmall,yPointsSmall;
    private int tempXPoint = 0,tempYPoint = 0,mouseX = 0,mouseY = 0;
    private int leben,sprit,maxSprit,maxLeben;

    private CopyOnWriteArrayList<Screentext> strings = new CopyOnWriteArrayList<>();

    private String name;

    private int xp;

    private int soundTimer = 0;

    private Clip engineClip;

    public Panzer(BufferedImage image, BufferedImage rohr, int leben, int maxSprit, String name){

        //erzeugen des Panzers (der verschiedenfarbigen Bilder)

        if(name.length() > 10) {
            this.name = name.substring(0, 8);
            this.name = this.name + "..";
        }else{
            this.name = name;
        }



        try {
            engineClip = AudioSystem.getClip();
            AudioInputStream engineIn = AudioSystem.getAudioInputStream(Var.engine);
            engineClip.open(engineIn);

        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        Var.inGameVolume = 0f;

        playSound(engineClip);

        Var.inGameVolume = Var.ingameSoundbar;

        affineTransform = new AffineTransform();

        this.image = image;
        this.rohrGreen = rohr;

        rohrBlue = new BufferedImage(rohrGreen.getWidth(),rohrGreen.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        rohrRed = new BufferedImage(rohrGreen.getWidth(),rohrGreen.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);

        imageRed = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);
        imageBlue = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);

        imageDark = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);

        for(int x = 0;x < image.getWidth();x++){
            for(int y = 0; y < image.getHeight();y++){
                Color c = new Color(image.getRGB(x,y),true);
                imageRed.setRGB(x,y,new Color(c.getGreen(),c.getRed(),c.getBlue(),c.getAlpha()).getRGB());
                imageBlue.setRGB(x,y,new Color(c.getRed(),c.getBlue(),c.getGreen(),c.getAlpha()).getRGB());
                imageDark.setRGB(x,y,new Color(50,50,50,c.getAlpha()).getRGB());
            }
        }

        for(int x = 0;x < rohrGreen.getWidth();x++){
            for(int y = 0; y < rohrGreen.getHeight();y++){
                Color c = new Color(rohrGreen.getRGB(x,y),true);
                rohrRed.setRGB(x,y,new Color(c.getGreen(),c.getRed(),c.getBlue(),c.getAlpha()).getRGB());
                rohrBlue.setRGB(x,y,new Color(c.getRed(),c.getBlue(),c.getGreen(),c.getAlpha()).getRGB());
            }
        }


        xPosition = (GameLoop.imgW - 100) * Math.random() + 50;
        yPosition = 30;
        width = 60;
        height = 30;

        this.leben = leben;
        maxLeben = leben;

        xPoints = new int[3];
        yPoints = new int[3];

        xPointsSmall = new int[3];
        yPointsSmall = new int[3];

        this.maxSprit = maxSprit;
        sprit = maxSprit;



        Shape hit = new Rectangle2D.Double((int)xPosition,(int)yPosition - height/2,(int)width,(int)height);

        hitbox = affineTransform.createTransformedShape(hit);

        strings.add(new Screentext(this.name,(int)getBulletspawn().getX() - 30, (int) (getBulletspawn().getY() - 100),2));
    }



    public void resetSprit(){
        sprit = maxSprit;
    }

    public void changeRohr(int x, int y, GameModel model){

        //verädern der Position des Rohrs/des weißen Dreiecks

        try {
            mouseX = (int) affineTransform.createInverse().transform(new Point2D.Double(x,y),null).getX();
            mouseY = (int) affineTransform.createInverse().transform(new Point2D.Double(x,y),null).getY();
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
        }

        setPolyPoints(model);
    }

    public void createHitbox(){
        Shape hit = new Rectangle2D.Double((int)xPosition,(int)yPosition - height/2,(int)width,(int)height);

        hitbox = affineTransform.createTransformedShape(hit);
    }

    public void schaden(int damage, int art,boolean sandbox,GameModel model) {
        if(!sandbox) {
            leben -= damage;
            model.updateUI();

        }
        int x = (int) (xPosition - width + Math.random() * width * 3);
        int y = (int) (yPosition + height - Math.random() * 2 * height);

        if(art == 0) {
            strings.add(new Screentext(String.valueOf(damage), x, y, 0));
        }else{
            strings.add(new Screentext(String.valueOf(damage), x, y, 1));
        }
    }

    public void addXP(int damage) {
        xp += damage;
        if(xp>400){
            xp=400;
        }
    }

    public void playSound(Clip clip){
        try{

            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = volume.getMaximum() - volume.getMinimum();
            float gain = (float) (range * Math.log10(Var.inGameVolume * 9 + 1) + volume.getMinimum());
            volume.setValue(gain);




            clip.stop();

            clip.setFramePosition(1);

            clip.start();



        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    //MOVE

    public void moveNotTurn(GameMap map){
        if(map.getHeight((int) getCenterX()) != getCenterY()) {

            double x = getCenterX();

            winkel = map.getWinkel(x);
            drawAncle = winkel;
            yPosition = map.getHeight(x) - height/2;

            affineTransform.setToRotation(drawAncle,x,getCenterY());


        }
    }

    public void move(GameMap map){

        if(moveRight || moveLeft) {
            if(sprit > 0) {

                if (soundTimer == 12) {
                    playSound(engineClip);
                    soundTimer = 0;
                } else {
                    soundTimer++;
                }

            }
        }

        double xDifferece = width/60;

        double ancleDiffrence = xDifferece/10;

        if(moveRight && xPosition < GameLoop.imgW-width*1.5 && sprit > 0){

            winkel = map.getWinkel(getCenterX()+5);




            xPosition += Math.cos(winkel) * xDifferece * (1 - (Math.abs(drawAncle - winkel)));




            //yPosition += Math.sin(winkel) * xDifferece;

            if(Math.abs(drawAncle - winkel)<ancleDiffrence){
                drawAncle = winkel;

            }else{
                if(drawAncle > winkel){
                    drawAncle -= ancleDiffrence;
                }else{
                    drawAncle +=  ancleDiffrence;
                }
            }


            affineTransform.setToRotation(drawAncle,getCenterX(),getCenterY());



            sprit--;

        }else if(moveLeft && xPosition > 0 && sprit > 0){

            winkel = map.getWinkel( getCenterX()-5);



            xPosition -=  Math.cos(winkel) * xDifferece * (1 - (Math.abs(drawAncle - winkel)));

            if(Math.abs(drawAncle - winkel)<ancleDiffrence){
                drawAncle = winkel;

            }else{
                if(drawAncle > winkel){
                    drawAncle -= ancleDiffrence;
                }else{
                    drawAncle +=  ancleDiffrence;
                }
            }


            affineTransform.setToRotation(drawAncle,getCenterX(),getCenterY());

            sprit--;


        }


        if(map.getHeight((int) getCenterX()) != getCenterY()) {

            double x = getCenterX();

            winkel = map.getWinkel(x);
            drawAncle = winkel;
            yPosition = map.getHeight(x) - height/2;

            affineTransform.setToRotation(drawAncle,x,getCenterY());

        }


        moveRight = false;
        moveLeft = false;



    }

    //DRAW

    public void drawUi(Graphics2D g2d,GameModel model) {
        setPolyPoints(model);

        if(isSelected()) {
            g2d.setColor(new Color(255, 255, 255, 125));

            g2d.setTransform(getAffineTransform());

            g2d.fillOval((int) getCenter().getX() - model.getHeight() / 8, (int) ((int) getCenter().getY() - model.getHeight() / (double) 8 - height / 2), model.getHeight() / 4, model.getHeight() / 4);

            if (isShootready()) {



                g2d.fillPolygon(getPolyXPoints(), getPolyYPoints(), 3);
                g2d.fillPolygon(getxPointsSmall(), getyPointsSmall(), 3);
            }
        }
    }

    public void draw(Graphics2D g2d, int art){
        AffineTransform old = new AffineTransform();

        Point2D rohrpoint = affineTransform.transform(new Point2D.Double(xPosition +width/2,yPosition),null);

        switch(art){
            case 0:
                AffineTransform rohr = new AffineTransform();

                if(isOrientationRight()) {
                    rohr.rotate(rohrwinkel, rohrpoint.getX(), rohrpoint.getY());

                }else{
                    rohr.rotate(Math.PI + rohrwinkel, rohrpoint.getX(), rohrpoint.getY());
                }
                g2d.setTransform(rohr);
                g2d.drawImage(rohrGreen, (int) (rohrpoint.getX()), (int) (rohrpoint.getY() - height/20), (int) (width/1.5),(int)(width/10),null);
                g2d.setTransform(affineTransform);
                g2d.drawImage(image,(int)xPosition, (int) (yPosition - height/2),(int)width,(int)height,null);
                break;
            case 1:
                AffineTransform rohr1 = new AffineTransform();

                if(isOrientationRight()) {
                    rohr1.rotate(rohrwinkel, rohrpoint.getX(), rohrpoint.getY());

                }else{
                    rohr1.rotate(Math.PI + rohrwinkel, rohrpoint.getX(), rohrpoint.getY());
                }
                g2d.setTransform(rohr1);
                g2d.drawImage(rohrBlue, (int) (rohrpoint.getX()), (int) (rohrpoint.getY() - height/20), (int) (width/1.5),(int)(width/10),null);
                g2d.setTransform(affineTransform);
                g2d.drawImage(imageBlue,(int)xPosition, (int) (yPosition - height/2),(int)width,(int)height,null);
                break;
            case 2:

                AffineTransform rohr2 = new AffineTransform();

                if(isOrientationRight()) {
                    rohr2.rotate(rohrwinkel, rohrpoint.getX(), rohrpoint.getY());

                }else{
                    rohr2.rotate(Math.PI + rohrwinkel, rohrpoint.getX(), rohrpoint.getY());
                }
                g2d.setTransform(rohr2);
                g2d.drawImage(rohrRed, (int) (rohrpoint.getX()), (int) (rohrpoint.getY() - height/20), (int) (width/1.5),(int)(width/10),null);
                g2d.setTransform(affineTransform);
                g2d.drawImage(imageRed,(int)xPosition, (int) (yPosition - height/2),(int)width,(int)height,null);
                break;
            case 3:
                g2d.setTransform(affineTransform);
                g2d.drawImage(imageDark,(int)xPosition, (int) (yPosition - height/2),(int)width,(int)height,null);
                break;
        }

        g2d.setTransform(old);




        for(Screentext screentext : strings) {

            if(screentext.getArt() == 2){
                screentext.setX((int) (getBulletspawn().getX()));
                screentext.setY((int) (getBulletspawn().getY() - 65));
                screentext.drawName(g2d);
            }else {

                screentext.drawText(g2d, (int) width);
            }

            if(screentext.getTime() == 0){
                strings.remove(screentext);
            }else{
                screentext.subTime();
            }
        }


        g2d.setColor(Color.GREEN);
        g2d.fillRect((int)(getBulletspawn().getX() - width/2),(int)(getBulletspawn().getY() - width/1.3),(int)(width/maxLeben * leben), (int) (width/8));

        //g2d.setColor(Color.RED);
        //g2d.drawRect((int)xPosition,(int)yPosition,(int)width,(int)height);
        //g2d.drawRect((int)(xPosition + width/2),collisionMap.getMapHeight((int)(xPosition + width/2)),1,1);
        //g2d.setColor(Color.orange);
        //g2d.drawLine((int)getCenterX(),(int)yPosition, (int) targetX, (int) targetY);

    }

    //GETTTER

    public boolean isHit(int xt, int yt){
        Shape hit = new Rectangle2D.Double((int)xPosition,(int)yPosition - height/2,(int)width,(int)height);



        hitbox = affineTransform.createTransformedShape(hit);

        return hitbox.contains(new Point2D.Double(xt, yt));
    }

    public boolean isSelected() {
        return selected;
    }

    private boolean isShootready() {
        return shootready;
    }

    public boolean isOrientationRight(){
        return orientationRight;
    }

    public Point2D getBulletspawn(){
        return affineTransform.transform(new Point2D.Double(getCenterX(),yPosition + height/4 - height/2),null);
    }

    public String getName() {
        return name;
    }

    public int getMaxSprit() {
        return maxSprit;
    }

    public double getyPosition() {
        return yPosition;
    }

    public double getxPosition() {
        return xPosition;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getXp() {
        return xp;
    }

    public double getWinkel() {
        return winkel;
    }

    public int getLeben() {
        return leben;
    }

    public Shape getHitbox() {

        return hitbox;
    }

    public double getRohrWinkel() {
        return rohrwinkel;
    }

    public double getShotstrength() {
        return shotstrength;
    }

    private int[] getPolyXPoints(){
        return xPoints;
    }

    private int[] getPolyYPoints(){
        return yPoints;
    }

    private int[] getxPointsSmall() {
        return xPointsSmall;
    }

    private int[] getyPointsSmall() {
        return yPointsSmall;
    }

    public double getCenterX(){
        return xPosition + width/2;
    }

    private double getCenterY(){
        return yPosition + height/2;
    }

    public Point2D getCenter(){
        double x = xPosition + width/2;
        double y = yPosition + height/2;
        return affineTransform.transform(new Point2D.Double(x,y),null);
    }

    public int getSprit() {
        return sprit;
    }

    public AffineTransform getAffineTransform() {
        return affineTransform;
    }

    public int getMaxLeben() {
        return maxLeben;
    }

    //SETTER

    public void setLeben(int i) {
        leben = i;
    }

    public void setxPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    public void setyPosition(double yPosition) {
        this.yPosition = yPosition;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void setShootready(boolean shootready) {
        this.shootready = shootready;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setPolyPoints(GameModel model){

        //erzeugen der weißen Dreiecke

        int x = (mouseX );
        int y = (int) (mouseY + height/2);





        if(new Point2D.Double(tempXPoint,tempYPoint).distance(new Point2D.Double(x,y)) > model.getHeight()/8){
            double scale = (model.getHeight()/(double)8)/new Point2D.Double(tempXPoint,tempYPoint).distance(new Point2D.Double(x,y));
            x -= tempXPoint;
            x = (int)(x * scale);
            x += tempXPoint;
            y -= tempYPoint;
            y = (int)(y * scale);
            y += tempYPoint;
        }

        shotstrength = new Point2D.Double(tempXPoint,tempYPoint).distance(x,y)/(model.getHeight()/(double)8);


        rohrwinkel = drawAncle + Math.atan((tempYPoint - y)/(double)(tempXPoint - x));

        orientationRight = (tempXPoint -x) < 0;

        int tempx = x-tempXPoint;
        xPoints = new int[]{(int) getCenter().getX(),(int)(getCenter().getX() +tempx - (tempYPoint - y)/(model.getHeight()/120)), (int)(getCenter().getX() +tempx + (tempYPoint - y)/ (model.getHeight()/(double)120))};
        xPointsSmall = new int[]{(int) getCenter().getX(),(int)(getCenter().getX() +tempx - (tempYPoint - y)/(model.getHeight()/45)), (int)(getCenter().getX() +tempx + (tempYPoint - y)/ (model.getHeight()/(double)45))};



        int tempy = y-tempYPoint;
        yPoints = new int[]{(int) ((int)getCenter().getY() - height/2), (int)(getCenter().getY()+tempy - (x - tempXPoint) / (double)(model.getHeight()/120) - height/2), (int)(getCenter().getY()+tempy + (x - tempXPoint) /(double) (model.getHeight()/120) - height/2)};
        yPointsSmall = new int[]{(int) ((int)getCenter().getY() - height/2), (int)(getCenter().getY()+tempy - (x - tempXPoint) / (double)(model.getHeight()/45) - height/2), (int)(getCenter().getY()+tempy + (x - tempXPoint) /(double) (model.getHeight()/45) - height/2)};

    }

    public void setTemps(){
        tempYPoint = (int)getCenter().getY();
        tempXPoint = (int)getCenter().getX();
    }

    //SCREENTEXT

    private class Screentext{

        //Text on screen

        private String text;
        private int x,y;
        private int time,art;
        private Color foreground,background;



        Screentext(String text, int x, int y,int art) {
            this.text = text;
            this.x = x;
            this.y = y;
            this.art = art;

            switch(art){
                case 0:time = 200;foreground = Color.WHITE;background = new Color(0,200,200,200);break;
                case 1:time = 200;foreground = Color.RED;background = Color.BLACK;break;
                case 2:time = 1;foreground = Color.WHITE;break;
            }
        }

        public void drawName(Graphics2D g2d){

            Font font = new Font("Calibri",Font.BOLD, (int) ((int) (height)/1.2));

            FontMetrics metrics = g2d.getFontMetrics(font);
            // Determine the X coordinate for the text
            int xt = x - (metrics.stringWidth(text)) / 2;


            g2d.setColor(foreground);
            g2d.setFont(font);
            g2d.drawString(text,xt,y);
        }

        public void drawText(Graphics2D g2d,int width){



            g2d.setColor(background);
            g2d.setFont(new Font("Calibri",Font.BOLD, (int) (height * 1.1)));
            g2d.drawString(text, (int) (x- height*0.05), (float) (y + height * 0.05));

            g2d.setColor(foreground);
            g2d.setFont(new Font("Calibri",Font.BOLD, (int) (height)));
            g2d.drawString(text,x,y);
        }

        public String getText() {
            return text;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getArt() {
            return art;
        }

        public void subTime() {
            if (art == 0 || art == 1) {
                time--;
            }
        }

        public int getTime() {
            return time;
        }
    }

    public static String getPanzerName(int level){
        switch (level){
            case 1:return "Normal Tank";
            case 2:return "Light Tank";
            case 3:return "Heavy Tank";
            default: return "null";
        }
    }

    public static int getPanzerLeben(int level){
        switch (level){
            case 1:return 100;
            case 2:return 100;
            case 3:return 150;
            default: return 0;
        }
    }

    public static int getPanzerSprit(int level){
        switch (level){
            case 1:return 300;
            case 2:return 500;
            case 3:return 300;
            default: return 0;
        }
    }

    public static BufferedImage getPanzerImage(int level){
        switch (level){
            case 1:return Var.panzerNormalGanz;
            case 2:return Var.heavyPanzerGanz;
            case 3:return Var.lightPanzerGanz;
            default: return null;
        }
    }
}
