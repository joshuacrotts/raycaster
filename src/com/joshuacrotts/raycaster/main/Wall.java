package com.joshuacrotts.raycaster.main;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Wall {

  private Line2D.Double line;
  private Color color;

  public Wall(Line2D.Double line, Color color) {
    this.line = line;
    this.color = color;
  }

  public Wall(double x1, double y1, double x2, double y2, Color color) {
    this.line = new Line2D.Double(x1, y1, x2, y2);
    ;
    this.color = color;
  }

  public Point2D.Double intersection(Line2D.Double oLine) {
    double x1 = this.line.x1;
    double y1 = this.line.y1;
    double x2 = this.line.x2;
    double y2 = this.line.y2;

    double x3 = oLine.x1;
    double y3 = oLine.y1;
    double x4 = oLine.x2;
    double y4 = oLine.y2;

    // Check if none of the lines are of length 0
    if ((x1 == x2 && y1 == y2) || (x3 == x4 && y3 == y4)) {
      return null;
    }

    int denominator = (int)((y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1));

    // Lines are parallel
    if (denominator == 0) {
      return null;
    }

    double ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / denominator;
    double ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / denominator;

    // is the intersection along the segments
    if (ua < 0 || ua > 1 || ub < 0 || ub > 1) {
      return null;
    }

    // Return a object with the x and y coordinates of the intersection
    double x = x1 + ua * (x2 - x1);
    double y = y1 + ua * (y2 - y1);

    return new Point2D.Double(x, y);
  }

  public Color getColor() {
    return this.color;
  }

  public Line2D.Double getLine() {
    return this.line;
  }
}
