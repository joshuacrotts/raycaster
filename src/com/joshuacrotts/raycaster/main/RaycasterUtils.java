package com.joshuacrotts.raycaster.main;

public class RaycasterUtils {

  /**
   * 
   * @param x1
   * @param y1
   * @param x2
   * @param y2
   * @return
   */
  public static double dist(double x1, double y1, double x2, double y2) {
    return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
  }
  
  /**
   * 
   * @param coordinate
   * @param depth
   * @param bound
   * @return
   */
  public static int perspectiveProject(double ordinate, double depth, double bound) {
    double halfBound = bound * 0.5;
    double newOrdinate = (ordinate - halfBound) / depth;
    return (int) (newOrdinate + halfBound);
  }

  /**
   * 
   * @param p0_x
   * @param p0_y
   * @param p1_x
   * @param p1_y
   * @param p2_x
   * @param p2_y
   * @param p3_x
   * @param p3_y
   * @return
   */
  public static double getRayCast(double p0_x, double p0_y, double p1_x, double p1_y, double p2_x, double p2_y,
      double p3_x, double p3_y) {
    double s1_x, s1_y, s2_x, s2_y;
    
    return -1; // No collision
  }
}
