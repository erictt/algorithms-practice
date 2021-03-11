package part1.week3;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {

    private LineSegment[] lineSegments = new LineSegment[0];

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        if (points == null) throw new IllegalArgumentException();
        if (points.length < 4) return;

        // make sure the loop start with the bottom
        Arrays.sort(points);

        // check null and duplicated points
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            if (i < points.length - 1 && points[i].compareTo(points[i+1]) == 0) throw new IllegalArgumentException();
        }

        int segIndex = 0;
        // loop in points
        // start with index 0, create lineSegment with all the other points and sort the lineSegment
        for (int i = 0; i < points.length; i++) {
            Point[] slopeToI = new Point[points.length - 1];
            int j = 0;
            int bump = 0;
            while (j < points.length - 1) {
                if (j == i) bump = 1;
                slopeToI[j] = points[j + bump];
                j++;
            }
            Arrays.sort(slopeToI, points[i].slopeOrder());
            int count = 1;
            Point currentPoint = slopeToI[0];
            for (int k = 1; k < slopeToI.length; k++) {
                if (points[i].slopeTo(slopeToI[k]) == points[i].slopeTo(currentPoint)) {
                    count++;
                    continue;
                }
                if (count >= 3) {
                    if (segIndex == lineSegments.length) resizeSegments();
                    if (points[i].compareTo(slopeToI[k-1]) < 0 && points[i].compareTo(slopeToI[k-count]) < 0) {
                        lineSegments[segIndex++] = new LineSegment(points[i], slopeToI[k-1]);
                    }
                }
                count = 1;
                currentPoint = slopeToI[k];
            }

            if (count >= 3) {
                if (segIndex == lineSegments.length) resizeSegments();
                if (points[i].compareTo(slopeToI[slopeToI.length-1]) < 0 && points[i].compareTo(slopeToI[slopeToI.length-count]) < 0) {
                    lineSegments[segIndex++] = new LineSegment(points[i], slopeToI[slopeToI.length-1]);
                }
            }
        }

        shrinkSegments(segIndex);
    }

    private void resizeSegments() {
        int resize = lineSegments.length == 0 ? 2 : lineSegments.length * 2;
        LineSegment[] newSegments = new LineSegment[resize];
        for (int i = 0; i < lineSegments.length; i++) {
            newSegments[i] = lineSegments[i];
        }
        lineSegments = newSegments;
    }

    private void shrinkSegments(int size) {
        LineSegment[] newSegments = new LineSegment[size];
        for (int i = 0; i < size; i++) {
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}