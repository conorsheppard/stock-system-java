#!/bin/bash

if [ "$1" = "native" ];
then
  ./target/stock-system-java
else
  mvn exec:java -q
fi