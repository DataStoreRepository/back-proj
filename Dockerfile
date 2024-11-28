# Define a imagem base como a versão 3.6.3 do Maven com OpenJDK 17. Essa imagem já contém o Maven e o Java instalados, o que facilita a construção do seu projeto.
# O 'as build' nomeia esta etapa como "build" para uso posterior.
FROM maven:3.6.3-openjdk-17 as build

# Define o diretório de trabalho dentro do contêiner como /app. Todos os comandos subsequentes serão executados nesse diretório.
WORKDIR /app

# Copia o arquivo pom.xml do seu projeto local para o diretório /app dentro do contêiner.
COPY pom.xml .

# Copia o diretório src do seu projeto local para o diretório /app/src dentro do contêiner.
COPY src ./src

# Executa o comando Maven para limpar, compilar e empacotar o seu projeto. A opção -DskipTests ignora a execução dos testes.
RUN mvn clean package -DskipTests

# Define a imagem base como a versão 17 do OpenJDK Alpine Linux. Essa imagem é menor e mais leve que a anterior, ideal para execução da aplicação.
FROM openjdk:17-jdk-alpine

# Define o diretório de trabalho dentro do contêiner como /app.
WORKDIR /app

# Copia o arquivo JAR gerado na etapa anterior (build) para o diretório /app dentro do contêiner.
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta 8080 do contêiner. Isso permite que a aplicação seja acessada de fora do contêiner.
EXPOSE 8080

# Define o comando que será executado quando o contêiner for iniciado. 
# Nesse caso, o comando executa o arquivo JAR da sua aplicação.
CMD [ "java", "-jar", "app.jar" ]