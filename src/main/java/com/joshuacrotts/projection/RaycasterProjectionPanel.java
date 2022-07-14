package com.joshuacrotts.projection;

import com.joshuacrotts.Ray;
import com.joshuacrotts.RaycasterPanel;
import com.joshuacrotts.RaycasterRunner;
import com.joshuacrotts.RaycasterUtils;

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
     * Sky rendered as the top-half of the projection plane.
     */
    private final ProjectionSky PROJECTION_SKY;

    /**
     * Floor rendered as the bottom-half of the projection plane.
     */
    private final ProjectionFloor PROJECTION_FLOOR;

    public RaycasterProjectionPanel(final RaycasterRunner raycasterRunner, final RaycasterPanel raycasterPanel) {
        this.RUNNER = raycasterRunner;
        this.setPreferredSize(new Dimension(this.RUNNER.getWidth() / 2, this.RUNNER.getHeight()));
        this.RAYCASTER_PANEL = raycasterPanel;
        this.PROJECTION_SKY = new ProjectionSky(this);
        this.PROJECTION_FLOOR = new ProjectionFloor(this);
    }

    public void update() {
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, this.getPreferredSize().width, this.getPreferredSize().height);
        this.PROJECTION_SKY.draw(g2d);
        this.PROJECTION_FLOOR.draw(g2d);
        this.project(g2d);
    }

    /**
     * @param g2
     */
    private void project(final Graphics2D g2) {
        ArrayList<Ray> rayList = this.RAYCASTER_PANEL.getRayList();
        for (int i = 0; i < rayList.size(); i++) {
            if (rayList.get(i).getDistance() == 0) {
                continue;
            }
            // Generate the (x, y) coordinate of the wall, as well as its height.
            double wallX = RaycasterUtils.normalize(i, 0, rayList.size(), 0, this.getPreferredSize().width);
            double wallHeight = this.getPreferredSize().height * MAX_HEIGHT_OFFSET / rayList.get(i).getDistance();
            double wallY = this.getPreferredSize().height / 2.f - wallHeight / 2.f;

            // Depending on what "type" the Ray stores, we render differently.
            Ray ray = rayList.get(i);
            if (ray.getEntityData().isTexture()) {
                this.projectTexture(wallX, wallY, wallHeight, ray, g2);
            } else if (ray.getEntityData().isColor()) {
                this.projectColor(wallX, wallY, wallHeight, ray, g2);
            }
        }
    }

    /**
     * @param wallX
     * @param wallY
     * @param wallHeight
     * @param ray
     * @param g2
     */
    private void projectTexture(final double wallX, final double wallY, final double wallHeight, final Ray ray, final Graphics2D g2) {
        BufferedImage img = ray.getEntityData().getTexture();
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
    private void projectColor(final double wallX, final double wallY, final double wallHeight, final Ray ray, final Graphics2D g2) {
        Color c = ray.getEntityData().getColor();
        g2.setColor(c);
        g2.drawLine((int) wallX, (int) wallY, (int) wallX, (int) (wallY + wallHeight));
    }

}
