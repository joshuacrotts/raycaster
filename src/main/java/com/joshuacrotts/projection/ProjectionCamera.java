package com.joshuacrotts.projection;

import com.joshuacrotts.entity.texture.TextureCache;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ProjectionCamera {

    /**
     *
     */
    private final RaycasterProjectionPanel PROJECTION_PANEL;

    /**
     *
     */
    private final BufferedImage TEXTURE;

    /**
     *
     */
    private final int OSCILLATION_Y_OFFSET = 30;

    /**
     *
     */
    private final int OSCILLATION_OFFSET = 20;

    /**
     *
     */
    private int oscillationAngle;

    public ProjectionCamera(final RaycasterProjectionPanel projectionPanel) {
        this.PROJECTION_PANEL = projectionPanel;
        this.TEXTURE = TextureCache.getImage("wolf3d.png");
    }

    public void draw(Graphics2D g2) {
        this.oscillationAngle++;
        double y = this.OSCILLATION_OFFSET * Math.sin(Math.toRadians(this.oscillationAngle<<1));
        double x = this.OSCILLATION_OFFSET * Math.cos(Math.toRadians(this.oscillationAngle));
        int cx = (int) (this.PROJECTION_PANEL.getPreferredSize().width / 2.f - this.TEXTURE.getWidth() / 2.f);
        g2.drawImage(this.TEXTURE,(int) (x + cx), this.OSCILLATION_Y_OFFSET + (int) (y + this.PROJECTION_PANEL.getPreferredSize().height - this.TEXTURE.getHeight()), null);
    }
}
