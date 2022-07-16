package com.joshuacrotts.projection;

import com.joshuacrotts.entity.texture.TextureCache;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public final class ProjectionCeiling extends Rectangle2D.Double {

    /**
     * Color to use for the sky when textures are disabled.
     */
    private final Color CEILING_COLOR = new Color(22, 23, 22);

    /**
     * Instance of the projection panel.
     */
    private final RaycasterProjectionPanel PROJECTION_PANEL;

    /**
     * Image projected onto the ceiling.
     */
    private final BufferedImage CEILING_TEXTURE;

    /**
     * Pixel data of the ceiling drawn on the current frame.
     */
    private final BufferedImage CEILING_BUFFER;

    /**
     * Keeps track of whether we render the texture on the ceiling or not.
     */
    private boolean texturedCeiling;

    public ProjectionCeiling(final RaycasterProjectionPanel projectionPanel) {
        super(0, 0, projectionPanel.getPreferredSize().width, projectionPanel.getPreferredSize().height / 2.f);
        this.PROJECTION_PANEL = projectionPanel;
        this.CEILING_TEXTURE = TextureCache.getImage("stonebrick.png");
        this.CEILING_BUFFER = new BufferedImage(projectionPanel.getPreferredSize().width, projectionPanel.getPreferredSize().height / 2, BufferedImage.TYPE_INT_RGB);
    }

    public void draw(final Graphics2D g2) {
        if (this.texturedCeiling) {
            g2.drawImage(this.CEILING_BUFFER, 0, 0, null);
        } else {
            g2.setColor(this.CEILING_COLOR);
            g2.fillRect(0, 0, this.PROJECTION_PANEL.getPreferredSize().width, this.PROJECTION_PANEL.getPreferredSize().height);
        }
    }

    public void setPixel(final int dx, final int dy, final int sx, final int sy) {
        this.CEILING_BUFFER.setRGB(dx, dy, this.CEILING_TEXTURE.getRGB(sx, sy));
    }

    public boolean isTexturedCeiling() {
        return this.texturedCeiling;
    }

    public void setTexturedCeiling(final boolean texturedCeiling) {
        this.texturedCeiling = texturedCeiling;
    }
}
