package com.joshuacrotts.entity.texture;

import com.joshuacrotts.entity.Texturable;

import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

public class TextureWallSegment extends Line2D.Double implements Texturable {

    /**
     * Image behind this wall segment.
     */
    private final BufferedImage IMAGE;

    public TextureWallSegment(final double x1, final double y1, final double x2, final double y2, final BufferedImage image) {
        super(x1, y1, x2, y2);
        this.IMAGE = image;
    }

    public BufferedImage getTexture() {
        return this.IMAGE;
    }
}

