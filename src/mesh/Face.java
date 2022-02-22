package mesh;

import obj.ObjObject;

public final class Face implements ObjObject {
    private final int[] vertices;

    /*@
      @ ensures \forall 0 <= i < vertices.length : this.vertices[i] == vertices[i];
      @*/
    /**
     * Constructor for the class Face.
     * Takes in a variable amount of vertex-ids (the reference to the actual vertex in the mesh object).
     * @param vertices list of vertex-ids of which the face consists
     */
    public Face(int ... vertices) {
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
        for(int i = 0; i < this.vertices.length; i++) {
            export.append(String.format(" %d", this.vertices[i]));
        }
        return export.toString();
    }
}
