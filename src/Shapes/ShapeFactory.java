package Shapes;

import java.awt.Color;

public class ShapeFactory {
    public static Shape createShape(String shapeType, int startx, int starty, int endx, int endy, boolean isFilled, boolean isDotted, Color color) {
        switch (shapeType) {
            case "Rectangle":
                return new Shapes.Rectangle(startx, starty, endx, endy, isFilled, isDotted, color);
            case "Circle":
                return new Shapes.Circle(startx, starty, endx, endy, isFilled, isDotted, color);
            case "Pencil":
                return new PathShape(startx, starty, isFilled, isDotted, color);
            case "Eraser":
                return new PathShape(startx, starty, isFilled, isDotted, Color.WHITE);
            default:
                throw new IllegalArgumentException("Invalid shape type: " + shapeType);
        }
    }
}
