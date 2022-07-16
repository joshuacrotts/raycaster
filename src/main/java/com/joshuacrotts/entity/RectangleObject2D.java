package com.joshuacrotts.entity;

import java.awt.*;
import java.awt.geom.Line2D;

public abstract class RectangleObject2D extends CollidableEntity2D {

    public RectangleObject2D(final double x, final double y, final double w, final double h) {
        super(x, y, w, h);
    }

    @Override
    public void draw(final Graphics2D g2) {
        g2.setColor(Color.BLUE);
        g2.drawRect((int) this.getX(), (int) this.getY(), (int) this.getWidth(), (int) this.getHeight());
    }

    @Override
    public abstract IntersectionDataPair intersectionPt(final Line2D.Double ray);
}
