package com.joshuacrotts.entity;

import java.awt.geom.Point2D;

public class IntersectionDataPair {

    /**
     *
     */
    private final Point2D.Double POINT;

    /**
     *
     */
    private final EntityData DATA;

    public IntersectionDataPair(final Point2D.Double point, final EntityData item) {
        this.POINT = point;
        this.DATA = item;
    }

    public Point2D.Double getPoint() {
        return this.POINT;
    }

    public EntityData getData() {
        return this.DATA;
    }
}
