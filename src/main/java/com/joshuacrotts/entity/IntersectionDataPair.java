package com.joshuacrotts.entity;

import java.awt.geom.Point2D;

public class IntersectionDataPair {

    /**
     * Point of intersection (i.e., where the collision occurs).
     */
    private final Point2D.Double POINT;

    /**
     * Data associated with this intersection point. Useful for the projection stage (can be a BI or a color).
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
