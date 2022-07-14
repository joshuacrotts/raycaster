package com.joshuacrotts.projection;

import com.joshuacrotts.RaycasterPanel;
import com.joshuacrotts.RaycasterRunner;
import com.joshuacrotts.Ray;
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
     *
     */
    private final ProjectionSky PROJECTION_SKY;

    /**
     *
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
        g2d.fillRect(0,0,this.getPreferredSize().width, this.getPreferredSize().height);
        this.PROJECTION_SKY.draw(g2d);
        this.PROJECTION_FLOOR.draw(g2d);
        this.project(g2d);
    }

    /**
     *
     * @param g2
     */
    private void project(final Graphics2D g2) {
        ArrayList<Ray> rayList = this.RAYCASTER_PANEL.getRayList();
        for (int i = 0; i < rayList.size(); i++) {
            if (rayList.get(i).getDistance() == 0) { continue; }
            // Generate the (x, y) coordinate of the wall, as well as its height.
            double wallX = RaycasterUtils.normalize(i, 0, rayList.size(), 0, this.getPreferredSize().width);
            double wallHeight = this.getPreferredSize().height * MAX_HEIGHT_OFFSET / rayList.get(i).getDistance();
            double wallY = this.getPreferredSize().height / 2.f - wallHeight / 2.f;

            Ray ray = rayList.get(i);
            if (ray.getProjectionImage() != null) {
                BufferedImage img = ray.getProjectionImage();
                int imgX;
                if (ray.getLine().y2 != (int) ray.getLine().y2) {
                    imgX = (int) ((ray.getLine().y2 / img.getWidth() - Math.floor(ray.getLine().y2 / img.getWidth())) * img.getWidth());
                } else {
                    imgX = (int) ((ray.getLine().x2 / img.getWidth() - Math.floor(ray.getLine().x2 / img.getWidth())) * img.getWidth());
                }
                g2.drawImage(img, (int) wallX, (int) wallY, (int) wallX + 1, (int) (wallHeight + wallY), imgX, 0,
                        imgX + 1, img.getHeight(), null);
            }

            // Compute a shaded color based on how far the wall is from the camera.
            else if (ray.getProjectionColor() != null) {
                double coloredHeight = RaycasterUtils.clamp(wallHeight, 0, this.getPreferredSize().height - 200);
                coloredHeight = RaycasterUtils.normalize(coloredHeight, 0, this.getPreferredSize().height - 200);

                // Convert the color used in the projection to HSB, then back to RGB to use the new brightness value.
                Color c = rayList.get(i).getProjectionColor();
                //float[] colors = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
                //g2.setColor(new Color(Color.HSBtoRGB(colors[0], colors[1], (float) coloredHeight)));
                g2.setColor(c);
                g2.drawLine((int) wallX, (int) wallY, (int) wallX, (int) (wallY + wallHeight));
            }
        }
    }
}