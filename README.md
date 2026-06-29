# NTP Tools v1

Android application untuk sinkronisasi NTP (Network Time Protocol) dengan fitur-fitur lengkap.

## Fitur

✅ **Login Security**
- Username: `nova`
- Password: `free`
- Persistent login dengan SharedPreferences

✅ **Drag HS Control**
- SeekBar untuk mengatur nilai hingga 20%
- Real-time value display
- Data tersimpan otomatis

✅ **DPI Settings**
- Switch untuk set DPI ke 520
- Memerlukan root atau Shizuku permission
- Status tracking

✅ **Shizuku Integration**
- Permission request untuk akses system
- Allow All Time notification
- System-level operations support

✅ **NTP Synchronization**
- Sinkronisasi waktu dengan server NTP
- Notification support
- Background service implementation

## Requirement

- Android 9.0+ (API 28+)
- Gradle 7.4.2
- Kotlin 1.8.0

## Build & Install

```bash
# Clone repository
git clone https://github.com/adamputra73925-maker/ntp-tools-v1.git

# Build APK
./gradlew assembleDebug

# Install APK
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Dependencies

- AndroidX Core & AppCompat
- Material Components
- Kotlin Coroutines
- Shizuku API
- Apache Commons Net

## Structure

```
app/src/main/
├── java/com/nova/ntptools/
│   ├── ui/
│   │   ├── LoginActivity.kt
│   │   └── MainActivity.kt
│   ├── service/
│   │   └── NTPSyncService.kt
│   └── utils/
│       ├── PreferencesHelper.kt
│       ├── DpiHelper.kt
│       ├── NotificationHelper.kt
│       └── ShizukuHelper.kt
├── res/
│   ├── layout/
│   │   ├── activity_login.xml
│   │   └── activity_main.xml
│   ├── values/
│   │   ├── colors.xml
│   │   ├── strings.xml
│   │   └── themes.xml
│   └── drawable/
│       └── edit_text_background.xml
└── AndroidManifest.xml
```

## Permissions

- `INTERNET` - NTP sync
- `SET_TIME` - Set system time
- `POST_NOTIFICATIONS` - Show notifications
- `ACCESS_NETWORK_STATE` - Check network
- `moe.shizuku.manager.permission.API_V23` - Shizuku access

## License

MIT License - Created by ADAM PUTRA TOOLS
