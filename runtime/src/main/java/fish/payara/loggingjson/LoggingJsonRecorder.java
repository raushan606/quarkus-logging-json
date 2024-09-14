package fish.payara.loggingjson;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Formatter;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fish.payara.loggingjson.config.Config;
import fish.payara.loggingjson.config.ConfigFormatter;
import fish.payara.loggingjson.providers.LogLevelJsonProvider;
import fish.payara.loggingjson.providers.LogMessageJsonProvider;
import fish.payara.loggingjson.providers.LoggerNameJsonProvider;
import fish.payara.loggingjson.providers.ThreadIdJsonProvider;
import fish.payara.loggingjson.providers.ThreadNameJsonProvider;
import fish.payara.loggingjson.providers.ThrowableJsonProvider;
import fish.payara.loggingjson.providers.TimestampJsonProvider;
import io.quarkus.arc.Arc;
import io.quarkus.arc.InjectableInstance;
import io.quarkus.runtime.RuntimeValue;
import io.quarkus.runtime.annotations.Recorder;

@Recorder
public class LoggingJsonRecorder {
    private static final Logger log = LoggerFactory.getLogger(LoggingJsonRecorder.class);

    public RuntimeValue<Optional<Formatter>> initializeConsoleJsonLogging(Config config,
            JsonFactory jsonFactory) {
        return initializeJsonLogging(config.console, config, jsonFactory);
    }

    public RuntimeValue<Optional<Formatter>> initializeJsonLogging(ConfigFormatter formatter, Config config,
            JsonFactory jsonFactory) {
        if (formatter == null || !formatter.isEnabled()) {
            return new RuntimeValue<>(Optional.empty());
        }

        List<JsonProvider> providers;

        providers = defaultFormat(config);

        InjectableInstance<JsonProvider> instance = Arc.container().select(JsonProvider.class);
        instance.forEach(providers::add);

        providers.removeIf(p -> {
            if (p instanceof Enabled) {
                return !((Enabled) p).isEnabled();
            } else {
                return false;
            }
        });

        if (log.isDebugEnabled()) {
            String installedProviders = providers.stream().map(p -> p.getClass().toString())
                    .collect(Collectors.joining(", ", "[", "]"));
            log.debug("Installed json providers {}", installedProviders);
        }

        return new RuntimeValue<>(Optional.of(new JsonFormatter(providers, jsonFactory, config)));

    }

    private List<JsonProvider> defaultFormat(Config config) {
        List<JsonProvider> providers = new ArrayList<>();
        providers.add(new TimestampJsonProvider(config.fields.timestamp));
        providers.add(new LoggerNameJsonProvider(config.fields.loggerName));
        providers.add(new LogLevelJsonProvider(config.fields.level));
        providers.add(new LogMessageJsonProvider(config.fields.message));
        providers.add(new ThreadNameJsonProvider(config.fields.threadName));
        providers.add(new ThreadIdJsonProvider(config.fields.threadId));
        providers.add(new ThrowableJsonProvider(config.fields.throwable));
        return providers;
    }

}
