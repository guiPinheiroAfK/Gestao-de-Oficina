# Oficina-Gestao-Java 🛠️

Sistema de gerenciamento de oficina desenvolvido em **Java SE** para operação via terminal (CLI). O projeto foca na aplicação de conceitos avançados de POO e persistência de dados.

## ✨ Funcionalidades
- **CRUD de Veículos:** Cadastro de Carros e Motos (Herança/Polimorfismo).
- **Gestão de OS:** Abertura de ordens de serviço vinculadas a peças e serviços.
- **Relatórios:** Cálculo de faturamento e filtros de busca por placa/modelo.
- **Persistência:** Conexão com banco de dados **PostgreSQL** via JDBC.

## 🚀 Tecnologias
- Java 17+
- PostgreSQL
- JDBC Driver
- Maven (Opcional)

## 🛠️ Critérios Técnicos Aplicados
- **POO:** Classes abstratas, interfaces e encapsulamento rigoroso.
- **Data Structures:** Uso de `List`, `ArrayList` e `Maps` (sem arrays fixos).
- **Tratamento de Erros:** Validação de inputs para evitar Exceptions fatais.
- **Arquitetura:** Separação entre modelo, serviço e persistência (DAO).

## ⚙️ Como Rodar o Projeto
**Siga os passos abaixo para configurar o ambiente e executar a aplicação:**

- **1. Preparação do Banco de Dados**
  Certifique-se de que o PostgreSQL está instalado e rodando. No seu terminal SQL ou pgAdmin:

Crie um banco de dados chamado oficina_db.

Execute o script contido no arquivo schema.sql (localizado na raiz do projeto) para criar as tabelas e inserir os dados iniciais de teste.

- **2. Configuração de Acesso**
  No IntelliJ, abra o arquivo src/banco/ConnectionFactory.java. Verifique se as credenciais batem com a sua instalação local:

URL: jdbc:postgresql://localhost:5433/oficina_db

Usuário: postgres

Senha: admin
(Dica: Se você não usa Docker, a porta padrão costuma ser 5432).

- **3. Execução**
  Importe o projeto como um projeto Maven para que as dependências do arquivo pom.xml sejam baixadas automaticamente.

Navegue até a classe Main.java no pacote rodarTerminal.

Execute o método main e interaja com o sistema através do console da IDE.