all: start

start:
	docker-compose up -d

console:
	docker-compose up

stop:
	docker-compose down

build:
	docker build -t cleancity_server .
pkg:
	./mwnv package
