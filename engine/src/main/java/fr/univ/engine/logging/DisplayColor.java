package fr.univ.engine.logging;

import javafx.scene.paint.Color;

/**
 * Valid colors to use for display, these color should be
 * readable on most console hopefully.
 */
final class DisplayColor {
    /**
     * The actual valid colors list.
     */
    private final static Color[] colors = new Color[]{
            Color.rgb(229, 43, 80),
            Color.rgb(255, 191, 0),
            Color.rgb(251, 206, 177),
            Color.rgb(127, 255, 212),
            Color.rgb(0, 127, 255),
            Color.rgb(137, 207, 240),
            Color.rgb(245, 245, 220),
            Color.rgb(203, 65, 84),
            Color.rgb(0, 0, 255),
            Color.rgb(0, 149, 182),
            Color.rgb(138, 43, 226),
            Color.rgb(222, 93, 131),
            Color.rgb(205, 127, 50),
            Color.rgb(150, 75, 0),
            Color.rgb(150, 0, 24),
            Color.rgb(222, 49, 99),
            Color.rgb(0, 123, 167),
            Color.rgb(247, 231, 206),
            Color.rgb(127, 255, 0),
            Color.rgb(123, 63, 0),
            Color.rgb(184, 115, 51),
            Color.rgb(255, 127, 80),
            Color.rgb(220, 20, 60),
            Color.rgb(0, 255, 255),
            Color.rgb(237, 201, 175),
            Color.rgb(125, 249, 255),
            Color.rgb(80, 200, 120),
            Color.rgb(0, 255, 63),
            Color.rgb(255, 215, 0),
            Color.rgb(128, 128, 128),
            Color.rgb(0, 128, 0),
            Color.rgb(63, 255, 0),
            Color.rgb(255, 255, 240),
            Color.rgb(0, 168, 107),
            Color.rgb(41, 171, 135),
            Color.rgb(181, 126, 220),
            Color.rgb(255, 247, 0),
            Color.rgb(200, 162, 200),
            Color.rgb(191, 255, 0),
            Color.rgb(255, 0, 255),
            Color.rgb(255, 0, 175),
            Color.rgb(128, 0, 0),
            Color.rgb(224, 176, 255),
            Color.rgb(204, 119, 34),
            Color.rgb(128, 128, 0),
            Color.rgb(255, 102, 0),
            Color.rgb(255, 69, 0),
            Color.rgb(218, 112, 214),
            Color.rgb(255, 229, 180),
            Color.rgb(209, 226, 49),
            Color.rgb(204, 204, 255),
            Color.rgb(253, 108, 158),
            Color.rgb(204, 136, 153),
            Color.rgb(227, 11, 92),
            Color.rgb(255, 0, 0),
            Color.rgb(199, 21, 133),
            Color.rgb(255, 0, 127),
            Color.rgb(250, 128, 114),
            Color.rgb(146, 0, 10),
            Color.rgb(15, 82, 186),
            Color.rgb(255, 36, 0),
            Color.rgb(192, 192, 192),
            Color.rgb(112, 128, 144),
            Color.rgb(0, 255, 127),
            Color.rgb(210, 180, 140),
            Color.rgb(0, 128, 128),
            Color.rgb(64, 224, 208),
            Color.rgb(64, 130, 109),
            Color.rgb(255, 255, 255),
            Color.rgb(255, 255, 0)
    };

    /**
     * Number of colors.
     */
    public static final int colorsCount = colors.length;

    /**
     * Return an color based on an object's hashcode.
     * The result is not truly unique due to hash collision and limited color list.
     *
     * @param o the object to use to get the hashcode
     * @return a color base on the index
     */
    public static Color get(Object o) {
        return colors[Math.abs(o.hashCode()) % (colorsCount - 1)];
    }
}
