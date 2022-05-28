package com.joshuacrotts.raycaster.main;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.joshuacrotts.raycaster.commands.ForwardCommand;
import com.joshuacrotts.raycaster.commands.LeftCommand;
import com.joshuacrotts.raycaster.commands.RightCommand;
import com.theta.graphic.ThetaGraphics;
import com.theta.platform.ThetaGraphicalApplication;
import com.theta.util.ThetaUtils;

import javax.imageio.ImageIO;

public class Raycaster extends ThetaGraphicalApplication {

	/**
	 * Width of JFrame.
	 */
	private static final int WIDTH = 1280;

	/**
	 * Height of JFrame.
	 */
	private static final int HEIGHT = 640;

	/**
	 * 
	 */
	private final ForwardCommand fwdCmd;

	/**
	 * 
	 */
	private final LeftCommand leftCmd;

	/**
	 * 
	 */
	private final RightCommand rightCmd;

	/**
	 * Max number of walls to generate in the program.
	 */
	private final int MAX_WALLS;

	private BufferedImage texture;

	/**
	 * Height offset of the walls.
	 */
	private final double H_OFFSET = 40.0;

	/**
	 * Maximum height that a wall can be generated at.
	 */
	private final int MAX_WALL_HEIGHT = (int) (this.getGameHeight() / 1.5);

	/**
	 * ArrayList of walls - generated walls are stored here for later comparison.
	 */
	private ArrayList<Wall> walls;

	/**
	 * Current x position of the camera.
	 */
	private double cameraX;

	/**
	 * Current y position of the camera.
	 */
	private double cameraY;

	/**
	 * Field of view for the camera.
	 */
	private int fov = 70;

	/**
	 * Current angle of the camera.
	 */
	private int angle = 0;

	public Raycaster() throws IOException {
		super(WIDTH, HEIGHT, "Raycaster");

		this.fwdCmd = new ForwardCommand(this);
		this.leftCmd = new LeftCommand(this);
		this.rightCmd = new RightCommand(this);
		this.MAX_WALLS = ThetaUtils.randomInt(10, 20);
		this.texture = ImageIO.read(new File("bird.png"));
		this.generateWalls();
	}

	@Override
	public void update() {
	}

	@Override
	public void render() {
		this.drawSky();
		this.drawFloor();
		this.drawWalls();
		this.drawRays();
		this.drawSeparator();
	}

	/**
	 * Generates the walls. Each wall is generated with a starting coordinate and an
	 * ending coordinate. Meaning that for now, each wall is just a line that has a
	 * color associated.
	 */
	private void generateWalls() {
		this.walls = new ArrayList<Wall>(MAX_WALLS);

		for (int i = 0; i < 1; i++) {
			int x = i * 32;
			int y = 128;
			int w = this.texture.getWidth();
			int h = this.texture.getHeight();
			Color color = ThetaGraphics.getRandomColor();

			this.walls.add(new WallRectangle(new Rectangle2D.Double(x, y, w, h), color));
		}

	}

