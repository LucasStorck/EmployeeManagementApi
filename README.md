# Gerenciador de Funcionários
#### Sistema de Gerenciamento de Funcionários: Este sistema permite a gestão de dados de funcionários, oferecendo funcionalidades para criar, ler, atualizar e excluir (CRUD) informações relacionadas aos funcionários. O acesso completo a esses recursos é restrito aos usuários registrados com a permissão de 'SUPERUSER', garantindo que apenas administradores ou pessoas com privilégios elevados possam realizar essas operações sensíveis.

## Tecnologias

- Java 21
- Spring
- Spring Security
- Oauth 2.0
- PostgreSQL
- Flyway
- Swagger
- Docker

# Configurando a Chave Pública e Chave Privada
Para configurar as chaves para autenticação via JWT, siga as instruções abaixo:

- Crie um diretório `jwt` dentro da pasta `resources`

- Acesse o diretório `jwt` dentro da pasta `resources`:
```
cd \src\main\resources\jwt>
```
- DGere da chave pública executando o comando:
```
openssl genpkey -algorithm RSA -out app.key -outform PEM
```
- Em seguida, gere a chave privada com o comando:
```
openssl rsa -pubout -in app.key -out app.pub
```

# Configurando o Docker
Para criar a imagem do Docker, siga os passos:

### 1. Configuração do Dockerfile
- Execute o comando para criar o pacote do aplicativo (sem rodar os testes):
  
```
mvn clean package -DskipTests
```
- Em seguida, execute o comando para criar a imagem Docker:
```
docker build -t employees-app .
```
### 2. Configuração do Docker Compose

- No arquivo docker-compose.yml, configure o usuário e a senha de acordo com o que será utilizado no banco de dados.
- No arquivo application.properties, configure as mesmas credenciais de usuário e senha que você definiu no docker-compose.yml.

Depois de realizar essas configurações, execute o seguinte comando para subir os containers:
```
docker-compose up -d
```
# Comfigurando o Swagger

Após configurar o Docker e subir os containers, você pode acessar a documentação interativa do Swagger no seguinte link:
```
http://localhost:8080/swagger-ui/index.html
```
