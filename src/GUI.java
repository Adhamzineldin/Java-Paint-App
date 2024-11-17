import Shapes.PathShape;
import Shapes.Shape;
import Shapes.ShapeFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class GUI extends JFrame {

    ArrayList<Shape> shapes = new ArrayList<>();
    ArrayList<Shape> lines = new ArrayList<>(); // Array to store the pencil drawing
    Color color = Color.BLACK;
    boolean isFilled = false;
    boolean isDotted = false;
    String shape = "Rectangle";
    int startx, starty, endx, endy;
    Shape currentShape = null;

    private class DrawingArea extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());  // Clear the drawing area

            for (Shape shape : shapes) {
                shape.paintShape(g);
            }
            for (Shape line : lines) {
                line.paintShape(g);
            }
            if (currentShape != null) {
                currentShape.paintShape(g);
            }

        }
    }

    public void createToolbar() {
        JToolBar toolbar = new JToolBar();
        String[] shapeOptions = {"Rectangle", "Circle", "Triangle" ,"Pencil", "Eraser"};
        JComboBox<String> shapeSelector = new JComboBox<>(shapeOptions);

        shapeSelector.addActionListener(e -> {
            shape = (String) shapeSelector.getSelectedItem();
            boolean enableFilled = !shape.equals("Pencil");
            for (Component component : toolbar.getComponents()) {
                if (component instanceof JCheckBox && ((JCheckBox) component).getText().equals("Filled")) {
                    component.setEnabled(enableFilled);
                }
            }
        });

        JButton colorButton = new JButton("Color");
        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Choose Color", color);
            if (newColor != null) {
                color = newColor;
            }
        });

        JCheckBox filledCheckbox = new JCheckBox("Filled");
        filledCheckbox.addActionListener(e -> isFilled = filledCheckbox.isSelected());

        JCheckBox dottedCheckbox = new JCheckBox("Dotted");
        dottedCheckbox.addActionListener(e -> isDotted = dottedCheckbox.isSelected());

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            shapes.clear();
            lines.clear();
            repaint();
        });

        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> {
            if (!shapes.isEmpty()) {
                shapes.remove(shapes.size() - 1);
                repaint();
            }
        });

        toolbar.add(new JLabel("Paint Mode: "));
        toolbar.add(shapeSelector);
        toolbar.add(colorButton);
        toolbar.add(filledCheckbox);
        toolbar.add(dottedCheckbox);
        toolbar.add(clearButton);
        toolbar.add(undoButton);

        add(toolbar, BorderLayout.NORTH);
    }

    public GUI() {
        setTitle("Adham's Paint");
        setSize(1500, 900);
        setBackground(Color.WHITE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        DrawingArea drawingArea = new DrawingArea();
        drawingArea.setDoubleBuffered(true);
        add(drawingArea, BorderLayout.CENTER);

        drawingArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startx = e.getX();
                starty = e.getY();


                currentShape = ShapeFactory.createShape(shape, startx, starty, startx, starty, isFilled, isDotted, color);
                if (shape.equals("Pencil") || shape.equals("Eraser")) {
                    lines.add(currentShape);
                } else {
                    shapes.add(currentShape);
                }


            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (shape.equals("Pencil") || shape.equals("Eraser")) {
                    endx = e.getX();
                    endy = e.getY();
                    currentShape.addPoint(endx, endy);
                    shapes.add(currentShape);
                    lines.clear();
                    currentShape = null;
                } else {
                    endx = e.getX();
                    endy = e.getY();
                    currentShape = ShapeFactory.createShape(shape, startx, starty, endx, endy, isFilled, isDotted, color);
                    shapes.add(currentShape);
                    currentShape = null;

                }
                drawingArea.repaint();
            }
        });

        drawingArea.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (shape.equals("Pencil") || shape.equals("Eraser")) {
                    endx = e.getX();
                    endy = e.getY();
                    currentShape.addPoint(endx, endy);
                    startx = endx;
                    starty = endy;
                    drawingArea.repaint();
                } else {
                    endx = e.getX();
                    endy = e.getY();
                    currentShape = ShapeFactory.createShape(shape, startx, starty, endx, endy, isFilled, isDotted, color);
                    drawingArea.repaint();
                }
            }
        });

        createToolbar();
    }
}
