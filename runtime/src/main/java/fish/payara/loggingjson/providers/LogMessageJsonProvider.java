package fish.payara.loggingjson.providers;

import java.io.IOException;

import org.jboss.logmanager.ExtFormatter;
import org.jboss.logmanager.ExtLogRecord;

import fish.payara.loggingjson.Enabled;
import fish.payara.loggingjson.JsonGenerator;
import fish.payara.loggingjson.JsonProvider;
import fish.payara.loggingjson.JsonWritingUtils;
import fish.payara.loggingjson.config.Config;

public class LogMessageJsonProvider extends ExtFormatter implements JsonProvider, Enabled {

    private final String fieldName;
    private final Config.FieldConfig config;

    public LogMessageJsonProvider(Config.FieldConfig config) {
        this.config = config;
        this.fieldName = config.fieldName.orElse("LogMessage");
    }

    @Override
    public void writeTo(JsonGenerator generator, ExtLogRecord event) throws IOException {
        JsonWritingUtils.writeStringField(generator, fieldName, formatMessage(event));
    }

    @Override
    public String format(ExtLogRecord record) {
        return null;
    }

    @Override
    public boolean isEnabled() {
        return config.enabled.orElse(true);
    }
}
