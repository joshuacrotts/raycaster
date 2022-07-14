package com.joshuacrotts.entity.texture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 */
public class TextureCache {

    /**
     *
     */
    private static final Map<String, BufferedImage> IMAGE_CACHE_LIST = new HashMap<>();

    /**
     *
     * @param imageName
     * @return
     */
    public static BufferedImage getImage(final String imageName) {
        if (IMAGE_CACHE_LIST.containsKey(imageName)) {
            return IMAGE_CACHE_LIST.get(imageName);
        }
        TextureCache.addImage(imageName);
        return TextureCache.IMAGE_CACHE_LIST.get(imageName);
    }

    /**
     *
     * @param imageName
     */
    private static void addImage(final String imageName) {
        InputStream is = TextureCache.class.getClassLoader().getResourceAsStream(imageName);
        if (is == null) {
            throw new IllegalArgumentException("input stream == null. Make sure " + imageName + " exists and is in the resources dir!");
        }
        BufferedImage image = null;
        try {
            image = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        TextureCache.IMAGE_CACHE_LIST.put(imageName, image);
    }
}
