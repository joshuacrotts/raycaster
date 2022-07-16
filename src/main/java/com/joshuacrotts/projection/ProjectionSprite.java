package com.joshuacrotts.projection;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ProjectionSprite {

    public static final int TEXTURE_BG_COLOR = -65281;

    /**
     * Projection plane for retrieving width and height.
     */
    private final RaycasterProjectionPanel PROJECTION_PANEL;

    /**
     * Image backing the sprite projection - all sprites are drawn to this image.
     */
    private final BufferedImage IMAGE;

    /**
     * Graphics context created by the image to clear the data after each frame.
     */
    private final Graphics2D G2D;

    public ProjectionSprite(final RaycasterProjectionPanel PROJECTION_PANEL) {
        this.PROJECTION_PANEL = PROJECTION_PANEL;
        this.IMAGE = new BufferedImage(PROJECTION_PANEL.getPreferredSize().width, PROJECTION_PANEL.getPreferredSize().height, BufferedImage.TYPE_INT_ARGB);
        this.G2D = this.IMAGE.createGraphics();
    }

    public void setPixel(double x, double y, int color) {
        this.IMAGE.setRGB((int) x, (int) y, color);
    }

    public void draw(final Graphics2D g2) {
        g2.drawImage(this.IMAGE, 0, 0, null);
        this.clear();
    }

    private void clear() {
        AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f);
        this.G2D.setComposite(composite);
        this.G2D.setColor(new Color(0, 0, 0, 0));
        this.G2D.fillRect(0, 0, this.IMAGE.getWidth(), this.IMAGE.getHeight());
        this.G2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
    }
}
