package part2.week2;

import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver {

    private static final double MAX_ENERGY = 1000.00;

    private Picture pic;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException();
        pic = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return new Picture(pic);
    }

    // width of current picture
    public int width() {
        return pic.width();
    }

    // height of current picture
    public int height() {
        return pic.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x > pic.width() - 1 || y < 0 || y > pic.height() - 1)
            throw new IllegalArgumentException();
        if (x == 0 || x == pic.width() - 1 || y == 0 || y == pic.height() - 1)
            return MAX_ENERGY;
        Color left = pic.get(x-1, y);
        Color right = pic.get(x+1, y);
        Color above = pic.get(x, y-1);
        Color below = pic.get(x, y+1);
        return Math.sqrt(
            Math.pow(right.getBlue() - left.getBlue(), 2)
            + Math.pow(right.getRed() - left.getRed(), 2)
            + Math.pow(right.getGreen() - left.getGreen(), 2)
            + Math.pow(below.getBlue() - above.getBlue(), 2)
            + Math.pow(below.getRed() - above.getRed(), 2)
            + Math.pow(below.getGreen() - above.getGreen(), 2)
        );
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        rotatePic();
        int[] result = findVerticalSeam();
        rotatePic();
        return result;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {

        double[][] energyMap = new double[width()][height()];
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                energyMap[i][j] = energy(i, j);
            }
        }

        int[][] parents = new int[width()][height()];
        double[] accumlatedEnergyMap = new double[width()];

        for (int i = 0; i < height(); i++) {
            double[] tempAccumlatedEnergyMap = new double[width()];
            for (int j = 0; j < width(); j++) {
                if (i == 0) {
                    parents[j][i] = -1;
                } else if (j == 0 || j == width() - 1) {
                    tempAccumlatedEnergyMap[j] = accumlatedEnergyMap[j] + MAX_ENERGY;
                    parents[j][i] = j;
                } else {
                    int smallestPosition = j + smallestInThreeDouble(
                            accumlatedEnergyMap[j - 1],
                            accumlatedEnergyMap[j],
                            accumlatedEnergyMap[j + 1]
                    );
                    parents[j][i] = smallestPosition;
                    tempAccumlatedEnergyMap[j] = accumlatedEnergyMap[smallestPosition] + energyMap[j][i];
                }
            }
            accumlatedEnergyMap = tempAccumlatedEnergyMap;
        }

        int smallest = 0;
        for (int j = 1; j < width() - 1; j++) {
            if (accumlatedEnergyMap[j] < accumlatedEnergyMap[smallest])
                smallest = j;
        }

        int[] result = new int[height()];
        int current = smallest;
        for (int i = height() - 1; i >= 0; i--) {
            result[i] = current;
            current = parents[current][i];
        }

        return result;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        verifySeam(seam, width(), height());
        Picture nPic = new Picture(width(), height()-1);
        for (int i = 0; i < width(); i++) {
            int plusOne = 0;
            for (int j = 0; j < height() - 1; j++) {
                if (seam[i] == j) plusOne = 1;
                nPic.set(i, j, new Color(pic.getRGB(i, j + plusOne)));
            }
        }
        pic = nPic;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        verifySeam(seam, height(), width());
        Picture nPic = new Picture(width() - 1, height());
        for (int j = 0; j < height(); j++) {
            int plusOne = 0;
            // notice *i* should less than width() - 1 because we add one in the middle
            for (int i = 0; i < width() - 1; i++) {
                if (seam[j] == i) plusOne = 1;
                nPic.set(i, j, new Color(pic.getRGB(i + plusOne, j)));
            }
        }
        pic = nPic;
    }

    private void rotatePic() {
        Picture nPic = new Picture(height(), width());
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                nPic.set(j, i, new Color(pic.getRGB(i, j)));
            }
        }
        pic = nPic;
    }

    private void verifySeam(int[] seam, int len, int limit) {
        if (seam == null || seam.length != len || seam.length < 1)
            throw new IllegalArgumentException();
        // check the first element to save some time.
        if (seam[0] < 0 || seam[0] >= limit)
            throw new IllegalArgumentException();
        // notice here start with 1
        for (int i = 1; i < seam.length; i++) {
            // check the current and last i in seam, make sure they differ in |1|.
            if (seam[i] < 0 || seam[i] >= limit || Math.abs(seam[i] - seam[i-1]) > 1)
                throw new IllegalArgumentException();
        }
    }

    private int smallestInThreeDouble(double d1, double d2, double d3) {
        int small = -1;
        if (d1 > d2) {
            if (d2 > d3)
                small = 1;
            else small = 0;
        } else if (d1 > d3) {
            small = 1;
        }
        return small;
    }
}