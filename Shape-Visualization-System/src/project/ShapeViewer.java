package project;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ShapeViewer extends JFrame {

    private final List<Shape> shapes = new ArrayList<>();
    private int currentIndex = -1;

    private final DrawPanel drawPanel;
    private final JLabel areaLabel;
    private final JButton importButton;
    private final JButton drawButton;

    public ShapeViewer() {
        super("Shape Viewer");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Center: drawing area
        drawPanel = new DrawPanel();
        add(drawPanel, BorderLayout.CENTER);

        // Bottom area: buttons + area label
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel buttonsPanel = new JPanel();
        importButton = new JButton("Import shapes file");
        drawButton = new JButton("Draw shape");
        drawButton.setEnabled(false); // disabled until a file is loaded

        buttonsPanel.add(importButton);
        buttonsPanel.add(drawButton);

        bottomPanel.add(buttonsPanel, BorderLayout.NORTH);

        areaLabel = new JLabel("Area: -");
        areaLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        bottomPanel.add(areaLabel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        // Button actions
        importButton.addActionListener(e -> importFile());
        drawButton.addActionListener(e -> drawNextShape());

        setSize(600, 500);
        setLocationRelativeTo(null); // center on screen
    }

    private void importFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            loadShapesFromFile(file);
        }
    }

    private void loadShapesFromFile(File file) {
        shapes.clear();
        currentIndex = -1;
        areaLabel.setText("Area: -");
        drawPanel.setShape(null);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+");
                String type = parts[0].toLowerCase();

                if ("circle".equals(type) && parts.length >= 2) {
                    double r = Double.parseDouble(parts[1]);
                    shapes.add(new circle(r));
                } else if ("cube".equals(type) && parts.length >= 2) {
                    double s = Double.parseDouble(parts[1]);
                    shapes.add(new Cube(s));
                } else {
                    // Unknown line → ignore or you can show a warning
                    System.out.println("Unknown or invalid line: " + line);
                }
            }

            if (shapes.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No valid shapes found in file.",
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE);
                drawButton.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Loaded " + shapes.size() + " shapes.",
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE);
                drawButton.setEnabled(true);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error reading file: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            drawButton.setEnabled(false);
        }

        repaint();
    }

    private void drawNextShape() {
        if (shapes.isEmpty()) return;

        // move to next shape (loop)
        currentIndex++;
        if (currentIndex >= shapes.size()) {
            currentIndex = 0;
        }

        Shape s = shapes.get(currentIndex);
        drawPanel.setShape(s);
        areaLabel.setText("Area: " + s.getArea());
    }

    // Panel that draws the current shape
    private static class DrawPanel extends JPanel {
        private Shape shape;

        public void setShape(Shape shape) {
            this.shape = shape;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (shape == null) return;

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            if (shape instanceof circle) {
                circle c = (circle) shape;

                // draw a big circle filling the panel (scaled, not real size)
                int diameter = Math.min(w, h) - 40;
                if (diameter < 0) diameter = 10;

                int x = (w - diameter) / 2;
                int y = (h - diameter) / 2;

                g2.drawOval(x, y, diameter, diameter);

            } else if (shape instanceof Cube) {
                // draw a simple "3D" cube
                int size = Math.min(w, h) / 3;
                if (size < 20) size = 20;

                int x = (w - size) / 2;
                int y = (h - size) / 2;

                // front square
                g2.drawRect(x, y, size, size);

                // back square
                int offset = size / 4;
                g2.drawRect(x + offset, y - offset, size, size);

                // connect corners
                g2.drawLine(x, y, x + offset, y - offset);
                g2.drawLine(x + size, y, x + size + offset, y - offset);
                g2.drawLine(x, y + size, x + offset, y + size - offset);
                g2.drawLine(x + size, y + size, x + size + offset, y + size - offset);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ShapeViewer().setVisible(true));
    }
}
