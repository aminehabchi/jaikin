package helpers;

import static helpers.Constants.*;
import java.awt.*;
import java.util.List;

public class DrawUtils {

    public static void drawInstructionsPanel(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 100));
        g2.fillRoundRect(10, 10, 250, 100, 15, 15);

        g2.setColor(new Color(255, 255, 255, 50));
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(10, 10, 250, 100, 15, 15);

        g2.setColor(INSTRUCTION_COLOR);
        g2.setFont(TITLE_FONT);
        g2.drawString("Chaikin Curve Smoother", 20, 35);

        g2.setFont(INSTRUCTION_FONT);
        String[] instructions = {
                "• Click to add control points",
                "• Press ENTER to start animation",
                "• Press SPACE to clear canvas",
                "• Press ESC to exit"
        };

        int y = 55;
        for (String instruction : instructions) {
            g2.drawString(instruction, 20, y);
            y += 15;
        }
    }

    public static void drawStyledOriginalPoints(Graphics2D g2, List<Point> points) {
        if (points.size() > 1) {
            g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.setColor(new Color(ORIGINAL_POINT_COLOR.getRed(), ORIGINAL_POINT_COLOR.getGreen(), ORIGINAL_POINT_COLOR.getBlue(), 80));
            for (Point p : points) {
                g2.drawLine(p.x, p.y, p.x, p.y);
            }
        }

        for (Point p : points) {
            g2.setColor(new Color(ORIGINAL_POINT_COLOR.getRed(), ORIGINAL_POINT_COLOR.getGreen(), ORIGINAL_POINT_COLOR.getBlue(), 50));
            g2.fillOval(p.x - 6, p.y - 6, 12, 12);

            g2.setColor(ORIGINAL_POINT_COLOR);
            g2.fillOval(p.x - 3, p.y - 3, 6, 6);

            g2.setColor(new Color(255, 255, 255, 200));
            g2.fillOval(p.x - 1, p.y - 1, 2, 2);
        }
    }

    public static void drawStyledSmoothedCurve(Graphics2D g2, List<Point> points) {
        g2.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(new Color(0, 0, 0, 50));
        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            g2.drawLine(p1.x + 2, p1.y + 2, p2.x + 2, p2.y + 2);
        }

        g2.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(SMOOTHED_CURVE_COLOR);
        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            g2.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

        g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(new Color(255, 255, 255, 100));
        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            g2.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    public static void drawStatusInfo(Graphics2D g2, int step, int count, int width) {
        String status = String.format("Iteration: %d/7 | Points: %d", step, count);
        FontMetrics fm = g2.getFontMetrics(INSTRUCTION_FONT);
        int textWidth = fm.stringWidth(status);

        g2.setColor(new Color(0, 0, 0, 120));
        g2.fillRoundRect(width - textWidth - 30, 15, textWidth + 20, 30, 10, 10);

        g2.setColor(new Color(255, 255, 255, 80));
        g2.setStroke(new BasicStroke(1.0f));
        g2.drawRoundRect(width - textWidth - 30, 15, textWidth + 20, 30, 10, 10);

        g2.setColor(new Color(150, 255, 150));
        g2.setFont(INSTRUCTION_FONT);
        g2.drawString(status, width - textWidth - 20, 35);
    }
}
