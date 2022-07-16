package com.joshuacrotts.projection;

import com.joshuacrotts.RaycasterUtils;
import com.joshuacrotts.entity.Camera;
import com.joshuacrotts.entity.texture.TextureCache;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ProjectionCamera {

    /**
     * Instance of the projection pane for dimensions.
     */
    private final RaycasterProjectionPanel PROJECTION_PANEL;

    /**
     * Texture (FPS) to render overtop all other objects.
     */
    private final BufferedImage TEXTURE;

    /**
     * Offset from the top of the image to prevent the image from being fully exposed
     * over the y-axis.
     */
    private final int OSCILLATION_Y_OFFSET = 30;

    /**
     * Oscillation intensity level for both x and y axis. The higher this value, the wilder the bob.
     */
    private final int OSCILLATION_INTENSITY = 20;

    /**
     *
     */
    private final double SPRITE_X_SCALE = 2.f;

    /**
     *
     */
    private final double SPRITE_Y_SCALE = 2.5f;

    /**
     * Current angle of the lemniscate iteration. Wraps around after 360.
     */
    private double oscillationAngle;

    public ProjectionCamera(final RaycasterProjectionPanel projectionPanel) {
        this.PROJECTION_PANEL = projectionPanel;
        this.TEXTURE = TextureCache.getImage("wolf3d_3.png");
    }

    public void draw(final Graphics2D g2) {
        this.oscillationAngle += this.getOscillationSpeed() % 360;
        // Convert to the lemniscate coordinates.
        double x = this.OSCILLATION_INTENSITY * RaycasterUtils.cos(Math.toRadians(this.oscillationAngle));
        double y = this.OSCILLATION_INTENSITY * RaycasterUtils.sin(Math.toRadians(this.oscillationAngle * 2.f));
        double w = this.PROJECTION_PANEL.getPreferredSize().width / this.SPRITE_X_SCALE;
        double h = this.PROJECTION_PANEL.getPreferredSize().height / this.SPRITE_Y_SCALE;
        int cx = (int) (this.PROJECTION_PANEL.getPreferredSize().width / 2.f - w / 2.f);
        g2.drawImage(this.TEXTURE,(int) (x + cx),
                this.OSCILLATION_Y_OFFSET + (int) (y + this.PROJECTION_PANEL.getPreferredSize().height - h),
                (int) w,(int) h, null);
    }

    private double getOscillationSpeed() {
        Camera ca = this.PROJECTION_PANEL.getCamera();
        if (ca.isRunning()) { return 5; }
        else if (ca.isWalking()) { return 2; }
        else { return 1; }
    }
}