	/**
	 * Draws the walls. Each wall has a line and a color denoting its existence.
	 * 
	 * @param void.
	 * 
	 * @return void.
	 */
	private void drawWalls() {
		if (this.walls != null) {
			for (Wall wall : this.walls) {
				ThetaGraphics.GFXContext.setColor(wall.getColor());
				ThetaGraphics.GFXContext.draw(wall.getShape());
			}
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
		for (Line2D.Double line : calcRays(this.walls, WIDTH / 2, 1280)) {
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
	 * Draws the sky - this is at the top-half of the screen.
	 * 
	 * @param void.
	 * 
	 * @return void.
	 */
	private void drawSky() {
		int w = this.getGameWidth() / 2;
		int h = this.getGameHeight() / 2;
		int x = this.getGameWidth() / 2 + w / 2;
		int y = this.getGameHeight() / 2 - h / 2;
		ThetaGraphics.rect(x, y, w, h, ThetaGraphics.BABY_BLUE, true, 0);
	}

	/**
	 * Draws the floor - this is at the bottom-half of the screen.
	 * 
	 * @param void.
	 * 
	 * @return void.
	 */
	private void drawFloor() {
		int w = this.getGameWidth() / 2;
		int h = this.getGameHeight() / 2;
		int x = this.getGameWidth() / 2 + w / 2;
		int y = this.getGameHeight() / 2 + h / 2;
		ThetaGraphics.rect(x, y, w, h, new Color(127, 127, 127), true, 0);
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
		double mx = this.getMouse().getMouseX();
		double my = this.getMouse().getMouseY();

		// If we exceed the right-boundary, just bail out.
		if (mx > this.getGameWidth() / 2.0) {
			return rays;
		}

		for (int r = 0; r < resolution; r++) {
			// Compute the angle of this ray, normalized to our field of view.
			double rayAngle = ThetaUtils.normalize(r, 0, resolution, angle, angle + fov);

			// Compute the coordinates of the end of this ray.
			double ex = (mx + maxDist * Math.cos(Math.toRadians(rayAngle)));
			double ey = (my + maxDist * Math.sin(Math.toRadians(rayAngle)));

			// Build the ray, and declare variables for finding the MINIMUM ray.
			Line2D.Double ray = new Line2D.Double(mx, my, ex, ey);
			Point2D.Double minRay = null;
			Color minColor = null;
			double minDist = Integer.MAX_VALUE;

			// For each wall, find the wall that is the closest intersected
			// if one exists.
			if (walls != null) {
				for (Wall wall : walls) {
					Point2D.Double rayEnd = wall.intersection(mx, my, ray);
					if (rayEnd != null) {
						double dist = rayEnd.distance(mx, my);
						if (dist <= minDist) {
							minDist = dist;
							minRay = rayEnd;
							minColor = wall.getModColor() != null ? wall.getModColor() : wall.getColor();
						}
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
			this.renderWalls(ray, r, resolution, fov, rayAngle, minDist, minColor);
		}

		return rays;
	}

	/**
	 * 
	 * @param r
	 * @param resolution
	 * @param fov
	 * @param rayAngle
	 * @param minDist
	 * @return
	 */
	private void renderWalls(Line2D.Double ray, double r, double resolution, double fov, double rayAngle,
			double minDist, Color minColor) {
		if (ray == null || texture == null)
			return;
		// Fix the fish-eye effect first (doesn't quite work).
		double ca = ThetaUtils.clamp((this.angle + fov / 2 - rayAngle), 0, 360);
		minDist = minDist * Math.cos(Math.toRadians(ca));

		// X coordinate.
		int rx = (int) ThetaUtils.normalize(r, 0, resolution, this.getGameWidth() / 2.0, this.getGameWidth());

		// Wall height calculation.
		double wallHeight = (this.getGameHeight() * H_OFFSET / minDist);

		// Y coordinate.
		double lineO = this.getGameHeight() / 2.0 - wallHeight / 2.0;
		int imgX = 0;
		final int w = 64;
		// ????????????????????????????????????????????
		if (ray.y2 != (int) ray.y2) {
			imgX = (int) ((ray.y2 / w - Math.floor(ray.y2 / w)) * this.texture.getWidth());
		} else {
			imgX = (int) ((ray.x2 / w - Math.floor(ray.x2 / w)) * this.texture.getWidth());
		}
		ThetaGraphics.GFXContext.drawImage(this.texture, rx, (int) lineO, rx + 1, (int) (wallHeight + lineO), imgX, 0,
				imgX + 1, this.texture.getHeight(), null);
	}

	public double getCameraX() {
		return cameraX;
	}

	public void setCameraX(double cameraX) {
		this.cameraX = cameraX;
	}

	public double getCameraY() {
		return cameraY;
	}

	public void setCameraY(double cameraY) {
		this.cameraY = cameraY;
	}

	public void setAngle(int a) {
		this.angle = a;
	}

	public int getAngle() {
		return this.angle;
	}

	public int getFOV() {
		return this.fov;
	}
}
