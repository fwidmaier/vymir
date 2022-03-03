package render;

import linalg.EuclideanVector;

public abstract class Light {
    public abstract double intensityAt(EuclideanVector position, EuclideanVector normal);
}
