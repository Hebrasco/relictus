package utils;

import javafx.geometry.Point2D;
import preferences.GamePreferences;

/**
 * Stores the custom relictus cursor data.
 *
 * @author Daniel Bedrich
 * @version 1.0
 */
public class CustomCursor {
    public static final String DEFAULT_CURSOR = GamePreferences.CURSOR_FILE_NAME;
    public static final Point2D DEFAULT_HOTSPOT = new Point2D(0, 0);
}
