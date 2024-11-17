package Shapes;

import java.awt.*;

public class Triangle extends Shape {

    public Triangle(int x1, int y1, int x2, int y2, boolean isFilled, boolean isDotted, Color color) {
        super(x1, y1, x2, y2, isFilled, isDotted, color);
    }

    @Override
    public void paintShape(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (getIsDotted()) {
            g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[]{9}, 0));
        } else {
            g2d.setStroke(new BasicStroke(2));
        }

        g2d.setColor(getColor());

        int[] xPoints = {getX1(), getX2(), (getX1() + getX2()) / 2};
        int[] yPoints = {getY1(), getY1(), getY2()};


        Polygon triangle = new Polygon(xPoints, yPoints, 3);


        if (getIsFilled()) {
            g2d.fill(triangle);
        } else {
            g2d.draw(triangle);
        }
    }
}
