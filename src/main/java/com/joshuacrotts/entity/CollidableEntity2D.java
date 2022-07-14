package com.joshuacrotts.entity;

import com.joshuacrotts.Ray;
import com.joshuacrotts.RaycasterUtils;
import com.joshuacrotts.entity.color.ColorWallSegment;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
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

    public abstract IntersectionDataPair<Point2D.Double,?> intersectionPt(final Line2D.Double rayLine);

    public void setLineSegments(final ArrayList<? extends Line2D.Double> lineSegments) {
        this.LINE_SEGMENTS = lineSegments;
    }

    public ArrayList<? extends Line2D.Double> getLineSegments() {
        assert this.LINE_SEGMENTS != null && !this.LINE_SEGMENTS.isEmpty();
        return this.LINE_SEGMENTS;
    }
}
