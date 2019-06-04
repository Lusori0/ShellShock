package Model;

import Panzer.Panzer;
import Views.GameLoop;
import Window.MyWindow;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class GameMap {

    private int[] mapX,mapY;
    private int genauigkeit;
    private GameModel gameModel;
    private Polygon map;
    private double xDifference;
    private double winkelgenauigkeit;

    private double[] rnd;


    public GameMap(GameModel gameModel,int art){
        genauigkeit = 400;
        this.gameModel = gameModel;

        mapX = new int[genauigkeit + 3];
        mapY = new int[genauigkeit + 3];

        mapX[genauigkeit + 2] = 0;
        mapX[genauigkeit + 1] = GameLoop.imgW;

        mapY[genauigkeit + 2] = GameLoop.imgH;
        mapY[genauigkeit + 1] = GameLoop.imgH;


        rnd = new double[8];

        for(int i = 0;i < 4;i++){
            rnd[i] = Math.random() * 0.1;
            rnd[i+4] = Math.random();
        }

        for(int i = 0;i <= genauigkeit;i++){
            mapX[i] = (int) (GameLoop.imgW/(double)genauigkeit * (i));

            switch(art){
                case 1:
                    mapY[i] = (int) (gameModel.getHeight()/(double)2 + Math.sin(i * 0.1) * gameModel.getHeight()/8);
                    break;
                case 2:
                    mapY[i] = (int) (gameModel.getHeight()/(double)2 + Math.sin(i * 0.01) * gameModel.getHeight()/8);
                    break;
                case 3:
                    mapY[i] = (int) (gameModel.getHeight()/(double)2 + Math.sin(i * 0.1) * gameModel.getHeight()/12 + i);
                    break;
                case 4:
                    mapY[i] = (int) (gameModel.getHeight()*0.8 - 1/(2000 + Math.pow(i - (genauigkeit - 2)/2.0,2)) * 1500000);
                    break;
                case 5:
                    mapY[i] = (int) (gameModel.getHeight()/(double)2 + Math.pow(Math.sin(i * 0.03),3) * gameModel.getHeight()/4);
                    break;
                case 6:
                    mapY[i] = (int)  (gameModel.getHeight()/2.0);// + gameModel.getHeight()*0.4);;
                    mapY[i] += Math.sin(i * (rnd[0])) * rnd[4] * gameModel.getHeight()*0.2;
                    mapY[i] += Math.sin(i * (rnd[1])) * rnd[5] * gameModel.getHeight()*0.2;
                    mapY[i] += Math.sin(i * (rnd[2])) * rnd[6] * gameModel.getHeight()*0.2;
                    mapY[i] += Math.sin(i * (rnd[3])) * rnd[7] * gameModel.getHeight()*0.2;


            }

        }



        map = new Polygon(mapX,mapY,genauigkeit+3);

        xDifference = GameLoop.imgW/(double)genauigkeit;

        winkelgenauigkeit = 2;
    }

    public Polygon getMap(){
        return map;
    }

    public double getHeight(double xCoord){
        if(xCoord%xDifference == 0){
            return mapY[(int) (xCoord/xDifference)];
        }else {
            double x1 = mapX[(int) (xCoord / xDifference)];
            double x2 = mapX[(int) (xCoord / xDifference + 1)];

            double y1 = mapY[(int) (xCoord / xDifference)];
            double y2 = mapY[(int) (xCoord / xDifference + 1)];

            return (y1 + (y1-y2)/(x1-x2) * (xCoord - x1));

        }
    }

    public double getWinkel(double xCoord){

        double yDiff = (getHeight((xCoord + winkelgenauigkeit)) - getHeight((xCoord - winkelgenauigkeit)));
        double xDiff = (xCoord + winkelgenauigkeit - (xCoord - winkelgenauigkeit));

        return Math.atan2(yDiff,xDiff);
    }

    public boolean isCollision(int x, int y) {
        if(x > -GameLoop.imgW/10 && x < GameLoop.imgW * 1.1 && y > 0) {
            return map.contains(new Point2D.Double(x, y));
        }else if(y > 0){
            return true;
        }else{
            return false;
        }
    }

    public void explosion(int x, int y, int sizeParat, int damage,Panzer herkunft) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                int sizePara = sizeParat/2;

                int size = (int) (sizePara/xDifference) * 2;


                for(int i = 0;i < size;i++){
                    int xCoord = (int)((x - size/2 * xDifference)/xDifference)  +  1 + i;


                    if(xCoord > 0 && xCoord < genauigkeit-2) {

                        double diffrence = Math.sqrt(Math.pow(sizePara, 2) - Math.pow(Math.abs(x - mapX[xCoord]), 2));

                        if (mapY[xCoord] < y - diffrence) {
                            mapY[xCoord] += diffrence * 2;
                        } else if (mapY[xCoord] < y + diffrence) {
                            mapY[xCoord] = (int) (y + diffrence);
                        }
                        if (mapY[xCoord] > GameLoop.imgH * 0.99) {
                            mapY[xCoord] = (int) (GameLoop.imgH*0.99);
                        }

                    }


                }

                map = new Polygon(mapX,mapY,genauigkeit+3);



                gameModel.movePanzerDown(x,y, (sizePara),damage,herkunft);


            }
        });
        thread.start();

        //TODO impact

    }

    public Point2D getHeighestPoint(int startX,int endX){

        Point2D returnValue = new Point2D.Double(0,gameModel.getHeight());

        for(int i = (int) (startX/xDifference); i < endX/xDifference; i++){
            if(mapY[i] < returnValue.getY()){
                returnValue = new Point2D.Double(mapX[i],mapY[i]);
            }
        }

        return returnValue;
    }

    public int[] getX(int start, int end) {
        int[] returnValues = new int[(int) ((end - start)/xDifference)+2];
        int temp = 0;
        for(int i = 0;i < mapX.length;i++){

                if (mapX[i] >= start && mapX[i] <= end) {
                    returnValues[temp] = mapX[i];
                    temp++;
                }

        }

        return returnValues;
    }

    public int[] getY(int start, int end) {
        int[] returnValues = new int[(int) ((end - start)/xDifference)+2];
        int temp = 0;
        for(int i = 0;i < mapX.length;i++){
                if (mapX[i] >= start && mapX[i] <= end) {
                    returnValues[temp] = mapY[i];
                    temp++;
                }

        }

        return returnValues;
    }

    public static BufferedImage getMapSmall(int art,GameModel gameModel){
        BufferedImage img = new BufferedImage(560,360,BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(getMap(art,gameModel),0,0,560,360,null);
        //TODO: Vergleiche mit PregameView Die Map bei art = 5 oder 6 sind jedes mal unterschiedlich!!
        return img;
    }

    public static BufferedImage getMap(int art,GameModel gameModel){
        BufferedImage image = new BufferedImage(2800,1800,BufferedImage.TYPE_4BYTE_ABGR);

        int genauigkeit = 400;

        int[] mapX = new int[genauigkeit + 2];
        int[] mapY = new int[genauigkeit + 2];

        mapX[genauigkeit + 1] = 0;
        mapX[genauigkeit] = GameLoop.imgW;

        mapY[genauigkeit + 1] = GameLoop.imgH;
        mapY[genauigkeit] = GameLoop.imgH;

        double[] rnd = new double[8];

        for(int i = 0;i < 4;i++){
            rnd[i] = Math.random() * 0.1;
            rnd[i+4] = Math.random();
        }

        for(int i = 0;i < genauigkeit;i++){
            mapX[i] = (int) (GameLoop.imgW/(double)genauigkeit * (i));

            switch(art){
                case 1:
                    mapY[i] = (int) (gameModel.getHeight()/(double)2 + Math.sin(i * 0.1) * gameModel.getHeight()/8);
                    break;
                case 2:
                    mapY[i] = (int) (gameModel.getHeight()/(double)2 + Math.sin(i * 0.01) * gameModel.getHeight()/8);
                    break;
                case 3:
                    mapY[i] = (int) (gameModel.getHeight()/(double)2 + Math.sin(i * 0.1) * gameModel.getHeight()/12 + i);
                    break;
                case 4:
                    mapY[i] = (int) (gameModel.getHeight()*0.8 - 1/(2000 + Math.pow(i - (genauigkeit - 2)/2.0,2)) * 1500000);
                    break;
                case 5:
                    mapY[i] = (int) (gameModel.getHeight()/(double)2 + Math.pow(Math.sin(i * 0.03),3) * gameModel.getHeight()/4);
                case 6:
                    mapY[i] = (int)  (gameModel.getHeight()/2.0);// + gameModel.getHeight()*0.4);;
                    mapY[i] += Math.sin(i * (rnd[0])) * rnd[4] * gameModel.getHeight()*0.2;
                    mapY[i] += Math.sin(i * (rnd[1])) * rnd[5] * gameModel.getHeight()*0.2;
                    mapY[i] += Math.sin(i * (rnd[2])) * rnd[6] * gameModel.getHeight()*0.2;
                    mapY[i] += Math.sin(i * (rnd[3])) * rnd[7] * gameModel.getHeight()*0.2;





            }

        }

        Polygon map = new Polygon(mapX,mapY,genauigkeit+2);

        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g2d.drawPolygon(map);

        return image;
    }
}
