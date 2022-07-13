package com.joshuacrotts.main.projection;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public final class ProjectionSky extends Rectangle2D.Double {

    /**
     *
     */
    private static final Color WOLF_SKY_COLOR = new Color(23, 22, 22);

    /**
     *
     */
    private final RaycasterProjectionPanel PROJECTION_PANEL;

    public ProjectionSky(final RaycasterProjectionPanel projectionPanel) {
        super(0, projectionPanel.getPreferredSize().height / 2.f, projectionPanel.getPreferredSize().width, projectionPanel.getPreferredSize().height / 2.f);
        this.PROJECTION_PANEL = projectionPanel;
    }

    public void draw(final Graphics2D g2) {
//        GradientPaint gp = new GradientPaint((float) 100.f, (float) 0, Color.BLACK, (float) 100.f, (float) 300.f, WOLF_SKY_COLOR);
//        g2.setPaint(gp);
        g2.setColor(WOLF_SKY_COLOR);
        g2.fill(this);
    }
}
