package com.joshuacrotts.entity;

import java.awt.*;
import java.awt.geom.Point2D;

public class IntersectionDataPair<P extends Point2D.Double, D> {

    /**
     *
     */
    private final P POINT;

    /**
     *
     */
    private final D ITEM;

    public IntersectionDataPair(final P point, final D item) {
        this.POINT = point;
        this.ITEM = item;
    }

    public P getPoint() {
        return this.POINT;
    }

    public D getData() {
        return this.ITEM;
    }
}
