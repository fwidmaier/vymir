package mesh;

import linalg.EuclideanVector;
import obj.ObjObject;

/**
 * A class to model a vertex in euclidean space, which is basically a vector
 * with the capability to be exported to wavefront obj format.
 */
public class Vertex extends EuclideanVector implements ObjObject {
    private static final int PRECISION = 5; // the number of decimal places

    public Vertex(double ... coordinates) {
        super(coordinates);
    }

    /*@
      @ ensures this.getDimension() == 0 ==> \result == "";
      @ ensures this.getDimension() > 0 ==> \result == "v this.getCoordinate(0) ... this.getCoordinate(n)";
      @*/
    /**
     * A method to get a wavefront obj representation of the vertex.
     * @return the wavefront obj representation
     */
    @Override
    public String exportToObj() {
        String pattern = " %." + PRECISION + "f";
        if(this.getDimension() == 0) {
            return "";
        }
        StringBuilder export = new StringBuilder("v");
        for(int i = 0; i < this.getDimension(); i++) {
            export.append(String.format(pattern, this.getCoordinate(i)));
        }
        return export.toString();
    }
}
