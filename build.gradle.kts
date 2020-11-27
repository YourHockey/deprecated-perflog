plugins {
    id("org.springframework.boot") version "2.4.0"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    java
	`maven-publish`
}

group = "ru.vakoom.yourhockey"
version = "0.0.1"

java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

    implementation("org.springframework.boot:spring-boot-starter-aop")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<Jar> {
	enabled = true
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
	enabled = false
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

publishing {
	publications {
		create<MavenPublication>("maven") {
			groupId = project.group.toString()
			artifactId = project.name
			version = project.version.toString()
			from(components["java"])
			versionMapping {
				usage("java-api") {
					fromResolutionOf("runtimeClasspath")
				}
				usage("java-runtime") {
					fromResolutionResult()
				}
			}
		}
	}

	repositories {
		maven {
			name = "GitHubPackages"
			url = uri("https://maven.pkg.github.com/YourHockey/perflog")
			credentials {
				username = System.getenv("GITHUB_ACTOR")
				password = System.getenv("GITHUB_TOKEN")
			}
		}
	}
}
