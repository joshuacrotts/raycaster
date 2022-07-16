package com.joshuacrotts.entity.texture;

import com.joshuacrotts.entity.Entity2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Comparator;

public class TextureSprite extends Entity2D {

    private BufferedImage TEXTURE;

    private double distance;

    public TextureSprite(final double x, final double y, final double w, final double h, final String fileName) {
        super(x,y,w,h);
        this.TEXTURE = TextureCache.getImage(fileName);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.RED);
        g2.fillRect((int) (this.getX() - this.getWidth() / 2), (int) (this.getY() - this.getHeight() / 2), (int)this.getWidth() / 2,(int)this.getHeight() / 2);
    }

    public BufferedImage getTexture() {
        return this.TEXTURE;
    }

    public double getDistance() {
        return this.distance;
    }

    public void setDistance(double d) {
        this.distance = d;
    }

    public static class TextureSpriteComparator implements Comparator<TextureSprite> {
        @Override
        public int compare(TextureSprite t1, TextureSprite t2) {
            return -Double.compare(t1.distance, t2.distance);
        }
    }
}
