dev: 
	docker compose up -d --remove-orphans

down:
	docker compose down

build:
	mvn --file ./smtppranker/pom.xml package

run:
	java -jar ./smtppranker/target/smtppranker-1.0.jar

clean:
	mvn --file ./smtppranker/pom.xml clean