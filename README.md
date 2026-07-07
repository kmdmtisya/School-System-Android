# School Management Mobile

This Android app provides a Compose-based mobile client for a local School Management System backend.

## Architecture
- Kotlin + Jetpack Compose
- MVVM + Repository pattern
- Retrofit + OkHttp
- Hilt dependency injection
- DataStore for session persistence

## Backend assumption
The app expects the backend at:
- http://10.0.2.2:8080/api

## Run locally
1. Start your backend in Docker and make sure it serves the API endpoints under /api.
2. Open the project in Android Studio.
3. Run the app on an Android Emulator.
4. Use any login credentials accepted by your backend; the app currently posts to /auth/login.

## Notes
- The app uses cleartext traffic because the backend is expected to run locally over HTTP.
- If the backend returns different JSON field names, adjust the DTO classes in the data/model package.
