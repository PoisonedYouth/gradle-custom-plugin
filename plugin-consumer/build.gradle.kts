plugins{
    id("com.poisonedyouth.countLines").version("1.0.0")
    id ("org.jetbrains.kotlin.jvm").version("1.8.0")
}

repositories{
    mavenCentral()
}

tasks.countLines {
    println("Configure Task")
    fileTypes = listOf("kt", "kts", "yaml")

}
