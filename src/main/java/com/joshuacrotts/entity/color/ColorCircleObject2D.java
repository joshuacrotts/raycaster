package com.joshuacrotts.entity.color;

import com.joshuacrotts.entity.CircleObject2D;
import com.joshuacrotts.entity.Colorable;
import com.joshuacrotts.entity.EntityData;
import com.joshuacrotts.entity.IntersectionDataPair;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class ColorCircleObject2D extends CircleObject2D implements Colorable {

    /**
     * Radius of the circle.
     */
    private final Color COLOR;

    public ColorCircleObject2D(final double x, final double y, double r, final Color color) {
        super(x, y, r);
        this.COLOR = color;
    }

    @Override
    public IntersectionDataPair intersectionPt(final Line2D.Double ray) {
        if (ray.ptSegDist(this.getX() + this.getRadius(), this.getY() + this.getRadius()) <= this.getRadius()) {
            return new IntersectionDataPair(this.findClosestIntersection(this.getX() + this.getRadius(), this.getY() + this.getRadius(), this.getRadius(),
                    (Point2D.Double) ray.getP1(), (Point2D.Double) ray.getP2()), new EntityData(this.getColor()));
        }
        return new IntersectionDataPair(null, new EntityData(this.getColor()));
    }

    @Override
    public Color getColor() {
        return this.COLOR;
    }
}
