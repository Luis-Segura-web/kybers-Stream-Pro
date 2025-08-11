# Kybers Stream Pro - Visual Architecture

## Application Flow

```
┌─────────────────────────────────────────────────────────────────┐
│                        LOGIN SCREEN                             │
│  ┌─────────────────────────────────────────────────────────┐    │
│  │  📺 Kybers Stream Pro                                   │    │
│  │                                                         │    │
│  │  Server Host: [_________________]                       │    │
│  │  Port:        [_________________]                       │    │
│  │  Username:    [_________________]                       │    │
│  │  Password:    [_________________]                       │    │
│  │                                                         │    │
│  │            [    LOGIN    ]                              │    │
│  └─────────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                        HOME SCREEN                              │
│  ┌─────────────────────────────────────────────────────────┐    │
│  │  📺 Kybers Stream Pro                              ⚙️   │    │
│  └─────────────────────────────────────────────────────────┘    │
│  ┌─────────────────────────────────────────────────────────┐    │
│  │ [📺 Live TV] [🎬 Movies] [📺 Series] [⭐ Favorites]     │    │
│  └─────────────────────────────────────────────────────────┘    │
│  ┌─────────────────────────────────────────────────────────┐    │
│  │ 🔍 Search channels...                                   │    │
│  └─────────────────────────────────────────────────────────┘    │
│  ┌─────────────────────────────────────────────────────────┐    │
│  │ [📺] CNN International                              #1  │    │
│  │ [📺] BBC World News                                 #2  │    │
│  │ [📺] Fox News                                       #3  │    │
│  │ [📺] ESPN                                           #4  │    │
│  └─────────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                       PLAYER SCREEN                             │
│  ┌─────────────────────────────────────────────────────────┐    │
│  │ CNN International                                       │    │
│  │                                                         │    │
│  │                                                         │    │
│  │                    VIDEO CONTENT                        │    │
│  │                                                         │    │
│  │                                                         │    │
│  │    ⏸️  ⏮️  ⏭️     ━━━━━━━━━━●──────     🔊  ⚙️  📱       │    │
│  └─────────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────────┘
```

## Technical Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                           UI LAYER                              │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  │
│  │   LoginScreen   │  │   HomeScreen    │  │  PlayerScreen   │  │
│  │     (Compose)   │  │    (Compose)    │  │   (ExoPlayer)   │  │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘  │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  │
│  │  LoginViewModel │  │ LiveTvViewModel │  │NavigationViewM. │  │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                         DOMAIN LAYER                            │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  │
│  │   LoginUseCase  │  │GetLiveStreamsUC │  │  Other UseCases │  │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘  │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  │
│  │   LiveStream    │  │    Category     │  │   EpgEvent      │  │
│  │     (Model)     │  │    (Model)      │  │    (Model)      │  │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                          DATA LAYER                             │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  │
│  │ XtreamRepository│  │FavoriteRepository│ │PreferencesManager│ │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘  │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  │
│  │   Xtream API    │  │  Room Database  │  │   DataStore     │  │
│  │   (Retrofit)    │  │   (Local Cache) │  │  (Encrypted)    │  │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
```

## Key Features Implemented

✅ **Authentication System**
- Secure login with Xtream Codes API
- Encrypted credential storage
- Auto-login on app restart

✅ **Live TV Streaming**
- HLS streaming with Media3 ExoPlayer
- Channel listing with search
- Stream metadata display

✅ **Modern Android Architecture**
- MVVM with Clean Architecture
- Jetpack Compose UI
- Hilt Dependency Injection
- Room Database + DataStore

✅ **Professional Media Player**
- ExoPlayer integration
- Full-screen playback
- Stream URL construction
- Error handling and retry

This implementation provides a solid foundation for an IPTV application that can be extended with additional features like EPG, VOD, Picture-in-Picture, and more.