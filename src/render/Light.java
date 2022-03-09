package render;

import linalg.EuclideanVector;
import mesh.Vertex;

public abstract class Light {
    public abstract double intensityAt(EuclideanVector position, EuclideanVector normal);
    public abstract void setPosition(Vertex position);
}
