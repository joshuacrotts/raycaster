package com.joshuacrotts.entity;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public abstract class CollidableEntity2D extends Entity2D {

    /**
     *
     */
    private ArrayList<? extends Line2D.Double> lineSegments;

    public CollidableEntity2D(final double x, final double y, final double w, final double h) {
        super(x, y, w, h);
    }

    public abstract void draw(final Graphics2D g2);

    public abstract IntersectionDataPair intersectionPt(final Line2D.Double rayLine);

    public Rectangle2D.Double getBoundingBox() {
        return new Rectangle2D.Double(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public ArrayList<? extends Line2D.Double> getLineSegments() {
        assert this.lineSegments != null && !this.lineSegments.isEmpty();
        return this.lineSegments;
    }

    public void setLineSegments(final ArrayList<? extends Line2D.Double> lineSegments) {
        this.lineSegments = lineSegments;
    }
}
