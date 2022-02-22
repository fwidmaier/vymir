package mesh;

public class Cube extends Mesh {
    public Cube(double a) {
        super();

        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++) {
                this.addVertex(new Vertex(a * Math.pow(-1, i), a * Math.pow(-1, j), a));
            }
            for(int j = 0; j < 2; j++) {
                this.addVertex(new Vertex(a * Math.pow(-1, i), a * Math.pow(-1, j), -a));
            }
        }

        try {
            this.addFace(new Face(0, 1, 3, 2));
            this.addFace(new Face(6, 7, 5, 4));
            this.addFace(new Face(1, 5, 7, 3));
            this.addFace(new Face(2, 6, 4, 0));
            this.addFace(new Face(0, 4, 5, 1));
            this.addFace(new Face(3, 7, 6, 2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
