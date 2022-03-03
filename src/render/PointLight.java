package render;

import linalg.EuclideanVector;
import mesh.Vertex;

public class PointLight extends Light{
    private Vertex position;
    private double intensity;

    public PointLight(Vertex posistion, double intensity) {
        this.position = posistion;
        this.intensity = intensity;
    }

    @Override
    public double intensityAt(EuclideanVector position, EuclideanVector normal) {
        return position.subtract(this.position).dot(normal) * intensity;
    }
}
