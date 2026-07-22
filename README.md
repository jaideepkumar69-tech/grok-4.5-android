# Grok 4.5 Android Client

Minimal native Android app for chatting with **Grok 4.5** (xAI API).

Built with:
- Kotlin
- Gradle 8.11.1
- Android Gradle Plugin 8.7.2
- OkHttp (streaming)
- Material Design dark UI

## Requirements

- JDK 17 or higher
- Android SDK (API 26+)
- Internet connection (to download Gradle + dependencies on first build)

## Quick Start — Generate Full Gradle Wrapper

The repository includes `gradle-wrapper.properties`.  
You still need the `gradlew` scripts + `gradle-wrapper.jar`.

### Option A: Using Android Studio (Easiest)

1. Open the project in Android Studio
2. Android Studio will automatically offer to create/fix the Gradle Wrapper
3. Click **OK** / **Sync Now**
4. Then build normally (`Build > Build Bundle(s) / APK(s) > Build APK(s)`)

### Option B: Using command line (if you already have Gradle installed)

```bash
git clone https://github.com/jaideepkumar69-tech/grok-4.5-android.git
cd grok-4.5-android

# Generate the full wrapper (gradlew + jar)
gradle wrapper --gradle-version 8.11.1

# Make it executable
chmod +x gradlew

# Build the debug APK
./gradlew assembleDebug
```

### Option C: Manual download of wrapper jar (no local Gradle needed)

```bash
git clone https://github.com/jaideepkumar69-tech/grok-4.5-android.git
cd grok-4.5-android

# Create the wrapper directory if missing
mkdir -p gradle/wrapper

# Download the official gradle-wrapper.jar for 8.11.1
curl -L -o gradle/wrapper/gradle-wrapper.jar \
  https://raw.githubusercontent.com/gradle/gradle/v8.11.1/gradle/wrapper/gradle-wrapper.jar

# Download the gradlew scripts
curl -L -o gradlew https://raw.githubusercontent.com/gradle/gradle/v8.11.1/gradlew
curl -L -o gradlew.bat https://raw.githubusercontent.com/gradle/gradle/v8.11.1/gradlew.bat

chmod +x gradlew

# Now build
./gradlew assembleDebug
```

## After successful build

The APK will be located at:

```
app/build/outputs/apk/debug/app-debug.apk
```

Install it:

```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

or copy the file to your phone.

## First launch

1. Open the app
2. It will ask for your **xAI API key**
3. Paste the key from https://console.x.ai
4. Start chatting with Grok 4.5

## Project Structure

```
grok-4.5-android/
├── app/
│   ├── build.gradle.kts
│   ├── src/main/...
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradle/wrapper/gradle-wrapper.properties
└── README.md
```

## Notes for Termux users

Full Android builds in Termux are possible but require significant setup (proot Ubuntu + Android SDK command-line tools + aapt2 ARM fix).  
It is much easier to build the APK on a PC and then copy it to your phone.

## License
MIT
