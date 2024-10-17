# Hello Compose Web Project

This project is a simple **Hello** web app built using the modern **Compose Multiplatform** stack. It targets the web platform only, using **Decompose** for state management and **ktlint** for code style.

## How to Set Up and Run

### Requirements:
- **JDK** (compatible with Kotlin projects)
- **Gradle**
- **Android Studio** (for better development experience)

### Useful Gradle Commands:

- **Run the app** in development mode with continuous updates:
  ```bash
  ./gradlew wasmJsBrowserRun --continuous
  ```

- **Build the project** for production (same as distribution):
  ```bash
  ./gradlew wasmJsBrowserDistribution
  ```

## Used Technologies:
- **Kotlin Compose for Web** for building the UI.
- **Decompose** for managing navigation and state.
- **KtLint** for enforcing code style
## License:
Licensed under the Apache License 2.0. See [LICENSE](./LICENSE) for details.
