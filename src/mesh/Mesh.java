package mesh;

import linalg.EuclideanVector;
import render.Color;
import render.Scene;
import render.Light;

import java.util.ArrayList;

/**
 * A class to model the concept of a mesh consisting of faces and vertices.
 * Allows for the calculation of surface normals and midpoints of given faces.
 */
public class Mesh {
    private ArrayList<Face> faces;
    private ArrayList<Vertex> vertices;

    public Mesh() {
        this.faces = new ArrayList<>();
        this.vertices = new ArrayList<>();
    }

    /*@
      @ requires i >= 0 && i < this.vertices.size();
      @*/
    /**
     * Returns the i-th vertex from the mesh.
     * @param i the index you want to reference
     * @return the i-th vertex from the mesh
     * @throws Exception if i is out of bounds
     */
    public Vertex getVertex(int i) throws Exception {
        if(i < 0 || i >= vertices.size()) {
            throw new Exception("The vertex-id is out of bounds!");
        }
        return this.vertices.get(i);
    }

    /*@
      @ ensures this.vertices.size() == \old this.vertices.size() + 1;
      @*/
    /**
     * Adds the vertex to the mesh.
     * @param vertex the vertex to add to the mesh
     */
    public void addVertex(Vertex vertex) {
        this.vertices.add(vertex);
    }

    /*@
      @ ensures this.faces.size() == \old this.faces.size() + 1;
      @*/
    /**
     * Adds the face to the mesh.
     * @param face the face to add to the mesh.
     */
    public void addFace(Face face) {
        this.faces.add(face);
    }

    public ArrayList<Face> getFaces() {
        return this.faces;
    }

    public ArrayList<Vertex> getVertices() {
        return this.vertices;
    }

    /*@
      @ requires faces.indexOf(face) != -1;
      @ ensures that the \result is the surface normal of the face
      @ TODO: write contract in JML.
      @*/
    /**
     * Returns the normal vector for the given face.
     * We calculate the surface normal by first calculating
     * a := v[0] - v[1] and b := v[0] - v[n] where v[i] denotes the i-th
     * vertex of the face and n is the number of vertices of the face.
     * Then the surface normal is just the normalized vector of the
     * cross product of a and b.
     * @param face the face you want to calculate the surface normal of
     * @return the surface normal vector of the face
     */
    public EuclideanVector getNormalVector(Face face) {
        if(face.getSurfaceNormal() != null) {
            return face.getSurfaceNormal();
        }
        int[] vertices = face.getVertices();
        EuclideanVector a = this.vertices.get(vertices[0]).subtract(this.vertices.get(vertices[1]));
        EuclideanVector b = this.vertices.get(vertices[0]).subtract(this.vertices.get(vertices[vertices.length - 1]));
        EuclideanVector n =  a.cross(b);
        return n.getNormalized();
    }

    /**
     * Calculates the midpoint of the given face.
     * @param face the face to calculate the midpoint of
     * @return the midpoint of the given face
     */
    public EuclideanVector getMidpoint(Face face) throws Exception {
        int[] vertices = face.getVertices();
        EuclideanVector sum = EuclideanVector.getZero(3);
        for (int i : vertices) {
            sum = sum.add(this.getVertex(i));
        }
        return sum.getScaled((double) 1 / vertices.length);
    }

    /**
     * Method to draw the wireframe of the mesh onto the scene
     * We use backface culling to speed up the process.
     * @param scene the scene to draw to
     */
    public void drawWireframe(Scene scene) throws Exception {
        for(Face f : faces) {
            for(int i = 0; i < f.getNumberOfVertices(); i++) {
                if(this.getNormalVector(f).dot(scene.getCamera().getLook()) <= 0) continue;
                scene.rasterizeLine(this.getVertex(f.getVertex(i)),
                        this.getVertex(f.getVertex((i+1) % f.getNumberOfVertices())),
                        Color.fromARGB((byte) 255, (byte) 252, (byte) 143, (byte) 0));
            }
        }
    }

    public void render(Scene scene) throws Exception {
        int white = Color.fromRGB((byte) 255, (byte) 255, (byte) 255);
        for(Face f : this.faces) {
            if(this.getNormalVector(f).dot(scene.getCamera().getLook()) <= 0) continue;
            double intensity = 0;
            for(Light light : scene.getLights()) intensity += light.intensityAt(getMidpoint(f), getNormalVector(f));
            if(intensity <= 0) intensity = 0;
            scene.rasterizeTriangle(this.getVertex(f.getVertex(0)),
                    this.getVertex(f.getVertex(1)), this.getVertex(f.getVertex(2)), Color.fromRGB((byte) (255*intensity),
                            (byte) (255*intensity), (byte) (255*intensity)));
        }
    }
}
