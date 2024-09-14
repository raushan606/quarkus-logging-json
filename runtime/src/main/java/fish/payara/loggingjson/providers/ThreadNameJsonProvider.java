package fish.payara.loggingjson.providers;

import java.io.IOException;

import org.jboss.logmanager.ExtLogRecord;

import fish.payara.loggingjson.Enabled;
import fish.payara.loggingjson.JsonGenerator;
import fish.payara.loggingjson.JsonProvider;
import fish.payara.loggingjson.JsonWritingUtils;
import fish.payara.loggingjson.config.Config;

public class ThreadNameJsonProvider implements JsonProvider, Enabled {

    private final String fieldName;
    private final Config.FieldConfig config;

    public ThreadNameJsonProvider(Config.FieldConfig config) {
        this(config, "ThreadName");
    }

    public ThreadNameJsonProvider(Config.FieldConfig config, String defaultName) {
        this.config = config;
        this.fieldName = config.fieldName.orElse(defaultName);
    }

    @Override
    public void writeTo(JsonGenerator generator, ExtLogRecord event) throws IOException {
        JsonWritingUtils.writeStringField(generator, fieldName, event.getThreadName());
    }

    @Override
    public boolean isEnabled() {
        return config.enabled.orElse(true);
    }
}
