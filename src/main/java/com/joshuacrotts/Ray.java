package com.joshuacrotts;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Ray {

    /**
     *
     */
    private final Line2D.Double line;

    /**
     *
     */
    private Color projectionColor;

    /**
     *
     */
    private BufferedImage projectionImage;

    /**
     *
     */
    private double distance;

    /**
     *
     */
    private final double angle;

    public Ray(final Line2D.Double line, BufferedImage image, final double angle) {
        // A distance of 0 implies that it's an infinite ray.
        this(line, image, angle, 0);
    }

    public Ray(final Line2D.Double line, final BufferedImage image, final double angle, final double distance) {
        this.line = line;
        this.projectionImage = image;
        this.angle = angle;
        this.distance = distance;
    }

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

    public BufferedImage getProjectionImage() {
        return projectionImage;
    }

    public void setProjectionImage(BufferedImage projectionImage) {
        this.projectionImage = projectionImage;
    }
}
