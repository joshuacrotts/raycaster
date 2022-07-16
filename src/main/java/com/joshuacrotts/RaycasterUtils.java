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
    private static final float RAD, DEG;
    private static final int SIN_BITS, SIN_MASK, SIN_COUNT;
    private static final float radFull, radToIndex;
    private static final float degFull, degToIndex;
    private static final float[] sin, cos;

    static {
        RAD = (float) Math.PI / 180.0f;
        DEG = 180.0f / (float) Math.PI;

        SIN_BITS = 12;
        SIN_MASK = ~(-1 << SIN_BITS);
        SIN_COUNT = SIN_MASK + 1;

        radFull = (float) (Math.PI * 2.0);
        degFull = (float) (360.0);
        radToIndex = SIN_COUNT / radFull;
        degToIndex = SIN_COUNT / degFull;

        sin = new float[SIN_COUNT];
        cos = new float[SIN_COUNT];

        for (int i = 0; i < SIN_COUNT; i++) {
            sin[i] = (float) Math.sin((i + 0.5f) / SIN_COUNT * radFull);
            cos[i] = (float) Math.cos((i + 0.5f) / SIN_COUNT * radFull);
        }

        // Four cardinal directions (credits: Nate)
        for (int i = 0; i < 360; i += 90) {
            sin[(int) (i * degToIndex) & SIN_MASK] = (float) Math.sin(i * Math.PI / 180.0);
            cos[(int) (i * degToIndex) & SIN_MASK] = (float) Math.cos(i * Math.PI / 180.0);
        }
    }

    public static final float sin(double rad) {
        return sin[(int) (rad * radToIndex) & SIN_MASK];
    }

    public static final float cos(double rad) {
        return cos[(int) (rad * radToIndex) & SIN_MASK];
    }

    public static final float sinDeg(double deg) {
        return sin[(int) (deg * degToIndex) & SIN_MASK];
    }

    public static final float cosDeg(double deg) {
        return cos[(int) (deg * degToIndex) & SIN_MASK];
    }

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