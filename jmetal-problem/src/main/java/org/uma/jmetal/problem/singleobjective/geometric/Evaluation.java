package org.uma.jmetal.problem.singleobjective.geometric;

import java.awt.*;

/**
 * Created by root on 07-12-20.
 */
public class Evaluation implements Comparable {

    Point point;
    Integer distance;

    public Evaluation(Point point, Integer distance) {
        this.point = point;
        this.distance = distance;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(Object o) {
        return  ((Evaluation) o).getDistance() - this.getDistance();
    }

    @Override
    public String toString() {
        return "Evaluation{" +
                "point= (" + point.getX() + "," + point.getY() + ")" +
                ", distance=" + distance +
                '}';
    }
}
