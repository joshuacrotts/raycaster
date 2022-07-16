package com.joshuacrotts;

import com.joshuacrotts.entity.CollidableEntity2D;
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

    public TileMap(final String mapFile) {
        this.ENTITIES = new ArrayList<>();
        this.SPRITES = new ArrayList<>();
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
                        case '1': {
                            this.ENTITIES.add(new TextureRectangleObject2D(x, y, TILE_SIZE, TILE_SIZE, "bird.png"));
                            break;
                        }
                        case '2': {
                            this.ENTITIES.add(new TextureRectangleObject2D(x, y, TILE_SIZE, TILE_SIZE, "redbrick.png"));
                            break;
                        }
                        case '3': {
                            this.ENTITIES.add(new TextureRectangleObject2D(x, y, TILE_SIZE, TILE_SIZE, "purplestone.png"));
                            break;
                        }
                        case '4': {
                            this.ENTITIES.add(new TextureRectangleObject2D(x, y, TILE_SIZE, TILE_SIZE, "stonebrick.png"));
                            break;
                        }
                        case '5': {
                            this.ENTITIES.add(new TextureRectangleObject2D(x, y, TILE_SIZE, TILE_SIZE, "bluestone.png"));
                            break;
                        }
                        case '6': {
                            this.ENTITIES.add(new TextureRectangleObject2D(x, y, TILE_SIZE, TILE_SIZE, "mossystone.png"));
                            break;
                        }
                        case '7': {
                            this.ENTITIES.add(new TextureRectangleObject2D(x, y, TILE_SIZE, TILE_SIZE, "wood.png"));
                            break;
                        }
                        case '8': {
                            this.ENTITIES.add(new TextureRectangleObject2D(x, y, TILE_SIZE, TILE_SIZE, "colorstone.png"));
                            break;
                        }
                        case '9': {
                            this.ENTITIES.add(new TextureCircleObject2D(x, y, TILE_SIZE / 2.f, "redbrick.png"));
                            break;
                        }
                        case 'S': {
                            this.SPRITES.add(new TextureSprite(x, y, TILE_SIZE, TILE_SIZE, "monster.png"));
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
        this.SPRITES.forEach(sprite -> sprite.draw(g2));
    }

    public ArrayList<CollidableEntity2D> getEntities() {
        return this.ENTITIES;
    }

    public ArrayList<TextureSprite> getSprites() {
        return this.SPRITES;
    }
}
