plugins {
    id("fabric-loom") version "1.6-SNAPSHOT"
    `maven-publish`
    java
}

val port_lib_modules: String by extra

jarJar.enable()

version = "${property("minecraft_version")}-${property("mod_version")}"
if (System.getenv("BUILD_NUMBER") != null) {
    version = "$minecraftVersion-$modVersion.${System.getenv("BUILD_NUMBER")}"
}

loom {
    accessWidenerPath = file("src/main/resources/malum.accesswidener")
}


repositories {
    flatDir {
        dirs("lib")
    }
    mavenCentral()
    maven {
        name = "Curios maven"
        url = uri("https://maven.theillusivec4.top/")
    }
    maven {
        name = "JEI maven"
        url = uri("https://dvs1.progwml6.com/files/maven")
    }
    maven {
        name = "tterrag maven"
        url = uri("https://maven.tterrag.com/")
    }
    maven {
        name = "BlameJared maven"
        url = uri("https://maven.blamejared.com/")
    }
    maven {
        name = "Curse Maven"
        url = uri("https://cursemaven.com")
        content {
            includeGroup("curse.maven")
        }
    }

    maven {
        name = "jitpack"
        url = uri("https://jitpack.io")
        content {
            includeGroup("io.github")
        }
    }

}

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")

    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-1.20.1:2023.09.03@zip")
    })

    modImplementation("net.fabricmc:fabric-loader:${property("loader_version")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabric_api_version")}")



    // JEI Dependency
    modCompileOnlyApi("mezz.jei:jei-${property("minecraft_version")}-common-api:${property("jei_version")}")
    modCompileOnlyApi("mezz.jei:jei-${property("minecraft_version")}-fabric-api:${property("jei_version")}")
    // at runtime, use the full JEI jar for Fabric
    modRuntimeOnly("mezz.jei:jei-${property("minecraft_version")}-fabric:${property("jei_version")}")

    // Trinkets Dependency
    modImplementation("dev.emi:trinkets:${property("trinkets_version")}") { isTransitive = false }

    modImplementation("team.lodestar.lodestone:lodestone:${property("minecraft_version")}-${property("lodestone_version")}")

    //compileOnly(fg.deobf("curse.maven:farmers_delight-398521:4638874"))

    //runtimeOnly(fg.deobf("curse.maven:create-328085:4626108"))
    //implementation(fg.deobf("curse.maven:jeed-532286:4599236"))

    //runtimeOnly(fg.deobf("curse.maven:world-stripper-250603:4578579"))
    //runtimeOnly(fg.deobf("curse.maven:spark-361579:4587309"))
    //runtimeOnly(fg.deobf("curse.maven:attributefix-280510:4911084"))
    //runtimeOnly(fg.deobf("curse.maven:overloaded-armor-bar-314002:4631133"))
}

tasks {

    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand(getProperties())
            expand(mutableMapOf("version" to project.version))
        }
    }

    jar {
        from("LICENSE")
    }


}

java {
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()
}

//jar {
////    exclude 'com/sammy/malum/core/data/**'
//	exclude 'com/sammy/malum/client/model/bbmodels/**'
//	exclude 'assets/malum/models/block/bbmodels/**'
//}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            artifactId = "${property("mod_id")}"
            from(components["java"])
            fg.component(this)
            jarJar.component(this)
        }
    }
    repositories {
        maven {
            url = uri("file://${System.getenv("local_maven")}")
        }
    }
}

idea {
    module {
        for (fileName in listOf("run", "out", "logs")) {
            excludeDirs.add(file(fileName))
        }
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
