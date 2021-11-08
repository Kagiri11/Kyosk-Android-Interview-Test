# Kyosk-Android-Test
A project done for the submission of my Kyosk interview application.
## Table Of Content.
- [Project Structure](##project-structure)
        - [Domain](###domain)
        - [Data](###data)
        - [Presentation](###presentation)
- [Tech Stack](#techstack)
    - [Libraries](##libraries)
- [Screenshots](#screenshots)

## Project Structure

### 1. Domain.
This layer is responsible for setting up the projects business rules. Also this is the layer that exposes the usecases around our software artifact.
Hence this tends to be the most abstracted layer of the project.

### 2. Data.
As the name declares, this layer is responsible for handling data that revolves our project. Following the abstraction principle, parts of this layer provide implemetations for the policies set in our domain layer.
All things to do with data transformation also happens here just to enforce the rule of crossing over boundaries.
### 3. Presentation.
This layer plays host to artifacts responsible for information presentation to the user. 

## Libraries used in the project.

- [Koin](https://github.com/google/hilt) - A Kotlin dependency injection framework.
- [Jetpack](https://developer.android.com/jetpack) - Tools to aid in ease when it comes to android development
- [Retrofit](https://square.github.io/retrofit/) - Retrofit is a type-safe http client for android.
- [GSON](https://github.com/square/gson)
- [OkHttp-Logging-Interceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/README.md) - Logs HTTP request and response data.

- [Truth](https://truth.dev/) - A clean and readable assertion library
- [Mockito](https://site.mockito.org/)
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support for coroutines.
- [Flow](https://developer.android.com/kotlin/flow)
- [Material Design](https://material.io/develop/android/docs/getting-started/)
- [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support for coroutines,provides runBlocking coroutine builder used in tests.

## GitHub Actions
- [GitHub Actions](https://github.com/Kagiri11/Kyosk-Android-Test/actions) - I used Github actions to enforce correct syntax and linting on files.

# Screenshots
These are the app's screenshots:

<img src="assets/img1.png" width="250"/><img src="assets/img1.1.png" width="250"/> <img src="assets/img2.png" width="250"/>

<img src="assets/img3.png" width="250"/> <img src="assets/img3.1.png" width="250"/> <img src="assets/img4.png" width="250"/>

<img src="assets/img5.png" width="250"/> <img src="assets/img5.1.png" width="250"/> <img src="assets/img6.png" width="250"/>

<img src="assets/img7.png" width="250"/>
 

