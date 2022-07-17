package com.joshuacrotts;

import com.joshuacrotts.entity.EntityData;

import java.awt.*;
import java.awt.geom.Line2D;

public class Ray {

    /**
     * Line behind this Ray; holds the position.
     */
    private Line2D.Double line;

    /**
     * EntityData associated with this Ray. To be useful in the projection stage.
     */
    private EntityData data;

    /**
     * Angle of this ray as cast from the Camera.
     */
    private double angle;

    /**
     * Length of this Ray from the start point to the end. 0 indicates an infinite ray.
     */
    private double distance;

    public Ray() {
        this(new Line2D.Double(), null, 0, Double.POSITIVE_INFINITY);
    }

    public Ray(final Line2D.Double line, final EntityData image, final double angle) {
        this(line, image, angle, Double.POSITIVE_INFINITY);
    }

    private boolean isXside=false;

    public Ray(final Line2D.Double line, final EntityData entityData, final double angle, final double distance) {
        this.line = line;
        this.data = entityData;
        this.angle = angle;
        this.distance = distance;
    }

    public boolean isX() {return this.isXside;}
    public void setXside(boolean b) {
        this.isXside=b;
    }

    public void draw(final Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.draw(this.line);
    }

    public void setRayCoordinates(double x1, double y1, double x2, double y2) {
        this.line.x1 = x1;
        this.line.y1 = y1;
        this.line.x2 = x2;
        this.line.y2 = y2;
    }

    public void setEndRayCoordinates(double x2, double y2) {
        this.line.x2 = x2;
        this.line.y2 = y2;
    }

    public Line2D.Double getLine() {
        return line;
    }

    public void setLine(Line2D.Double line) {
        this.line = line;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public EntityData getData() {
        return data;
    }

    public void setData(EntityData data) {
        this.data = data;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
