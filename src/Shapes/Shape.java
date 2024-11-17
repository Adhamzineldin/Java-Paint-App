package Shapes;

import java.awt.*;

public abstract class Shape {
    private boolean isFilled;
    private boolean isDotted;
    private Color color;

    protected final int x1;
    protected final int y1;
    protected final int x2;
    protected final int y2;


    Shape(int x1, int y1, int x2, int y2,boolean isFilled, boolean isDotted, Color color ){
        this.isFilled = isFilled;
        this.isDotted = isDotted;
        this.color = color;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }


    public void setFilled(boolean isFilled) {
        this.isFilled = isFilled;
    }

    public void setDotted(boolean isDotted) {
        this.isDotted = isDotted;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean getIsFilled() {
        return isFilled;
    }

    public boolean getIsDotted() {
        return isDotted;
    }

    public Color getColor() {
        return color;
    }



    public abstract void paintShape(Graphics g);

    public void addPoint(int x, int y) {
        // Do nothing
    }
public void clearPath() {

    }

}
