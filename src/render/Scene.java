package render;

import linalg.EuclideanVector;
import mesh.Mesh;
import mesh.Vertex;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 * A class to model a Scene for rendering.
 * A scene has a width/height. These are the width/height of the resulting
 * image.
 * The pixel-information (color) is stored in the 2-dim array frameBuffer.
 * The depth-information for each pixel is stored in the depthBuffer.
 */
public class Scene {
    private final int width;
    private final int height;
    private int[][] frameBuffer;
    private double[][] depthBuffer;
    private Camera camera;
    private ArrayList<Mesh> meshes;

    /*@
      @ requires width > 0 && height > 0;
      @*/
    /**
     * Constructor for a standard scene.
     * The camera is automatically placed on (2,2,2) and is locked onto (0,0,0).
     * @param width the width of the scene
     * @param height the height of the scene
     */
    public Scene(int width, int height) {
        this.width = width;
        this.height = height;
        this.frameBuffer = new int[width][height];
        this.depthBuffer = new double[width][height];
        this.camera = new Camera(new EuclideanVector(0, 5, 2), new EuclideanVector(0,0,0),
                new EuclideanVector(0, 0, -1), this);
        this.meshes = new ArrayList<>();

        for(int x0 = 0; x0 < this.width; x0++) {
            for(int y0 = 0; y0 < this.height; y0++) {
                this.depthBuffer[x0][y0] = Double.POSITIVE_INFINITY;
            }
        }
    }

    /*@
      @ ensures \result == this.width;
      @*/
    /**
     * Getter method for the width of the scene.
     * @return the width of the scene
     */
    public int getWidth() {
        return this.width;
    }

    /*@
      @ ensures \result == this.height;
      @*/
    /**
     * Getter method for the height of the scene.
     * @return the height of the scene
     */
    public int getHeight() {
        return this.height;
    }

    /*@
      @ ensures \result == this.camera;
      @*/
    /**
     * Getter for the camera of the scene
     * @return
     */
    public Camera getCamera() {
        return this.camera;
    }

    /*@
      @ ensures \forall 0 <= i < this.width, 0 <= j < this.height : result.getRBG(i, j) == this.frameBuffer[i][j];
      @*/
    /**
     * Method to take the frameBuffer and write its content pixel by pixel to a BufferedImage.
     * @return a BufferedImage compiled from the frameBuffer
     */
    public BufferedImage toBufferedImage() {
        BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
        for(int x0 = 0; x0 < this.width; x0++) {
            for(int y0 = 0; y0 < this.height; y0++) {
                image.setRGB(x0, y0, this.frameBuffer[x0][y0]);
            }
        }
        return image;
    }

    /*@
      @ requires x >= 0 && x < this.width && y >= 0 && y < this.height;
      @ ensures \result this.frameBuffer[x][y] == color;
      @*/
    /**
     * Method to set the pixel at (x,y) to the given color in the frame buffer.
     * If the referenced pixel is outside the frame buffer (aka out of bounds),
     * the method call is just ignored.
     * @param x the x component of the pixel you want to manipulate
     * @param y the y component of the pixel you want to manipulate
     * @param color the color to set the pixel to
     */
    private void setPixel(int x, int y, int color) {
        if(x < 0 || x >= this.width || y < 0 || y >= this.height) {
            return;
        }
        this.frameBuffer[x][y] = color;
    }

    /**
     * If a vertex is behind the viewing plane of the camera but another is in sight of the camera when drawing a line,
     * we would not want to project this vertex on the viewing plane, because in that case simple projection
     * would place the other point off its actual position and the line would not be displayed correctly.
     * We calculate the intersection of the viewing plane with the line between a and b and return it.
     * @param a the starting vertex for the line
     * @param b the end vertex of the line
     * @return the screen coordinates of the intersection between the line and the viewing plane
     */
    private EuclideanVector clipVertexToViewPlane(Vertex a, Vertex b) {
        EuclideanVector transformedA = a.subtract(this.camera.getLock()).subtract(this.camera.getPosition());
        EuclideanVector transformedB = b.subtract(this.camera.getLock()).subtract(this.camera.getPosition());
        EuclideanVector v = transformedB.subtract(transformedA);
        double t = -(transformedA.dot(this.camera.getLook())) / v.dot(this.camera.getLook());
        return this.camera.project(new Vertex(v.getScaled(t).add(transformedA).add(this.camera.getLook()).getCoordinates()));
    }

    /**
     * Method to draw a line between two given vertices.
     * We use bresenham's line algorithm to draw the line and interpolate
     * the depth for each pixel linearly to update the depth buffer.
     * @param a the starting vertex of the line
     * @param b the end vertex of the line
     * @param color the color of the line
     */
    public void rasterizeLine(Vertex a, Vertex b, int color) {
        // We first take the screen coordinates of the vertices
        EuclideanVector aScreen = this.camera.project(a);
        if(aScreen == null) {
            aScreen = clipVertexToViewPlane(a, b);
        }
        EuclideanVector bScreen = this.camera.project(b);
        if(bScreen == null) {
            bScreen = clipVertexToViewPlane(a, b);
        }
        // Now we use bresenham's line algorithm to draw the line
        int x0 = (int) (aScreen.getCoordinate(0) / aScreen.z());
        int y0 = (int) (aScreen.getCoordinate(1) / aScreen.z());
        int x1 = (int) (bScreen.getCoordinate(0) / bScreen.z());
        int y1 = (int) (bScreen.getCoordinate(1) / bScreen.z());

        int dx = Math.abs(x1 - x0), sx = x0<x1 ? 1 : -1;
        int dy = -Math.abs(y1 - y0), sy = y0<y1 ? 1 : -1;
        int err = dx+dy, e2;
        while (true) {
            double t;
            // We now calculate the depth of the pixel by representing the point as
            // (x0,y0) = (aScreen.x(),aScreen.y()) * (1 - t) + (bScreen.x(),bScreen.y()) * t;
            if (bScreen.x() - aScreen.x() == 0) t = (y0 - aScreen.y()) / (bScreen.y() - aScreen.y());
            else t = (x0 - aScreen.x()) / (bScreen.x() - aScreen.x());
            double depth = aScreen.z() * (1 - t) + bScreen.z() * t;
            if(x0 >= -width/2 && y0 >= -height/2 && x0 < width/2 && y0 < height/2) {
                if (depth < this.depthBuffer[x0 + width / 2][y0 + height / 2]) {
                    this.setPixel(x0 + width / 2, y0 + height / 2, color);
                    // we now update the depth buffer
                    depthBuffer[x0 + width / 2][y0 + height / 2] = depth;
                }
            }
            if (x0==x1 && y0==y1) break;
            e2 = 2*err;
            if (e2 > dy) { err += dy; x0 += sx; }
            if (e2 < dx) { err += dx; y0 += sy; }
        }
    }

    /*@
      @ ensures this.meshes.indexOf(mesh) != -1;
      @*/
    /**
     * Method to add a mesh to the scene
     * @param mesh the mesh to add to the scene for rendering
     */
    public void addMesh(Mesh mesh) {
        this.meshes.add(mesh);
    }

    /**
     * Method to render all meshes from the scene to the frame buffer
     * For now, only wireframe rendering is possible
     */
    public void render() throws Exception {
        for(Mesh mesh : this.meshes) {
            mesh.drawWireframe(this);
        }
    }
}
