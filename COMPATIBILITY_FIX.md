# Android Studio compatibility fix

This copy is configured for Android Studio supporting **AGP 8.2.2**.

- Android Gradle Plugin: 8.2.2
- Gradle: 8.2.1
- Java (JDK): 17
- compileSdk / targetSdk: 34

## Windows
1. Extract the ZIP into a NEW folder.
2. In Android Studio choose File > Open and select the INNER SmartPantryManager folder (containing settings.gradle).
3. Set Gradle JDK to 17 (Settings > Build, Execution, Deployment > Build Tools > Gradle).
4. From the project Terminal run `.\gradlew.bat --version` to download and confirm Gradle 8.2.1. This project uses a custom launcher, not the standard Gradle wrapper JAR.
5. In that same Gradle settings page, use Local installation / Specified location and point it to `%USERPROFILE%\.gradle\smartpantry-distributions\gradle-8.2.1`.
6. Install Android SDK Platform 34 if prompted. Select File > Sync Project with Gradle Files.
7. Run `.\gradlew.bat assembleDebug`, then launch the app using an emulator.

If Android Studio shows an error path pointing to an old `SmartPantryManager_Source` folder, you have opened the old project rather than this updated one.

Android build/emulator operation has not been verified in this environment.
