package com.joshuacrotts.entity;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public abstract class CollidableEntity2D extends Entity2D {

    /**
     *
     */
    private ArrayList<? extends Line2D.Double> LINE_SEGMENTS;

    public CollidableEntity2D(final double x, final double y) {
        super(x, y);
    }

    public abstract void draw(final Graphics2D g2);

    public abstract IntersectionDataPair intersectionPt(final Line2D.Double rayLine);

    public ArrayList<? extends Line2D.Double> getLineSegments() {
        assert this.LINE_SEGMENTS != null && !this.LINE_SEGMENTS.isEmpty();
        return this.LINE_SEGMENTS;
    }

    public void setLineSegments(final ArrayList<? extends Line2D.Double> lineSegments) {
        this.LINE_SEGMENTS = lineSegments;
    }
}
