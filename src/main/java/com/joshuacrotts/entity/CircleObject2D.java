package com.joshuacrotts.entity;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


public abstract class CircleObject2D extends CollidableEntity2D {

    /**
     * Radius of the circle.
     */
    private final double radius;

    public CircleObject2D(final double x, final double y, final double r) {
        super(x, y, r, r);
        this.radius = r;
    }

    @Override
    public void draw(final Graphics2D g2) {
        g2.setColor(Color.RED);
        g2.draw(new Ellipse2D.Double(this.getX(), this.getY(), this.getDiameter(), this.getDiameter()));
    }

    @Override
    public abstract IntersectionDataPair intersectionPt(final Line2D.Double ray);

    /**
     * @param cx
     * @param cy
     * @param radius
     * @param lineStart
     * @param lineEnd
     * @return
     */
    protected Point2D.Double findClosestIntersection(final double cx, final double cy, final double radius, final Point2D.Double lineStart, final Point2D.Double lineEnd) {
        Point2D.Double intersection1 = new Point2D.Double();
        Point2D.Double intersection2 = new Point2D.Double();
        int intersections = this.findLineCircleIntersections(cx, cy, radius, lineStart, lineEnd, intersection1, intersection2);
        if (intersections == 1) {
            return intersection1;
        }
        if (intersections == 2) {
            double dist1 = intersection1.distance(lineStart);
            double dist2 = intersection2.distance(lineStart);

            if (dist1 < dist2) {
                return intersection1;
            } else {
                return intersection2;
            }
        }
        return null;
    }

    // Find the points of intersection.
    protected int findLineCircleIntersections(final double cx, final double cy, final double radius,
                                              final Point2D.Double point1, final Point2D.Double point2,
                                              Point2D.Double intersection1, Point2D.Double intersection2) {
        double dx, dy, A, B, C, det, t;

        dx = point2.x - point1.x;
        dy = point2.y - point1.y;

        A = dx * dx + dy * dy;
        B = 2 * (dx * (point1.x - cx) + dy * (point1.y - cy));
        C = (point1.x - cx) * (point1.x - cx) + (point1.y - cy) * (point1.y - cy) - radius * radius;

        det = B * B - 4 * A * C;
        if ((A <= 0.000001) || (det < 0)) {
            // No real solutions.
            intersection1.x = intersection1.y = Double.NaN;
            intersection2.x = intersection2.y = Double.NaN;
            return 0;
        } else if (det == 0) {
            // One solution.
            t = -B / (2 * A);
            intersection1.x = point1.x + t * dx;
            intersection1.y = point1.y + t * dy;
            intersection2.x = intersection2.y = Double.NaN;
            return 1;
        } else {
            // Two solutions.
            t = (float) ((-B + Math.sqrt(det)) / (2 * A));
            intersection1.x = point1.x + t * dx;
            intersection1.y = point1.y + t * dy;
            t = (float) ((-B - Math.sqrt(det)) / (2 * A));
            intersection2.x = point1.x + t * dx;
            intersection2.y = point1.y + t * dy;
            return 2;
        }
    }

    @Override
    public Rectangle2D.Double getBoundingBox() {
        return new Rectangle2D.Double(this.getX(), this.getY(), this.getDiameter(), this.getDiameter());
    }

    public double getRadius() {
        return this.radius;
    }

    public double getDiameter() {
        return this.radius * 2;
    }
}
