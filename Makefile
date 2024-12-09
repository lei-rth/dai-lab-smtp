include .env
export $(shell sed 's/=.*//' .env)

dev: 
	docker compose up -d --remove-orphans

down:
	docker compose down

install:
	mvn --file ./smtppranker/pom.xml clean install

build:
	mvn --file ./smtppranker/pom.xml clean package

run:
	java -jar ./smtppranker/target/smtppranker-1.0.jar $$VICTIMS_LIST $$MESSAGES_LIST $$NUMBER_GROUPS

clean:
	mvn --file ./smtppranker/pom.xml clean
	rm -Rf ./smtppranker/dependency-reduced-pom.xml
