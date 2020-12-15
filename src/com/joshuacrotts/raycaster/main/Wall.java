package com.joshuacrotts.raycaster.main;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public abstract class Wall {

  private Color color;
  
  private Color modColor;

  public Wall(Color color) {
    this.color = color;
  }
  
  public abstract Point2D.Double intersection(double mx, double my, Line2D.Double oLine);

  public abstract Shape getShape();
  
  public void setColor(Color c) {
    this.color = c;
  }
  
  public Color getColor() {
    return this.color;
  }
  
  public void setModColor(Color c) {
    this.modColor = c;
  }
  
  public Color getModColor() {
    return this.modColor;
  }
}
