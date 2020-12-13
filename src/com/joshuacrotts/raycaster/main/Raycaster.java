package com.joshuacrotts.raycaster.main;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Line2D.Double;
import java.util.ArrayList;

import com.theta.graphic.ThetaGraphics;
import com.theta.platform.ThetaGraphicalApplication;
import com.theta.util.ThetaUtils;

public class Raycaster extends ThetaGraphicalApplication {

  /**
   * Width of JFrame.
   */
  private static final int WIDTH = 1600;

  /**
   * Height of JFrame.
   */
  private static final int HEIGHT = 600;

  /**
   * Max number of walls to generate in the program.
   */
  private final int MAX_WALLS;

  /**
   * ArrayList of walls - generated walls are stored here for later comparison.
   */
  private ArrayList<Wall> walls;

  /**
   * Field of view for the camera.
   */
  private int fov = 90;

  /**
   * Current angle of the player.
   */
  private int angle = 0;

  public Raycaster() {
    super(WIDTH, HEIGHT, "Raycaster");
    this.MAX_WALLS = ThetaUtils.randomInt(10, 20);
    this.addKeyListener(new ChangeAngleEvent(this));
    this.generateWalls();
  }

  @Override
  public void update() {
  }

  @Override
  public void render() {
    this.drawWalls();
    this.drawRays();
    this.drawSeparator();
  }

  /**
   * 
   */
  private void generateWalls() {
    this.walls = new ArrayList<Wall>(MAX_WALLS);

    for (int i = 0; i < MAX_WALLS; i++) {
      int x1 = ThetaUtils.randomInt(0, this.getGameWidth() / 2);
      int y1 = ThetaUtils.randomInt(0, this.getGameHeight());
      int x2 = ThetaUtils.randomInt(0, this.getGameWidth() / 2);
      int y2 = ThetaUtils.randomInt(0, this.getGameHeight());
      Color color = ThetaGraphics.getRandomColor();

      this.walls.add(new Wall(x1, y1, x2, y2, color));
    }
  }

  /**
   * 
   */
  private void drawWalls() {
    for (Wall wall : this.walls) {
      ThetaGraphics.GFXContext.setColor(wall.getColor());
      ThetaGraphics.GFXContext.draw(wall.getLine());
    }
  }

  /**
   * Draws the rays shot out by the camera/perspective.
   * 
   * @param void.
   * 
   * @return void.
   */
  private void drawRays() {
    ThetaGraphics.GFXContext.setColor(Color.white);
    for (Line2D.Double line : calcRays(this.walls, 3000, 250)) {
      ThetaGraphics.GFXContext.draw(line);
    }
  }

  /**
   * Draws the graphical separator between the two views, so to speak. The left is
   * the side with the 2D overhead of the raycasting, where the right is the
   * pseudo-3D perspective.
   * 
   * @param void.
   * 
   * @return void.
   */
  private void drawSeparator() {
    final int SEP_WIDTH = 2;
    ThetaGraphics.GFXContext.setColor(Color.RED);
    ThetaGraphics.rect(this.getGameWidth() / 2, 0, SEP_WIDTH, this.getGameHeight(), Color.RED, true, 0);
  }

  /**
   * 
   * @param lines
   * @param angle
   * @param resolution
   * @return
   */
  private ArrayList<Line2D.Double> calcRays(ArrayList<Wall> walls, int resolution, int maxDist) {
    ArrayList<Line2D.Double> rays = new ArrayList<>();
    int mx = this.getMouse().getMouseX();
    int my = this.getMouse().getMouseY();

    // If we exceed the right-boundary, just bail out.
    if (mx > this.getGameWidth() / 2) {
      return rays;
    }

    for (int r = 0; r < resolution; r++) {
      // Compute the angle of this ray, normalized to our field of view.
      double rayAngle = ThetaUtils.normalize(r, 0, resolution, angle, angle + fov);

      // Compute the coordinates of the end of this ray.
      int ex = (int) (mx + maxDist * Math.cos(Math.toRadians(rayAngle)));
      int ey = (int) (my + maxDist * Math.sin(Math.toRadians(rayAngle)));

      // Build the ray, and declare variables for finding the MINIMUM ray.
      Line2D.Double ray = new Line2D.Double(mx, my, ex, ey);
      Color minColor = null;
      Point2D.Double minRay = null;
      double minDist = Integer.MAX_VALUE;

      // For each wall, find the wall that is the closest intersected
      // if one exists.
      for (Wall wall : walls) {
        if (wall.getLine().intersectsLine(ray)) {
          Point2D.Double rayEnd = wall.intersection(ray);
          double dist = rayEnd.distance(mx, my);
          if (dist <= minDist) {
            minDist = dist;
            minRay = rayEnd;
            minColor = wall.getColor();
          }
        }
      }

      // If we found a nearest collision, assign it to be the end-point of the ray.
      if (minRay != null) {
        ray.x2 = minRay.x;
        ray.y2 = minRay.y;
      }

      // If the ray extends beyond the separator, set that as the end-point.
      ray.x2 = ThetaUtils.clamp((int) ray.x2, 0, this.getGameWidth() / 2);
      rays.add(ray);

      // Now... draw the rectangle in pseudo-3D.
      int wallHeight = 100;
      int screenR = RaycasterUtils.perspectiveProject(r, minDist, this.getGameWidth() / 2);
      int topY = RaycasterUtils.perspectiveProject(-wallHeight / 2, minDist, this.getGameHeight());
      int bottomY = RaycasterUtils.perspectiveProject(wallHeight / 2, minDist, this.getGameHeight());
      
      ThetaGraphics.GFXContext.setColor(minColor);
      ThetaGraphics.GFXContext.drawLine(screenR + this.getGameWidth() / 2, topY, screenR + this.getGameWidth() / 2, bottomY);
    }

    return rays;
  }

  /**
   * 
   * @author Joshua
   *
   */
  private class ChangeAngleEvent extends KeyAdapter {

    private final int MAX_ANGLE = 360;

    private Raycaster raycaster;

    public ChangeAngleEvent(Raycaster raycaster) {
      this.raycaster = raycaster;
    }

    public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_A) {
        raycaster.angle = (raycaster.angle - 1) + MAX_ANGLE % MAX_ANGLE;
      }

      else if (e.getKeyCode() == KeyEvent.VK_D) {
        raycaster.angle = (raycaster.angle + 1) % MAX_ANGLE;
      }
    }
  }
}
