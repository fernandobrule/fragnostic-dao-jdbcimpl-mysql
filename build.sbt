import com.jsuereth.sbtpgp.PgpKeys
import scala.xml._
import java.net.URL
import Dependencies._

val unusedOptions = Seq("-Ywarn-unused:imports")

val scala3migaration = Def.settings(
  scalacOptions ++= {
    if (scalaBinaryVersion.value == "3") {
      Seq(
        "-source:3.0-migration",
      )
    } else {
      Nil
    }
  }
)

lazy val fragnosticDaoJdbcMysqlImplSettings = Seq(
  organization := "com.fragnostic",
  //logLevel := Level.Error,
  Test / fork := true,
  Test / baseDirectory := (ThisBuild / baseDirectory).value,
  crossScalaVersions := Seq("2.12.18", "2.13.10", "2.13.11", "2.13.12", "2.13.13", "3.3.0"),
  scalaVersion := crossScalaVersions.value.head,
  Test / testOptions ++= {
    if (scalaBinaryVersion.value == "3") {
      Seq(
        Tests.Exclude(Set(
          //"org.scalatra.swagger.ModelSpec",
          //"org.scalatra.swagger.SwaggerSpec2",
        )),
      )
    } else {
      Nil
    }
  },
  scalacOptions ++= {
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, _)) =>
        unusedOptions ++ Seq(
          "-release:8",
          "-Xlint",
          "-Xcheckinit",
        )
      case _ =>
        Nil
    }
  },
  javacOptions ++= Seq(
    "-source", "11",
    "-target", "11",
  ),
  scalacOptions ++= Seq(
    "-unchecked",
    "-deprecation",
    /*"-Yinline-warnings",*/
    "-encoding", "utf8",
    "-feature",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-language:existentials"
  ),
  manifestSetting,
) ++ mavenCentralFrouFrou ++ Seq(Compile, Test).flatMap(c =>
  c / console / scalacOptions --= unusedOptions
)

lazy val fragnosticDaoJdbcMysqlImplProject = Project(
  id = "fragnostic-dao-jdbc-mysql-impl-project",
  base = file(".")).settings(
    fragnosticDaoJdbcMysqlImplSettings ++ Seq(
    name := "fragnostic-dao-jdbc-mysql-impl",
    artifacts := Classpaths.artifactDefs(Seq(Compile / packageDoc, Compile / makePom)).value,
    packagedArtifacts := Classpaths.packaged(Seq(Compile / packageDoc, Compile / makePom)).value,
    description := "fragnostic-dao-jdbc-mysql-impl",
    shellPrompt := { state =>
      s"sbt:${Project.extract(state).currentProject.id}" + Def.withColor("> ", Option(scala.Console.CYAN))
    }
  ) ++ Defaults.packageTaskSettings(
    Compile / packageDoc, (Compile / unidoc).map(_.flatMap(Path.allSubpaths))
  )).aggregate(
    fragnosticDaoJdbcMysqlImpl
  ).enablePlugins(ScalaUnidocPlugin)

lazy val fragnosticDaoJdbcMysqlImpl = Project(
  id = "fragnostic-dao-jdbc-mysql-impl",
  base = file("fragnostic-dao-jdbc-mysql-impl")).settings(fragnosticDaoJdbcMysqlImplSettings ++ Seq(
    libraryDependencies ++= Seq(
      fragnosticConfEnv,
      fragnosticDaoApi,
      fragnosticSupport,
      logbackClassic,
      mysql8JavaClient,
      scalatestFunSpec,
      slf4jApi//
    ),
    description := "fragnostic-dao-jdbc-mysql-impl"
  )
) dependsOn(
  //
)

lazy val manifestSetting = packageOptions += {
  Package.ManifestAttributes(
    "Created-By" -> "Simple Build Tool",
    "Built-By" -> System.getProperty("user.name"),
    "Build-Jdk" -> System.getProperty("java.version"),
    "Specification-Title" -> name.value,
    "Specification-Version" -> version.value,
    "Specification-Vendor" -> organization.value,
    "Implementation-Title" -> name.value,
    "Implementation-Version" -> version.value,
    "Implementation-Vendor-Id" -> organization.value,
    "Implementation-Vendor" -> organization.value
  )
}

// Things we care about primarily because Maven Central demands them
lazy val mavenCentralFrouFrou = Seq(
  homepage := Some(url("http://www.fragnostic-dao-jdbc-mysql-impl.org/")),
  startYear := Some(2022),
  licenses := Seq(("BSD", url("http://github.com/fragnostic-dao-jdbc-mysql-impl/fragnostic-dao-jdbc-mysql-impl/raw/HEAD/LICENSE"))),
  pomExtra := pomExtra.value ++ Group(
    <scm>
      <url>http://github.com/fragnostic-dao-jdbc-mysql-impl/fragnostic-dao-jdbc-mysql-impl</url>
      <connection>scm:git:git://github.com/fragnostic-dao-jdbc-mysql-impl/fragnostic-dao-jdbc-mysql-impl.git</connection>
    </scm>
    <developers>
      <developer>
        <id>fbrule</id>
        <name>Fernando Brule</name>
        <url>http://www.fbrule.info</url>
      </developer>      
    </developers>
  )
)

lazy val doNotPublish = Seq(publish := {}, publishLocal := {}, PgpKeys.publishSigned := {}, PgpKeys.publishLocalSigned := {})

