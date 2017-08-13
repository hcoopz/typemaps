lazy val core = project

lazy val benchmark = project dependsOn core
lazy val shapeless = project dependsOn core
