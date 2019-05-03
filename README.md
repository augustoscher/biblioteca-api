# Biblioteca-API #

#### Java multi-tenancy rest api for BibliotecaApp ####

* Rest-Api - Biblioteca
* 0.1.0


####a) Setting Up ####

----
##### Pré-requisitos:  
- Docker deve estar instalado.
- Maven pode estar instalado (opcional).

----
##### Clonar repositório  
> git clone git@bitbucket.org:augustoscher/biblioteca-api.git  

----
##### Acessar repositório
> cd biblioteca-api  

----
##### Imagem Docker

a) Através do Maven
> mvn clean package dockerfile:build  

b) Através do Docker
> docker build -t augustoscher/biblioteca-api:0.0.1 .  

----
##### Iniciar aplicação  
> docker-compose up -d  

----
##### Testar database  
> docker-compose exec db psql -U postgres -f /scripts/check.sql  
ou  
> docker-compose exec db psql -U postgres biblioteca -c "SELECT * FROM usuario;"  

----
##### Testar API
> http://localhost:8282/v2/api-docs


###b) Configuração Tenant ###
