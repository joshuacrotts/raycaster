package com.joshuacrotts.main.entity.color;

import com.joshuacrotts.main.RaycasterUtils;
import com.joshuacrotts.main.entity.CollidableEntity2D;
import com.joshuacrotts.main.entity.IntersectionDataPair;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class ColorRectangleObject2D extends CollidableEntity2D {

    /**
     * Color for this RectangleObject. The y-axis walls are shaded in a darker variant of this color.
     */
    private Color color;

    /**
     * Width of the object in the 2D plane.
     */
    private double width;

    /**
     * Height of the object in the 2D plane.
     */
    private double height;

    public ColorRectangleObject2D(final double x, final double y, final double w, final double h, final Color color) {
        super(x, y);
        this.width = w;
        this.height = h;
        this.color = color;
        this.setLineSegments(this.computeLineSegments());
    }

    @Override
    public void draw(final Graphics2D g2) {
        for (ColorWallSegment lineSegment : this.getLineSegments()) {
            g2.setColor(this.color);
            g2.draw(lineSegment);
        }
    }

    @Override
    public IntersectionDataPair intersectionPt(final Line2D.Double ray) {
        ArrayList<ColorWallSegment> lineSegments = this.getLineSegments();
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

        return new IntersectionDataPair(minPt, minColor);
    }

    private ArrayList<ColorWallSegment> computeLineSegments() {
        Color darkerColor = this.color.darker();
        ArrayList<ColorWallSegment> lineSegments = new ArrayList<>();
        lineSegments.add(new ColorWallSegment(this.getX(), this.getY(), this.getX(), this.getY() + this.height, darkerColor)); // TL -> BL
        lineSegments.add(new ColorWallSegment(this.getX() + this.width, this.getY(), this.getX() + this.width, this.getY() + this.height, darkerColor)); // TR -> BR
        lineSegments.add(new ColorWallSegment(this.getX(), this.getY(), this.getX() + this.width, this.getY(), this.color)); // TL -> TR
        lineSegments.add(new ColorWallSegment(this.getX(), this.getY() + this.height, this.getX() + this.width, this.getY() + this.height, this.color)); // BL -> BR
        return lineSegments;
    }
}
