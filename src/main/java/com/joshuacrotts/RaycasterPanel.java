package com.joshuacrotts;

import com.joshuacrotts.entity.*;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Displays and updates the logic for the top-down raycasting implementation.
 * This class is where the collision detection and movement occurs, whereas the
 * RaycasterPerspectivePanel just projects it to a pseudo-3d environment.
 */
public final class RaycasterPanel extends JPanel {

    /**
     * Max length of any ray.
     */
    private static final int MAX_DIST = 1280;

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
    private final ArrayList<Ray> RAY_LIST;

    /**
     * Number of rays to fire from the camera.
     */
    private final int RESOLUTION;

    public RaycasterPanel(final RaycasterRunner raycasterRunner) {
        this.RUNNER = raycasterRunner;
        this.RAY_LIST = new ArrayList<>();
        this.MAP = new TileMap("map1.dat", 22, 22);
        this.CAMERA = new Camera(400, 400);
        this.RESOLUTION = 1280;
        this.setPreferredSize(new Dimension(this.RUNNER.getWidth() / 2, this.RUNNER.getHeight()));
        this.addKeyListener(this.CAMERA.getKeyAdapter());
        this.requestFocusInWindow(true);
    }

    public void update() {
        this.CAMERA.update();
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
     *
     */
    private void computeRays() {
        this.RAY_LIST.clear();
        for (int r = 0; r < this.RESOLUTION; r++) {
            double newMin = (this.CAMERA.getCurrentAngle() - this.CAMERA.getFov() / 2) + 360;
            double newMax = (this.CAMERA.getCurrentAngle() + this.CAMERA.getFov() / 2) + 360;

            // Compute the angle of this ray, normalized to our FOV.
            double rayAngle = RaycasterUtils.normalize(r, 0, this.RESOLUTION, newMin, newMax);

            // Compute the coordinates of the end of this ray.
            double ex = this.CAMERA.getX() + MAX_DIST * Math.cos(Math.toRadians(rayAngle));
            double ey = this.CAMERA.getY() + MAX_DIST * Math.sin(Math.toRadians(rayAngle));

            // Construct the current ray object for later.
            Line2D.Double rayLine = new Line2D.Double(this.CAMERA.getX(), this.CAMERA.getY(), ex, ey);

            // Iterate through all objects in the plane and find collisions.
            Point2D.Double minPt = null;
            BufferedImage minImg = null;
            Color minColor = null;
            double minDist = Integer.MAX_VALUE;
            for (CollidableEntity2D entity : this.MAP.getEntities()) {
                IntersectionDataPair<?,?> ip = entity.intersectionPt(rayLine);
                if (ip.getPoint() != null) {
                    double currMinDist = ip.getPoint().distance(this.CAMERA.getX(), this.CAMERA.getY());
                    if (currMinDist <= minDist) {
                        minDist = currMinDist;
                        minPt = ip.getPoint();
                        if (entity instanceof Colorable) {
                            minColor = (Color) ip.getData();
                        } else if (entity instanceof Texturable) {
                            minImg = (BufferedImage) ip.getData();
                        }
                    }
                }
            }

            // If we found a closest minima, assign it as the ray's end coordinate.
            Ray ray = null;
            if (minPt != null) {
                rayLine.x2 = minPt.x;
                rayLine.y2 = minPt.y;
                double ca = RaycasterUtils.normalize(rayAngle, newMin, newMax, -this.CAMERA.getFov() / 2, this.CAMERA.getFov() / 2);
                if (minColor != null) {
                    ray = new Ray(rayLine, minColor, rayAngle, minDist * Math.cos(Math.toRadians(ca)));
                } else {
                    ray = new Ray(rayLine, minImg , rayAngle, minDist * Math.cos(Math.toRadians(ca)));
                }
            } else {
                if (minColor != null) {
                    ray = new Ray(rayLine, minColor, rayAngle);
                } else {
                    ray = new Ray(rayLine, minImg , rayAngle);
                }
            }

            this.RAY_LIST.add(ray);
        }
    }

    /**
     *
     * @param g2
     */
    private void drawRays(final Graphics2D g2) {
        ArrayList<Ray> rayList = this.RAY_LIST;
        for (int i = 0; i < rayList.size(); i++) {
            rayList.get(i).draw(g2);
        }
    }

    public TileMap getTileMap() {
        return this.MAP;
    }

    public ArrayList<Ray> getRayList() {
        return this.RAY_LIST;
    }
}