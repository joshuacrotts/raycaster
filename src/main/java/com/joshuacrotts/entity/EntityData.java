package com.joshuacrotts.entity;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * EntityData acts as a union from C. Only one field is populated at a time and its data should be accessed
 * based on its type.
 */
public class EntityData {

    /**
     * Texture associated with the Entity.
     */
    private final BufferedImage TEXTURE;

    /**
     * Color associated with the Entity.
     */
    private final Color COLOR;

    /**
     * Type given by the EntityData constructor. Depends on what data is provided.
     */
    private final EntityDataType TYPE;

    public EntityData(final BufferedImage image) {
        this.TYPE = EntityDataType.TEXTURE;
        this.TEXTURE = image;
        this.COLOR = null;
    }

    public EntityData(final Color color) {
        this.TYPE = EntityDataType.COLOR;
        this.COLOR = color;
        this.TEXTURE = null;
    }

    public boolean isTexture() {
        return this.TYPE == EntityDataType.TEXTURE;
    }

    public boolean isColor() {
        return this.TYPE == EntityDataType.COLOR;
    }

    public BufferedImage getTexture() {
        if (!this.isTexture()) {
            throw new RuntimeException("EntityData does not contain a BufferedImage object.");
        }
        return this.TEXTURE;
    }

    public Color getColor() {
        if (!this.isColor()) {
            throw new RuntimeException("EntityData does not contain a Color object.");
        }
        return this.COLOR;
    }
}
