package obj;

import mesh.Face;
import mesh.Mesh;
import mesh.Vertex;
import java.io.*;

/**
 * A (simple) class to load and write wavefront .obj files.
 */
public class OBJFile {
    private final static String HEADER = "# Automatically written .obj file by 'vymir' " +
            "- PLEASE DO NOT MODIFY\n# (c) Felix Widmaier\n";

    /*@
      @ requires a.length > Math.max(i, j);
      @ ensures \result a[i] == \old a[j] && \result a[j] == \old a[i];
      @ ensures \forall k != i && k != j : \result a[k] = \old a[k];
      @*/
    /**
     * Method to swap the entries i and j in the given array.
     * @param array given array of types T
     * @param i the first index to swap
     * @param j the second index to swap
     * @param <T> some generic datatype
     */
    private static <T> void swap (T[] array, int i, int j) {
        T tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    /*@
      @ requires that the file exists...
      @*/
    /**
     * Method to read a .obj file and compile its content to a mesh-object.
     * For now, only vertices and faces are considered in reading the file.
     * @TODO: allow for pre-defined surface-normals, etc..
     *
     * @param path the path to the .obj file
     * @return a mesh compiled from the vertex/face information in the .obj file
     * @throws Exception if there is no access to the filepath or the file does not exist.
     */
    public static Mesh read(String path) throws Exception {
        File file = new File(path);
        BufferedReader content = new BufferedReader(new FileReader(file));
        Mesh mesh = new Mesh();
        String line;
        while ((line = content.readLine()) != null) {
            // we ignore multiple spaces in the line
            line = line.replaceAll("\\s+", " ");
            String[] splitLine = line.split(" ");
            if(splitLine[0].equals("v")) {
                // the last coordinate in the vector should be the "up"-direction
                swap(splitLine, 2, 3);
                double[] coordinates = new double[splitLine.length - 1];
                for(int i = 0; i < coordinates.length; i++) coordinates[i] = Double.parseDouble(splitLine[i + 1]);
                mesh.addVertex(new Vertex(coordinates));
            } else if(splitLine[0].equals("f")) {
                int[] vertexIds = new int[splitLine.length - 1];
                for(int i = 0; i < vertexIds.length; i++) {
                    vertexIds[i] = Integer.parseInt(splitLine[i + 1].split("/")[0]) - 1;
                }
                mesh.addFace(new Face(vertexIds));
            }
        }
        return mesh;
    }

    /**
     * A method to write a mesh to a given filepath.
     * For now, we only write vertices and faces.
     * @param mesh the mesh you want to write to the file
     * @param path the filepath to write the mesh to
     * @throws IOException if there is no access to the filepath i.e. the file cannot the created etc.
     */
    public static void write(Mesh mesh, String path) throws IOException {
        FileWriter fileWriter = new FileWriter(path);
        StringBuilder export = new StringBuilder();
        export.append(HEADER);
        for(Vertex vertex : mesh.getVertices()) {
            export.append(vertex.exportToObj());
        }
        for (Face face : mesh.getFaces()) {
            export.append(face.exportToObj());
        }
        fileWriter.write(export.toString());
        fileWriter.close();
    }
}
