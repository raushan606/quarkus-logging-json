package io.quarkiverse.loggingjson.deployment;

import java.io.StringWriter;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.jboss.logmanager.handlers.ConsoleHandler;
import org.jboss.logmanager.handlers.WriterHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;

import io.quarkiverse.loggingjson.JsonFormatter;
import io.quarkus.bootstrap.logging.InitialConfigurator;
import io.quarkus.bootstrap.logging.QuarkusDelayedHandler;

public abstract class JsonDefaultFormatterBaseTest {
    private static StringWriter writer = new StringWriter();
    private static WriterHandler handler;

    static {
        System.setProperty("java.util.logging.manager", org.jboss.logmanager.LogManager.class.getName());
    }

    @BeforeAll
    static void setUp() {
        Formatter formatter = InitialConfigurator.DELAYED_HANDLER.getHandlers()[0].getFormatter();
        handler = new WriterHandler();
        handler.setFormatter(formatter);
        handler.setWriter(writer);
        InitialConfigurator.DELAYED_HANDLER.addHandler(handler);
    }

    public static JsonFormatter getJsonFormatter() {
        LogManager logManager = LogManager.getLogManager();
        Assertions.assertTrue(logManager instanceof org.jboss.logmanager.LogManager);

        QuarkusDelayedHandler delayedHandler = InitialConfigurator.DELAYED_HANDLER;
        Assertions.assertTrue(Arrays.asList(Logger.getLogger("").getHandlers()).contains(delayedHandler));
        Assertions.assertEquals(Level.ALL, delayedHandler.getLevel());

        Handler handler = Arrays.stream(delayedHandler.getHandlers())
                .filter(h -> (h instanceof ConsoleHandler))
                .findFirst().orElse(null);
        Assertions.assertNotNull(handler);
        Assertions.assertEquals(Level.WARNING, handler.getLevel());

        Formatter formatter = handler.getFormatter();
        Assertions.assertTrue(formatter instanceof JsonFormatter);
        return (JsonFormatter) formatter;
    }

    protected String[] logLines() {
        handler.flush();
        return writer.toString().split("\n");
    }

    protected void testLogOutput() throws Exception {
        JsonFormatter jsonFormatter = getJsonFormatter();

        org.slf4j.Logger log = LoggerFactory.getLogger("JsonStructuredTest");
        OffsetDateTime beforeFirstLog = OffsetDateTime.now().minusSeconds(1);

        try (MDC.MDCCloseable closeable = MDC.putCloseable("mdcKey", "mdcVal")) {
            log.error("Test {}", "message",
                    new RuntimeException("Testing stackTrace"));
        }

        OffsetDateTime afterLastLog = OffsetDateTime.now();

        ObjectMapper mapper = new ObjectMapper();
        String[] lines = logLines();

        Assertions.assertEquals(1, lines.length);
        JsonNode jsonNode = mapper.readValue(lines[0], JsonNode.class);
        Assertions.assertTrue(jsonNode.isObject());

        List<String> expectedFields = Arrays.asList(
                "Timestamp",
                "LoggerName",
                "Level",
                "LogMessage",
                "ThreadName",
                "ThreadID",
                "Throwable");
        Assertions.assertEquals(expectedFields, ImmutableList.copyOf(jsonNode.fieldNames()));

        String timestamp = jsonNode.findValue("Timestamp").asText();
        Assertions.assertNotNull(timestamp);
        OffsetDateTime logTimestamp = OffsetDateTime.parse(timestamp);
        Assertions.assertTrue(beforeFirstLog.isBefore(logTimestamp) || beforeFirstLog.isEqual(logTimestamp));
        Assertions.assertTrue(afterLastLog.isAfter(logTimestamp) || afterLastLog.isEqual(logTimestamp));

        Assertions.assertTrue(jsonNode.findValue("LoggerName").isTextual());
        Assertions.assertEquals("JsonStructuredTest", jsonNode.findValue("LoggerName").asText());

        Assertions.assertTrue(jsonNode.findValue("Level").isTextual());
        Assertions.assertEquals("ERROR", jsonNode.findValue("Level").asText());

        Assertions.assertTrue(jsonNode.findValue("LogMessage").isTextual());
        Assertions.assertEquals("Test message", jsonNode.findValue("LogMessage").asText());

        Assertions.assertTrue(jsonNode.findValue("ThreadName").isTextual());
        Assertions.assertEquals("main", jsonNode.findValue("ThreadName").asText());

        Assertions.assertTrue(jsonNode.findValue("ThreadID").isNumber());

        Assertions.assertTrue(jsonNode.findValue("StackTrace").isTextual());
        Assertions.assertTrue(jsonNode.findValue("StackTrace").asText().length() > 100);

        Assertions.assertTrue(jsonNode.findValue("Exception").isTextual());
        Assertions.assertEquals("java.lang.RuntimeException", jsonNode.findValue("Exception").asText());

        Assertions.assertTrue(jsonNode.findValue("Throwable").isObject());
        Assertions.assertTrue(jsonNode.findValue("Throwable").fields().hasNext());
    }
}
