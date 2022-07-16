package com.joshuacrotts.entity.texture;

import com.joshuacrotts.entity.Entity2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TextureSprite extends Entity2D {

    private BufferedImage TEXTURE;

    public TextureSprite(final double x, final double y, final double w, final double h, final String fileName) {
        super(x,y,w,h);
        this.TEXTURE = TextureCache.getImage(fileName);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(this.TEXTURE,(int)this.getX(), (int)this.getY() ,null);
    }

    public BufferedImage getTexture() {
        return this.TEXTURE;
    }
}
