# Kybers Stream Pro

An Android IPTV streaming application built with modern Android development practices, supporting Xtream Codes API for live TV, VOD, and series streaming.

## Features

### Core Functionality
- ✅ **Xtream Codes API Integration** - Full support for login, live streams, VOD, and series
- ✅ **Live TV Streaming** - HLS/TS streaming with Media3 ExoPlayer
- ✅ **Modern UI** - Material 3 design with Jetpack Compose
- ✅ **Clean Architecture** - MVVM pattern with use cases and repositories
- ✅ **Offline Storage** - Room database for EPG and favorites
- ✅ **Secure Credentials** - Encrypted DataStore for login information
- ✅ **Search & Filtering** - Search channels and content
- ✅ **Android TV Support** - Optimized for TV devices

### Planned Features
- 🔄 **EPG (Electronic Program Guide)** - XMLTV parsing and display
- 🔄 **Picture-in-Picture** - Background video playback
- 🔄 **Favorites Management** - Save and organize favorite content
- 🔄 **Parental Controls** - PIN protection and content filtering
- 🔄 **Catch-up TV** - Time-shifted viewing where supported
- 🔄 **Download & Offline** - Save content for offline viewing

## Technology Stack

### Architecture & Patterns
- **Architecture**: Clean Architecture (Data → Domain → UI)
- **UI Pattern**: MVVM with Compose State Management
- **Dependency Injection**: Hilt (Dagger)

### UI & Design
- **UI Framework**: Jetpack Compose
- **Design System**: Material 3
- **Navigation**: Navigation Compose
- **Image Loading**: Coil

### Media & Streaming
- **Video Player**: Media3 ExoPlayer
- **Streaming Protocols**: HLS (.m3u8), TS
- **Background Playback**: MediaSessionService

### Data & Storage
- **Local Database**: Room
- **Preferences**: DataStore (encrypted)
- **Network**: Retrofit + OkHttp + Moshi
- **Async**: Kotlin Coroutines + Flow

### Platform
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
- **Language**: Kotlin

## Project Structure

```
app/src/main/java/com/kybers/streampro/
├── data/                          # Data layer
│   ├── local/                     # Room database, DAOs, entities
│   ├── remote/                    # API interfaces, DTOs
│   └── repository/                # Repository implementations
├── domain/                        # Domain layer
│   ├── model/                     # Domain models
│   ├── repository/                # Repository interfaces
│   └── usecase/                   # Business logic use cases
├── ui/                           # UI layer
│   ├── home/                      # Home screen with tabs
│   ├── livetv/                    # Live TV channel listing
│   ├── login/                     # Authentication
│   ├── player/                    # Video player
│   ├── navigation/                # Navigation logic
│   └── theme/                     # UI theming
├── di/                           # Dependency injection modules
└── media/                        # Media session service
```

## Xtream Codes API Integration

The app supports standard Xtream Codes API endpoints:

### Authentication
```
GET /player_api.php?username=USER&password=PASS
```

### Live Streams
```
GET /player_api.php?username=USER&password=PASS&action=get_live_streams
GET /player_api.php?username=USER&password=PASS&action=get_live_categories
```

### VOD & Series
```
GET /player_api.php?username=USER&password=PASS&action=get_vod_streams
GET /player_api.php?username=USER&password=PASS&action=get_series
```

### EPG Data
```
GET /xmltv.php?username=USER&password=PASS
```

### Stream URLs
Live streams: `http://host:port/live/username/password/stream_id.m3u8`
VOD content: `http://host:port/movie/username/password/stream_id.ext`

## Getting Started

### Prerequisites
- Android Studio (latest stable)
- Android SDK 24+
- Kotlin 1.9+

### Building the App
1. Clone the repository
2. Open in Android Studio
3. Sync Gradle files
4. Build and run on device/emulator

### Configuration
1. Launch the app
2. Enter your Xtream Codes server details:
   - Host (IP or domain)
   - Port
   - Username
   - Password
3. Login and start streaming

## Permissions

The app requires the following permissions:
- `INTERNET` - Network access for streaming
- `ACCESS_NETWORK_STATE` - Network state monitoring
- `WAKE_LOCK` - Keep device awake during playback
- `FOREGROUND_SERVICE` - Background media service
- `POST_NOTIFICATIONS` - Playback notifications (Android 13+)

## Security

- Login credentials are encrypted and stored using DataStore
- Network security config allows cleartext traffic for IPTV servers
- No sensitive data is logged or transmitted to third parties

## Contributing

This project follows modern Android development best practices:
- Clean Architecture
- SOLID principles
- Dependency injection
- Reactive programming with Coroutines/Flow
- Comprehensive testing

## License

This project is for educational and personal use. Please respect content licensing and provider terms of service.