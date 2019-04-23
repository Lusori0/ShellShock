package Model;

import Window.MyWindow;

import java.awt.*;
import java.awt.geom.Point2D;

public class GameMap {

    private int[] mapX,mapY;
    private int genauigkeit;
    private GameModel gameModel;
    private Polygon map;
    private double xDifference;
    private double winkelgenauigkeit;


    public GameMap(GameModel gameModel){
        genauigkeit = 100;
        this.gameModel = gameModel;

        mapX = new int[genauigkeit + 2];
        mapY = new int[genauigkeit + 2];

        mapX[genauigkeit + 1] = 0;
        mapX[genauigkeit] = MyWindow.WIDTH;

        mapY[genauigkeit + 1] = gameModel.getHeight();
        mapY[genauigkeit] = gameModel.getHeight();

        for(int i = 0;i < genauigkeit;i++){
            mapX[i] = MyWindow.WIDTH/genauigkeit * (i);
            mapY[i] = (int) (gameModel.getHeight()/(double)2 + Math.random() * 100);
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
        }else{
            return true;
        }
    }

    public void explosion(int x, int y, int sizePara, int damage) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                int size = (int) (sizePara/xDifference);

                for(int i = 0; i < size;i++){
                    if(Math.abs(mapY[(int) (x/xDifference - size/2 + i)] - y) < (size/(double)2 * xDifference)) {
                        double xtemp = Math.abs(size / 2 - i) * xDifference / (size / (double) 2 * xDifference);
                        double ytemp = Math.abs((mapY[(int) (x / xDifference - size / 2 + i)] - y)) / (size / (double) 2 * xDifference);
                        mapY[(int) (x / xDifference - size / 2 + i)] += ((size / 2 * xDifference) * Math.sqrt(1 - Math.pow(xtemp, 2))) * Math.sqrt(1 - Math.pow(ytemp, 2));
                        if (mapY[(int) (x / xDifference - size / 2 + i)] > gameModel.getHeight() * 0.9) {
                            mapY[(int) (x / xDifference - size / 2 + i)] = (int) (gameModel.getHeight() * 0.9);
                        }
                    }
                }

                map = new Polygon(mapX,mapY,genauigkeit+2);

                gameModel.movePanzerDown(x,y, (int) (sizePara),damage);


            }
        });
        thread.start();

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
}
