# gdpr-documentation-poc

PoC for an automated GDPR documentation generation tool

## Development

```
./gradlew build
./gradlew :test:generateGdprDocumentation
./gradlew publishToMavenLocal
```

(note that due to a bug in gradle regarding composite builds you cannot run `./gradlew clean build`, but you need to run
this as two separate commands)

## Usage

See [example project](./test).

Generate the documentation by running:
```
./gradlew generateGdprDocumentation
```
You find the documentation in `build/reports/gdpr-documentation.md`. It currently needs to be manually 
copied to `docs/gdpr-documentation.md`

Make sure to enable PlantUML in your markdown renderer in your IDE to see the Data Flow Diagram.
Backstage also supports PlantUML, so it should work there without additional setup.