SHELL := /bin/bash

default: run

clean:
	./mvnw clean

install:
	./mvnw clean install -U

package:
	./mvnw package

test:
	./mvnw test

build-native:
	./mvnw clean -P native,no-tests native:compile

run-native:
	./shell/run.sh native

mvn-run: clean package
	./shell/run.sh

test-coverage:
	./mvnw clean org.jacoco:jacoco-maven-plugin:0.8.12:prepare-agent verify org.jacoco:jacoco-maven-plugin:0.8.12:report

check-coverage:
	open -a Google\ Chrome target/jacoco-report/index.html

coverage-badge-gen:
	python3 -m jacoco_badge_generator -j target/jacoco-report/jacoco.csv

build: clean package
	docker build --no-cache . -t conorsheppard/stock-system-java

run:
	docker run --rm -it conorsheppard/stock-system-java

.SILENT:
.PHONY: default clean install package test build-native mvn-run test-coverage check-coverage coverage-badge-gen build pull run