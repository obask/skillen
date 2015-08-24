name         := "skillen"

version      := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= {
  val akkaV       = "2.3.12"
  val akkaStreamV = "1.0"
  val luceneVersion = "5.3.0"
  val redisVersion = "0.7"
  Seq(
    "com.typesafe.akka" %% "akka-actor"                        % akkaV,
    "com.typesafe.akka" %% "akka-stream-experimental"          % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-core-experimental"       % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-experimental"            % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-testkit-experimental"    % akkaStreamV,
    "org.apache.lucene" %  "lucene-core"                       % luceneVersion,
    "org.apache.lucene" %  "lucene-analyzers-common"           % luceneVersion,
    "org.apache.lucene" %  "lucene-queryparser"                % luceneVersion,
    "net.debasishg"     %% "redisreact"                        % redisVersion
  )
}
