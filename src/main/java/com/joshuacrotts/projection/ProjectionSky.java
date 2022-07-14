package com.joshuacrotts.projection;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public final class ProjectionSky extends Rectangle2D.Double {

    /**
     * Color to use for the sky.
     */
    private static final Color WOLF_SKY_COLOR = new Color(22, 23, 22);

    /**
     * Instance of the projection panel.
     */
    private final RaycasterProjectionPanel PROJECTION_PANEL;

    public ProjectionSky(final RaycasterProjectionPanel projectionPanel) {
        super(0, 0, projectionPanel.getPreferredSize().width, projectionPanel.getPreferredSize().height / 2.f);
        this.PROJECTION_PANEL = projectionPanel;
    }

    public void draw(final Graphics2D g2) {
//        GradientPaint gp = new GradientPaint((float) 100.f, (float) 0, WOLF_SKY_COLOR, (float) 100.f, (float) 300.f, Color.BLACK);
//        g2.setPaint(gp);
        g2.setColor(WOLF_SKY_COLOR);
        g2.fill(this);
    }
}
