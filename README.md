# Grok 4.5 Android Client

Minimal native Android app for chatting with **Grok 4.5** (xAI API).

Built with:
- Kotlin
- Gradle
- OkHttp (for streaming API calls)
- Simple Material-style UI

## Requirements

- Android Studio **or** command-line build tools
- JDK 17+
- Android SDK (API 26+)

## Build with Gradle (Command Line)

```bash
# Clone
git clone https://github.com/jaideepkumar69-tech/grok-4.5-android.git
cd grok-4.5-android

# Make gradlew executable (Linux / Termux / macOS)
chmod +x gradlew

# Build debug APK
./gradlew assembleDebug
```

The APK will be generated at:

```
app/build/outputs/apk/debug/app-debug.apk
```

### Install the APK on your phone

```bash
# Using adb
adb install app/build/outputs/apk/debug/app-debug.apk

# Or just copy the APK to your phone and install it
```

## Configuration

1. Open the app
2. Tap the settings icon (or long-press the title)
3. Enter your xAI API key
4. Start chatting

You can also set the key in `local.properties`:

```properties
XAI_API_KEY=xai-your-key-here
```

## Project Structure

```
grok-4.5-android/
├── app/
│   ├── build.gradle.kts
│   ├── src/main/
│   │   ├── AndroidManifest.xml
│   │   ├── java/com/grok45/chat/
│   │   │   ├── MainActivity.kt
│   │   │   ├── GrokApi.kt
│   │   │   └── ChatAdapter.kt
│   │   └── res/
│   └── ...
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── gradlew / gradlew.bat
```

## Notes for Termux users

Building a full Android project in Termux is possible but heavy (requires proot Ubuntu + Android SDK + aapt2 fixes).  
Recommended: Build on a PC / Android Studio, then copy the APK to your phone.

## License
MIT
