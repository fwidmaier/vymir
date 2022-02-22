package render;

import linalg.EuclideanVector;
import mesh.Vertex;

public class Camera {
    private EuclideanVector position;
    private EuclideanVector lock;
    private EuclideanVector upGuide;
    private EuclideanVector look = new EuclideanVector();
    private EuclideanVector up = new EuclideanVector();
    private EuclideanVector right = new EuclideanVector();
    private double zoom = 1;

    public Camera(EuclideanVector positon, EuclideanVector lock, EuclideanVector upGuide) {
        this.position = positon;
        this.lock = lock;
        this.upGuide = upGuide;
        this.update();
    }

    private void update() {
        this.look = this.lock.subtract(this.position).getNormalized();
        this.upGuide = this.upGuide.getNormalized();
        this.right = this.look.cross(this.upGuide);
        this.up = this.right.cross(this.look);
    }

    public void setPosition(EuclideanVector newPosition) {
        this.position = newPosition;
        this.update();
    }

    public void setLock(EuclideanVector newLock) {
        this.lock = newLock;
        this.update();
    }

    public EuclideanVector project(Vertex vertex) {
        EuclideanVector w = vertex.subtract(this.position);
        double d = w.subtract(this.lock).dot(this.look);
        if(d < -0.0001) {
            return null;
        }
        double x = w.dot(this.right);
        double y = w.dot(this.up);
        double c = (2.5 * this.zoom)/(d + 0.0001);
        return new EuclideanVector(x, y).getScaled(c);
    }
}
