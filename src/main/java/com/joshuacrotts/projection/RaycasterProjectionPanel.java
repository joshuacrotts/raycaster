package com.joshuacrotts.projection;

import com.joshuacrotts.Ray;
import com.joshuacrotts.RaycasterPanel;
import com.joshuacrotts.RaycasterRunner;
import com.joshuacrotts.RaycasterUtils;
import com.joshuacrotts.entity.Camera;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RaycasterProjectionPanel extends JPanel {

    /**
     * Maximum offset that can be applied to the screen height for the walls.
     */
    private static final double MAX_HEIGHT_OFFSET = 40.0;

    /**
     * Root driver object to keep track of sizing.
     */
    private final RaycasterRunner RUNNER;

    /**
     * Overhead panel to access the generated rays.
     */
    private final RaycasterPanel RAYCASTER_PANEL;

    /**
     *
     */
    private final ProjectionCamera PROJECTION_CAMERA;

    /**
     * Sky rendered as the top-half of the projection plane.
     */
    private final ProjectionCeiling PROJECTION_CEILING;

    /**
     * Floor rendered as the bottom-half of the projection plane.
     */
    private final ProjectionFloor PROJECTION_FLOOR;

    public RaycasterProjectionPanel(final RaycasterRunner raycasterRunner, final RaycasterPanel raycasterPanel) {
        this.RUNNER = raycasterRunner;
        this.setPreferredSize(new Dimension(this.RUNNER.getWidth() / 2, this.RUNNER.getHeight()));
        this.RAYCASTER_PANEL = raycasterPanel;
        this.PROJECTION_CAMERA = new ProjectionCamera(this);
        this.PROJECTION_CEILING = new ProjectionCeiling(this);
        this.PROJECTION_FLOOR = new ProjectionFloor(this);
        this.PROJECTION_CEILING.setTexturedCeiling(true);
        this.PROJECTION_FLOOR.setTexturedFloor(true);
    }

    public void update() {
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        this.PROJECTION_CEILING.draw(g2d);
        this.PROJECTION_FLOOR.draw(g2d);
        this.project(g2d);
        this.PROJECTION_CAMERA.draw(g2d);
    }

    /**
     * @param g2
     */
    private void project(final Graphics2D g2) {
        Ray[] rayList = this.RAYCASTER_PANEL.getRayList();
        for (int i = 0; i < rayList.length; i++) {
            if (rayList[i].getDistance() == Double.POSITIVE_INFINITY) {
                continue;
            }
            // Generate the (x, y) coordinate of the wall, as well as its height.
            double wallX = RaycasterUtils.normalize(i, 0, rayList.length, 0, this.getPreferredSize().width);
            double wallHeight = this.getPreferredSize().height * MAX_HEIGHT_OFFSET / rayList[i].getDistance();
            double wallY = this.getPreferredSize().height / 2.f - wallHeight / 2.f;

            // Depending on what "type" the Ray stores, we render differently.
            this.projectWall(rayList[i], wallX, wallY, wallHeight, g2);
            this.projectFloorCeiling(rayList[i], wallX, wallY, wallHeight);
        }
    }

    /**
     *
     * @param ray
     * @param wallX
     * @param wallY
     * @param wallHeight
     * @param g2
     */
    private void projectWall(final Ray ray, final double wallX, final double wallY, final double wallHeight, final Graphics2D g2) {
        if (ray.getData().isTexture()) {
            this.projectTexture(ray, wallX, wallY, wallHeight, g2);
        } else if (ray.getData().isColor()) {
            this.projectColor(ray, wallX, wallY, wallHeight, g2);
        }
    }

    /**
     * @param wallX
     * @param wallY
     * @param wallHeight
     * @param ray
     * @param g2
     */
    private void projectTexture(final Ray ray, final double wallX, final double wallY, final double wallHeight, final Graphics2D g2) {
        BufferedImage img = ray.getData().getTexture();
        int imgX;
        if (ray.getLine().getY2() != (int) ray.getLine().getY2()) {
            imgX = (int) ((ray.getLine().getY2() / img.getWidth() - Math.floor(ray.getLine().getY2() / img.getWidth())) * img.getWidth());
        } else {
            imgX = (int) ((ray.getLine().getX2() / img.getWidth() - Math.floor(ray.getLine().getX2() / img.getWidth())) * img.getWidth());
        }
        g2.drawImage(img, (int) wallX, (int) wallY, (int) wallX + 1, (int) (wallHeight + wallY), imgX, 0,
                imgX + 1, img.getHeight(), null);
    }

    /**
     * @param wallX
     * @param wallY
     * @param wallHeight
     * @param ray
     * @param g2
     */
    private void projectColor(final Ray ray, final double wallX, final double wallY, final double wallHeight, final Graphics2D g2) {
        g2.setColor(ray.getData().getColor());
        g2.drawLine((int) wallX, (int) wallY, (int) wallX, (int) (wallY + wallHeight));
    }

    /**
     *
     * @param ray
     * @param wallX
     * @param wallY
     * @param wallHeight
     */
    private void projectFloorCeiling(final Ray ray, final double wallX, final double wallY, final double wallHeight) {
        // Yes, this is a little ugly, but it works rather well.
        if (this.PROJECTION_CEILING.isTexturedCeiling() || this.PROJECTION_FLOOR.isTexturedFloor()) {
            final double TEXTURE_SCALE = 64;
            final double CAMERA_HEIGHT = 32;
            final int TEXTURE_SIZE = 64;
            final double CAMERA_X = this.RAYCASTER_PANEL.getCamera().getX();
            final double CAMERA_Y = this.RAYCASTER_PANEL.getCamera().getY();
            final double DTPP = this.RAYCASTER_PANEL.getCamera().getDistanceToProjectionPlane();
            final double ANGLE = RaycasterUtils.cos(Math.toRadians(this.RAYCASTER_PANEL.getCamera().getCurrentAngle() - ray.getAngle()));
            final double RAY_COSANGLE = RaycasterUtils.cos(Math.toRadians(ray.getAngle()));
            final double RAY_SINANGLE = RaycasterUtils.sin(Math.toRadians(ray.getAngle()));

            // Iterate from the bottom of the wall to the bottom of the projection plane.
            for (int y = (int) (wallY + wallHeight + 1); y < this.getPreferredSize().height; y++) {
                double r = y - this.getPreferredSize().height / 2.f;
                double d = (CAMERA_HEIGHT * DTPP / r) / ANGLE;
                double tileX = CAMERA_X + d * RAY_COSANGLE;
                double tileY = CAMERA_Y + d * RAY_SINANGLE;
                int textureX = Math.floorMod((int) (tileX * TEXTURE_SIZE / TEXTURE_SCALE), TEXTURE_SIZE);
                int textureY = Math.floorMod((int) (tileY * TEXTURE_SIZE / TEXTURE_SCALE), TEXTURE_SIZE);
                if (this.PROJECTION_FLOOR.isTexturedFloor()) {
                    this.PROJECTION_FLOOR.setPixel((int) wallX, y - this.getPreferredSize().height / 2, textureX, textureY);
                }
                if (this.PROJECTION_CEILING.isTexturedCeiling()) {
                    this.PROJECTION_CEILING.setPixel((int) wallX, this.getPreferredSize().height - y, textureX, textureY);
                }
            }
        }
    }

    public Camera getCamera() {
        return this.RAYCASTER_PANEL.getCamera();
    }
}
