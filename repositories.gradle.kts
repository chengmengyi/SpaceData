rootProject.extra.apply {
    set("androidPlugin", "com.android.tools.build:gradle:7.0.2")
    set("kotlinVersion", "1.7.10")
}

repositories {
    google()
    mavenCentral()
    jcenter()
    maven(url = "https://jitpack.io")
    maven(url = "https://maven.google.com")
    maven(url = "https://maven.aliyun.com/repository/public")
}
