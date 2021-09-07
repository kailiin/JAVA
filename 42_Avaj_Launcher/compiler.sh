#!/bin/sh

find * -name "*.java" > sources.txt
javac --release 7 @sources.txt
echo "Usage: java avaj.Simulation scenario.txt"