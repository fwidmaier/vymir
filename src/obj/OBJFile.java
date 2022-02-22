package obj;

import mesh.Face;
import mesh.Mesh;
import mesh.Vertex;

import java.io.*;

public class OBJFile {
    private final static String HEADER = "# Automatically writen .obj file by 'vymir' " +
            "- PLEASE DO NOT MODIFY\n# (c) Felix Widmaier\n";

    public static Mesh read(String path) throws Exception {
        File file = new File(path);
        BufferedReader content = new BufferedReader(new FileReader(file));
        Mesh mesh = new Mesh();
        String line;
        while ((line = content.readLine()) != null) {
            String[] splitLine = line.split(" ");
            if(splitLine[0].equals("v")) {
                double[] coordinates = new double[splitLine.length - 1];
                for(int i = 0; i < coordinates.length; i++) coordinates[i] = Double.parseDouble(splitLine[i + 1]);
                mesh.addVertex(new Vertex(coordinates));
            } else if(splitLine[0].equals("f")) {
                int[] vertexIds = new int[splitLine.length - 1];
                for(int i = 0; i < vertexIds.length; i++) vertexIds[i] = Integer.parseInt(splitLine[i + 1]) - 1;
                mesh.addFace(new Face(vertexIds));
            }
        }
        return mesh;
    }

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
