package linalg;

import org.junit.Test;

import static org.junit.Assert.*;

public class EuclideanVectorTest {
    @Test
    public void getDimension() {
        EuclideanVector v = new EuclideanVector(1, 2, 3);
        assertEquals(3, v.getDimension());
        EuclideanVector w = new EuclideanVector(3.141, 6);
        assertEquals(2, w.getDimension());
    }

    @Test
    public void getCoordinate() {
        EuclideanVector v = new EuclideanVector(3.141, 7.2);
        assertEquals(3.141, v.getCoordinate(0), 0.001);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void getCoordinateFail() {
        EuclideanVector v = new EuclideanVector(1);
        v.getCoordinate(3);
    }

    @Test
    public void add() {
        EuclideanVector v = new EuclideanVector(1, 2, 3);
        EuclideanVector w = new EuclideanVector(4, 5, 6);
        EuclideanVector sum = v.add(w);
        for(int i = 0; i < v.getDimension(); i++) {
            assertEquals(v.getCoordinate(i) + w.getCoordinate(i), sum.getCoordinate(i), 0.001);
        }
    }

    @Test(expected = ArithmeticException.class)
    public void addDimensionFail() {
        EuclideanVector v = new EuclideanVector(2, 3);
        EuclideanVector w = new EuclideanVector(1, 2, 3, 4);
        v.add(w);
    }

    @Test
    public void dot() {
        EuclideanVector v = new EuclideanVector(1, 2, 3);
        EuclideanVector w = new EuclideanVector(3, 4, 5);
        assertEquals(26, v.dot(w), 0.001);
    }

    @Test(expected = ArithmeticException.class)
    public void dotFail() {
        EuclideanVector v = new EuclideanVector(2, 3);
        EuclideanVector w = new EuclideanVector(1, 2, 3, 4);
        v.dot(w);
    }

    @Test
    public void getScaled() {
        double[] coordinates = {1, 2, 3};
        EuclideanVector v = new EuclideanVector(coordinates);
        EuclideanVector scaled = v.getScaled(2);
        for(int i = 0; i < v.getDimension(); i++) {
            assertEquals(2 * coordinates[i], scaled.getCoordinate(i), 0.001);
        }
    }

    @Test
    public void getMagnitude() {
        EuclideanVector v = new EuclideanVector(1, 0, 0);
        assertEquals(1, v.getMagnitude(), 0.001);
        EuclideanVector w = new EuclideanVector(2, 3, 4);
        assertEquals(5.385, w.getMagnitude(), 0.001);
    }
}