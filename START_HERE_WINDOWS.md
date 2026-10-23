# Start here — Android Studio on Windows

This project is **Java, not Kotlin**. Open the existing project folder; do not create an Empty Activity Kotlin project.

1. Extract `SmartPantryManager_Rebuilt_Source.zip` and open the INNER `SmartPantryManager` folder in Android Studio using **File > Open**. It must contain `settings.gradle` and `app/build.gradle`.
2. Install JDK 17 and Android SDK Platform 34 in Android Studio SDK Manager if needed. In Settings > Build, Execution, Deployment > Build Tools > Gradle, set Gradle JDK = 17.
3. In the project root **Terminal** (PowerShell), run `./gradlew.bat --version`. The bundled Windows launcher fetches the official Gradle **8.2.1** distribution to `%USERPROFILE%\.gradle\smartpantry-distributions\gradle-8.2.1` (internet required on first run).
4. Open **Settings > Build Tools > Gradle > Distribution**. Since this project uses a *custom bootstrap launcher*, not the binary official Gradle wrapper JAR, choose **Local installation / Specified location** and browse to the Gradle 8.2.1 folder from Step 3. Never select Gradle 8.0.
5. Use **File > Sync Project with Gradle Files**. If sync succeeds, run `./gradlew.bat assembleDebug` in the root project Terminal. The installer downloads required Gradle/Android artifacts if needed.
6. In Tools > Device Manager, create and start a Pixel emulator running a supported Android version (API 26+). Select `app` and your emulator, then press **Run**.
7. Test add/edit/delete and strict matching following `TEST_PLAN.md`.

**If Android Studio says “Minimum supported Gradle version 8.2.1. Current 8.0,” Step 4 has not been applied to the currently opened project.** Check the path in the error message points to the new extracted project, not the old `SmartPantryManager_Source` folder.

**If the build fails for another reason**, copy the first actual FAILURE error including its cause. Do not fix unrelated Kotlin warnings; this app has no Kotlin files.
