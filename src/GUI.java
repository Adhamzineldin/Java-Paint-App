import Shapes.PathShape;
import Shapes.Shape;
import Shapes.ShapeFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Stack;

public class GUI extends JFrame {

    ArrayList<Shape> shapes = new ArrayList<>();

    Stack<Shape> undoStack = new Stack<>();
    Color color = Color.BLACK;
    boolean isFilled = false;
    boolean isDotted = false;
    String shape = "Rectangle";
    int startx, starty, endx, endy;
    Shape currentShape = null;
    JToolBar toolbar;

    private class DrawingArea extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());

            for (Shape shape : shapes) {
                shape.paintShape(g);
            }

            if (currentShape != null) {
                currentShape.paintShape(g);
            }

        }
    }

    private void updateToolbarButtons(JToolBar toolbar) {
        boolean enableUndo = !shapes.isEmpty();
        boolean enableRedo = !undoStack.isEmpty();

        for (Component component : toolbar.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().equals("Undo")) {
                    button.setEnabled(enableUndo);
                } else if (button.getText().equals("Redo")) {
                    button.setEnabled(enableRedo);
                }
            }
        }
}

    public void createToolbar() {
        toolbar = new JToolBar();
        String[] shapeOptions = {"Rectangle", "Circle", "Triangle" ,"Pencil", "Eraser"};
        JComboBox<String> shapeSelector = new JComboBox<>(shapeOptions);

        shapeSelector.addActionListener(e -> {
            shape = (String) shapeSelector.getSelectedItem();
            boolean enableFilled = !shape.equals("Pencil");
            boolean enableUndo = !shapes.isEmpty();
            boolean enableRedo = !undoStack.isEmpty();
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
        JCheckBox dottedCheckbox = new JCheckBox("Dotted");


         filledCheckbox.addActionListener(e -> {
            isFilled = filledCheckbox.isSelected();
            if (isFilled) {
                dottedCheckbox.setSelected(false);
                isDotted = false;
            }
        });


        dottedCheckbox.addActionListener(e -> {
            isDotted = dottedCheckbox.isSelected();
            if (isDotted) {
                filledCheckbox.setSelected(false);
                isFilled = false;
            }
        });


        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            shapes.clear();
            undoStack.clear();
            updateToolbarButtons(toolbar);
            repaint();
        });

        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> {
            if (!shapes.isEmpty()) {
                undoStack.push(shapes.getLast());
                shapes.removeLast();
                updateToolbarButtons(toolbar);
                repaint();
            }
        });


        JButton redoButton = new JButton("Redo");
        redoButton.addActionListener(e -> {
            if (!undoStack.isEmpty()) {
                shapes.add(undoStack.pop());
                updateToolbarButtons(toolbar);
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
        toolbar.add(redoButton);

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
                    currentShape.addPoint(startx, starty);
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (shape.equals("Pencil") || shape.equals("Eraser")) {
                    endx = e.getX();
                    endy = e.getY();
                    currentShape.addPoint(endx, endy);
                    shapes.add(currentShape);
                    currentShape = null;
                    undoStack.clear();
                } else {
                    endx = e.getX();
                    endy = e.getY();
                    currentShape = ShapeFactory.createShape(shape, startx, starty, endx, endy, isFilled, isDotted, color);
                    shapes.add(currentShape);
                    currentShape = null;
                    undoStack.clear();

                }
                updateToolbarButtons(toolbar);
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
        updateToolbarButtons(toolbar);
    }
}
