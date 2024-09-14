package fish.payara.loggingjson.it;

import java.io.IOException;

import jakarta.inject.Singleton;

import org.jboss.logmanager.ExtLogRecord;

import fish.payara.loggingjson.JsonGenerator;
import fish.payara.loggingjson.JsonProvider;

@Singleton
public class CustomJsonProvider implements JsonProvider {

    @Override
    public void writeTo(JsonGenerator generator, ExtLogRecord event) throws IOException {
        generator.writeStringField("myCustomField", "and my custom value"); // Will be added to every log, as a field on the json.
    }
}
