package Shapes;

import java.awt.*;

public class Pencil extends Shape{
    public Pencil(int x1, int y1, int x2, int y2, boolean isDotted, Color color) {
        super(x1, y1, x2, y2, false, isDotted, color);
    }

    @Override
    public void paintShape(Graphics g) {
        g.setColor(getColor());
        if (getIsDotted()) {
            Graphics2D g2d = (Graphics2D) g;
            float[] dash1 = {2f, 0f, 2f};
            BasicStroke bs1 = new BasicStroke(1, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_ROUND, 1.0f, dash1, 2f);
            g2d.setStroke(bs1);
        }
        g.drawLine(x1, y1, x2, y2);
    }
}
