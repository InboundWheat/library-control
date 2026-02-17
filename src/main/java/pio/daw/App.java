package pio.daw;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {

    /**
     * Parse the arguments of the program to get the library registry file
     * path. Exits the program if the args are not correct or the file does
     * not exists.
     * @param args program args.
     * @return Path to file if exists.
     */
    public static Path getPathFromArgs(String[] args) {
        if (args.length != 1) {
            System.err.println("Uso: App <ruta_fichero>");
            System.exit(1);
        }

        Path path = Paths.get(args[0]);

        if (!Files.exists(path)) {
            System.err.println("Error: el fichero no existe: " + path);
            System.exit(1);
        }

        if (!Files.isReadable(path)) {
            System.err.println("Error: el fichero no se puede leer: " + path);
            System.exit(1);
        }

        return path;
    }

    public static void main(String[] args) {
        Path p = getPathFromArgs(args);
        Controlable controler = Library.fromFile(p);
        controler.printResume();
    }
}