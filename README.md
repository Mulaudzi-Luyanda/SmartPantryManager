# Smart Pantry Manager

A basic Java Android app for tracking leftover ingredients and displaying **only** recipes for which all ingredients and required quantities are already in the pantry.

## Technology and database
- Java Activities, native Android widgets, `ListView` and custom row adapters.
- SQLite via `SQLiteOpenHelper`. It works entirely offline without accounts or web services, making it easy to explain and run in an emulator.
- 20 preloaded recipes, inserted on first database creation.
- Pantry Create/Read/Update/Delete, recipe detail view, settings preference and input validation.
- Recipe matching normalises common plurals and converts `kg` to `g` and `l` to `ml`. Incompatible unit categories do not match.

## Setup
1. Open this directory in **Android Studio**.
2. Set **Gradle JDK** to **JDK 17** in Settings → Build Tools → Gradle; install **Android SDK Platform 34** if prompted.
3. This repository includes a **Windows Gradle 8.2.1 bootstrap launcher** (`gradlew.bat`) and `gradle/wrapper/gradle-wrapper.properties` pinned to 8.2.1. It downloads the official Gradle distribution on first use, rather than requiring a locally installed Gradle 8.0. In Android Studio Terminal run ` .\gradlew.bat --version ` and confirm **Gradle 8.2.1**. IMPORTANT: this is a custom bootstrap launcher, **not** an official `gradle-wrapper.jar`; Android Studio may not automatically recognize it as a standard Gradle wrapper. If Android Studio asks for a Gradle distribution, select **Local installation** at `%USERPROFILE%\.gradle\smartpantry-distributions\gradle-8.2.1` after running the launcher.
4. Choose **File → Sync Project with Gradle Files** and run ` .\gradlew.bat assembleDebug ` in the project terminal. An APK should be produced in `app/build/outputs/apk/debug/` if compilation succeeds.
5. Run `app` on an emulator or Android phone running API 26 or higher.
6. Add ingredients to Pantry; open Recipes to view strict matches. Long-press an ingredient to delete it.

## Demonstrate strict matching
- Add `egg`, quantity `2`, unit `pcs` and `oil`, quantity `10`, unit `ml`: **Scrambled Eggs** appears.
- Edit eggs to `1 pcs`: **Scrambled Eggs** disappears.
- Set eggs back to `2 pcs`: it returns.
- `tomatoes` is accepted when a recipe requires `tomato`.
- `milk 0.25 l` satisfies `milk 250 ml`.
- Kill/relaunch app and confirm pantry is still present.

## Limitations
- No background expiry notifications; Settings toggles the visible expiry label.
- Normalisation handles common forms, not every language or naming synonym.
- Recipes use explicit measurable quantities; pantry does not automatically deduct quantities after cooking.

## Version control
Create your own GitHub repository **at the beginning of development** and commit each genuine stage of your work. The required 10+ incremental commits cannot be retroactively generated as authentic development history. Add the repository URL to your report.

## Building and validation status
Project files were statically checked and the Java recipe-matching logic was exercised locally. **A complete Android build and emulator run were not possible in this environment** because the Android SDK and online Gradle distribution are unavailable. Build and app behavior must be confirmed on your Android Studio installation; take authentic screenshots only after running it.

## Source files
- `PantryActivity.java`: list, edit/delete navigation and visible expiry labels.
- `EditIngredientActivity.java`: add/edit form with name, quantity, unit and optional date validation.
- `DatabaseHelper.java`: SQLite schema, CRUD and 20 first-run recipe records.
- `RecipeMatcher.java`: quantity-sensitive full-ingredient matching.
- `SuggestedActivity.java`: dynamically generated strict matches and empty state.
- `RecipeDetailActivity.java`: full selected recipe and method.
- `SettingsActivity.java`: locally saved expiry-label preference.

## Small assignment limitation
Most screens use native Android Java-created `LinearLayout` widgets instead of separate XML layout files, which is permitted by the brief's activity-and-layout requirements. There are no Kotlin or location services.
