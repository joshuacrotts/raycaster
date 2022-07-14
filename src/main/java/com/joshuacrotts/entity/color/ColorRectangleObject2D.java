package com.joshuacrotts.entity.color;

import com.joshuacrotts.RaycasterUtils;
import com.joshuacrotts.entity.Colorable;
import com.joshuacrotts.entity.IntersectionDataPair;
import com.joshuacrotts.entity.RectangleObject2D;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class ColorRectangleObject2D extends RectangleObject2D implements Colorable {

    /**
     * Color associated with this rectangle object (i.e., color of projected rectangle).
     */
    private final Color COLOR;

    public ColorRectangleObject2D(final double x, final double y, final double w, final double h, final Color color) {
        super(x, y, w, h);
        this.COLOR = color;
        this.setLineSegments(this.computeLineSegments());
    }

    @Override
    public void draw(final Graphics2D g2) {
        for (Line2D.Double lineSegment : this.getLineSegments()) {
            g2.setColor(this.getColor());
            g2.draw(lineSegment);
        }
    }

    @Override
    public IntersectionDataPair<Point2D.Double, ?> intersectionPt(final Line2D.Double ray) {
        ArrayList<ColorWallSegment> lineSegments = (ArrayList<ColorWallSegment>) this.getLineSegments();
        Point2D.Double minPt = null;
        Color minColor = null;
        double minDist = Integer.MAX_VALUE;
        for (int i = 0; i < lineSegments.size(); i++) {
            ColorWallSegment line = lineSegments.get(i);
            if (line.intersectsLine(ray)) {
                Point2D.Double currMinPt = RaycasterUtils.intersection(line, ray);
                double currMinDist = line.ptSegDist(ray.x1, ray.y1);
                if (currMinDist <= minDist) {
                    minPt = currMinPt;
                    minDist = currMinDist;
                    minColor = line.getColor();
                }
            }
        }

        return new IntersectionDataPair<>(minPt, minColor);
    }

    private ArrayList<ColorWallSegment> computeLineSegments() {
        Color darkerColor = this.getColor().darker();
        ArrayList<ColorWallSegment> lineSegments = new ArrayList<>();
        lineSegments.add(new ColorWallSegment(this.getX(), this.getY(), this.getX(), this.getY() + this.getHeight(), darkerColor)); // TL -> BL
        lineSegments.add(new ColorWallSegment(this.getX() + this.getWidth(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), darkerColor)); // TR -> BR
        lineSegments.add(new ColorWallSegment(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY(), this.getColor())); // TL -> TR
        lineSegments.add(new ColorWallSegment(this.getX(), this.getY() + this.getHeight(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), this.getColor())); // BL -> BR
        return lineSegments;
    }

    @Override
    public Color getColor() {
        return this.COLOR;
    }
}
