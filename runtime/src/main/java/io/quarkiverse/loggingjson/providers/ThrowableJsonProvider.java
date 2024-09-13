package io.quarkiverse.loggingjson.providers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.jboss.logmanager.ExtLogRecord;

import io.quarkiverse.loggingjson.Enabled;
import io.quarkiverse.loggingjson.JsonGenerator;
import io.quarkiverse.loggingjson.JsonProvider;
import io.quarkiverse.loggingjson.JsonWritingUtils;
import io.quarkiverse.loggingjson.StringBuilderWriter;
import io.quarkiverse.loggingjson.config.Config;

public class ThrowableJsonProvider implements JsonProvider, Enabled {

    private final String fieldName;
    private final Config.ThrowableField config;

    public ThrowableJsonProvider(Config.ThrowableField throwable) {
        this(throwable, "Throwable");
    }

    public ThrowableJsonProvider(Config.ThrowableField throwable, String defaultName) {
        this.config = throwable;
        this.fieldName = throwable.fieldName.orElse(defaultName);
        this.config.stackTrace = new Config.FieldConfig();
        this.config.stackTrace.fieldName = Optional.of("StackTrace");
        this.config.exception = new Config.FieldConfig();
        this.config.exception.fieldName = Optional.of("Exception");
    }

    @Override
    public boolean isEnabled() {
        return config.enabled.orElse(true);
    }

    @Override
    public void writeTo(JsonGenerator generator, ExtLogRecord event) throws IOException {
        if (event.getThrown() != null) {
            Map<String, String> map = new HashMap<>();
            final StringBuilderWriter w = new StringBuilderWriter();
            event.getThrown().printStackTrace(new PrintWriter(w));
            map.put(config.stackTrace.fieldName.orElse("StackTrace"), w.toString());
            map.put(config.exception.fieldName.orElse("Exception"), event.getThrown().getClass().getName());
            JsonWritingUtils.writeMapStringFields(generator, fieldName, map);
        }
    }
}
