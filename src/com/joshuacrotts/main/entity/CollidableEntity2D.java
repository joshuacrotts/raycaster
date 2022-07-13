package com.joshuacrotts.main.entity;

import com.joshuacrotts.main.entity.color.ColorWallSegment;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public abstract class CollidableEntity2D extends Entity2D {

    private ArrayList<ColorWallSegment> LINE_SEGMENTS;

    public CollidableEntity2D(final double x, final double y) {
        super(x, y);
    }

    public abstract void draw(final Graphics2D g2);

    public abstract IntersectionDataPair intersectionPt(final Line2D.Double rayLine);

    public void setLineSegments(final ArrayList<ColorWallSegment> lineSegments) {
        this.LINE_SEGMENTS = lineSegments;
    }

    public ArrayList<ColorWallSegment> getLineSegments() {
        assert LINE_SEGMENTS != null;
        return this.LINE_SEGMENTS;
    }
}
