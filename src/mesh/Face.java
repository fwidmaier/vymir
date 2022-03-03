package mesh;

import linalg.EuclideanVector;
import obj.ObjObject;

public final class Face implements ObjObject {
    private final int[] vertices;
    private EuclideanVector surfaceNormal;

    /*@
      @ ensures \forall 0 <= i < vertices.length : this.vertices[i] == vertices[i];
      @ ensures 3 <= this.vertices.length <= 4;
      @*/
    /**
     * Constructor for the class Face.
     * Takes in a variable amount of vertex-ids (the reference to the actual vertex in the mesh object).
     * @param vertices list of vertex-ids of which the face consists
     */
    public Face(int ... vertices) throws Exception {
        if(vertices.length < 3 || vertices.length > 4) {
            throw new Exception("The number of vertices has to be 3 or 4.");
        }
        this.vertices = new int[vertices.length];
        System.arraycopy(vertices, 0, this.vertices, 0, vertices.length);
    }

    /*@
      @ requires i >= 0 && i < this.vertices.length;
      @ ensures \result == this.vertices[i];
      @*/
    /**
     * Returns the i-th vertex stored in the face. The faces are counted counterclockwise.
     * @param i the i-th vertex-id you want to reference
     * @return the i-th vertex-id of the face.
     */
    public int getVertex(int i) throws ArrayIndexOutOfBoundsException {
        if(i < 0 || i >= this.vertices.length) {
            throw new ArrayIndexOutOfBoundsException("The vertex id you tried to reference is out of bounds!");
        }
        return this.vertices[i];
    }

    /*@
      @ ensures \forall 0 <= i < this.vertices.length : \result[i] == this.vertices[i];
      @*/
    /**
     * Returns an array of the vertex-ids of which the face consists. The faces are counted counterclockwise.
     * @return the array of vertex-ids of which the face consists
     */
    public int[] getVertices() {
        int[] vertices = new int[this.vertices.length];
        System.arraycopy(this.vertices, 0, vertices, 0, vertices.length);
        return vertices;
    }

    /*@
      @ ensures \result == this.vertices.length;
      @*/
    /**
     * Returns the number of vertices of which the face is consisting.
     * @return the number of vertices of the face
     */
    public int getNumberOfVertices() {
        return this.vertices.length;
    }

    /*@
      @ ensures this.vertices.length == 0 ==> \result == "";
      @ ensures this.getDimension() > 0 ==> \result == "v this.vertices[0] ... this.vertices[i]";
      @*/
    /**
     * A method to get a wavefront obj representation of the face.
     * @return the wavefront obj representation
     */
    @Override
    public String exportToObj() {
        if(this.vertices.length == 0) {
            return "";
        }
        StringBuilder export = new StringBuilder("f");
        for (int vertex : this.vertices) {
            export.append(String.format(" %d", vertex + 1));
        }
        export.append("\n");
        return export.toString();
    }

    public void setSurfaceNormal(EuclideanVector newNormal) {
        this.surfaceNormal = newNormal;
    }

    public EuclideanVector getSurfaceNormal() {
        return this.surfaceNormal;
    }
}
