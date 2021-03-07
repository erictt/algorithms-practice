package week5;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;

import java.util.Set;
import java.util.TreeSet;

public class PointSET {

    private final Set<Point2D> points;

    public PointSET() {                              // construct an empty set of points
        points = new TreeSet<>();
    }

    public boolean isEmpty() {                     // is the set empty?
        return points.isEmpty();
    }

    public int size() {                     // number of points in the set
        return points.size();
    }

    public void insert(Point2D p) {             // add the point to the set (if it is not already in the set)
        if (p == null)
            throw new IllegalArgumentException();
        if (!contains(p)) points.add(p);
    }

    public boolean contains(Point2D p) {           // does the set contain point p?
        if (p == null)
            throw new IllegalArgumentException();
        return points.contains(p);
    }

    public void draw() {          // draw all points to standard draw
        for (Point2D p : points) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {         // all points that are inside the rectangle (or on the boundary)
        if (rect == null)
            throw new IllegalArgumentException();

        Set<Point2D> inPoints = new TreeSet<>();

        for (Point2D p : points) {
            if (rect.contains(p))
                inPoints.add(p);
        }
        return inPoints;
    }

    public Point2D nearest(Point2D p) {        // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null)
            throw new IllegalArgumentException();

        Point2D nearest = null;
        double minDistance = 1;
        for (Point2D q : points) {
            double dis = q.distanceSquaredTo(p);
            if (dis <= minDistance) {
                minDistance = dis;
                nearest = q;
            }
        }
        return nearest;
    }

    public static void main(String[] args) {       // unit testing of the methods (optional)
        PointSET ps = new PointSET();
        In in = new In(args[0]);
        while (in.hasNextLine() && !in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            ps.insert(new Point2D(x, y));
        }
//        ps.draw();
        RectHV rh = new RectHV(0.21875, 0.3125, 0.75, 0.59375);
        Iterable<Point2D> points = ps.range(rh);
        for (Point2D p : points) {
            StdOut.printf("%8.6f %8.6f\n", p.x(), p.y());
        }
    }
}