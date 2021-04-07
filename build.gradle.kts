import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.4"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.31"
	kotlin("plugin.spring") version "1.4.31"
	id("com.netflix.dgs.codegen") version "4.4.3"
}

group = "sall.good"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2020.0.2"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
	implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")

	implementation("org.springframework.cloud:spring-cloud-starter-bootstrap:3.0.2")
	implementation("com.playtika.testcontainers:embedded-elasticsearch:2.0.5")

	implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter:latest.release")
	implementation ("com.graphql-java:graphql-java-extended-scalars:2021-03-30T02-07-32-573beab")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

@OptIn(ExperimentalStdlibApi::class)
tasks.withType<com.netflix.graphql.dgs.codegen.gradle.GenerateJavaTask> {
	packageName = "com.example.demo.generated"
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
