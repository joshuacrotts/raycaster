package com.joshuacrotts.main.entity;

import java.awt.*;

public abstract class Entity2D implements Drawable2D {

    private double x;

    private double y;

    public Entity2D() {
    }

    public Entity2D(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

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
}
