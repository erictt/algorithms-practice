package week5;

import algs4.In;
import algs4.Point2D;
import algs4.RectHV;
import algs4.StdDraw;
import algs4.StdOut;

import java.util.Set;
import java.util.TreeSet;

public class KdTree {

    private Node root;
    private final Set<Point2D> points;

    private static class Node {
        private final Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private boolean isRed = true;

        private Node(Point2D q) {
            p = q;
        }

        private void appendRectHVByParent(Node parent, boolean isLeft) {
            double x1 = parent.p.x();
            double x2 = parent.p.x();
            double y1 = parent.p.y();
            double y2 = parent.p.y();
            if (parent.isRed) {
                y1 = parent.rect.ymin();
                y2 = parent.rect.ymax();
                if (isLeft) {
                    x1 = parent.rect.xmin();
                } else {
                    x2 = parent.rect.xmax();
                }
            } else {
                x1 = parent.rect.xmin();
                x2 = parent.rect.xmax();
                if (isLeft) {
                    y1 = parent.rect.ymin();
                } else {
                    y2 = parent.rect.ymax();
                }
            }
            rect = new RectHV(x1, y1, x2, y2);
        }
    }

    public KdTree() {                              // construct an empty set of points
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
        if (contains(p)) return;

        points.add(p);
        Node node = new Node(p);
        if (root == null) {
            node.rect = new RectHV(0, 0, 1, 1);
            root = node;
        } else insert(node, root);
    }

    private void insert(Node node, Node parent) {
        if (parent.isRed && node.p.x() < parent.p.x() || !parent.isRed && node.p.y() < parent.p.y()) {
            if (parent.lb == null) {
                if (parent.isRed) node.isRed = false;
//                StdOut.printf("parent(%8.6f, %8.6f), child(%8.6f, %8.6f) %b\n", parent.p.x(), parent.p.y(), node.p.x(), node.p.y(), node.isRed);
                node.appendRectHVByParent(parent, true);
                parent.lb = node;
            } else insert(node, parent.lb);
        } else {
            if (parent.rt == null) {
                if (parent.isRed) node.isRed = false;
                node.appendRectHVByParent(parent, false);
                parent.rt = node;
//                StdOut.printf("parent(%8.6f, %8.6f), child(%8.6f, %8.6f) %b\n", parent.p.x(), parent.p.y(), node.p.x(), node.p.y(), node.isRed);
            } else insert(node, parent.rt);
        }
    }

    public boolean contains(Point2D p) {           // does the set contain point p?
        if (p == null)
            throw new IllegalArgumentException();
        return points.contains(p);
    }

    public void draw() {          // draw all points to standard draw
        draw(root);
    }

    private void draw(Node node) {
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLUE);
        node.p.draw();
        double x1 = node.p.x();
        double x2 = node.p.x();
        double y1 = node.rect.ymin();
        double y2 = node.rect.ymax();
        if (!node.isRed) {
            y1 = node.p.y();
            y2 = node.p.y();
            x1 = node.rect.xmin();
            x2 = node.rect.xmax();
        }

        Point2D p1 = new Point2D(x1, y1);
        Point2D p2 = new Point2D(x2, y2);

//        StdOut.printf("(%8.6f, %8.6f), (%8.6f, %8.6f)\n", node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax());
//        StdOut.printf("(%8.6f, %8.6f) -> (%8.6f, %8.6f)\n", x1, y1, x2, y2);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        node.rect.draw();
        p1.drawTo(p2);
        if (node.lb != null) draw(node.lb);
        if (node.rt != null) draw(node.rt);
    }

    public Iterable<Point2D> range(RectHV rect) {         // all points that are inside the rectangle (or on the boundary)
        if (rect == null)
            throw new IllegalArgumentException();
        Set<Point2D> in = new TreeSet<>();
        range(rect, root, in);
        return in;
    }

    private void range(RectHV rect, Node node, Set<Point2D> in) {
        if (node == null) return;

        if (node.rect.intersects(rect)) {
            if (rect.contains(node.p)) {
                in.add(node.p);
            }
            range(rect, node.lb, in);
            range(rect, node.rt, in);
        }
    }

    public Point2D nearest(Point2D p) {        // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null)
            throw new IllegalArgumentException();
        if (root == null)
            return null;

        return nearest(p, root, root.p);
    }

    private Point2D nearest(Point2D p, Node node, Point2D n) {
        if (node == null) return n;

        double minDis = n.distanceSquaredTo(p);
        if (!node.rect.contains(p) && node.rect.distanceSquaredTo(p) > minDis) return n;

        double dis = node.p.distanceSquaredTo(p);

        if (dis < n.distanceSquaredTo(p)) n = node.p;
//        StdOut.printf("(%8.6f, %8.6f) -> %8.6f, min: %8.6f\n", node.p.x(), node.p.y(), dis, minDis);

        if (node.lb != null && node.lb.rect.contains(p)) {
            n = nearest(p, node.lb, n);
            n = nearest(p, node.rt, n);
        } else {
            n = nearest(p, node.rt, n);
            n = nearest(p, node.lb, n);
        }

        return n;
    }

    public static void main(String[] args) {       // unit testing of the methods (optional)
        KdTree kd = new KdTree();
        In in = new In(args[0]);
        while (in.hasNextLine() && !in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            kd.insert(new Point2D(x, y));
        }
        kd.draw();
//        RectHV rh = new RectHV(0.0, 0.125, 0.625, 0.625);
//        Iterable<Point2D> ps = kd.range(rh);
//        for (Point2D p : ps) {
//            StdOut.printf("%8.6f %8.6f\n", p.x(), p.y());
//        }
        Point2D p = new Point2D(0.64, 0.06);
        StdDraw.setPenRadius(0.02);
        StdDraw.setPenColor(StdDraw.RED);
        p.draw();
        Point2D n = kd.nearest(p);
        StdOut.printf("%8.6f %8.6f\n", n.x(), n.y());
    }
}