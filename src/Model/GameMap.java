package Model;

import Panzer.Panzer;
import Window.MyWindow;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class GameMap {

    private int[] mapX,mapY;
    private int genauigkeit;
    private GameModel gameModel;
    private Polygon map;
    private double xDifference;
    private double winkelgenauigkeit;


    public GameMap(GameModel gameModel){
        genauigkeit = 400;
        this.gameModel = gameModel;

        mapX = new int[genauigkeit + 2];
        mapY = new int[genauigkeit + 2];

        mapX[genauigkeit + 1] = 0;
        mapX[genauigkeit] = MyWindow.WIDTH;

        mapY[genauigkeit + 1] = gameModel.getHeight();
        mapY[genauigkeit] = gameModel.getHeight();

        for(int i = 0;i < genauigkeit;i++){
            mapX[i] = (int) (MyWindow.WIDTH/(double)genauigkeit * (i));
            mapY[i] = (int) (gameModel.getHeight()/(double)2 + Math.sin(i * 0.1) * gameModel.getHeight()/8);
        }



        map = new Polygon(mapX,mapY,genauigkeit+2);

        xDifference = MyWindow.WIDTH/(double)genauigkeit;

        winkelgenauigkeit = 5;
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
        if(x > -MyWindow.WIDTH/10 && x < MyWindow.WIDTH * 1.1 && y > 0) {
            return map.contains(new Point2D.Double(x, y));
        }else if(y > 0){
            return true;
        }else{
            return false;
        }
    }

    public void explosion(int x, int y, int sizePara, int damage,Panzer herkunft) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                int size = (int) (sizePara/xDifference) * 2;





                for(int i = 0; i < size*2;i++){
                    int coord = (int) (x / xDifference - size + i);
                    if(coord >=0 && coord < mapX.length-2) {

                            double xtemp = Math.abs(size / 2 - i) * xDifference / (size / (double) 2 * xDifference);

                            double temp = size/(double)8;

                            double diffrence2 = Math.abs(Math.pow((i-size)/temp,4)) * 2;






                                if(mapY[coord] < y + size - diffrence2) {
                                    mapY[coord] = (int) (y+size - (diffrence2));
                                }



                            /*double ytemp = Math.abs((mapY[(int) (x / xDifference - size / 2 + i)] - y)) / (size / (double) 2 * xDifference);
                            mapY[(int) (x / xDifference - size / 2 + i)] += ((size / 2 * xDifference) * Math.sqrt(1 - Math.pow(xtemp, 2))) * Math.sqrt(1 - Math.pow(ytemp, 2));
                            if (mapY[(int) (x / xDifference - size / 2 + i)] > gameModel.getHeight() * 0.9) {
                                mapY[(int) (x / xDifference - size / 2 + i)] = (int) (gameModel.getHeight() * 0.9);
                            }*/

                    }
                }

                map = new Polygon(mapX,mapY,genauigkeit+2);



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
}
