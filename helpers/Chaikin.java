package helpers;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Chaikin {
    private static Point interpolat(Point p1, Point p2) {
        int x = (int) (p1.x + 0.25 * (p2.x - p1.x));
        int y = (int) (p1.y + 0.25 * (p2.y - p1.y));
        return new Point(x, y);
    }

    public static List<Point> chaikin(List<Point> points) {
        List<Point> new_points = new ArrayList<>();
        if (points.size() < 3) {
            return new_points;
        }
        new_points.add(points.get(0));
        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = interpolat(points.get(i), points.get(i + 1));
            Point p2 = interpolat(points.get(i + 1), points.get(i));
            if (i != 0) {
                new_points.add(p1);
            }
            if (i != points.size() - 2) {
                new_points.add(p2);
            }
        }
        new_points.add(points.get(points.size() - 1));
        return new_points;
    }
}
