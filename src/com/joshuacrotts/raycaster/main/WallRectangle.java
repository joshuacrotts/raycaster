package com.joshuacrotts.raycaster.main;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;

public class WallRectangle extends Wall {

  private Rectangle2D rect;

  public WallRectangle(Rectangle2D rect, Color color) {
    super(color);
    this.rect = rect;
  }

  @Override
  public Point2D.Double intersection(double cx, double cy, Line2D.Double oLine) {
    ArrayList<WallRectangleLine> wallLines = new ArrayList<>();
    Line2D.Double lLine = new Line2D.Double(rect.getX(), rect.getY(), rect.getX(), rect.getY() + rect.getHeight());
    Line2D.Double rLine = new Line2D.Double(rect.getX() + rect.getWidth(), rect.getY(), rect.getX() + rect.getWidth(),
        rect.getY() + rect.getHeight());
    Line2D.Double tLine = new Line2D.Double(rect.getX(), rect.getY(), rect.getX() + rect.getWidth(), rect.getY());
    Line2D.Double bLine = new Line2D.Double(rect.getX(), rect.getY() + rect.getHeight(), rect.getX() + rect.getWidth(),
        rect.getY() + rect.getHeight());

    wallLines.add(new WallRectangleLine(lLine, lLine.ptSegDist(cx, cy), 0));
    wallLines.add(new WallRectangleLine(rLine, rLine.ptSegDist(cx, cy), 2));
    wallLines.add(new WallRectangleLine(tLine, tLine.ptSegDist(cx, cy), 1));
    wallLines.add(new WallRectangleLine(bLine, bLine.ptSegDist(cx, cy), 3));

    Collections.sort(wallLines);

    for (WallRectangleLine l : wallLines) {
      if (l.getLine().intersectsLine(oLine)) {
        Raycaster.color = l.getID() == 0 ? this.getColor().darker().darker() : l.getID() == 1 || l.getID() == 3 ? this.getColor().darker() : this.getColor();
        return RaycasterUtils.intersection(l.getLine(), oLine);
      }
    }

    return null;
  }

  @Override
  public Shape getShape() {
    return this.rect;
  }

  /**
   * 
   * @author joshuacrotts
   */
  private class WallRectangleLine implements Comparable<WallRectangleLine> {

    private Line2D.Double line;
    private double dist;
    private int id;

    public WallRectangleLine(Line2D.Double line, double dist, int id) {
      this.line = line;
      this.dist = dist;
      this.id = id;
    }

    @Override
    public int compareTo(WallRectangleLine oWallRect) {
      if (this.dist < oWallRect.dist) {
        return -1;
      } else if (this.dist > oWallRect.dist) {
        return 1;
      }

      return 0;
    }

    public int getID() {
      return this.id;
    }

    public Line2D.Double getLine() {
      return this.line;
    }
  }
}
