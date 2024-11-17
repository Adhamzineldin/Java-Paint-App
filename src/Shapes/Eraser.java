package Shapes;

import java.awt.*;

public class Eraser extends Shape {
    private final Color backgroundColor;

    public Eraser(int x1, int y1, int x2, int y2, Color backgroundColor) {
        super(x1, y1, x2, y2, true, false, backgroundColor);
        this.backgroundColor = backgroundColor; // Always use the background color
    }

    @Override
    public void paintShape(Graphics g) {
        g.setColor(backgroundColor);
        int x = Math.min(x1, x2);
        int y = Math.min(y1, y2);
        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);

        g.fillOval(x, y, width+20, height+20);
    }
}
