# 🗂️ PManager - Sistema de Gerenciamento de Projetos

O **PManager** é uma API RESTful construída com **Spring Boot** que oferece funcionalidades completas para gerenciar projetos, tarefas, membros e armários. Este sistema foi desenvolvido como parte de um projeto de estudo e certificação em Java, e segue as boas práticas de separação de responsabilidades, modularidade e RESTful API Design.

> 💡 Projeto desenvolvido com apoio e autorização do professor Carlos Tosin, com fins acadêmicos e demonstrativos.

---

## 📌 Objetivo

O sistema visa facilitar o gerenciamento de projetos, incluindo:

- Associação de membros a projetos
- Controle de status de tarefas e projetos
- Validação de regras de negócio via exceções
- Autenticação via API Key para segurança básica
- Organização modular com uso de DTOs, services, controllers e entidades JPA

---

## ⚙️ Tecnologias Utilizadas

- ✅ Java 17
- ✅ Spring Boot
- ✅ Spring Data JPA
- ✅ H2 Database (ambiente local/testes)
- ✅ MySQL (banco de dados em produção);
- ✅ Postman (para testes da API);
- ✅ Lombok
- ✅ Spring Security (autenticação via API Key)
- ✅ Maven Wrapper (`mvnw`)

---

## ✨ Funcionalidades

-  **Gerenciamento de Projetos** (CRUD, status)
-  **Cadastro de Tarefas** vinculadas a projetos e membros
-  **Gerenciamento de Membros**
-  **Controle de Armários** (alocação e liberação)
-  **Autenticação com API Key**
-  **Tratamento de exceções** com mensagens específicas
-  **Filtros úteis** (ex: membros sem armário, projetos sem tarefas, etc.)

---

## 🧩 Estrutura do Projeto

```plaintext
src/
└── main/
    ├── java/
    │   └── pmanager/
    │       ├── domain/
    │       │   ├── applicationservice/   # Serviços de aplicação (orquestram regras de negócio)
    │       │   ├── document/             # Representações de documentos como ApiKey
    │       │   ├── entity/               # Entidades JPA: Aluno, Armario, Projeto, etc.
    │       │   ├── exceptionDomain/      # Exceções específicas da camada de domínio
    │       │   ├── model/                # Enums e objetos de valor
    │       │   └── repository/           # Interfaces dos repositórios Spring Data
    │       ├── infrastructure/
    │       │   ├── config/               # Beans e configurações Spring Boot
    │       │   ├── controller/           # REST Controllers
    │       │   ├── dto/                  # Objetos de Transferência (DTOs)
    │       │   ├── exception/            # Tratamento centralizado via @ControllerAdvice
    │       │   ├── security/             # Filtro e autenticação por API Key
    │       │   └── util/                 # Helpers e utilitários gerais
    │       └── PManagerApplication.java  # Ponto de entrada da aplicação Spring Boot
    └── resources/
        ├── application.properties        # Configurações da aplicação
        └── static/                       # Arquivos estáticos (se necessário)

```
## ▶️ Como Executar
Você pode executar este projeto localmente com os seguintes passos:
### **Pré-requisitos:**

- **Java** 17 ou superior (recomendado: 21);
- MySQL instalado, com um banco de dados criado (ex.: `pmanager_db`);
- Gerenciador de dependências **Maven** (ou o wrapper `./mvnw` incluído);
- **Git** para clonar este repositório.

## **Instalação e Execução:**

- 1 Clone o repositório:
``` bash
   git clone https://github.com/seu-usuario/pmanager.git
```
- 2 Entre no diretório do projeto clonado:
``` bash
   cd pmanager
```
- 3 Configure o banco de dados MySQL no arquivo `application.properties`:
``` properties
   spring.datasource.url=jdbc:mysql://localhost:3306/pmanager_db
   spring.jpa.hibernate.ddl-auto=update
```
- 4 Execute o projeto usando o Maven Wrapper:
``` bash
   ./mvnw spring-boot:run
```
- 5 Acesse no navegador (ou API client como Postman):
``` 
   http://localhost:8080
```


## 📖 Endpoints Disponíveis (API)
Para facilitar, seguem os principais **endpoints** da API:

### **1. Projetos:**
- Criar Projeto:
`POST /projects`
- Listar Projetos:
`GET /projects`
- Buscar por ID:
`GET /projects/{id}`
- Atualizar Projeto:
`PUT /projects/{id}`
- Deletar Projeto:
`DELETE /projects/{id}`

### **2. Tarefas:**
- Criar Tarefa:
`POST /tasks`
- Excluir Tarefa:
`DELETE /tasks/{id}`

### **3. Membros:**
- Associar Membro ao Projeto:
`POST /projects/{projectId}/members`
- Listar Membros do Projeto:
`GET /projects/{projectId}/members`

## 📋 Resumo
O **PManager** é um sistema robusto para gerenciamento de projetos e equipes, desenvolvido com **Spring Boot** e diversas tecnologias modernas. Ele utiliza o banco de dados **H2** como padrão para testes, mas pode ser facilmente ajustado a outros bancos, como **MySQL**. Seguindo boas práticas de arquitetura e modularidade, o projeto é uma demonstração prática de desenvolvimento Java para APIs RESTful. Ideal para estudos e aplicação em projetos similares. 😊
Se houver qualquer dúvida, sinta-se à vontade para reportar!
