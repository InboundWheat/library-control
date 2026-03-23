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
            throw new IllegalArgumentException("Uso: App <ruta_fichero>");
        }
 
        Path path = Paths.get(args[0]);
 
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("Error: el fichero no existe: " + path);
        }
 
        if (!Files.isReadable(path)) {
            throw new IllegalArgumentException("Error: el fichero no se puede leer: " + path);
        }
 
        return path;
    }

    public static void main(String[] args) {
        Path p = getPathFromArgs(args);
        Controlable controler = Library.fromFile(p);
        controler.printResume();
    }
}