plugins {
    id("idea")
    id("java-library")
    id("maven-publish")
    id("net.neoforged.moddev") version "2.0.141"
}

version = "${property("minecraft_version")}-${property("mod_version")}"
if (System.getenv("BUILD_NUMBER") != null) {
    version = "$version.${System.getenv("BUILD_NUMBER")}"
}
val baseArchivesName = project.property("mod_id").toString()
group = "${property("mod_group_id")}"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

val localRuntime: Configuration by configurations.creating

configurations.runtimeClasspath {
    extendsFrom(localRuntime)
}

neoForge {
    version = property("neo_version").toString()

    parchment {
        mappingsVersion.set(project.property("parchment_mappings_version").toString())
        minecraftVersion.set(project.property("parchment_minecraft_version").toString())
    }

    setAccessTransformers(
        "src/main/resources/META-INF/accesstransformer.cfg"
    )

    runs {
        register("client") {
            client()
        }

        register("server") {
            server()
        }

        register("data") {
            data()
            programArguments.addAll(
                "--mod", property("mod_id").toString(),
                "--all",
                "--output", file("src/generated/resources/").absolutePath,
                "--existing", file("src/main/resources/").absolutePath,
                "--existing-mod", "lodestone"
            )
        }

        configureEach {
            jvmArgument("-Dmixin.debug=true")
            jvmArgument("-Xmx4G")
        }
    }

    mods {
        create("${property("mod_id")}") {
            sourceSet(sourceSets.main.get())
        }
    }
}

sourceSets {
    main {
        resources.srcDir("src/generated/resources")
    }
}

repositories {
    mavenCentral()
    maven("https://maven.blamejared.com/") // Lodestone
    maven("https://maven.theillusivec4.top/") // Curios

    maven("https://cursemaven.com") { // Curse Maven
        content {
            includeGroup("curse.maven")
        }
    }

    maven("https://maven.parchmentmc.org") { // ParchmentMC
        content {
            includeGroup("org.parchmentmc.data")
        }
    }

    maven("https://modmaven.dev") // ModMaven

    maven("https://api.modrinth.com/maven") // Modrinth Maven

    maven("https://maven.latvian.dev/releases") { // KubeJS
        content {
            includeGroup("dev.latvian.mods")
            includeGroup("dev.latvian.apps")
        }
    }

    maven("https://jitpack.io") { // KubeJS Dependencies
        content {
            includeGroup("io.github")
            includeGroup("com.github.rtyley")
        }
    }

    maven("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/") { // GeckoLib
        content {
            includeGroup("software.bernie.geckolib")
        }
    }

    maven("https://maven.kosmx.dev/") // Player Animation Lib
    maven("https://maven.teamresourceful.com/repository/maven-public/") // Athena
    maven("https://maven.squiddev.cc") // CC Tweaked
    maven("https://maven.ryanhcode.dev/releases") // Aero & Sable
    maven("https://maven.createmod.net") // Create
    maven("https://maven.tterrag.com") // Registrate
}

dependencies {
    val minecraftVersion = property("minecraft_version")
    val sableCompanionVersion = property("sable_companion_version")

    val lodestone = "team.lodestar.lodestone:lodestone:$minecraftVersion-${property("lodestone_version")}"
    val curios = "top.theillusivec4.curios:curios-neoforge:${property("curios_version")}"
    val waywardAttributes = "team.lodestar.wayward_attributes:wayward_attributes:$minecraftVersion-${property("wayward_attributes_version")}"

    val kubejs = "dev.latvian.mods:kubejs-neoforge:${property("kubejs_version")}"
    val resourcefulLib = "com.teamresourceful.resourcefullib:resourcefullib-neoforge-1.21:${property("resourceful_lib_version")}"
    val athena = "earth.terrarium.athena:athena-neoforge-$minecraftVersion:${property("athena_version")}"

    accessTransformers(lodestone)

    jarJar(api("dev.ryanhcode.sable-companion:sable-companion-common-$minecraftVersion:$sableCompanionVersion") {
        version {
            prefer("$sableCompanionVersion")
        }
    })

    // JEI
    compileOnlyApi("mezz.jei:jei-$minecraftVersion-neoforge-api:${property("jei_version")}")
    runtimeOnly("mezz.jei:jei-$minecraftVersion-neoforge:${property("jei_version")}")

    modRuntime(curios)
    modRuntime(lodestone)
    modRuntime(waywardAttributes)

    modRuntime(kubejs)
    modRuntime(resourcefulLib)
    modRuntime(athena)

    modRuntime("curse.maven:farmers-delight-398521:5878217")
    
//    modRuntime("dev.eriksonn.aeronautics:aeronautics-neoforge-1.21.1:1.3.0")
//    modRuntime("dev.simulated_team.simulated:simulated-neoforge-1.21.1:1.3.0")

//    modRuntime("curse.maven:imguimc-1524200:8617391")

    runtimeOnly("curse.maven:ftb-library-forge-404465:5754910")
    runtimeOnly("curse.maven:architectury-api-419699:5786327")

    localRuntime("curse.maven:neat-238372:7774002")


//    localRuntime("curse.maven:blueprint-382216:8397829")
//    localRuntime("curse.maven:caverns-and-chasms-438005:8155745")
}

val generateModMetadata by tasks.registering(ProcessResources::class) {
    val replaceProperties = mapOf(
        "minecraft_version" to project.findProperty("minecraft_version") as String,
        "minecraft_version_range" to project.findProperty("minecraft_version_range") as String,
        "neo_version" to project.findProperty("neo_version") as String,
        "neo_version_range" to project.findProperty("neo_version_range") as String,
        "loader_version_range" to project.findProperty("loader_version_range") as String,
        "mod_id" to project.findProperty("mod_id") as String,
        "mod_name" to project.findProperty("mod_name") as String,
        "mod_license" to project.findProperty("mod_license") as String,
        "mod_version" to project.findProperty("mod_version") as String,
        "mod_authors" to project.findProperty("mod_authors") as String,
        "mod_description" to project.findProperty("mod_description") as String,
        "lodestone_version_range" to project.findProperty("lodestone_version_range") as String
    )
    inputs.properties(replaceProperties)
    expand(replaceProperties)

    filesMatching("**/*.java") {
        exclude()
    }

    from("src/main/templates")
    into("build/generated/sources/modMetadata")
}
sourceSets["main"].resources.srcDir(generateModMetadata)
neoForge.ideSyncTask(generateModMetadata)

java {
//    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            artifactId = "${property("mod_id")}"
            from(components["java"])
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


private fun DependencyHandlerScope.modRuntime(dep: Any) {
    compileOnlyApi(dep)
    localRuntime(dep)
}

