package Panzer;

import Model.GameMap;
import Model.GameModel;
import Window.*;


import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.LinkedList;

public abstract class Panzer {

    BufferedImage image,imageRed,imageBlue,rohrGreen,rohrRed,rohrBlue;

    private double xPosition,yPosition;

    private double width,height,winkel,shotstrength,rohrwinkel,drawAncle;

    private AffineTransform affineTransform;

    private Shape hitbox;

    private int accuracy;

    private boolean selected,shootready,orientationRight,moveRight,moveLeft;

    private int[] xPoints,yPoints,xPointsSmall,yPointsSmall;
    private int tempXPoint = 0,tempYPoint = 0,mouseX = 0,mouseY = 0;
    private double targetX,targetY;
    private int leben,sprit,maxSprit;

    private LinkedList<Screentext> strings = new LinkedList<>();

    public Panzer(BufferedImage image,BufferedImage rohr,int leben,int maxSprit){

        affineTransform = new AffineTransform();

        this.image = image;
        this.rohrGreen = rohr;

        rohrBlue = new BufferedImage(rohrGreen.getWidth(),rohrGreen.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        rohrRed = new BufferedImage(rohrGreen.getWidth(),rohrGreen.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);

        imageRed = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);
        imageBlue = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);

        for(int x = 0;x < image.getWidth();x++){
            for(int y = 0; y < image.getHeight();y++){
                Color c = new Color(image.getRGB(x,y),true);
                imageRed.setRGB(x,y,new Color(c.getGreen(),c.getRed(),c.getBlue(),c.getAlpha()).getRGB());
                imageBlue.setRGB(x,y,new Color(c.getRed(),c.getBlue(),c.getGreen(),c.getAlpha()).getRGB());
            }
        }

        for(int x = 0;x < rohrGreen.getWidth();x++){
            for(int y = 0; y < rohrGreen.getHeight();y++){
                Color c = new Color(rohrGreen.getRGB(x,y),true);
                rohrRed.setRGB(x,y,new Color(c.getGreen(),c.getRed(),c.getBlue(),c.getAlpha()).getRGB());
                rohrBlue.setRGB(x,y,new Color(c.getRed(),c.getBlue(),c.getGreen(),c.getAlpha()).getRGB());
            }
        }


        xPosition = Math.random() *1000;
        yPosition = 30;
        targetX = getCenterX();
        targetY = yPosition;
        width = MyWindow.WIDTH/(double)60;
        height = width/2;
        accuracy = (int) (width/8);

        this.leben = leben;

        xPoints = new int[3];
        yPoints = new int[3];

        xPointsSmall = new int[3];
        yPointsSmall = new int[3];

        this.maxSprit = maxSprit;
        sprit = maxSprit;



        Shape hit = new Rectangle2D.Double((int)xPosition,(int)yPosition - height/2,(int)width,(int)height);

