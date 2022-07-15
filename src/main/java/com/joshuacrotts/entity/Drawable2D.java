package com.joshuacrotts.entity;

import java.awt.*;

public interface Drawable2D {

    /**
     * Defines the 2D representation, i.e., how the object should look in the top-down perspective. This does NOT
     * correlate with the 3D projection.
     *
     * @param g2 Graphics object.
     */
    void draw(final Graphics2D g2);
}
