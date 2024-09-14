[![Maven Central](https://img.shields.io/maven-central/v/io.quarkiverse.loggingjson/quarkus-logging-json?logo=apache-maven&style=for-the-badge)](https://search.maven.org/artifact/io.quarkiverse.loggingjson/quarkus-logging-json)
# Quarkus Logging Json

Quarkus logging extension outputting the logging in json.

## Version to use

| Quarkus Version | Use version  |
|-----------------|--------------|
| 3.x.x           | 3.x.x        |


# Configuration

The extension is enabled by default for console, when added to the project.
Console logging can be disabled using configuration: `quarkus.log.json.console.enable=false`

# Custom log handler

If you want to add your own custom way to handle the LogRecords.
You can create your own implementations of `fish.payara.loggingjson.JsonProvider`, and provide it using CDI.
Example implementation:

```java
import jakarta.inject.Singleton;
import java.io.IOException;

import fish.payara.loggingjson.JsonProvider;
import fish.payara.loggingjson.JsonGenerator;
import org.jboss.logmanager.ExtLogRecord;

@Singleton
public class MyJsonProvider implements JsonProvider {

    @Override
    public void writeTo(JsonGenerator generator, ExtLogRecord event) throws IOException {
        generator.writeStringField("myCustomField", "and my custom value"); // Will be added to every log, as a field on the json.
    }
}
```

## Contributors ✨

Thanks goes to these wonderful people ([emoji key](https://allcontributors.org/docs/en/emoji-key)):

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tr>
    <td align="center"><a href="https://github.com/SlyngDK"><img src="https://avatars2.githubusercontent.com/u/6666094?v=4" width="100px;" alt=""/><br /><sub><b>Simon Bengtsson</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-logging-json/commits?author=SlyngDK" title="Code">💻</a> <a href="#maintenance-SlyngDK" title="Maintenance">🚧</a></td>
  </tr>
</table>

<!-- markdownlint-enable -->
<!-- prettier-ignore-end -->
<!-- ALL-CONTRIBUTORS-LIST:END -->

This project follows the [all-contributors](https://github.com/all-contributors/all-contributors) specification.
Contributions of any kind welcome!
