package com.joshuacrotts;

import com.joshuacrotts.entity.CollidableEntity2D;
import com.joshuacrotts.entity.texture.TextureCircleObject2D;
import com.joshuacrotts.entity.texture.TextureRectangleObject2D;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

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
        BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(mapFile))));
        int x = 0;
        int y = 0;
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                for (char ch : line.toCharArray()) {
                    switch (ch) {
                        case 'W': {
                            this.ENTITIES.add(new TextureRectangleObject2D(x, y, TILE_SIZE, TILE_SIZE, "wall1.png"));
                            break;
                        }
                        case 'R': {
                            this.ENTITIES.add(new TextureRectangleObject2D(x, y, TILE_SIZE, TILE_SIZE, "wall2.png"));
                            break;
                        }
                        case 'C': {
                            this.ENTITIES.add(new TextureCircleObject2D(x, y, TILE_SIZE / 2, "wall3.png"));
                            break;
                        }
                        case 'T': {
                            //this.ENTITIES.add(new ColorTriangleObject2D(x, y, TILE_SIZE, Color.YELLOW));
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
