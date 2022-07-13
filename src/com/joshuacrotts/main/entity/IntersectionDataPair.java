package com.joshuacrotts.main.entity;

import java.awt.*;
import java.awt.geom.Point2D;

public class IntersectionDataPair {

    /**
     *
     */
    private final Point2D.Double POINT;

    /**
     *
     */
    private final Color COLOR;

    public IntersectionDataPair(final Point2D.Double point, final Color color) {
        this.POINT = point;
        this.COLOR = color;
    }

    public Point2D.Double getPoint() {
        return this.POINT;
    }

    public Color getColor() {
        return this.COLOR;
    }
}
