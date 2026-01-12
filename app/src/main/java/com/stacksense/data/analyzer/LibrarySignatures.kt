package com.stacksense.data.analyzer

import com.stacksense.data.model.LibraryCategory
import com.stacksense.data.model.LibraryInfo

/**
 * Database of known library signatures for detection.
 * Each entry maps a package prefix to library information.
 */
object LibrarySignatures {

    /**
     * Map of package prefix to LibraryInfo for detection.
     * The analyzer will search for these prefixes in the DEX files.
     */
    val SIGNATURES: Map<String, LibraryInfo> = mapOf(
        // ==================== NETWORKING ====================
        "retrofit2" to LibraryInfo(
            name = "Retrofit",
            category = LibraryCategory.NETWORKING,
            packagePrefix = "retrofit2",
            description = "Type-safe HTTP client for Android",
            website = "https://square.github.io/retrofit/"
        ),
        "com.squareup.okhttp3" to LibraryInfo(
            name = "OkHttp",
            category = LibraryCategory.NETWORKING,
            packagePrefix = "com.squareup.okhttp3",
            description = "HTTP client for Android and Java",
            website = "https://square.github.io/okhttp/"
        ),
        "com.android.volley" to LibraryInfo(
            name = "Volley",
            category = LibraryCategory.NETWORKING,
            packagePrefix = "com.android.volley",
            description = "HTTP library by Google",
            website = "https://developer.android.com/training/volley"
        ),
        "io.ktor" to LibraryInfo(
            name = "Ktor Client",
            category = LibraryCategory.NETWORKING,
            packagePrefix = "io.ktor",
            description = "Kotlin multiplatform HTTP client",
            website = "https://ktor.io/"
        ),
        "com.apollographql.apollo" to LibraryInfo(
            name = "Apollo GraphQL",
            category = LibraryCategory.NETWORKING,
            packagePrefix = "com.apollographql.apollo",
            description = "GraphQL client for Android",
            website = "https://www.apollographql.com/"
        ),

        // ==================== IMAGE LOADING ====================
        "com.bumptech.glide" to LibraryInfo(
            name = "Glide",
            category = LibraryCategory.IMAGE_LOADING,
            packagePrefix = "com.bumptech.glide",
            description = "Fast and efficient image loading",
            website = "https://bumptech.github.io/glide/"
        ),
        "coil" to LibraryInfo(
            name = "Coil",
            category = LibraryCategory.IMAGE_LOADING,
            packagePrefix = "coil",
            description = "Kotlin-first image loading library",
            website = "https://coil-kt.github.io/coil/"
        ),
        "com.squareup.picasso" to LibraryInfo(
            name = "Picasso",
            category = LibraryCategory.IMAGE_LOADING,
            packagePrefix = "com.squareup.picasso",
            description = "Image downloading and caching library",
            website = "https://square.github.io/picasso/"
        ),
        "com.facebook.fresco" to LibraryInfo(
            name = "Fresco",
            category = LibraryCategory.IMAGE_LOADING,
            packagePrefix = "com.facebook.fresco",
            description = "Facebook's image loading library",
            website = "https://frescolib.org/"
        ),

        // ==================== DEPENDENCY INJECTION ====================
        "dagger.hilt" to LibraryInfo(
            name = "Hilt",
            category = LibraryCategory.DEPENDENCY_INJECTION,
            packagePrefix = "dagger.hilt",
            description = "Dependency injection for Android",
            website = "https://dagger.dev/hilt/"
        ),
        "dagger" to LibraryInfo(
            name = "Dagger",
            category = LibraryCategory.DEPENDENCY_INJECTION,
            packagePrefix = "dagger",
            description = "Compile-time dependency injection",
            website = "https://dagger.dev/"
        ),
        "org.koin" to LibraryInfo(
            name = "Koin",
            category = LibraryCategory.DEPENDENCY_INJECTION,
            packagePrefix = "org.koin",
            description = "Pragmatic Kotlin DI framework",
            website = "https://insert-koin.io/"
        ),
        "toothpick" to LibraryInfo(
            name = "Toothpick",
            category = LibraryCategory.DEPENDENCY_INJECTION,
            packagePrefix = "toothpick",
            description = "Scope tree based DI library",
            website = "https://github.com/stephanenicolas/toothpick"
        ),

        // ==================== DATABASE ====================
        "androidx.room" to LibraryInfo(
            name = "Room",
            category = LibraryCategory.DATABASE,
            packagePrefix = "androidx.room",
            description = "SQLite abstraction layer by Google",
            website = "https://developer.android.com/training/data-storage/room"
        ),
        "io.realm" to LibraryInfo(
            name = "Realm",
            category = LibraryCategory.DATABASE,
            packagePrefix = "io.realm",
            description = "Mobile database",
            website = "https://realm.io/"
        ),
        "app.cash.sqldelight" to LibraryInfo(
            name = "SQLDelight",
            category = LibraryCategory.DATABASE,
            packagePrefix = "app.cash.sqldelight",
            description = "Typesafe SQL for Kotlin",
            website = "https://cashapp.github.io/sqldelight/"
        ),
        "com.squareup.sqldelight" to LibraryInfo(
            name = "SQLDelight",
            category = LibraryCategory.DATABASE,
            packagePrefix = "com.squareup.sqldelight",
            description = "Typesafe SQL for Kotlin",
            website = "https://cashapp.github.io/sqldelight/"
        ),
        "org.greenrobot.greendao" to LibraryInfo(
            name = "greenDAO",
            category = LibraryCategory.DATABASE,
            packagePrefix = "org.greenrobot.greendao",
            description = "Fast ORM for Android",
            website = "https://greenrobot.org/greendao/"
        ),

        // ==================== REACTIVE PROGRAMMING ====================
        "io.reactivex.rxjava3" to LibraryInfo(
            name = "RxJava 3",
            category = LibraryCategory.REACTIVE,
            packagePrefix = "io.reactivex.rxjava3",
            description = "Reactive Extensions for JVM",
            website = "https://github.com/ReactiveX/RxJava"
        ),
        "io.reactivex.rxjava2" to LibraryInfo(
            name = "RxJava 2",
            category = LibraryCategory.REACTIVE,
            packagePrefix = "io.reactivex.rxjava2",
            description = "Reactive Extensions for JVM",
            website = "https://github.com/ReactiveX/RxJava"
        ),
        "kotlinx.coroutines" to LibraryInfo(
            name = "Kotlin Coroutines",
            category = LibraryCategory.REACTIVE,
            packagePrefix = "kotlinx.coroutines",
            description = "Asynchronous programming with coroutines",
            website = "https://kotlinlang.org/docs/coroutines-overview.html"
        ),
        "kotlinx.coroutines.flow" to LibraryInfo(
            name = "Kotlin Flow",
            category = LibraryCategory.REACTIVE,
            packagePrefix = "kotlinx.coroutines.flow",
            description = "Cold asynchronous data streams",
            website = "https://kotlinlang.org/docs/flow.html"
        ),

        // ==================== ANALYTICS ====================
        "com.google.firebase.analytics" to LibraryInfo(
            name = "Firebase Analytics",
            category = LibraryCategory.ANALYTICS,
            packagePrefix = "com.google.firebase.analytics",
            description = "App analytics by Google",
            website = "https://firebase.google.com/products/analytics"
        ),
        "com.google.android.gms.analytics" to LibraryInfo(
            name = "Google Analytics",
            category = LibraryCategory.ANALYTICS,
            packagePrefix = "com.google.android.gms.analytics",
            description = "Google Analytics for Android",
            website = "https://analytics.google.com/"
        ),
        "com.amplitude" to LibraryInfo(
            name = "Amplitude",
            category = LibraryCategory.ANALYTICS,
            packagePrefix = "com.amplitude",
            description = "Product analytics platform",
            website = "https://amplitude.com/"
        ),
        "com.mixpanel" to LibraryInfo(
            name = "Mixpanel",
            category = LibraryCategory.ANALYTICS,
            packagePrefix = "com.mixpanel",
            description = "Event analytics platform",
            website = "https://mixpanel.com/"
        ),
        "com.segment" to LibraryInfo(
            name = "Segment",
            category = LibraryCategory.ANALYTICS,
            packagePrefix = "com.segment",
            description = "Customer data platform",
            website = "https://segment.com/"
        ),
        "ly.count.android.sdk" to LibraryInfo(
            name = "Countly",
            category = LibraryCategory.ANALYTICS,
            packagePrefix = "ly.count.android.sdk",
            description = "Product analytics",
            website = "https://countly.com/"
        ),

        // ==================== ADVERTISING ====================
        "com.google.android.gms.ads" to LibraryInfo(
            name = "Google AdMob",
            category = LibraryCategory.ADVERTISING,
            packagePrefix = "com.google.android.gms.ads",
            description = "Mobile advertising SDK",
            website = "https://admob.google.com/"
        ),
        "com.facebook.ads" to LibraryInfo(
            name = "Facebook Audience Network",
            category = LibraryCategory.ADVERTISING,
            packagePrefix = "com.facebook.ads",
            description = "Facebook ad network",
            website = "https://www.facebook.com/audiencenetwork"
        ),
        "com.unity3d.ads" to LibraryInfo(
            name = "Unity Ads",
            category = LibraryCategory.ADVERTISING,
            packagePrefix = "com.unity3d.ads",
            description = "Unity advertising SDK",
            website = "https://unity.com/products/unity-ads"
        ),
        "com.applovin" to LibraryInfo(
            name = "AppLovin",
            category = LibraryCategory.ADVERTISING,
            packagePrefix = "com.applovin",
            description = "Mobile marketing platform",
            website = "https://www.applovin.com/"
        ),
        "com.ironsource" to LibraryInfo(
            name = "ironSource",
            category = LibraryCategory.ADVERTISING,
            packagePrefix = "com.ironsource",
            description = "App monetization platform",
            website = "https://www.is.com/"
        ),

        // ==================== UI COMPONENTS ====================
        "com.airbnb.lottie" to LibraryInfo(
            name = "Lottie",
            category = LibraryCategory.UI,
            packagePrefix = "com.airbnb.lottie",
            description = "Animation library by Airbnb",
            website = "https://airbnb.io/lottie/"
        ),
        "com.airbnb.epoxy" to LibraryInfo(
            name = "Epoxy",
            category = LibraryCategory.UI,
            packagePrefix = "com.airbnb.epoxy",
            description = "RecyclerView library by Airbnb",
            website = "https://github.com/airbnb/epoxy"
        ),
        "com.google.android.material" to LibraryInfo(
            name = "Material Components",
            category = LibraryCategory.UI,
            packagePrefix = "com.google.android.material",
            description = "Material Design components",
            website = "https://material.io/develop/android"
        ),
        "com.github.bumptech.glide.transformations" to LibraryInfo(
            name = "Glide Transformations",
            category = LibraryCategory.UI,
            packagePrefix = "jp.wasabeef.glide.transformations",
            description = "Image transformations for Glide",
            website = "https://github.com/wasabeef/glide-transformations"
        ),
        "com.mikepenz.materialdrawer" to LibraryInfo(
            name = "MaterialDrawer",
            category = LibraryCategory.UI,
            packagePrefix = "com.mikepenz.materialdrawer",
            description = "Flexible navigation drawer",
            website = "https://github.com/mikepenz/MaterialDrawer"
        ),
        "com.zhpan.bannerview" to LibraryInfo(
            name = "BannerViewPager",
            category = LibraryCategory.UI,
            packagePrefix = "com.zhpan.bannerview",
            description = "Banner/Carousel library",
            website = "https://github.com/zhpanvip/BannerViewPager"
        ),
        "com.github.chrisbanes" to LibraryInfo(
            name = "PhotoView",
            category = LibraryCategory.UI,
            packagePrefix = "com.github.chrisbanes",
            description = "Zoomable ImageView",
            website = "https://github.com/chrisbanes/PhotoView"
        ),

        // ==================== SERIALIZATION ====================
        "com.google.gson" to LibraryInfo(
            name = "Gson",
            category = LibraryCategory.SERIALIZATION,
            packagePrefix = "com.google.gson",
            description = "JSON serialization by Google",
            website = "https://github.com/google/gson"
        ),
        "com.squareup.moshi" to LibraryInfo(
            name = "Moshi",
            category = LibraryCategory.SERIALIZATION,
            packagePrefix = "com.squareup.moshi",
            description = "Modern JSON library for Android",
            website = "https://github.com/square/moshi"
        ),
        "kotlinx.serialization" to LibraryInfo(
            name = "Kotlinx Serialization",
            category = LibraryCategory.SERIALIZATION,
            packagePrefix = "kotlinx.serialization",
            description = "Kotlin multiplatform serialization",
            website = "https://github.com/Kotlin/kotlinx.serialization"
        ),
        "com.fasterxml.jackson" to LibraryInfo(
            name = "Jackson",
            category = LibraryCategory.SERIALIZATION,
            packagePrefix = "com.fasterxml.jackson",
            description = "Java JSON processor",
            website = "https://github.com/FasterXML/jackson"
        ),

        // ==================== CRASH REPORTING ====================
        "com.google.firebase.crashlytics" to LibraryInfo(
            name = "Firebase Crashlytics",
            category = LibraryCategory.CRASH_REPORTING,
            packagePrefix = "com.google.firebase.crashlytics",
            description = "Crash reporting by Firebase",
            website = "https://firebase.google.com/products/crashlytics"
        ),
        "io.sentry" to LibraryInfo(
            name = "Sentry",
            category = LibraryCategory.CRASH_REPORTING,
            packagePrefix = "io.sentry",
            description = "Error tracking and monitoring",
            website = "https://sentry.io/"
        ),
        "com.bugsnag" to LibraryInfo(
            name = "Bugsnag",
            category = LibraryCategory.CRASH_REPORTING,
            packagePrefix = "com.bugsnag",
            description = "Error monitoring platform",
            website = "https://www.bugsnag.com/"
        ),
        "com.instabug" to LibraryInfo(
            name = "Instabug",
            category = LibraryCategory.CRASH_REPORTING,
            packagePrefix = "com.instabug",
            description = "Bug reporting and feedback",
            website = "https://instabug.com/"
        ),

        // ==================== LOGGING ====================
        "timber.log" to LibraryInfo(
            name = "Timber",
            category = LibraryCategory.LOGGING,
            packagePrefix = "timber.log",
            description = "Logger with a small API",
            website = "https://github.com/JakeWharton/timber"
        ),
        "com.orhanobut.logger" to LibraryInfo(
            name = "Logger",
            category = LibraryCategory.LOGGING,
            packagePrefix = "com.orhanobut.logger",
            description = "Simple and pretty logger",
            website = "https://github.com/orhanobut/logger"
        ),

        // ==================== ARCHITECTURE ====================
        "org.orbitmvi" to LibraryInfo(
            name = "Orbit MVI",
            category = LibraryCategory.ARCHITECTURE,
            packagePrefix = "org.orbitmvi",
            description = "MVI framework for Kotlin",
            website = "https://orbit-mvi.org/"
        ),
        "com.airbnb.mvrx" to LibraryInfo(
            name = "Mavericks (MvRx)",
            category = LibraryCategory.ARCHITECTURE,
            packagePrefix = "com.airbnb.mvrx",
            description = "MVI framework by Airbnb",
            website = "https://github.com/airbnb/mavericks"
        ),

        // ==================== SOCIAL ====================
        "com.facebook" to LibraryInfo(
            name = "Facebook SDK",
            category = LibraryCategory.SOCIAL,
            packagePrefix = "com.facebook",
            description = "Facebook integration SDK",
            website = "https://developers.facebook.com/docs/android/"
        ),
        "com.google.android.gms.auth" to LibraryInfo(
            name = "Google Sign-In",
            category = LibraryCategory.SOCIAL,
            packagePrefix = "com.google.android.gms.auth",
            description = "Google authentication",
            website = "https://developers.google.com/identity"
        ),
        "com.twitter" to LibraryInfo(
            name = "Twitter SDK",
            category = LibraryCategory.SOCIAL,
            packagePrefix = "com.twitter",
            description = "Twitter integration SDK",
            website = "https://developer.twitter.com/"
        ),

        // ==================== SECURITY ====================
        "androidx.security.crypto" to LibraryInfo(
            name = "AndroidX Security",
            category = LibraryCategory.SECURITY,
            packagePrefix = "androidx.security.crypto",
            description = "Secure data storage",
            website = "https://developer.android.com/jetpack/androidx/releases/security"
        ),
        "com.google.crypto.tink" to LibraryInfo(
            name = "Tink",
            category = LibraryCategory.SECURITY,
            packagePrefix = "com.google.crypto.tink",
            description = "Multi-language crypto library",
            website = "https://github.com/google/tink"
        ),

        // ==================== UTILITIES ====================
        "com.jakewharton.threetenabp" to LibraryInfo(
            name = "ThreeTenABP",
            category = LibraryCategory.UTILITIES,
            packagePrefix = "com.jakewharton.threetenabp",
            description = "Date/Time API backport",
            website = "https://github.com/JakeWharton/ThreeTenABP"
        ),
        "org.greenrobot.eventbus" to LibraryInfo(
            name = "EventBus",
            category = LibraryCategory.UTILITIES,
            packagePrefix = "org.greenrobot.eventbus",
            description = "Event bus for Android",
            website = "https://greenrobot.org/eventbus/"
        ),
        "com.squareup.leakcanary" to LibraryInfo(
            name = "LeakCanary",
            category = LibraryCategory.UTILITIES,
            packagePrefix = "com.squareup.leakcanary",
            description = "Memory leak detection",
            website = "https://square.github.io/leakcanary/"
        ),
        "com.github.kirich1409.viewbindingdelegate" to LibraryInfo(
            name = "ViewBinding Delegate",
            category = LibraryCategory.UTILITIES,
            packagePrefix = "by.kirich1409.viewbindingdelegate",
            description = "ViewBinding property delegate",
            website = "https://github.com/androidbroadcast/ViewBindingPropertyDelegate"
        ),

        // ==================== TESTING ====================
        "org.mockito" to LibraryInfo(
            name = "Mockito",
            category = LibraryCategory.TESTING,
            packagePrefix = "org.mockito",
            description = "Mocking framework for tests",
            website = "https://site.mockito.org/"
        ),
        "io.mockk" to LibraryInfo(
            name = "MockK",
            category = LibraryCategory.TESTING,
            packagePrefix = "io.mockk",
            description = "Kotlin mocking library",
            website = "https://mockk.io/"
        ),
        "app.cash.turbine" to LibraryInfo(
            name = "Turbine",
            category = LibraryCategory.TESTING,
            packagePrefix = "app.cash.turbine",
            description = "Flow testing library",
            website = "https://github.com/cashapp/turbine"
        ),

        // ==================== REACT NATIVE SPECIFIC ====================
        "com.facebook.react" to LibraryInfo(
            name = "React Native Core",
            category = LibraryCategory.REACT_NATIVE,
            packagePrefix = "com.facebook.react",
            description = "React Native framework core",
            website = "https://reactnative.dev/"
        ),
        "com.swmansion.reanimated" to LibraryInfo(
            name = "React Native Reanimated",
            category = LibraryCategory.REACT_NATIVE,
            packagePrefix = "com.swmansion.reanimated",
            description = "React Native animations library",
            website = "https://docs.swmansion.com/react-native-reanimated/"
        ),
        "com.swmansion.gesturehandler" to LibraryInfo(
            name = "Gesture Handler",
            category = LibraryCategory.REACT_NATIVE,
            packagePrefix = "com.swmansion.gesturehandler",
            description = "Native gesture handling",
            website = "https://docs.swmansion.com/react-native-gesture-handler/"
        ),
        "com.th3rdwave.safeareacontext" to LibraryInfo(
            name = "Safe Area Context",
            category = LibraryCategory.REACT_NATIVE,
            packagePrefix = "com.th3rdwave.safeareacontext",
            description = "Safe area insets handling",
            website = "https://github.com/th3rdwave/react-native-safe-area-context"
        ),
        "com.reactnativecommunity.asyncstorage" to LibraryInfo(
            name = "Async Storage",
            category = LibraryCategory.REACT_NATIVE,
            packagePrefix = "com.reactnativecommunity.asyncstorage",
            description = "Async key-value storage",
            website = "https://react-native-async-storage.github.io/async-storage/"
        ),
        "com.reactnativecommunity.netinfo" to LibraryInfo(
            name = "NetInfo",
            category = LibraryCategory.REACT_NATIVE,
            packagePrefix = "com.reactnativecommunity.netinfo",
            description = "Network information API",
            website = "https://github.com/react-native-netinfo/react-native-netinfo"
        ),
        "com.oblador.vectoricons" to LibraryInfo(
            name = "Vector Icons",
            category = LibraryCategory.REACT_NATIVE,
            packagePrefix = "com.oblador.vectoricons",
            description = "Customizable icons for RN",
            website = "https://github.com/oblador/react-native-vector-icons"
        ),
        "com.horcrux.svg" to LibraryInfo(
            name = "React Native SVG",
            category = LibraryCategory.REACT_NATIVE,
            packagePrefix = "com.horcrux.svg",
            description = "SVG library for React Native",
            website = "https://github.com/software-mansion/react-native-svg"
        ),
        "org.reactnative.maskedview" to LibraryInfo(
            name = "Masked View",
            category = LibraryCategory.REACT_NATIVE,
            packagePrefix = "org.reactnative.maskedview",
            description = "Masked view component",
            website = "https://github.com/react-native-masked-view/masked-view"
        ),
        "com.reactnativenavigation" to LibraryInfo(
            name = "React Native Navigation",
            category = LibraryCategory.REACT_NATIVE,
            packagePrefix = "com.reactnativenavigation",
            description = "Native navigation by Wix",
            website = "https://wix.github.io/react-native-navigation/"
        ),
        "com.zoontek.rnpermissions" to LibraryInfo(
            name = "RN Permissions",
            category = LibraryCategory.REACT_NATIVE,
            packagePrefix = "com.zoontek.rnpermissions",
            description = "Unified permissions API",
            website = "https://github.com/zoontek/react-native-permissions"
        ),
        "com.zoontek.rnbootsplash" to LibraryInfo(
            name = "RN Bootsplash",
            category = LibraryCategory.REACT_NATIVE,
            packagePrefix = "com.zoontek.rnbootsplash",
            description = "Native splash screen",
            website = "https://github.com/zoontek/react-native-bootsplash"
        ),
        "com.BV.LinearGradient" to LibraryInfo(
            name = "Linear Gradient",
            category = LibraryCategory.REACT_NATIVE,
            packagePrefix = "com.BV.LinearGradient",
            description = "Gradient view component",
            website = "https://github.com/react-native-linear-gradient/react-native-linear-gradient"
        ),
        "com.reactnativecommunity.webview" to LibraryInfo(
            name = "RN WebView",
            category = LibraryCategory.REACT_NATIVE,
            packagePrefix = "com.reactnativecommunity.webview",
            description = "WebView component",
            website = "https://github.com/react-native-webview/react-native-webview"
        ),
        "com.rnfs" to LibraryInfo(
            name = "RN File System",
            category = LibraryCategory.REACT_NATIVE,
            packagePrefix = "com.rnfs",
            description = "File system access",
            website = "https://github.com/itinance/react-native-fs"
        ),
        "com.reactnativecommunity.cameraroll" to LibraryInfo(
            name = "Camera Roll",
            category = LibraryCategory.REACT_NATIVE,
            packagePrefix = "com.reactnativecommunity.cameraroll",
            description = "Access device photos",
            website = "https://github.com/react-native-cameraroll/react-native-cameraroll"
        ),
        "com.mrousavy.camera" to LibraryInfo(
            name = "Vision Camera",
            category = LibraryCategory.REACT_NATIVE,
            packagePrefix = "com.mrousavy.camera",
            description = "High-performance camera",
            website = "https://react-native-vision-camera.com/"
        ),

        // ==================== MAPS & LOCATION ====================
        "com.google.android.gms.maps" to LibraryInfo(
            name = "Google Maps",
            category = LibraryCategory.MAPS,
            packagePrefix = "com.google.android.gms.maps",
            description = "Google Maps SDK",
            website = "https://developers.google.com/maps/documentation/android-sdk"
        ),
        "com.google.android.gms.location" to LibraryInfo(
            name = "Google Location Services",
            category = LibraryCategory.MAPS,
            packagePrefix = "com.google.android.gms.location",
            description = "Fused location provider",
            website = "https://developers.google.com/location-context"
        ),
        "com.mapbox.mapboxsdk" to LibraryInfo(
            name = "Mapbox",
            category = LibraryCategory.MAPS,
            packagePrefix = "com.mapbox.mapboxsdk",
            description = "Mapbox Maps SDK",
            website = "https://www.mapbox.com/"
        ),
        "org.osmdroid" to LibraryInfo(
            name = "OSMDroid",
            category = LibraryCategory.MAPS,
            packagePrefix = "org.osmdroid",
            description = "OpenStreetMap library",
            website = "https://github.com/osmdroid/osmdroid"
        ),
        "com.here.sdk" to LibraryInfo(
            name = "HERE SDK",
            category = LibraryCategory.MAPS,
            packagePrefix = "com.here.sdk",
            description = "HERE Maps SDK",
            website = "https://developer.here.com/"
        ),
        "com.airbnb.android.react.maps" to LibraryInfo(
            name = "React Native Maps",
            category = LibraryCategory.MAPS,
            packagePrefix = "com.airbnb.android.react.maps",
            description = "Maps for React Native",
            website = "https://github.com/react-native-maps/react-native-maps"
        ),

        // ==================== MEDIA & VIDEO ====================
        "com.google.android.exoplayer2" to LibraryInfo(
            name = "ExoPlayer",
            category = LibraryCategory.MEDIA,
            packagePrefix = "com.google.android.exoplayer2",
            description = "Media player by Google",
            website = "https://exoplayer.dev/"
        ),
        "androidx.media3" to LibraryInfo(
            name = "Media3",
            category = LibraryCategory.MEDIA,
            packagePrefix = "androidx.media3",
            description = "Jetpack Media3 library",
            website = "https://developer.android.com/jetpack/androidx/releases/media3"
        ),
        "com.brentvatne.react" to LibraryInfo(
            name = "React Native Video",
            category = LibraryCategory.MEDIA,
            packagePrefix = "com.brentvatne.react",
            description = "Video player for RN",
            website = "https://github.com/react-native-video/react-native-video"
        ),
        "com.danikula.videocache" to LibraryInfo(
            name = "AndroidVideoCache",
            category = LibraryCategory.MEDIA,
            packagePrefix = "com.danikula.videocache",
            description = "Video caching library",
            website = "https://github.com/danikula/AndroidVideoCache"
        ),
        "com.yausername.youtubedl_android" to LibraryInfo(
            name = "YouTube-DL Android",
            category = LibraryCategory.MEDIA,
            packagePrefix = "com.yausername.youtubedl_android",
            description = "YouTube downloader",
            website = "https://github.com/yausername/youtubedl-android"
        ),

        // ==================== PUSH NOTIFICATIONS ====================
        "com.google.firebase.messaging" to LibraryInfo(
            name = "Firebase Cloud Messaging",
            category = LibraryCategory.PUSH_NOTIFICATIONS,
            packagePrefix = "com.google.firebase.messaging",
            description = "Push notifications by Firebase",
            website = "https://firebase.google.com/products/cloud-messaging"
        ),
        "com.onesignal" to LibraryInfo(
            name = "OneSignal",
            category = LibraryCategory.PUSH_NOTIFICATIONS,
            packagePrefix = "com.onesignal",
            description = "Push notification platform",
            website = "https://onesignal.com/"
        ),
        "com.pusher" to LibraryInfo(
            name = "Pusher",
            category = LibraryCategory.PUSH_NOTIFICATIONS,
            packagePrefix = "com.pusher",
            description = "Real-time messaging",
            website = "https://pusher.com/"
        ),
        "io.intercom.android" to LibraryInfo(
            name = "Intercom",
            category = LibraryCategory.PUSH_NOTIFICATIONS,
            packagePrefix = "io.intercom.android",
            description = "Customer messaging platform",
            website = "https://www.intercom.com/"
        ),
        "com.clevertap" to LibraryInfo(
            name = "CleverTap",
            category = LibraryCategory.PUSH_NOTIFICATIONS,
            packagePrefix = "com.clevertap",
            description = "Customer engagement platform",
            website = "https://clevertap.com/"
        ),
        "com.braze" to LibraryInfo(
            name = "Braze",
            category = LibraryCategory.PUSH_NOTIFICATIONS,
            packagePrefix = "com.braze",
            description = "Customer engagement platform",
            website = "https://www.braze.com/"
        ),
        "com.urbanairship" to LibraryInfo(
            name = "Airship",
            category = LibraryCategory.PUSH_NOTIFICATIONS,
            packagePrefix = "com.urbanairship",
            description = "Mobile engagement platform",
            website = "https://www.airship.com/"
        ),

        // ==================== PAYMENTS ====================
        "com.stripe.android" to LibraryInfo(
            name = "Stripe",
            category = LibraryCategory.PAYMENTS,
            packagePrefix = "com.stripe.android",
            description = "Stripe payments SDK",
            website = "https://stripe.com/"
        ),
        "com.razorpay" to LibraryInfo(
            name = "Razorpay",
            category = LibraryCategory.PAYMENTS,
            packagePrefix = "com.razorpay",
            description = "Razorpay payments",
            website = "https://razorpay.com/"
        ),
        "com.paypal" to LibraryInfo(
            name = "PayPal",
            category = LibraryCategory.PAYMENTS,
            packagePrefix = "com.paypal",
            description = "PayPal payments SDK",
            website = "https://developer.paypal.com/"
        ),
        "com.braintreepayments" to LibraryInfo(
            name = "Braintree",
            category = LibraryCategory.PAYMENTS,
            packagePrefix = "com.braintreepayments",
            description = "Braintree payments",
            website = "https://www.braintreepayments.com/"
        ),
        "com.android.billingclient" to LibraryInfo(
            name = "Google Play Billing",
            category = LibraryCategory.PAYMENTS,
            packagePrefix = "com.android.billingclient",
            description = "In-app purchases",
            website = "https://developer.android.com/google/play/billing"
        ),
        "com.revenuecat.purchases" to LibraryInfo(
            name = "RevenueCat",
            category = LibraryCategory.PAYMENTS,
            packagePrefix = "com.revenuecat.purchases",
            description = "In-app subscription platform",
            website = "https://www.revenuecat.com/"
        ),
        "com.paytm" to LibraryInfo(
            name = "Paytm",
            category = LibraryCategory.PAYMENTS,
            packagePrefix = "com.paytm",
            description = "Paytm payments",
            website = "https://paytm.com/"
        ),
        "com.phonepe" to LibraryInfo(
            name = "PhonePe",
            category = LibraryCategory.PAYMENTS,
            packagePrefix = "com.phonepe",
            description = "PhonePe payments SDK",
            website = "https://phonepe.com/"
        ),

        // ==================== DEEP LINKING ====================
        "io.branch" to LibraryInfo(
            name = "Branch",
            category = LibraryCategory.DEEP_LINKING,
            packagePrefix = "io.branch",
            description = "Deep linking platform",
            website = "https://branch.io/"
        ),
        "com.appsflyer" to LibraryInfo(
            name = "AppsFlyer",
            category = LibraryCategory.DEEP_LINKING,
            packagePrefix = "com.appsflyer",
            description = "Attribution & deep linking",
            website = "https://www.appsflyer.com/"
        ),
        "com.adjust.sdk" to LibraryInfo(
            name = "Adjust",
            category = LibraryCategory.DEEP_LINKING,
            packagePrefix = "com.adjust.sdk",
            description = "Mobile attribution",
            website = "https://www.adjust.com/"
        ),
        "com.singular.sdk" to LibraryInfo(
            name = "Singular",
            category = LibraryCategory.DEEP_LINKING,
            packagePrefix = "com.singular.sdk",
            description = "Marketing analytics",
            website = "https://www.singular.net/"
        ),

        // ==================== FIREBASE ====================
        "com.google.firebase.auth" to LibraryInfo(
            name = "Firebase Auth",
            category = LibraryCategory.FIREBASE,
            packagePrefix = "com.google.firebase.auth",
            description = "Authentication by Firebase",
            website = "https://firebase.google.com/products/auth"
        ),
        "com.google.firebase.firestore" to LibraryInfo(
            name = "Cloud Firestore",
            category = LibraryCategory.FIREBASE,
            packagePrefix = "com.google.firebase.firestore",
            description = "NoSQL cloud database",
            website = "https://firebase.google.com/products/firestore"
        ),
        "com.google.firebase.database" to LibraryInfo(
            name = "Realtime Database",
            category = LibraryCategory.FIREBASE,
            packagePrefix = "com.google.firebase.database",
            description = "Realtime JSON database",
            website = "https://firebase.google.com/products/realtime-database"
        ),
        "com.google.firebase.storage" to LibraryInfo(
            name = "Firebase Storage",
            category = LibraryCategory.FIREBASE,
            packagePrefix = "com.google.firebase.storage",
            description = "Cloud file storage",
            website = "https://firebase.google.com/products/storage"
        ),
        "com.google.firebase.remoteconfig" to LibraryInfo(
            name = "Remote Config",
            category = LibraryCategory.FIREBASE,
            packagePrefix = "com.google.firebase.remoteconfig",
            description = "Cloud-based config",
            website = "https://firebase.google.com/products/remote-config"
        ),
        "com.google.firebase.perf" to LibraryInfo(
            name = "Firebase Performance",
            category = LibraryCategory.FIREBASE,
            packagePrefix = "com.google.firebase.perf",
            description = "Performance monitoring",
            website = "https://firebase.google.com/products/performance"
        ),
        "com.google.firebase.dynamiclinks" to LibraryInfo(
            name = "Dynamic Links",
            category = LibraryCategory.FIREBASE,
            packagePrefix = "com.google.firebase.dynamiclinks",
            description = "Smart URLs",
            website = "https://firebase.google.com/products/dynamic-links"
        ),
        "com.google.firebase.inappmessaging" to LibraryInfo(
            name = "In-App Messaging",
            category = LibraryCategory.FIREBASE,
            packagePrefix = "com.google.firebase.inappmessaging",
            description = "Contextual messages",
            website = "https://firebase.google.com/products/in-app-messaging"
        ),

        // ==================== MACHINE LEARNING ====================
        "com.google.mlkit" to LibraryInfo(
            name = "ML Kit",
            category = LibraryCategory.MACHINE_LEARNING,
            packagePrefix = "com.google.mlkit",
            description = "On-device ML by Google",
            website = "https://developers.google.com/ml-kit"
        ),
        "org.tensorflow.lite" to LibraryInfo(
            name = "TensorFlow Lite",
            category = LibraryCategory.MACHINE_LEARNING,
            packagePrefix = "org.tensorflow.lite",
            description = "ML inference on mobile",
            website = "https://www.tensorflow.org/lite"
        ),
        "com.google.android.gms.vision" to LibraryInfo(
            name = "Mobile Vision",
            category = LibraryCategory.MACHINE_LEARNING,
            packagePrefix = "com.google.android.gms.vision",
            description = "Face/barcode detection",
            website = "https://developers.google.com/vision"
        ),
        "ai.onnxruntime" to LibraryInfo(
            name = "ONNX Runtime",
            category = LibraryCategory.MACHINE_LEARNING,
            packagePrefix = "ai.onnxruntime",
            description = "ML model inference",
            website = "https://onnxruntime.ai/"
        ),

        // ==================== CAMERA & SCANNER ====================
        "androidx.camera" to LibraryInfo(
            name = "CameraX",
            category = LibraryCategory.CAMERA,
            packagePrefix = "androidx.camera",
            description = "Jetpack Camera library",
            website = "https://developer.android.com/training/camerax"
        ),
        "com.google.zxing" to LibraryInfo(
            name = "ZXing",
            category = LibraryCategory.CAMERA,
            packagePrefix = "com.google.zxing",
            description = "Barcode scanning",
            website = "https://github.com/zxing/zxing"
        ),
        "com.journeyapps.barcodescanner" to LibraryInfo(
            name = "ZXing Android Embedded",
            category = LibraryCategory.CAMERA,
            packagePrefix = "com.journeyapps.barcodescanner",
            description = "ZXing barcode scanner",
            website = "https://github.com/journeyapps/zxing-android-embedded"
        ),
        "io.scanbot.sdk" to LibraryInfo(
            name = "Scanbot SDK",
            category = LibraryCategory.CAMERA,
            packagePrefix = "io.scanbot.sdk",
            description = "Document scanner SDK",
            website = "https://scanbot.io/"
        ),

        // ==================== BLUETOOTH & IOT ====================
        "no.nordicsemi.android" to LibraryInfo(
            name = "Nordic BLE",
            category = LibraryCategory.BLUETOOTH,
            packagePrefix = "no.nordicsemi.android",
            description = "Nordic BLE library",
            website = "https://github.com/NordicSemiconductor/Android-BLE-Library"
        ),
        "com.polidea.rxandroidble2" to LibraryInfo(
            name = "RxAndroidBle",
            category = LibraryCategory.BLUETOOTH,
            packagePrefix = "com.polidea.rxandroidble2",
            description = "RxJava BLE library",
            website = "https://github.com/Polidea/RxAndroidBle"
        ),

        // ==================== STORAGE & FILES ====================
        "com.amazonaws.mobile" to LibraryInfo(
            name = "AWS Amplify",
            category = LibraryCategory.STORAGE,
            packagePrefix = "com.amazonaws.mobile",
            description = "AWS mobile SDK",
            website = "https://aws.amazon.com/amplify/"
        ),
        "com.amazonaws.services.s3" to LibraryInfo(
            name = "AWS S3",
            category = LibraryCategory.STORAGE,
            packagePrefix = "com.amazonaws.services.s3",
            description = "Amazon S3 storage",
            website = "https://aws.amazon.com/s3/"
        ),
        "io.minio" to LibraryInfo(
            name = "MinIO",
            category = LibraryCategory.STORAGE,
            packagePrefix = "io.minio",
            description = "Object storage SDK",
            website = "https://min.io/"
        ),

        // ==================== MORE NETWORKING ====================
        "com.github.kittinunf.fuel" to LibraryInfo(
            name = "Fuel",
            category = LibraryCategory.NETWORKING,
            packagePrefix = "com.github.kittinunf.fuel",
            description = "Kotlin HTTP library",
            website = "https://github.com/kittinunf/fuel"
        ),
        "com.koushikdutta.async" to LibraryInfo(
            name = "AndroidAsync",
            category = LibraryCategory.NETWORKING,
            packagePrefix = "com.koushikdutta.async",
            description = "Async socket/HTTP",
            website = "https://github.com/nicholasquinn/AndroidAsync"
        ),

        // ==================== MORE UI ====================
        "com.facebook.shimmer" to LibraryInfo(
            name = "Shimmer",
            category = LibraryCategory.UI,
            packagePrefix = "com.facebook.shimmer",
            description = "Shimmer loading effect",
            website = "https://github.com/facebook/shimmer-android"
        ),
        "com.github.skydoves.balloon" to LibraryInfo(
            name = "Balloon",
            category = LibraryCategory.UI,
            packagePrefix = "com.skydoves.balloon",
            description = "Tooltip library",
            website = "https://github.com/skydoves/Balloon"
        ),
        "com.github.skydoves.landscapist" to LibraryInfo(
            name = "Landscapist",
            category = LibraryCategory.UI,
            packagePrefix = "com.skydoves.landscapist",
            description = "Compose image loading",
            website = "https://github.com/skydoves/landscapist"
        ),
        "com.google.accompanist" to LibraryInfo(
            name = "Accompanist",
            category = LibraryCategory.UI,
            packagePrefix = "com.google.accompanist",
            description = "Compose utilities",
            website = "https://google.github.io/accompanist/"
        ),
        "io.github.raamcosta.composedestinations" to LibraryInfo(
            name = "Compose Destinations",
            category = LibraryCategory.UI,
            packagePrefix = "com.ramcosta.composedestinations",
            description = "Type-safe navigation",
            website = "https://composedestinations.rafaelcosta.xyz/"
        ),
        "cafe.adriel.voyager" to LibraryInfo(
            name = "Voyager",
            category = LibraryCategory.UI,
            packagePrefix = "cafe.adriel.voyager",
            description = "Compose navigation",
            website = "https://voyager.adriel.cafe/"
        ),
        "com.patrykandpatrick.vico" to LibraryInfo(
            name = "Vico",
            category = LibraryCategory.UI,
            packagePrefix = "com.patrykandpatrick.vico",
            description = "Compose charts",
            website = "https://patrykandpatrick.com/vico/"
        ),
        "com.github.PhilJay.MPAndroidChart" to LibraryInfo(
            name = "MPAndroidChart",
            category = LibraryCategory.UI,
            packagePrefix = "com.github.mikephil.charting",
            description = "Charting library",
            website = "https://github.com/PhilJay/MPAndroidChart"
        ),

        // ==================== MORE UTILITIES ====================
        "com.google.android.play.core" to LibraryInfo(
            name = "Play Core",
            category = LibraryCategory.UTILITIES,
            packagePrefix = "com.google.android.play.core",
            description = "In-app updates/reviews",
            website = "https://developer.android.com/guide/playcore"
        ),
        "androidx.work" to LibraryInfo(
            name = "WorkManager",
            category = LibraryCategory.UTILITIES,
            packagePrefix = "androidx.work",
            description = "Background task scheduling",
            website = "https://developer.android.com/topic/libraries/architecture/workmanager"
        ),
        "androidx.datastore" to LibraryInfo(
            name = "DataStore",
            category = LibraryCategory.UTILITIES,
            packagePrefix = "androidx.datastore",
            description = "Data storage solution",
            website = "https://developer.android.com/topic/libraries/architecture/datastore"
        ),
        "com.google.android.play.integrity" to LibraryInfo(
            name = "Play Integrity",
            category = LibraryCategory.SECURITY,
            packagePrefix = "com.google.android.play.integrity",
            description = "App integrity verification",
            website = "https://developer.android.com/google/play/integrity"
        ),
        "com.scottyab.rootbeer" to LibraryInfo(
            name = "RootBeer",
            category = LibraryCategory.SECURITY,
            packagePrefix = "com.scottyab.rootbeer",
            description = "Root detection library",
            website = "https://github.com/scottyab/rootbeer"
        ),

        // ==================== CORDOVA/IONIC/CAPACITOR ====================
        "org.apache.cordova" to LibraryInfo(
            name = "Apache Cordova",
            category = LibraryCategory.UTILITIES,
            packagePrefix = "org.apache.cordova",
            description = "Cordova hybrid framework",
            website = "https://cordova.apache.org/"
        ),
        "com.getcapacitor" to LibraryInfo(
            name = "Capacitor",
            category = LibraryCategory.UTILITIES,
            packagePrefix = "com.getcapacitor",
            description = "Ionic's native runtime",
            website = "https://capacitorjs.com/"
        ),
        "io.ionic.keyboard" to LibraryInfo(
            name = "Ionic Keyboard",
            category = LibraryCategory.UTILITIES,
            packagePrefix = "io.ionic.keyboard",
            description = "Ionic keyboard plugin",
            website = "https://ionicframework.com/"
        ),
        "org.pgsqlite" to LibraryInfo(
            name = "Cordova SQLite",
            category = LibraryCategory.DATABASE,
            packagePrefix = "org.pgsqlite",
            description = "Cordova SQLite storage",
            website = "https://github.com/nicholasquinn/pgsqlite"
        ),
        "nl.nicholasquinn.webintent" to LibraryInfo(
            name = "Cordova WebIntent",
            category = LibraryCategory.UTILITIES,
            packagePrefix = "nl.nicholasquinn.webintent",
            description = "Cordova web intent plugin",
            website = "https://cordova.apache.org/"
        ),

        // ==================== MORE ANALYTICS ====================
        "com.moengage" to LibraryInfo(
            name = "MoEngage",
            category = LibraryCategory.ANALYTICS,
            packagePrefix = "com.moengage",
            description = "Customer engagement platform",
            website = "https://www.moengage.com/"
        ),
        "com.webengage" to LibraryInfo(
            name = "WebEngage",
            category = LibraryCategory.ANALYTICS,
            packagePrefix = "com.webengage",
            description = "Customer data platform",
            website = "https://webengage.com/"
        ),
        "com.netcore.android" to LibraryInfo(
            name = "Netcore Smartech",
            category = LibraryCategory.ANALYTICS,
            packagePrefix = "com.netcore.android",
            description = "Marketing automation",
            website = "https://netcorecloud.com/"
        )
    )
}
