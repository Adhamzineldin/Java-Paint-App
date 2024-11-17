package Shapes;

import java.awt.*;

public class Circle extends Shape {


    final int x = Math.min(getX1(), getX2());
    final int y = Math.min(getY1(), getY2());
    final int width = Math.abs(getX1() - getX2());
    final int height = Math.abs(getY1() - getY2());


    public Circle(int x1, int y1, int x2, int y2, boolean isFilled, boolean isDotted, Color color) {
        super(x1, y1, x2, y2, isFilled, isDotted, color);

    }

    @Override
    public void paintShape(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getColor());

        if (!getIsFilled() && getIsDotted()) {
            float[] dashPattern = {2f, 2f};
            g2d.setStroke(new BasicStroke(
                    1,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_ROUND,
                    1.0f,
                    dashPattern,
                    0f
            ));
        } else {
            g2d.setStroke(new BasicStroke(1));
        }

        if (getIsFilled()) {
            g2d.fillOval(x, y, width, height);
        } else {
            g2d.drawOval(x, y, width, height);
        }

    }
}
