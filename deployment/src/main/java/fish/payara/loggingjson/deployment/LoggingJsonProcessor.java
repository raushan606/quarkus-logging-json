package fish.payara.loggingjson.deployment;

import fish.payara.loggingjson.JsonFactory;
import fish.payara.loggingjson.LoggingJsonRecorder;
import fish.payara.loggingjson.config.Config;
import fish.payara.loggingjson.jackson.JacksonJsonFactory;
import fish.payara.loggingjson.jsonb.JsonbJsonFactory;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.deployment.Capabilities;
import io.quarkus.deployment.Capability;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.CombinedIndexBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.LogConsoleFormatBuildItem;
import org.jboss.jandex.ClassInfo;

import java.util.Collection;

class LoggingJsonProcessor {

    private static final String FEATURE = "logging-json";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    @Record(ExecutionTime.RUNTIME_INIT)
    LogConsoleFormatBuildItem setUpConsoleFormatter(Capabilities capabilities, LoggingJsonRecorder recorder,
                                                    Config config) {
        return new LogConsoleFormatBuildItem(recorder.initializeConsoleJsonLogging(config, jsonFactory(capabilities)));
    }

    private JsonFactory jsonFactory(Capabilities capabilities) {
        if (capabilities.isPresent(Capability.JACKSON)) {
            return new JacksonJsonFactory();
        } else if (capabilities.isPresent(Capability.JSONB)) {
            return new JsonbJsonFactory();
        } else {
            throw new RuntimeException(
                    "Missing json implementation to use for logging-json. Supported: [quarkus-jackson, quarkus-jsonb]. "
                            + "Please add either one of them to your project.");
        }
    }

    @BuildStep
    void discoverJsonProviders(BuildProducer<AdditionalBeanBuildItem> beans,
                               CombinedIndexBuildItem combinedIndexBuildItem) {
        Collection<ClassInfo> jsonProviders = combinedIndexBuildItem.getIndex()
                .getAllKnownImplementors(LoggingJsonDotNames.JSON_PROVIDER);
        for (ClassInfo provider : jsonProviders) {
            beans.produce(AdditionalBeanBuildItem.unremovableOf(provider.name().toString()));
        }
    }
}
