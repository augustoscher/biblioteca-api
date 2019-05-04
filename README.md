# Biblioteca-API #

#### Java multi-tenancy rest api for BibliotecaApp ####

* Rest-Api - Biblioteca
* 0.1.0

##### Pré-requisitos:  
- Docker deve estar instalado.
- Apache Maven deve estar instalado.
- Postman deve estar instaldo. (para execução de testes)

  
### Setting Up 
----

##### 1. Clonar repositório  
> git clone git@github.com:augustoscher/biblioteca-api.git

##### 2. Acessar repositório
> cd biblioteca-api  

##### 3. Imagem Docker
a) Gerar .jar
> mvn install

b) Através do Maven
> mvn clean package dockerfile:build  

ou
c) Através do Docker
> docker build -t augustoscher/biblioteca-api:0.0.1 .  

##### 4. Iniciar aplicação  
> docker-compose up -d  

##### 5. Testar database  
> docker-compose exec db psql -U postgres -f /scripts/check.sql  

ou  
> docker-compose exec db psql -U postgres biblioteca -c "SELECT * FROM usuario;"  

##### 6. Testar API
> http://localhost:8282/v2/api-docs

  
### Configurações
----

##### 1. Fazer login
a) Através do Postman, criar uma nova requisição POST  
> localhost:8282/login  

Body:
>```{"login": "usera", "senha": "./augusto"}```

b) Guardar access token
>```{"access_token":"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyYSIsImV4cCI6MTU1NzAyNjk2Nn0.5HtKQlsIhj1zdM0O1U0uLShLpQ6T7MNRQSrFcv73hDbQ1C4FM9tm_l9yHVoUMLqAWpSCHt7UCJ99hZgcInznRA","tenant":""}```

