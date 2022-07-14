package com.joshuacrotts.projection;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public final class ProjectionFloor extends Rectangle2D.Double {

    /**
     * Color used for the floor.
     */
    private static final Color WOLF_FLOOR_COLOR = new Color(127, 127, 127);

    /**
     * Instance of the projection panel.
     */
    private final RaycasterProjectionPanel PROJECTION_PANEL;

    public ProjectionFloor(final RaycasterProjectionPanel projectionPanel) {
        super(0, projectionPanel.getPreferredSize().height / 2.f, projectionPanel.getPreferredSize().width, projectionPanel.getPreferredSize().height);
        this.PROJECTION_PANEL = projectionPanel;
    }

    public void draw(final Graphics2D g2) {
        g2.setColor(WOLF_FLOOR_COLOR);
        g2.fill(this);
    }
}
