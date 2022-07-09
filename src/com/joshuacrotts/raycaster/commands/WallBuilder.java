package com.joshuacrotts.raycaster.commands;

import com.joshuacrotts.raycaster.main.Raycaster;
import com.joshuacrotts.raycaster.main.WallRectangle;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class WallBuilder extends MouseAdapter {

    private final Raycaster raycaster;

    public WallBuilder(Raycaster raycaster) {
        this.raycaster = raycaster;
    }

    public void mousePressed(MouseEvent e) {
        int ex = e.getX();
        int ey = e.getY();
        int textureSize = this.raycaster.getTextureSize();
        this.raycaster.getWalls().add(new WallRectangle(new Rectangle2D.Double(ex - textureSize / 2, ey - textureSize / 2, textureSize, textureSize), Color.RED));
    }
}
