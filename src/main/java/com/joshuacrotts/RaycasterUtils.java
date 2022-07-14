package com.joshuacrotts;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 */
public final class RaycasterUtils {

    /**
     * Placeholder variable for 2 * PI.
     */
    public static final double TWOPI = 2 * Math.PI;

    /**
     * Placeholder variable for half of pi.
     */
    public static final double HALFPI = Math.PI / 2;

    /**
     * @param thisLine
     * @param oLine
     * @return
     */
    public static Point2D.Double intersection(final Line2D.Double thisLine, final Line2D.Double oLine) {
        double x1 = thisLine.x1;
        double y1 = thisLine.y1;
        double x2 = thisLine.x2;
        double y2 = thisLine.y2;

        double x3 = oLine.x1;
        double y3 = oLine.y1;
        double x4 = oLine.x2;
        double y4 = oLine.y2;

        // Check if any of the lines are of length 0.
        if ((x1 == x2 && y1 == y2) || (x3 == x4 && y3 == y4)) {
            return null;
        }

        double denominator = ((y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1));

        // If the lines are parallel then there's no intersection.
        if (denominator == 0) {
            return null;
        }

        double ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / denominator;
        double ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / denominator;

        // Is the intersection along the segments?
        if (ua < 0 || ua > 1 || ub < 0 || ub > 1) {
            return null;
        }

        // Return an object with the x and y coordinates of the intersection.
        double x = x1 + ua * (x2 - x1);
        double y = y1 + ua * (y2 - y1);

        return new Point2D.Double(x, y);
    }

    public static double clamp(double n, double min, double max) {
        if (n >= min && n <= max) {
            return n;
        } else if (n < min) {
            return min;
        } else {
            return max;
        }
    }

    /**
     * @param n
     * @param oldMin
     * @param oldMax
     * @param newMin
     * @param newMax
     * @return
     */
    public static double normalize(double n, double oldMin, double oldMax, double newMin, double newMax) {
        return (((n - oldMin) * (newMax - newMin)) / (oldMax - oldMin)) + newMin;
    }

    /**
     * Normalizes a number between 0.0 and 1.0.
     *
     * @param n
     * @param oldMin
     * @param oldMax
     * @return
     */
    public static double normalize(double n, double oldMin, double oldMax) {
        return normalize(n, oldMin, oldMax, 0.0, 1.0);
    }

    /**
     * Returns a random integer between min and max.
     *
     * @param min
     * @param max
     * @return random integer
     */
    public static int randomInt(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException(" Max must be smaller than min ");
        }

        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    /**
     * Returns a random double between min and max.
     *
     * @param min
     * @param max
     * @return
     */
    public static double randomDouble(double min, double max) {
        if (min >= max) {
            throw new IllegalArgumentException(" Max must be smaller than min ");
        }

        return ThreadLocalRandom.current().nextDouble(min, max + 1);
    }
}