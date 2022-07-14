package com.joshuacrotts.entity;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public abstract class RectangleObject2D extends CollidableEntity2D {

    /**
     *
     */
    private final double WIDTH;

    /**
     *
     */
    private final double HEIGHT;

    public RectangleObject2D(final double x, final double y, final double w, final double h) {
        super(x, y);
        this.WIDTH = w;
        this.HEIGHT = h;
    }

    @Override
    public void draw(final Graphics2D g2) {
        g2.setColor(Color.BLUE);
        g2.drawRect((int) this.getX(), (int) this.getY(), (int) this.WIDTH, (int) this.HEIGHT);
    }

    @Override
    public abstract IntersectionDataPair<Point2D.Double, ?> intersectionPt(final Line2D.Double ray);

    public double getWidth() {
        return this.WIDTH;
    }

    public double getHeight() {
        return this.HEIGHT;
    }
}
