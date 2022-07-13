package com.joshuacrotts.main;

import com.joshuacrotts.main.entity.CollidableEntity2D;
import com.joshuacrotts.main.entity.Drawable2D;
import com.joshuacrotts.main.entity.color.ColorRectangleObject2D;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TileMap {

    /**
     * Each tile in the tile map is 64 x 64 pixels.
     */
    private static final int TILE_SIZE = 64;

    /**
     *
     */
    private final ArrayList<CollidableEntity2D> ENTITIES;

    /**
     *
     */
    private final int ROWS;

    /**
     *
     */
    private final int COLS;

    public TileMap(final String mapFile, final int rows, final int cols) {
        this.ROWS = rows;
        this.COLS = cols;
        this.ENTITIES = new ArrayList<>(this.ROWS * this.COLS);
        this.parseFile(mapFile);
    }

    public void parseFile(final String mapFile) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(mapFile));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int x = 0;
        int y = 0;
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                for (char ch : line.toCharArray()) {
                    switch (ch) {
                        case 'W': {
                            this.ENTITIES.add(new ColorRectangleObject2D(x, y, TILE_SIZE, TILE_SIZE, Color.BLUE));
                            break;
                        }
                        case 'R': {
                            this.ENTITIES.add(new ColorRectangleObject2D(x, y, TILE_SIZE, TILE_SIZE, Color.RED));
                            break;
                        }
                    }
                    x += TILE_SIZE;
                }
                x = 0;
                y += TILE_SIZE;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(final Graphics2D g2) {
        this.ENTITIES.forEach(entity -> entity.draw(g2));
    }

    public ArrayList<CollidableEntity2D> getEntities() {
        return this.ENTITIES;
    }
}
