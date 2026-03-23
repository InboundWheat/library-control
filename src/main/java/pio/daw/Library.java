package pio.daw;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Library implements Controlable {

    private Map<String, User> users;

    /**
     * Read the library register file (.txt) and create a library object
     * with the current status of the users.
     * @param path Library registry file path.
     * @return Library object.
     */
    public static Library fromFile(Path path) {
        Library library = new Library();

        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(";");
                if (parts.length != 2) continue;

                String id = parts[0].trim();
                String eventStr = parts[1].trim();

                EventType event;
                if (eventStr.equalsIgnoreCase("ENTRADA")) {
                    event = EventType.ENTRY;
                } else if (eventStr.equalsIgnoreCase("SALIDA")) {
                    event = EventType.EXIT;
                } else {
                    continue; // línea con evento desconocido, se ignora
                }

                library.registerChange(id, event);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el fichero: " + e.getMessage());
            System.exit(1);
        }

        return library;
    }

    private Library() {
        this.users = new HashMap<>();
    }

    @Override
    public void registerChange(String id, EventType e) {
        // Si el usuario no existe aún, lo creamos
        users.putIfAbsent(id, new User(id));
        users.get(id).registerNewEvent(e);
    }

    @Override
    public List<User> getCurrentInside() {
        return users.values().stream()
                .filter(User::isInside)
                .sorted(Comparator.comparing(User::getId))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getMaxEntryUsers() {
        if (users.isEmpty()) return Collections.emptyList();

        int max = users.values().stream()
                .mapToInt(User::getNEntries)
                .max()
                .orElse(0);

        return users.values().stream()
                .filter(u -> u.getNEntries() == max)
                .sorted(Comparator.comparing(User::getId))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getUserList() {
        return users.values().stream()
                .filter(u -> u.getNEntries() > 0)
                .sorted(Comparator.comparing(User::getId))
                .collect(Collectors.toList());
    }

    @Override
    public void printResume() {
        StringBuilder sb = new StringBuilder();
 
        sb.append("Usuarios actualmente dentro de la biblioteca:\n");
        getCurrentInside().forEach(u -> sb.append(u.getId()).append("\n"));
 
        sb.append("\nNúmero de entradas por usuario:\n");
        getUserList().forEach(u -> sb.append(u.getId()).append(" -> ").append(u.getNEntries()).append("\n"));
 
        sb.append("\nUsuario(s) con más entradas:\n");
        getMaxEntryUsers().forEach(u -> sb.append(u.getId()).append("\n"));
 
        System.out.print(sb.toString().stripTrailing());
    }
}