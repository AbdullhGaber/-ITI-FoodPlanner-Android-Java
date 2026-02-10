# FoodPlanner App

FoodPlanner is an Android application for discovering meals, browsing categories and areas, marking favorites, and managing user authentication (email/password + Google Sign-In). It follows an MVP architecture with clear separation between Presentation, Data and DI layers.

## Table of Contents

- [Architecture](#architecture)
- [Libraries Used](#libraries-used)
- [Setup](#setup)
- [Configuration](#configuration)
- [Build & Run](#build--run)
- [Usage](#usage)
- [Features](#features)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [Contacts](#contacts)

## Architecture

This project uses MVP (Model–View–Presenter) with dependency injection and clean layering:

- Presentation: Activities, Fragments, Presenters (Fragment-scoped presenters via Hilt)
- Data: Repositories, Remote/Local data sources (Retrofit + Room)
- DI: Hilt modules for presenter and repository bindings

Flow: Fragment -> Presenter -> Repository -> (Remote API | Room DB)

## Libraries Used

- Retrofit (network)
- Hilt (dependency injection)
- RxJava 3 / RxAndroid (async flows)
- Room (local database)
- Glide (image loading)
- Firebase Auth (email/password and Google Sign-In)
- Navigation (Jetpack Navigation component)
- ViewBinding (type-safe view access)

## Setup

1. Clone the repository:

```bash
git clone https://github.com/yourusername/FoodPlannerApp.git
cd FoodPlannerApp
```

2. Open the project in Android Studio.
3. Make sure Gradle sync completes.

## Configuration

Before running the app you need to configure Firebase + Google Sign-In:

1. Add your `google-services.json` file to the `app/` folder (Firebase project settings).
2. Register your app's SHA-1 fingerprint in the Firebase console. For the debug keystore on Windows the default path is typically:

```
C:\Users\<your-user>\.android\debug.keystore
```

3. In the code, replace the placeholder Web Client ID used for Google Sign-In in the login/register fragments (search for `YOUR_WEB_CLIENT_ID_HERE`) with the OAuth 2.0 Web client ID from the Firebase console (under Project Settings → OAuth 2.0 client IDs).

4. Ensure Google Sign-In is enabled in the Firebase Authentication providers.

## Build & Run

From Android Studio: Run the `app` module on a device or emulator.

From the command line (Windows):

```powershell
cd path\to\FoodPlannerApp
.\gradlew.bat assembleDebug
```

## Usage

- Launch the app. A splash screen appears, then the Intro screen.
- Use the Login or Register screen to sign in with email/password or Google.
- Browse meals by category/area, view meal details, and add meals to favorites.

## Features

- MVP architecture with Hilt-based DI
- Meal browsing by category, area and search
- Meal detail view with offline-capable image caching
- Favorites persisted in Room database
- Authentication: Email/password + Google Sign-In (Firebase)
- ViewBinding across activities, fragments and adapters

## Troubleshooting

- Hilt errors: Ensure any Activity that hosts a Hilt-enabled Fragment is annotated with `@AndroidEntryPoint` (for example, `MainActivity`).
- Google Sign-In failures: verify you replaced `YOUR_WEB_CLIENT_ID_HERE`, added SHA-1 to Firebase, and have a valid `google-services.json`.
- Build issues: run `./gradlew clean assembleDebug` and inspect errors in Android Studio.

## Contributing

Contributions are welcome. Please fork the repo and open a pull request describing your changes.

## Contacts

- Author: Your Name
- Email: your.email@example.com

---

If you'd like, I can also:
- add screenshots to this README (please provide images or let me take screenshots),
- add a short development setup section for common tasks (run instrumentation tests, generate signed APK), or
- commit the README and create a git branch/pull request.
