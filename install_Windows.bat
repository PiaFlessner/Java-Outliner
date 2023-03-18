#!/bin/bash
#compile
javac -cp src -d bin src\App.java -encoding UTF-8
#build
jar cvmf Manifest.txt Java-Outliner.jar -C bin -C bin main