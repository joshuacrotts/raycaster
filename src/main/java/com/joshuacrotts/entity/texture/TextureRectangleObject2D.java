package com.joshuacrotts.entity.texture;

import com.joshuacrotts.RaycasterUtils;
import com.joshuacrotts.entity.IntersectionDataPair;
import com.joshuacrotts.entity.RectangleObject2D;
import com.joshuacrotts.entity.Texturable;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.ArrayList;

public class TextureRectangleObject2D extends RectangleObject2D implements Texturable {

    /**
     *
     */
    private final BufferedImage TEXTURE;

    public TextureRectangleObject2D(final double x, final double y, final double w, final double h, final String imageName) {
        super(x, y, w, h);
        this.TEXTURE = TextureCache.getImage(imageName);
        this.setLineSegments(this.computeLineSegments());
    }

    @Override
    public void draw(final Graphics2D g2) {
        super.draw(g2);
    }

    @Override
    public IntersectionDataPair<Point2D.Double, ?> intersectionPt(final Line2D.Double ray) {
        ArrayList<TextureWallSegment> lineSegments = (ArrayList<TextureWallSegment>) this.getLineSegments();
        Point2D.Double minPt = null;
        BufferedImage minImg = null;
        double minDist = Integer.MAX_VALUE;
        for (int i = 0; i < lineSegments.size(); i++) {
            TextureWallSegment line = lineSegments.get(i);
            if (line.intersectsLine(ray)) {
                Point2D.Double currMinPt = RaycasterUtils.intersection(line, ray);
                double currMinDist = line.ptSegDist(ray.x1, ray.y1);
                if (currMinDist <= minDist) {
                    minPt = currMinPt;
                    minDist = currMinDist;
                    minImg = line.getTexture();
                }
            }
        }

        return new IntersectionDataPair<>(minPt, minImg);
    }

    private ArrayList<TextureWallSegment> computeLineSegments() {
        RescaleOp op = new RescaleOp(0.65f, 0, null);
        BufferedImage darkerImage = op.filter(this.getTexture(), null);
        ArrayList<TextureWallSegment> lineSegments = new ArrayList<>();
        lineSegments.add(new TextureWallSegment(this.getX(), this.getY(), this.getX(), this.getY() + this.getHeight(), darkerImage)); // TL -> BL
        lineSegments.add(new TextureWallSegment(this.getX() + this.getWidth(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), darkerImage)); // TR -> BR
        lineSegments.add(new TextureWallSegment(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY(), this.getTexture())); // TL -> TR
        lineSegments.add(new TextureWallSegment(this.getX(), this.getY() + this.getHeight(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), this.getTexture())); // BL -> BR
        return lineSegments;
    }

    @Override
    public BufferedImage getTexture() {
        return this.TEXTURE;
    }
}
