package com.joshuacrotts.projection;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public final class ProjectionFloor extends Rectangle2D.Double {

    /**
     *
     */
    private static final Color WOLF_FLOOR_COLOR = new Color(51, 49, 49);

    /**
     *
     */
    private final RaycasterProjectionPanel PROJECTION_PANEL;

    public ProjectionFloor(final RaycasterProjectionPanel projectionPanel) {
        super(0, projectionPanel.getPreferredSize().height / 2.f, projectionPanel.getPreferredSize().width, projectionPanel.getPreferredSize().height);
        this.PROJECTION_PANEL = projectionPanel;
    }

    public void draw(final Graphics2D g2) {
//        GradientPaint gp = new GradientPaint((float) x + 100.f, (float) y, Color.BLACK, (float) x + 100.f, (float) y + 300.f, WOLF_FLOOR_COLOR);
//        g2.setPaint(gp);
        g2.setColor(WOLF_FLOOR_COLOR);
        g2.fill(this);
    }
}
