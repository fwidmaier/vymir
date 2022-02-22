import mesh.Cube;
import obj.OBJFile;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Cube c = new Cube(1);
        try {
            OBJFile.write(c, "cube.obj");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
