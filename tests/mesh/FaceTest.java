package mesh;

import org.junit.Test;

import static org.junit.Assert.*;

public class FaceTest {

    @Test
    public void getVertex() throws Exception {
        Face f = new Face(1, 2, 3);
        assertEquals(2, f.getVertex(1));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void getVertexFail() throws Exception {
        Face f = new Face(1);
        f.getVertex(5);
    }

    @Test
    public void exportToObj() throws Exception {
        Face f = new Face(1, 2, 3);
        assertEquals("f 1 2 3", f.exportToObj());
    }
}