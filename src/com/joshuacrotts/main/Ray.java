package com.joshuacrotts.main;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Ray {

    /**
     *
     */
    private Line2D.Double line;

    /**
     *
     */
    private Color projectionColor;

    /**
     *
     */
    private double distance;

    /**
     *
     */
    private double angle;

    public Ray(final Line2D.Double line, final Color color, final double angle) {
        // A distance of 0 implies that it's an infinite ray.
        this(line, color, angle, 0);
    }

    public Ray(final Line2D.Double line, final Color color, final double angle, final double distance) {
        this.line = line;
        this.projectionColor = color;
        this.angle = angle;
        this.distance = distance;
    }

    public void draw(final Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.draw(this.line);
    }

    public void setDistance(final double dist) {
        this.distance = dist;
    }

    public double getDistance() {
        return this.distance;
    }

    public double getAngle() { return this.angle; }

    public Line2D.Double getLine() {
        return this.line;
    }

    public Color getProjectionColor() { return this.projectionColor; }

    public void setProjectionColor(final Color color) {
        this.projectionColor = color;
    }
}
