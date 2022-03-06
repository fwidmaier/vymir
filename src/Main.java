import mesh.Vertex;
import render.Color;
import render.PointLight;
import render.Scene;
import obj.OBJFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        Scene sc = new Scene(1000, 500);
        sc.addLight(new PointLight(new Vertex(-1, 5, 2), 0.15));
        /*sc.rasterizeLine(new Vertex(-5,0,0), new Vertex(5,0,0), Color.fromARGB((byte) 255, (byte) 163, (byte) 14, (byte) 66));
        sc.rasterizeLine(new Vertex(0,-5,0), new Vertex(0,5,0), Color.fromARGB((byte) 255, (byte) 142, (byte) 209, (byte) 18));*/
        //sc.rasterizeLine(new Vertex(0,0,0), new Vertex(0,0,1), Color.fromARGB((byte) 255, (byte) 142, (byte) 209, (byte) 18));
        //OBJFile.write(OBJFile.read("torus.obj"), "torus.obj");
        sc.addMesh(OBJFile.read("teapot.obj"));
        //sc.addMesh(new Cube(1));
        //OBJFile.write(new Cube(1), "test.obj");
        try {
            long start = System.currentTimeMillis();
            sc.render();
            //sc.rasterizeTriangle(new Vertex(0, 0, 0), new Vertex(0, 1, 0), new Vertex(1, 0, 0), Color.fromRGB((byte) 255, (byte) 255, (byte) 255));
            long end = System.currentTimeMillis();
            System.out.println("Rendering took " + (end - start) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
        sc.rasterizeLine(new Vertex(-15,0,0), new Vertex(15,0,0), Color.fromARGB((byte) 255, (byte) 163, (byte) 14, (byte) 66));
        sc.rasterizeLine(new Vertex(0,-1000,0), new Vertex(0,5,0), Color.fromARGB((byte) 255, (byte) 142, (byte) 209, (byte) 18));
        File file = new File("cover.png");
        try {
            ImageIO.write(sc.toBufferedImage(), "PNG", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
