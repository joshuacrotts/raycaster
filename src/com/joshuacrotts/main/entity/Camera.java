package com.joshuacrotts.main.entity;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;

public final class Camera {

    /**
     * KeyAdapter that listens for new keyboard events.
     */
    private final KeyAdapter KEY_ADAPTER;

    /**
     * Use a 70-degree field-of-view.
     */
    private final double FOV = 70;

    /**
     * Width of the camera in pixels.
     */
    private final int WIDTH = 20;

    /**
     * Height of the camera in pixels.
     */
    private final int HEIGHT = 20;

    /**
     * Current x ordinate of the camera.
     */
    private double x;

    /**
     * Current y ordinate of the camera.
     */
    private double y;

    /**
     * Delta for changing the FOV.
     */
    private double fovDelta;

    /**
     * Speed of the Camera when moving forward or backward.
     */
    private double speed = 5;

    /**
     * Current angle of the camera.
     */
    private double currentAngle;

    /**
     * Keeps track of what the camera is doing according to the key listener.
     */
    private int currentState;

    public Camera(final double x, final double y) {
        this.x = x;
        this.y = y;
        this.currentAngle = 0;
        this.currentState = CameraState.STATIONARY;
        this.KEY_ADAPTER = new CameraKeyAdapter(this);
    }

    public void update() {
        if (this.isMoving()) {
            this.x += this.speed * Math.cos(Math.toRadians(this.currentAngle));
            this.y += this.speed * Math.sin(Math.toRadians(this.currentAngle));
        }
        if (this.isTurning()) {
            this.currentAngle += this.fovDelta;
        }
    }

    public void draw(final Graphics2D g2) {
        g2.setColor(Color.YELLOW);
        g2.fill(new Ellipse2D.Double(this.x - this.WIDTH / 2.f, this.y - this.HEIGHT / 2.f, this.WIDTH, this.HEIGHT));
    }

    public int getWidth() {
        return this.WIDTH;
    }

    public int getHeight() {
        return this.HEIGHT;
    }

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

    public double getCurrentAngle() {
        return this.currentAngle;
    }

    public void setCurrentAngle(final double currentAngle) {
        this.currentAngle = currentAngle;
    }

    public double getFov() {
        return this.FOV;
    }

    public double getFovDelta() { return this.fovDelta; }

    public void setFovDelta(double fovDelta) {
        this.fovDelta = fovDelta;
    }

    public boolean isMoving() {
        return CameraState.isFlagEnabled(this.currentState, CameraState.MOVE_FORWARD)
                || CameraState.isFlagEnabled(this.currentState, CameraState.MOVE_BACKWARD);

    }

    public boolean isTurning() {
        return CameraState.isFlagEnabled(this.currentState, CameraState.TURN_LEFT)
                || CameraState.isFlagEnabled(this.currentState, CameraState.TURN_RIGHT);
    }

    public KeyAdapter getKeyAdapter() {
        return this.KEY_ADAPTER;
    }

    /**
     *
     */
    private static class CameraKeyAdapter extends KeyAdapter {

        /**
         *
         */
        private final Camera CAMERA;

        public CameraKeyAdapter(final Camera camera) {
            this.CAMERA = camera;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                this.CAMERA.currentState |= CameraState.MOVE_FORWARD;
                this.CAMERA.speed = 5;
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                this.CAMERA.currentState |= CameraState.MOVE_BACKWARD;
                this.CAMERA.speed = -5;
            }

            if (e.getKeyCode() == KeyEvent.VK_A) {
                this.CAMERA.currentState |= CameraState.TURN_LEFT;
                this.CAMERA.setFovDelta(-5);
            } else if (e.getKeyCode() == KeyEvent.VK_D) {
                this.CAMERA.currentState |= CameraState.TURN_RIGHT;
                this.CAMERA.setFovDelta(+5);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S) {
                this.CAMERA.currentState &= ~(CameraState.MOVE_FORWARD | CameraState.MOVE_BACKWARD);
                this.CAMERA.speed = 0;
            } if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D) {
                this.CAMERA.currentState &= ~(CameraState.TURN_LEFT | CameraState.TURN_RIGHT);
                this.CAMERA.setFovDelta(0);
            }
        }
    }
}
