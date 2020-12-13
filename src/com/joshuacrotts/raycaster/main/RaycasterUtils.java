package com.joshuacrotts.raycaster.main;

public class RaycasterUtils {

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
}
