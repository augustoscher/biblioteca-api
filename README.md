# Biblioteca-API #

#### Java multi-tenancy rest api for BibliotecaApp ####

* Rest-Api - Biblioteca
* 0.1.0

##### Pré-requisitos:  
- Docker deve estar instalado.
- Maven pode estar instalado (opcional).

  
### Setting Up 
----

##### 1. Clonar repositório  
> git clone git@github.com:augustoscher/biblioteca-api.git

##### 2. Acessar repositório
> cd biblioteca-api  

##### 3. Imagem Docker

a) Através do Maven
> mvn clean package dockerfile:build  

b) Através do Docker
> docker build -t augustoscher/biblioteca-api:0.0.1 .  

##### 4. Iniciar aplicação  
> docker-compose up -d  

##### 5. Testar database  
> docker-compose exec db psql -U postgres -f /scripts/check.sql  

ou  
> docker-compose exec db psql -U postgres biblioteca -c "SELECT * FROM usuario;"  

##### 6. Testar API
> http://localhost:8282/v2/api-docs

  
### Configuração Tenant
----
