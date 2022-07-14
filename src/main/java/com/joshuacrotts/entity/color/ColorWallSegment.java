package com.joshuacrotts.entity.color;

import com.joshuacrotts.entity.Colorable;

import java.awt.*;
import java.awt.geom.Line2D;

public class ColorWallSegment extends Line2D.Double implements Colorable {

    /**
     *
     */
    private final Color COLOR;

    public ColorWallSegment(final double x1, final double y1, final double x2, final double y2, final Color color) {
        super(x1, y1, x2, y2);
        this.COLOR = color;
    }

    @Override
    public Color getColor() {
        return this.COLOR;
    }
}
