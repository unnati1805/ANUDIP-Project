#!/bin/bash
# simple build & run (Linux / macOS)
mkdir -p out
JAR=lib/mysql-connector-java-8.0.x.jar
if [ ! -f "$JAR" ]; then
  echo "Please put mysql-connector-java-8.0.x.jar into lib/ before running."
  exit 1
fi
javac -cp "$JAR" -d out src/*.java
if [ $? -eq 0 ]; then
  java -cp "out:$JAR" HostelSystem
fi
