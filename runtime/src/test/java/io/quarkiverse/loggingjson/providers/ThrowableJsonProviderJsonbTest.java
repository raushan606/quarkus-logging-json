package io.quarkiverse.loggingjson.providers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;
import java.util.logging.Level;

import org.jboss.logmanager.ExtLogRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;

import io.quarkiverse.loggingjson.config.Config;

public class ThrowableJsonProviderJsonbTest extends JsonProviderBaseTest {
    @Override
    protected Type type() {
        return Type.JSONB;
    }

    @Test
    void testDefaultConfig() throws Exception {
        final Config.ThrowableField config = new Config.ThrowableField();
        config.fieldName = Optional.empty();
        config.enabled = Optional.empty();
        final ThrowableJsonProvider provider = new ThrowableJsonProvider(config);

        final RuntimeException t = new RuntimeException("Testing stackTrace");
        final ExtLogRecord event = new ExtLogRecord(Level.SEVERE, "", "");
        event.setThrown(t);
        final JsonNode result = getResultAsJsonNode(provider, event);

        String stackTrace = result.findValue("StackTrace").asText();
        Assertions.assertNotNull(stackTrace);
        Assertions.assertFalse(stackTrace.isEmpty());

        final StringWriter out = new StringWriter();
        t.printStackTrace(new PrintWriter(out));

        Assertions.assertEquals(out.toString(), stackTrace);
        Assertions.assertTrue(provider.isEnabled());

        String errorType = result.findValue("Exception").asText();
        Assertions.assertNotNull(errorType);
        Assertions.assertFalse(errorType.isEmpty());

        Assertions.assertEquals("java.lang.RuntimeException", errorType);
        Assertions.assertTrue(provider.isEnabled());
    }
}
