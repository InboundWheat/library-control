package pio.daw;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

/**
 * Unit test App.
 */
public class AppTest {


    /**
     * Check for a correct path
     */
    @Test
    public void getPathFromArgsTest() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource("test-data/testInput.txt");
        assertNotNull(url, "Test Data file not found.");
        Path expectedPath = Path.of(url.toURI());
        String[] args = { expectedPath.toString() };
        Path p = App.getPathFromArgs(args).toAbsolutePath();
        System.out.printf("%-8s-> %s\n", "EXPECTED", expectedPath);
        System.out.printf("%-8s-> %s\n", "RESULT",  p);
        assertTrue(p.compareTo(expectedPath) == 0);
    }

    @Test
    public void getPathFromArgsTestBadPath() {
        String[] args = { "INVALIDPATH" };
        assertThrows(Exception.class, () -> {App.getPathFromArgs(args);});
    }

    @Test
    public void getPathFromArgsTestIncorrectArgsLen() {
        String[] args = { "arg0", "arg1", "arg2" };
        assertThrows(Exception.class, () -> {App.getPathFromArgs(args);});
    }
}

