package com.joshuacrotts;

import com.joshuacrotts.entity.CollidableEntity2D;
import com.joshuacrotts.entity.Entity2D;
import com.joshuacrotts.entity.texture.TextureCircleObject2D;
import com.joshuacrotts.entity.texture.TextureRectangleObject2D;
import com.joshuacrotts.entity.texture.TextureSprite;
import org.w3c.dom.Text;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class TileMap {

    /**
     * Each tile in the tile map is 64 x 64 pixels.
     */
    private static final int TILE_SIZE = 64;

    /**
     * List of collildable entities in our world.
     */
    private final ArrayList<CollidableEntity2D> ENTITIES;

    /**
     * List of sprites to project in the world. For now, these are non-collidable.
     */
    private final ArrayList<TextureSprite> SPRITES;

    private char[][] charMap;

    public TileMap(final String mapFile) {
        this.ENTITIES = new ArrayList<>();
        this.SPRITES = new ArrayList<>();
        this.parseFile(mapFile);
    }

    public void parseFile(final String mapFile) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(mapFile))));
        int rows = 0;
        try {
            rows= Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        int cols=0;
        try {
            cols = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.charMap=new char[rows][cols];
        int x = 0;
        int y = 0;
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                for (char ch : line.toCharArray()) {
                    charMap[x/64][y/64]=ch;
                    Entity2D entity = this.extractEntity(ch, x, y);
                    // Only add the entity if non-null.
                    if (entity != null) {
                        if (this.isEntity(ch)) {
                            this.ENTITIES.add((CollidableEntity2D) entity);
                        } else {
                            this.SPRITES.add((TextureSprite) entity);
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
        this.SPRITES.forEach(sprite -> sprite.draw(g2));
    }

    public ArrayList<CollidableEntity2D> getEntities() {
        return this.ENTITIES;
    }

    public ArrayList<TextureSprite> getSprites() {
        return this.SPRITES;
    }

    private boolean isEntity(char ch) {
        return ch >= '0' && ch <= '9';
    }

    private Entity2D extractEntity(char ch, double x, double y) {
        return switch (ch) {
            case '0',' ' -> null;
            case '1' -> new TextureRectangleObject2D(x, y, TILE_SIZE, TILE_SIZE, "bird.png");
            case '2' -> new TextureRectangleObject2D(x, y, TILE_SIZE, TILE_SIZE, "redbrick.png");
            case '3' -> new TextureRectangleObject2D(x, y, TILE_SIZE, TILE_SIZE, "purplestone.png");
            case '4' -> new TextureRectangleObject2D(x, y, TILE_SIZE, TILE_SIZE, "stonebrick.png");
            case '5' -> new TextureRectangleObject2D(x, y, TILE_SIZE, TILE_SIZE, "bluestone.png");
            case '6' -> new TextureRectangleObject2D(x, y, TILE_SIZE, TILE_SIZE, "mossystone.png");
            case '7' -> new TextureRectangleObject2D(x, y, TILE_SIZE, TILE_SIZE, "wood.png");
            case '8' -> new TextureRectangleObject2D(x, y, TILE_SIZE, TILE_SIZE, "colorstone.png");
            case '9' -> new TextureCircleObject2D(x, y, TILE_SIZE / 2.f, "redbrick.png");
            case 'B' -> new TextureSprite(x, y, TILE_SIZE, TILE_SIZE, "barrel.png");
            case 'C' -> new TextureSprite(x, y, TILE_SIZE, TILE_SIZE, "barrel_2.png");
            case 'L' -> new TextureSprite(x, y, TILE_SIZE, TILE_SIZE, "light.png");
            case 'P' -> new TextureSprite(x, y, TILE_SIZE, TILE_SIZE, "pole.png");
            case 'Q' -> new TextureSprite(x, y, TILE_SIZE, TILE_SIZE, "pole_2.png");
            case 'S' -> new TextureSprite(x, y, TILE_SIZE, TILE_SIZE, "monster.png");
            case 'T' -> new TextureSprite(x, y, TILE_SIZE, TILE_SIZE, "table.png");
            default -> throw new IllegalArgumentException("Improper entity symbol found in map " + ch);
        };
    }

    public boolean isBlock(double x, double y) {
        char c = this.charMap[(int)(x/64)][(int)(y/64)];
        return c != '0' && c != ' ';
    }
}
