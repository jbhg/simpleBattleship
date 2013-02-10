all: compile run

compile:
	mkdir -p bin 
	javac -Xlint:deprecation -Xlint:unchecked */*.java -d bin

run:
	#!/bin/bash
	java -ea -cp ./bin logic.Main

clean: 
	rm -rf bin 


