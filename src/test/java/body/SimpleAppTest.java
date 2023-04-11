package body;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class SimpleAppTest {

    @Test
    public void testAppConstructor() {
        SimpleApp app1 = new SimpleApp();
        SimpleApp app2 = new SimpleApp();
        assertEquals(app1.getMessage(), app2.getMessage());
    }

    @Test
    public void testAppMessage()
    {
        SimpleApp app = new SimpleApp();
        assertEquals("Hello World!", app.getMessage());
    }

}
