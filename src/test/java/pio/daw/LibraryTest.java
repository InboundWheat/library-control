package pio.daw;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LibraryTest {
    private Library testLibrary;
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @BeforeEach
    public void loadLibrary() throws IOException, URISyntaxException{
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource("test-data/testInput.txt");
        assertNotNull(url, "Test Data file not found.");
        Path expectedPath = Path.of(url.toURI());
        assertDoesNotThrow(() -> {this.testLibrary = Library.fromFile(expectedPath);});
    }

    @Test
    public void getUserListTest(){
        List<String> users = this.testLibrary.getUserList().stream().map(u -> u.getId()).toList();
        List<String> userIDs = List.of(
            "U001",
            "U002",
            "U003"
        );
        for (String id : userIDs) {
            assertTrue(users.contains(id));
        }
    }

    @Test
    public void getMaxEntryUsersTest(){
        List<User> maxUsers = this.testLibrary.getMaxEntryUsers();
        assertTrue(maxUsers.size() == 1);
        assertTrue(maxUsers.get(0).getId().equals("U002"));
        assertTrue(maxUsers.get(0).getNEntries() == 2);
    }
    
    @Test
    public void registerNewEventTest(){
        this.testLibrary.registerChange("U005", EventType.ENTRY);
        assertTrue(this.testLibrary.getUserList().stream().map(u -> u.getId()).toList().contains("U005"));
    }

    @Test
    public void printResumeTest(){
        String testStr = "Usuarios actualmente dentro de la biblioteca:\n" + //
                        "U002\n" + //
                        "U003\n" + //
                        "\n" + //
                        "Número de entradas por usuario:\n" + //
                        "U001 -> 1\n" + //
                        "U002 -> 2\n" + //
                        "U003 -> 1\n" + //
                        "\n" + //
                        "Usuario(s) con más entradas:\n" + //
                        "U002";
        this.testLibrary.printResume();
        String resultStr = this.outputStreamCaptor.toString().trim();
        assertTrue(testStr.equals(resultStr));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
