package com.joshuacrotts;

import com.joshuacrotts.entity.Camera;
import com.joshuacrotts.entity.CollidableEntity2D;
import com.joshuacrotts.entity.EntityData;
import com.joshuacrotts.entity.IntersectionDataPair;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Displays and updates the logic for the top-down raycasting implementation.
 * This class is where the collision detection and movement occurs, whereas the
 * RaycasterPerspectivePanel just projects it to a pseudo-3d environment.
 */
public final class RaycasterPanel extends JPanel {

    /**
     * Max length of any ray.
     */
    private static final int MAX_DIST = 2000;

    /**
     * We need to keep a reference to the parent swing app for sizing and
     * other bookkeeping.
     */
    private final RaycasterRunner RUNNER;

    /**
     * TileMap keeps track of walls and collidables in the scene.
     */
    private final TileMap MAP;

    /**
     * Camera is the "player's" position - where rays are fired from.
     */
    private final Camera CAMERA;

    /**
     * Current iteration of ray objects.
     */
    private final Ray[] RAY_LIST;

    /**
     * Number of rays to fire from the camera.
     */
    private final int RESOLUTION;

    public RaycasterPanel(final RaycasterRunner raycasterRunner) {
        this.RUNNER = raycasterRunner;
        this.setPreferredSize(new Dimension(this.RUNNER.getWidth() / 2, this.RUNNER.getHeight()));
        this.RESOLUTION = this.getPreferredSize().width;
        this.RAY_LIST = new Ray[this.RESOLUTION];
        this.MAP = new TileMap("map2.dat");
        this.CAMERA = new Camera(this, 400, 225);
        this.addKeyListener(this.CAMERA.getKeyAdapter());
        this.requestFocusInWindow(true);
        this.initializeRayList();
    }

    public void update() {
        this.CAMERA.update();
        this.updateCollisions();
        this.computeRays();

    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2d.translate(-(this.CAMERA.getX() - this.getWidth() / 2.f), -(this.CAMERA.getY() - this.getHeight() / 2.f));
        this.drawRays(g2d);
        this.MAP.draw(g2d);
        this.CAMERA.draw(g2d);
    }

    /**
     * Computes the rays originating from the Camera's position. Each ray starts as an "infinite" line segment that
     * spans up to RESOLUTION pixels. Its endpoint is then truncated to the nearest object that it intersects.
     */
    private void computeRays() {
        for (int r = 0; r < this.RESOLUTION; r++) {
            double newMin = this.CAMERA.getCurrentAngle() - this.CAMERA.getFov() / 2;
            double newMax = this.CAMERA.getCurrentAngle() + this.CAMERA.getFov() / 2;

            // Compute the angle of this ray, normalized to our FOV.
            double rayAngle = RaycasterUtils.normalize(r, 0, this.RESOLUTION, newMin, newMax);

            // Compute the coordinates of the end of this ray.
            double ex = this.CAMERA.getX() + RaycasterPanel.MAX_DIST * RaycasterUtils.cos(Math.toRadians(rayAngle));
            double ey = this.CAMERA.getY() + RaycasterPanel.MAX_DIST * RaycasterUtils.sin(Math.toRadians(rayAngle));

            // Construct the current ray object for later.
            this.RAY_LIST[r].setRayCoordinates(this.CAMERA.getX(), this.CAMERA.getY(), ex, ey);

            // Iterate through all objects in the plane and find collisions.
            Point2D.Double minPt = null;
            EntityData minData = null;
            double minDist = Integer.MAX_VALUE;
            for (CollidableEntity2D entity : this.MAP.getEntities()) {
                IntersectionDataPair ip = entity.intersectionPt(this.RAY_LIST[r].getLine());
                if (ip.getPoint() != null) {
                    double currMinDist = ip.getPoint().distance(this.CAMERA.getX(), this.CAMERA.getY());
                    if (currMinDist <= minDist) {
                        minDist = currMinDist;
                        minPt = ip.getPoint();
                        minData = ip.getData();
                    }
                }
            }

            // If we found a closest minima, assign it as the ray's end coordinate.
            this.RAY_LIST[r].setData(minData);
            this.RAY_LIST[r].setAngle(rayAngle);
            if (minPt != null) {
                double ca = RaycasterUtils.normalize(rayAngle, newMin, newMax, -this.CAMERA.getFov() / 2, this.CAMERA.getFov() / 2);
                this.RAY_LIST[r].setEndRayCoordinates(minPt.x, minPt.y);
                this.RAY_LIST[r].setDistance(minDist * RaycasterUtils.cos(Math.toRadians(ca)));
            } else {
                this.RAY_LIST[r].setDistance(Double.POSITIVE_INFINITY);
            }
        }
    }

    /**
     *
     */
    private void updateCollisions() {
        Rectangle2D.Double cbb = this.CAMERA.getBoundingBox();
        for (CollidableEntity2D ce2d : this.MAP.getEntities()) {
            // For now just assume that all are collidable.
            if (cbb.intersects(ce2d.getBoundingBox())) {
                this.CAMERA.stopMoving();
            }
        }
    }

    /**
     * @param g2
     */
    private void drawRays(final Graphics2D g2) {
        for (int i = 0; i < this.RAY_LIST.length; i++) {
            this.RAY_LIST[i].draw(g2);
        }
    }

    private void initializeRayList() {
        for (int i = 0; i < this.RAY_LIST.length; i++) {
            this.RAY_LIST[i] = new Ray();
        }
    }

    public TileMap getTileMap() {
        return this.MAP;
    }

    public Ray[] getRayList() {
        return this.RAY_LIST;
    }

    public Camera getCamera() {
        return this.CAMERA;
    }
}