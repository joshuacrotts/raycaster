package com.joshuacrotts.projection;

import com.joshuacrotts.entity.texture.TextureCache;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public final class ProjectionFloor extends Rectangle2D.Double {

    /**
     * Color used for the floor.
     */
    private final Color FLOOR_COLOR = new Color(41, 41, 41);

    /**
     * Instance of the projection panel.
     */
    private final RaycasterProjectionPanel PROJECTION_PANEL;

    /**
     *
     */
    private final BufferedImage FLOOR_TEXTURE;

    /**
     *
     */
    private final BufferedImage FLOOR_BUFFER;

    /**
     *
     */
    private boolean texturedFloor;

    public ProjectionFloor(final RaycasterProjectionPanel projectionPanel) {
        super(0, projectionPanel.getPreferredSize().height / 2.f, projectionPanel.getPreferredSize().width, projectionPanel.getPreferredSize().height);
        this.PROJECTION_PANEL = projectionPanel;
        this.FLOOR_TEXTURE = TextureCache.getImage("floor_2.png");
        this.FLOOR_BUFFER = new BufferedImage(projectionPanel.getPreferredSize().width, projectionPanel.getPreferredSize().height / 2, BufferedImage.TYPE_INT_RGB);
    }

    public void draw(final Graphics2D g2) {
        if (this.texturedFloor) {
            g2.drawImage(this.FLOOR_BUFFER, 0, (int) this.getHeight() / 2, null);
        } else {
            g2.setColor(this.FLOOR_COLOR);
            g2.fillRect(0, this.PROJECTION_PANEL.getPreferredSize().height / 2, this.PROJECTION_PANEL.getPreferredSize().width, this.PROJECTION_PANEL.getPreferredSize().height / 2);
        }
    }

    public void setPixel(final int dx, final int dy, final int sx, final int sy) {
        this.FLOOR_BUFFER.setRGB(dx, dy, this.FLOOR_TEXTURE.getRGB(sx, sy));
    }

    public boolean isTexturedFloor() {
        return this.texturedFloor;
    }

    public void setTexturedFloor(final boolean texturedFloor) {
        this.texturedFloor = texturedFloor;
    }
}
