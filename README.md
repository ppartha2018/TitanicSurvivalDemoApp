# TitanicSurvivalDemoApp
Demo of a full stack application with Spring Boot and JQX grid.

Demo url hosted in aws: [http://ec2-3-86-103-32.compute-1.amazonaws.com:8080/]

The application is created with Spring boot for RESTful layer, Spring Data JPA Repositories (with hibernate ORM) for handling all CRUD operations. The frontend is created with JQX grid and highcharts. The demo is performed by using a MySQL database version 5.6.

# Running the applicaiton:

git clone https://github.com/ppartha2018/TitanicSurvivalDemoApp
mvn clean
mvn install
java -jar target/TitanicDemo-0.0.1-SNAPSHOT.jar

# MySQL setup(non-docker):

MySQL can used from a standalone installation or a dockerized container (more on that later).
Spring boot's application.properties is configured with 
spring.jpa.hibernate.ddl-auto=create - This ensures the DDL is created for the first the application is executed
spring.datasource.url=jdbc:mysql://localhost:3306/titanic - Expects mysql server on localhost with a database created with name "titanic"
spring.datasource.username=demo
spring.datasource.password=demo123

In MySQL console: - Creating database, application user and setting permissions.
mysql -u root -p
<root_password>

CREATE DATABASE titanic;
CREATE USER 'demo'@'localhost' identified by 'demo123';
GRANT ALL ON titanic.* to 'demo'@'localhost' identified by 'demo123';

# Docker Instructions:
The Dockerfile present in the root of the project has following important details:
#Make port 8080 available to the world outside this container
EXPOSE 8080

#The application's jar file
ARG JAR_FILE=target/TitanicDemo-0.0.1-SNAPSHOT.jar

#Add the application's jar to the container
ADD ${JAR_FILE} titanic_demo.jar

# Containerize the application:
mvn clean
mvn install
docker build -f Dockerfile -t titanicdemo .

Before running the docker, we need to setup mysql container.
Mysql provides docker containers from docker hub and tagged based on their verions. The following command pulls and installs the mysql standalone docker container with necessary settings (database, app user, port).

docker run -d --name=app-network --name mysql-docker-container -e MYSQL_ROOT_PASSWORD=root123 -e MYSQL_DATABASE=titanic -e MYSQL_USER=demo -e MYSQL_PASSWORD=demo123 -p 3306:3306 -d mysql:5.6

The -p 3306:3306 tells the container to run mysql on 3306 and expose it through 3306 port of the host.

Now that we have installed the mysql container, we can go ahead the run our application contianer.
docker run --name titanicdemo_container -p 8080:8080 -t titanicdemo --link mysql-docker-container:mysql

The --link property tells the app container to link with mysql container so that the app can use mysql service. Other option would be to run mysql docker separately and configure the database url with container ip_address.

# Other Information:
# AWS Setup:
This demo application runs on AWS EC2 machine with CENT OS7. 
Setup details:
LAUNCH CentOS7 EC2 instance from AWS Console.
Create security group that allows port forwarding rules for inbound, outbound on 22 (SSH), 8080 (App port).
We can access the server with Putty like SSH client by creating Auth keys.
//share an url here
// ssh to <aws_ip> with centos user login

Installing necessary services:
sudo yum update
sudo yum install git
sudo yum install maven
sudo yum install docker

Mysql: (for non-docker setup)

wget http://repo.mysql.com/mysql-community-release-el7-5.noarch.rpm
sudo rpm -ivh mysql-community-release-el7-5.noarch.rpm
yum update

sudo yum install mysql-server
sudo systemctl start mysqld
