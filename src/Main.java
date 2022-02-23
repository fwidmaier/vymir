import mesh.Cube;
import mesh.Vertex;
import obj.OBJFile;
import render.Color;
import render.Scene;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        Scene sc = new Scene(1000, 1000);
        long start = System.currentTimeMillis();
        sc.rasterizeLine(new Vertex(-5,0,0), new Vertex(5,0,0), Color.fromARGB((byte) 255, (byte) 163, (byte) 14, (byte) 66));
        sc.rasterizeLine(new Vertex(0,-5,0), new Vertex(0,5,0), Color.fromARGB((byte) 255, (byte) 142, (byte) 209, (byte) 18));
        //sc.rasterizeLine(new Vertex(0,0,0), new Vertex(0,0,1), Color.fromARGB((byte) 255, (byte) 142, (byte) 209, (byte) 18));
        //OBJFile.write(OBJFile.read("torus.obj"), "torus.obj");
        sc.addMesh(OBJFile.read("teapot.obj"));
        //sc.addMesh(new Cube(1));
        //OBJFile.write(new Cube(1), "test.obj");
        try {
            sc.render();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File file = new File("test.png");
        try {
            ImageIO.write(sc.toBufferedImage(), "PNG", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("Task took " + (end - start));
    }
}