        hitbox = affineTransform.createTransformedShape(hit);
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
        }

        g2d.setTransform(old);




        for(Screentext screentext : strings) {

            screentext.drawText(g2d, (int) width);

            if(screentext.getTime() == 0){
                strings.remove(screentext);
            }else{
                screentext.subTime();
            }
        }
        //g2d.setColor(Color.RED);
        //g2d.drawRect((int)xPosition,(int)yPosition,(int)width,(int)height);
        //g2d.drawRect((int)(xPosition + width/2),collisionMap.getMapHeight((int)(xPosition + width/2)),1,1);
        //g2d.setColor(Color.orange);
        //g2d.drawLine((int)getCenterX(),(int)yPosition, (int) targetX, (int) targetY);

    }

    public void move(GameMap map){

        double xDifferece = width/40;

        double ancleDiffrence;

        accuracy = (int)(1 + (width/8) - (drawAncle/(Math.PI/2) * (width/8)));

        if(moveRight && xPosition < MyWindow.WIDTH-width*1.5 && sprit > 0){

            winkel = map.getWinkel(getCenterX());

            xPosition += Math.cos(winkel) * xDifferece;

            yPosition += Math.sin(winkel) * xDifferece;

            affineTransform.setToRotation(winkel,getCenterX(),getCenterY());

            sprit--;

        }else if(moveLeft && xPosition > 0 && sprit > 0){

            winkel = map.getWinkel( getCenterX());

            xPosition -= Math.cos(winkel) * xDifferece;

            yPosition -= Math.sin(winkel) * xDifferece;

            affineTransform.setToRotation(winkel,getCenterX(),getCenterY());

            sprit--;


        }else {


           if(map.getHeight((int) getCenterX()) != getCenterY()) {

                double x = getCenterX();

                winkel = map.getWinkel(x);
                yPosition = map.getHeight(x) - height/2;

                affineTransform.setToRotation(winkel,x,getCenterY());

            }
        }

        moveRight = false;
        moveLeft = false;

        Shape hit = new Rectangle2D.Double((int)xPosition,(int)yPosition - height/2,(int)width,(int)height);

        hitbox = affineTransform.createTransformedShape(hit);
    }

    public void resetSprit(){
        sprit = maxSprit;
    }

    public int getSprit() {
        return sprit;
    }

    public void moveNotTurn(GameMap map){
        if(map.getHeight((int) getCenterX()) != getCenterY()) {

            double x = getCenterX();

            winkel = map.getWinkel(x);
            yPosition = map.getHeight(x) - height/2;

            affineTransform.setToRotation(winkel,x,getCenterY());

        }
    }


    public double getCenterX(){
        return xPosition + width/2;
    }

    private double getCenterY(){
        return yPosition + height/2;
    }

    private Point2D getCenterXExact(){
        return affineTransform.transform(new Point2D.Double(xPosition+width/2,yPosition + height),null);
    }

    public Point2D getCenter(){
        double x = xPosition + width/2;
        double y = yPosition + height/2;
        return affineTransform.transform(new Point2D.Double(x,y),null);
    }


    public boolean isHit(int xt, int yt){
        Shape hit = new Rectangle2D.Double((int)xPosition,(int)yPosition - height/2,(int)width,(int)height);

        hitbox = affineTransform.createTransformedShape(hit);

        if(hitbox.contains(new Point2D.Double(xt,yt))){

            return true;
        }else{
            return false;
        }
    }

    public AffineTransform getAffineTransform() {
        return affineTransform;
    }

    public void changeRohr(int x, int y, GameModel model){
        try {
            mouseX = (int) affineTransform.createInverse().transform(new Point2D.Double(x,y),null).getX();
            mouseY = (int) affineTransform.createInverse().transform(new Point2D.Double(x,y),null).getY();
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
        }

        setPolyPoints(model);
    }

    public Point2D getBulletspawn(){
        return affineTransform.transform(new Point2D.Double(getCenterX(),yPosition + height/4 - height/2),null);
    }

    public void setPolyPoints(GameModel model){

        int x = (int) (mouseX );
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


        rohrwinkel = winkel + Math.atan((tempYPoint - y)/(double)(tempXPoint - x));

        orientationRight = (tempXPoint -x) < 0;

        int tempx = x-tempXPoint;
        xPoints = new int[]{(int) getCenter().getX(),(int)(getCenter().getX() +tempx - (tempYPoint - y)/(model.getHeight()/80)), (int)(getCenter().getX() +tempx + (tempYPoint - y)/ (model.getHeight()/(double)80))};
        xPointsSmall = new int[]{(int) getCenter().getX(),(int)(getCenter().getX() +tempx - (tempYPoint - y)/(model.getHeight()/30)), (int)(getCenter().getX() +tempx + (tempYPoint - y)/ (model.getHeight()/(double)30))};



        int tempy = y-tempYPoint;
        yPoints = new int[]{(int) ((int)getCenter().getY() - height/2), (int)(getCenter().getY()+tempy - (x - tempXPoint) / (double)(model.getHeight()/80) - height/2), (int)(getCenter().getY()+tempy + (x - tempXPoint) /(double) (model.getHeight()/80) - height/2)};
        yPointsSmall = new int[]{(int) ((int)getCenter().getY() - height/2), (int)(getCenter().getY()+tempy - (x - tempXPoint) / (double)(model.getHeight()/30) - height/2), (int)(getCenter().getY()+tempy + (x - tempXPoint) /(double) (model.getHeight()/30) - height/2)};

    }

    public void setTemps(){
        tempYPoint = (int)getCenter().getY();
        tempXPoint = (int)getCenter().getX();
    }

    public boolean isOrientationRight(){
        return orientationRight;
    }

    public int[] getPolyXPoints(){
        return xPoints;
    }

    public int[] getPolyYPoints(){
        return yPoints;
    }

    public int[] getxPointsSmall() {
        return xPointsSmall;
    }

    public int[] getyPointsSmall() {
        return yPointsSmall;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isShootready() {
        return shootready;
    }

    public void setShootready(boolean shootready) {
        this.shootready = shootready;
    }

    public double getRohrWinkel() {
        return rohrwinkel;
    }

    public double getShotstrength() {
        return shotstrength;
    }

    public Shape getHitbox() {
        return hitbox;
    }

    public void schaden(int damage, int art) {
        leben -= damage;
        int x = (int) (xPosition - width + Math.random() * width * 3);
        int y = (int) (yPosition + height - Math.random() * 2 * height);

        if(art == 0) {
            strings.add(new Screentext(String.valueOf(damage), x, y, 0));
        }else{
            strings.add(new Screentext(String.valueOf(damage), x, y, 1));
        }



    }

    public int getLeben() {
        return leben;
    }

    public void drawUi(Graphics2D g2d, int x, int y,GameModel model) {
        g2d.setColor(new Color(255, 255, 255, 125));

        g2d.setTransform(getAffineTransform());

        g2d.fillOval((int) getCenter().getX() - model.getHeight() / 8, (int) ((int) getCenter().getY() - model.getHeight() / (double)8 - height/2), model.getHeight() / 4, model.getHeight() / 4);

        if (isShootready()) {
            setPolyPoints(model);



            g2d.fillPolygon(getPolyXPoints(), getPolyYPoints(), 3);
            g2d.fillPolygon(getxPointsSmall(),getyPointsSmall(),3);
        }
    }

    public void setMove(){
        moveRight = MyKeys.right;
        moveLeft = MyKeys.left;
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    private class Screentext{
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
            }
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

        public void subTime() {
            if (art == 0 || art == 1) {
                time--;
            }
        }

        public int getTime() {
            return time;
        }
    }
}
