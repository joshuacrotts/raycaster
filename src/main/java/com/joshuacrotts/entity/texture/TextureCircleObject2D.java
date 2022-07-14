package com.joshuacrotts.entity.texture;

import com.joshuacrotts.entity.CircleObject2D;
import com.joshuacrotts.entity.IntersectionDataPair;
import com.joshuacrotts.entity.Texturable;
import com.joshuacrotts.entity.texture.TextureCache;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class TextureCircleObject2D extends CircleObject2D implements Texturable {

    /**
     * Texture associated with this circle object.
     */
    private final BufferedImage TEXTURE;

    public TextureCircleObject2D(final double x, final double y, double r, final String imageData) {
        super(x, y, r);
        this.TEXTURE = TextureCache.getImage(imageData);
    }

    @Override
    public IntersectionDataPair<Point2D.Double, ?> intersectionPt(final Line2D.Double ray) {
        if (ray.ptSegDist(this.getX() + this.getRadius(), this.getY() + this.getRadius()) <= this.getRadius()) {
            return new IntersectionDataPair<>(this.findClosestIntersection(this.getX() + this.getRadius(), this.getY() + getRadius(), this.getRadius(),
                    (Point2D.Double) ray.getP1(), (Point2D.Double) ray.getP2()), this.getTexture());
        }
        return new IntersectionDataPair<>(null, this.getTexture());
    }

    public BufferedImage getTexture() {
        return this.TEXTURE;
    }
}
