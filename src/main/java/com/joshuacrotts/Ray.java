package com.joshuacrotts;

import com.joshuacrotts.entity.EntityData;

import java.awt.*;
import java.awt.geom.Line2D;

public class Ray {

    /**
     * Line behind this Ray; holds the position.
     */
    private final Line2D.Double LINE;

    /**
     * Angle of this ray as cast from the Camera.
     */
    private final double ANGLE;

    /**
     * EntityData associated with this Ray. To be useful in the projection stage.
     */
    private final EntityData DATA;

    /**
     * Length of this Ray from the start point to the end. 0 indicates an infinite ray.
     */
    private final double DISTANCE;

    public Ray(final Line2D.Double line, final EntityData image, final double angle) {
        this(line, image, angle, 0);
    }

    public Ray(final Line2D.Double line, final EntityData entityData, final double angle, final double distance) {
        this.LINE = line;
        this.DATA = entityData;
        this.ANGLE = angle;
        this.DISTANCE = distance;
    }

    public void draw(final Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.draw(this.LINE);
    }

    public double getDistance() {
        return this.DISTANCE;
    }

    public double getAngle() {
        return this.ANGLE;
    }

    public Line2D.Double getLine() {
        return this.LINE;
    }

    public EntityData getEntityData() {
        return this.DATA;
    }
}
