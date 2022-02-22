package mesh;

import org.junit.Test;

import static org.junit.Assert.*;

public class VertexTest {

    @Test
    public void exportToObj() {
        Vertex v = new Vertex(Math.PI, 2, 5);
        assertEquals("v 3.14159 2.00000 5.00000", v.exportToObj());
    }
}