package pio.daw;

public class User implements Localizable {
    private String id;
    private EventType lastEvent = null;
    private Boolean inside = false;
    private int entryCount = 0;

    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    /**
     * Registers an event for this user, handling:
     * - Duplicate entries (already inside -> ignore)
     * - Exits without prior entry (not inside -> ignore)
     */
    public void registerEvent(EventType e) {
        if (e == EventType.ENTRY) {
            if (!inside) {
                inside = true;
                entryCount++;
            }
            // Entrada duplicada: ya está dentro, se ignora
        } else if (e == EventType.EXIT) {
            if (inside) {
                inside = false;
            }
            // Salida sin entrada previa: no está dentro, se ignora
        }
        lastEvent = e;
    }

    /**
     * Returns the number of times this user has entered.
     */
    public int getEntryCount() {
        return entryCount;
    }

    @Override
    public Boolean isInside() {
        return inside;
    }

    @Override
    public String toString() {
        return id;
    }
}