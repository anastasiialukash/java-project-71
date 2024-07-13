package hexlet.code;

import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DifferTest {
    Logger logger = Logger.getLogger(Differ.class.getName());
    @Test
    public void differJsonTest() {
        logger.info("Starting test");
        String a = "dfsdfsdf";
        String b = "dfsdfsdf";
        assertEquals(a, b);
        logger.info("Finished test");
    }

    @Test
    public void differJsonTest0() {
        logger.info("Starting test");
        String a = "dfsdfsdf";
        String b = "dfsdfsdf";
        assertEquals(a, b);
        logger.info("Finished test");
    }
}
