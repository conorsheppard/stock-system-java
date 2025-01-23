#!/bin/bash

if [ "$1" = "native" ];
then
  ./target/ferovinum-technical-assignment
else
  mvn exec:java -q
fi