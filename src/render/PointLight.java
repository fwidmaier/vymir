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
        EuclideanVector d = position.subtract(this.position);
        return d.dot(normal) * intensity/d.dot(d);
    }

    @Override
    public void setPosition(Vertex position) {
        this.position = position;
    }
}
