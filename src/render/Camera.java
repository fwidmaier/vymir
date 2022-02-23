package render;

import linalg.EuclideanVector;
import mesh.Vertex;

/**
 * A class to implement a (very simple) perspective camera.
 */
public class Camera {
    private EuclideanVector position;
    private EuclideanVector lock;
    private EuclideanVector upGuide;
    private EuclideanVector look = new EuclideanVector();
    private EuclideanVector up = new EuclideanVector();
    private EuclideanVector right = new EuclideanVector();
    private final Scene scene;
    private double zoom = 1;

    /*@
      @ requires position.getDimension() == 3 && lock.getDimension() == 3 && upGuide.getDimension() == 3;
      @*/
    /**
     * Constructor for the camera.
     * A camera has a position, a locked point (all coordinates are given relative to that point)
     * and an upGuide which encodes the "up" direction for the camera.
     * From these, we calculate an orthonormal basis (right, up) of the viewing plane which
     * may be completed to a basis of R^3 with the look vector.
     * @param positon the position of the camera in R^3
     * @param lock the locked point for the camera
     * @param upGuide the "up" direction for the camera
     * @param scene the scene in which the camera is a part of
     */
    public Camera(EuclideanVector positon, EuclideanVector lock, EuclideanVector upGuide, Scene scene) {
        this.position = positon;
        this.lock = lock;
        this.upGuide = upGuide;
        this.scene = scene;
        this.update();
    }

    /**
     * Calculates the orthonormal basis (right, up, look) for R^3 with given position,
     * lock and upGuide.
     */
    private void update() {
        this.look = this.lock.subtract(this.position).getNormalized();
        this.upGuide = this.upGuide.getNormalized();
        this.right = this.look.cross(this.upGuide);
        this.up = this.right.cross(this.look);
        this.zoom = Math.min(this.scene.getWidth(), this.scene.getHeight()) >> 1;
    }

    /*@
      @ ensures this.position == newPosition;
      @*/
    /**
     * Method to update the position of the camera and update its parameters
     * @param newPosition the new position of the camera
     */
    public void setPosition(EuclideanVector newPosition) {
        this.position = newPosition;
        this.update();
    }

    /**
     * Method to update the lock for the camera.
     * The lock point is the point from which all coordinates are given relative to.
     * @param newLock the new lock point for the camera
     */
    public void setLock(EuclideanVector newLock) {
        this.lock = newLock;
        this.update();
    }

    /*@
      @ ensures \result == this.lock;
      @*/
    /**
     * Getter method for the lock point
     * @return the lock point of the camera
     */
    public EuclideanVector getLock() {
        return this.lock;
    }

    /*@
      @ ensures \result == this.position;
      @*/
    /**
     * Getter method for the position of the camera
     * @return the position of the camera
     */
    public EuclideanVector getPosition() {
        return this.position;
    }

    /*@
      @ ensures \result == this.look;
      @*/
    /**
     * Getter method for the look vector of the camera
     * @return the look vector of the camera
     */
    public EuclideanVector getLook() {
        return this.look;
    }

    /**
     * Calculates screen coordinates (orthogonal projection) and depth of the given vertex.
     * If the vertex is not in sight, null will be returned.
     * @param vertex the vertex you want to know the screen coordinates of
     * @return A vector consisting of the screen coordinates (x, y, depth) and the depth of the
     *         vertex.
     */
    public EuclideanVector project(Vertex vertex) {
        EuclideanVector w = vertex.subtract(this.position);
        double depth = (w.subtract(this.lock)).dot(this.look);
        if(depth < -0.0001) {
            return null;
        }
        double x = w.dot(this.right);
        double y = w.dot(this.up);
        return new EuclideanVector(this.zoom*x, this.zoom*y, depth);
    }
}
