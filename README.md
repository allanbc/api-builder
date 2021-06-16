# api-builder-cliente

API de clientes da prova da Builder.

1. Sobre o projeto
    O objetivo deste projeto é criar uma API rest que armazenará dados clientes Retornando também a idade do cliente.

2.Tecnologias utilizadas
    Para o presente projeto foi utilizado a linguagem Java na sua versão 11, Spring Boot 2.4.4 e a IDE Eclipse STS. Para a persistência dos dados foi utilizado o banco de dados relacional MySQL e para testes da api o Postman junto com o framework JUNIT.
	Para a comunicação com o banco de dados foi criado um container no docker com a imagem do mysql 8.0.21.
	Para gerenciamento de cache foi criado nesse mesmo container uma imagem do Redis.
	Para o gerenciamento de dependências foi utilizado o MAVEN.

3.Configurando a API
    Para utilizar o projeto deverá ser instalado o Java SDK 11, o Eclipse e subir o banco de dados através do docker compose criado.

    NO MAVEN
    Primeiramente instalar o Apache Maven e setar suas variaveis de ambiente corretamente, acessar a pasta raiz do projeto pelo prompt, executar o comando "mvn package", aguardar baixar as dependências, aguardar executar os testes automatizados e ao terminar de buildar o projeto acessar na pasta raiz a pasta "target" lá estará o jar responsavel pela api, basta executa-lo junto com o docker-compose que a api estará rodando em segundo plano, utilize o postman para fazer testes mais facilmente.

    NO ECLIPSE
    Após importar o projeto, executar o docker-compose e dar Run em Spring Boot App no Eclipse.

    Obs1: A propria API gera automaticamente o banco vazio caso não exista.
    Obs2: Se ocorrer algum problema da porta default, você poderá entrar em application.yml e mudar server.port para alguma outra porta que desejar.

4.Efetuando testes
    Com o Eclipse aberto, ir em src/test/Java e rodar o projeto com JUNIT, o sistema efetuará alguns testes unitários, retornando verde se tudo deu certo ou vermelho se algo deu errado.(Normalmente, quando é feito o build do projeto com mvn package ele já efetua os testes unitários).

5. Funcionalidades
	1. Em todo MS está sendo respeitado os padrões de codificação como princípios de SOLID por exemplo.
	2. Na aplicação foi implementado também um controle de exceção.
	3. A documentação da API foi feita utilizando o Swagger através de implementação de classe SwaggerConfigurations.
	4. o Swagger é possível acessar após subir a aplicação através do endreço: http://localhost:8080/swagger-ui/index.html#/.
	5. Foi implementado inicialmente uma autenticação, porém estava dando alguns conflitos com o swagger e pra não perder tempo foi removido.
	6. Foi feita toda a configuração de cache do Redis, porém estava dando conflitos também que eu não tive tempo hábil para corrigir