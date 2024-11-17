package Shapes;

import java.awt.*;
import java.util.ArrayList;

public class PathShape extends Shape {
    private ArrayList<Point> points = new ArrayList<>();

    public PathShape(int startX, int startY, boolean isFilled, boolean isDotted, Color color) {
        super(startX, startY, startX, startY, isFilled, isDotted, color);
        points.add(new Point(startX, startY));
    }

    @Override
    public void paintShape(Graphics g) {
        if (points.size() < 2) return;

        Graphics2D g2d = (Graphics2D) g;

        if (getIsDotted()) {
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[]{9}, 0));
        } else {
            g2d.setStroke(new BasicStroke(2));
        }


        if (getColor().equals(Color.WHITE)) {
            int eraserSize = 50;

            g2d.setStroke(new BasicStroke(eraserSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            for (Point p : points) {
                g2d.fillOval(p.x - eraserSize / 2, p.y - eraserSize / 2, eraserSize, eraserSize);  // Circle instead of a line
            }
        }

        g2d.setColor(getColor());

        for (int i = 1; i < points.size(); i++) {
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    @Override
    public void addPoint(int x, int y) {
        points.add(new Point(x, y));
    }

    @Override
    public void clearPath() {
        points.clear();
    }
}
