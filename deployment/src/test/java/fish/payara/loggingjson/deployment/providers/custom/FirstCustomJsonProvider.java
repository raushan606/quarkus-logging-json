package fish.payara.loggingjson.deployment.providers.custom;

import java.io.IOException;

import jakarta.enterprise.context.ApplicationScoped;

import org.jboss.logmanager.ExtLogRecord;

import fish.payara.loggingjson.JsonGenerator;
import fish.payara.loggingjson.JsonProvider;

@ApplicationScoped
public class FirstCustomJsonProvider implements JsonProvider {
    private long writeToNumberOfCalls = 0;

    @Override
    public void writeTo(JsonGenerator generator, ExtLogRecord event) throws IOException {
        writeToNumberOfCalls++;
        generator.writeNumberField("first", 1);
    }

    public long getWriteToNumberOfCalls() {
        return writeToNumberOfCalls;
    }
}
