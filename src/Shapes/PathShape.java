package Shapes;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

public class PathShape extends Shape {
    private final ArrayList<Point> points = new ArrayList<>();
    private final GeneralPath path = new GeneralPath();
    private static final int MIN_DISTANCE = 1;
    private final int eraserSize = 40;
    public PathShape(int startX, int startY, boolean isFilled, boolean isDotted, Color color) {
        super(startX, startY, startX, startY, isFilled, isDotted, color);
        addPoint(startX, startY);
    }

    @Override
    public void paintShape(Graphics g) {
        if (points.size() < 2) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getColor());

        g2d.setStroke(getIsDotted()
                ? new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[]{9}, 0)
                : new BasicStroke(2));

        if (getColor().equals(Color.WHITE)) {
            g2d.setStroke(new BasicStroke(eraserSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        }

        g2d.draw(path);
    }

    @Override
    public void addPoint(int x, int y) {
        if (points.isEmpty()) {
            points.add(new Point(x, y));
            path.moveTo(x, y);
            return;
        }

        Point lastPoint = points.getLast();
        double distance = lastPoint.distance(x, y);


        int distanceThreshold = getColor().equals(Color.WHITE) ? eraserSize / 2 : MIN_DISTANCE;

        if (distance >= distanceThreshold) {
            Point newPoint = new Point(x, y);
            boolean isWithinMargin = points.stream()
                                       .anyMatch(p -> p.distance(newPoint) < distanceThreshold);

            if (!isWithinMargin) {
                points.add(newPoint);
                path.lineTo(x, y);
            }
            }

    }


    @Override
    public void clearPath() {
        points.clear();
        path.reset();
    }
}
