package render;

import linalg.EuclideanVector;
import mesh.Mesh;
import mesh.Vertex;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Scene {
    private int width;
    private int height;
    private int[][] canvas;
    private double[][] depthBuffer;
    Camera camera;
    ArrayList<Mesh> meshes;
    public Scene(int width, int height) {
        this.width = width;
        this.height = height;
        this.canvas = new int[width][height];
        this.depthBuffer = new double[width][height];
        this.camera = new Camera(new EuclideanVector(0, 2.5, 5), new EuclideanVector(0,0,0),
                new EuclideanVector(0, -1, 0));
        this.meshes = new ArrayList<>();
    }

    public BufferedImage toBufferedImage() {
        BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
        for(int x0 = 0; x0 < this.width; x0++) {
            for(int y0 = 0; y0 < this.height; y0++) {
                image.setRGB(x0, y0, this.canvas[x0][y0]);
            }
        }
        return image;
    }

    private void setPixel(int x, int y, int color) {
        if(x < 0 || x >= this.width || y < 0 || y >= this.height) {
            return;
        }
        this.canvas[x][y] = color;
    }

    public void rasterizeLine(Vertex a, Vertex b, int color) {
        EuclideanVector aScreen = this.camera.project(a).add(new EuclideanVector(width/2, height/2));
        EuclideanVector bScreen = this.camera.project(b).add(new EuclideanVector(width/2, height/2));
        int x0 = (int) aScreen.getCoordinate(0);
        int y0 = (int) aScreen.getCoordinate(1);
        int x1 = (int) bScreen.getCoordinate(0);
        int y1 = (int) bScreen.getCoordinate(1);

        int dx = Math.abs(x1 - x0), sx = x0<x1 ? 1 : -1;
        int dy = -Math.abs(y1-y0), sy = y0<y1 ? 1 : -1;
        int err = dx+dy, e2;
        while (true) {
            this.setPixel(x0, y0, color);
            if (x0==x1 && y0==y1) break;
            e2 = 2*err;
            if (e2 > dy) { err += dy; x0 += sx; }
            if (e2 < dx) { err += dx; y0 += sy; }
        }
    }

    public void addMesh(Mesh mesh) {
        this.meshes.add(mesh);
    }

    public void render() throws Exception {
        for(int x0 = 0; x0 < this.width; x0++) {
            for(int y0 = 0; y0 < this.height; y0++) {
                this.depthBuffer[x0][y0] = 1;//Double.POSITIVE_INFINITY;
            }
        }
        for(Mesh mesh : this.meshes) {
            mesh.drawWireframe(this);
        }
    }
}
