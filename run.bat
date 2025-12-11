\
@echo off
if not exist lib\mysql-connector-java-8.0.x.jar (
  echo Please put mysql-connector-java-8.0.x.jar into lib\ before running.
  pause
  exit /b 1
)
if not exist out mkdir out
javac -cp "lib\mysql-connector-java-8.0.x.jar" -d out src\*.java
if %errorlevel%==0 (
  java -cp "out;lib\mysql-connector-java-8.0.x.jar" HostelSystem
)
