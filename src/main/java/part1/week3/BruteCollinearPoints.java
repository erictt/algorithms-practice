package part1.week3;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {

    private LineSegment[] lineSegments = new LineSegment[0];

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        if (points == null) throw new IllegalArgumentException();
        if (points.length < 4) return;

        // make sure the loop start with the bottom
        Arrays.sort(points);

        // check null and duplicated points
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            if (i < points.length - 1 && points[i].compareTo(points[i+1]) == 0) throw new IllegalArgumentException();
        }

        int amount = 0;

        int i = 0, j = 1, x = 2, y = 3;
        while (i < points.length - 3) {
           if (y == points.length) {
               x += 1;
               y = x + 1;
           }
           if (x == points.length - 1) {
               j += 1;
               x = j + 1;
               y = x + 1;
           }
           if (j == points.length - 2) {
               i += 1;
               j = i + 1;
               x = j + 1;
               y = x + 1;
           }
           if (i == points.length - 3) break;

           if (points[i].slopeOrder().compare(points[x], points[j]) == 0 &&
               points[i].slopeOrder().compare(points[x], points[y]) == 0
           ) {
               Point left = points[i];
               Point right = points[i];
               for (Point p: new Point[]{points[j], points[x], points[y]}) {
                   if (left.compareTo(p) > 0) left = p;
                   if (right.compareTo(p) < 0) right = p;
               }
               if (amount == lineSegments.length) resizeSegments();
               lineSegments[amount++] = new LineSegment(left, right);
           }
           y++;
        }
        shrinkSegments(amount);
    }

    private void shrinkSegments(int size) {
        LineSegment[] newSegments = new LineSegment[size];
        for (int i = 0; i < size; i++) {
            newSegments[i] = lineSegments[i];
        }
        lineSegments = newSegments;
    }

    private void resizeSegments() {
        int resize = lineSegments.length == 0 ? 2 : lineSegments.length * 2;
        LineSegment[] newSegments = new LineSegment[resize];
        for (int i = 0; i < lineSegments.length; i++) {
            newSegments[i] = lineSegments[i];
        }
        lineSegments = newSegments;
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.length;
    }
    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(lineSegments, lineSegments.length);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
