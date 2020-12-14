package com.joshuacrotts.raycaster.main;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class WallLine extends Wall {

  private Line2D.Double line;

  public WallLine(Line2D.Double line, Color color) {
    super(color);
    this.line = line;
  }

  @Override
  public Point2D.Double intersection(double mx, double my, Line2D.Double oLine) {
    return RaycasterUtils.intersection(this.line, oLine);
  }

  @Override
  public Shape getShape() {
    return this.line;
  }
}
