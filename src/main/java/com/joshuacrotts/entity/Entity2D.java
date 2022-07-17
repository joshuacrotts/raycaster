package com.joshuacrotts.entity;

import java.awt.*;

public abstract class Entity2D implements Drawable2D {

    /**
     * X coordinate of the Entity.
     */
    private double x;

    /**
     * Y coordinate of the Entity.
     */
    private double y;

    /**
     * Width of the entity.
     */
    private double w;

    /**
     * Height of the entity.
     */
    private double h;

    public Entity2D(final double x, final double y, final double w, final double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public abstract EntityData getEntityData();

    @Override
    public abstract void draw(final Graphics2D g2);

    public double getX() {
        return this.x;
    }

    public void setX(final double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(final double y) {
        this.y = y;
    }

    public double getWidth() {
        return w;
    }

    public void setWidth(double w) {
        this.w = w;
    }

    public double getHeight() {
        return h;
    }

    public void setHeight(double h) {
        this.h = h;
    }
}
