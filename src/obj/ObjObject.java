package obj;

/**
 * Interface to model the 'abstract' concept of an .obj exportable object
 */
public interface ObjObject {
    /**
     * Returns the wavefront .obj representation of the ObjObject.
     * @return the .obj representation of the object
     */
    String exportToObj();
}
