package fish.payara.loggingjson.providers;

import static java.util.logging.Level.SEVERE;
import static java.util.logging.Level.WARNING;

import java.io.IOException;

import org.jboss.logmanager.ExtLogRecord;

import fish.payara.loggingjson.Enabled;
import fish.payara.loggingjson.JsonGenerator;
import fish.payara.loggingjson.JsonProvider;
import fish.payara.loggingjson.JsonWritingUtils;
import fish.payara.loggingjson.config.Config;

public class LogLevelJsonProvider implements JsonProvider, Enabled {
    private final String fieldName;
    private final Config.FieldConfig config;

    public LogLevelJsonProvider(Config.FieldConfig config) {
        this(config, "Level");
    }

    public LogLevelJsonProvider(Config.FieldConfig config, String defaultName) {
        this.config = config;
        this.fieldName = config.fieldName.orElse(defaultName);
    }

    @Override
    public void writeTo(JsonGenerator generator, ExtLogRecord event) throws IOException {
        switch (event.getLevel().getName()) {
            case "ERROR":
                JsonWritingUtils.writeStringField(generator, fieldName, String.valueOf(SEVERE));
                break;
            case "WARN":
                JsonWritingUtils.writeStringField(generator, fieldName, String.valueOf(WARNING));
                break;
            default:
                JsonWritingUtils.writeStringField(generator, fieldName, event.getLevel().toString());
        }

    }

    @Override
    public boolean isEnabled() {
        return config.enabled.orElse(true);
    }
}
