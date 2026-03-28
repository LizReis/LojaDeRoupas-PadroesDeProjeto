# Loja de Roupas - Padrões de Projeto 👗👔

Este é um sistema de gerenciamento para uma loja de roupas construído em **Java** com interface gráfica utilizando **JavaFX**. O principal foco deste projeto é a aplicação prática de **Padrões de Projeto** (Design Patterns) como Strategy, Chain of Responsibility, Facade, DAO e Singleton na estruturação de um sistema orientado a objetos.

## 🚀 Tecnologias Utilizadas

* **Java (JDK 11 ou superior)**: Linguagem principal do projeto.
* **JavaFX**: Biblioteca para construção da Interface Gráfica de Usuário (GUI).
* **Maven**: Ferramenta de automação de compilação e gerenciamento de dependências.
* **Padrões de Projeto (GoF)**: Strategy, Chain of Responsibility, Facade, Proxy, MVC.

## 📋 Pré-requisitos

Antes de começar, você precisará ter as seguintes ferramentas instaladas na sua máquina:
* [Java JDK 11+](https://www.oracle.com/java/technologies/downloads/) (Recomendado Java 17 ou superior).
* [Git](https://git-scm.com/) (opcional, para clonar o repositório).
* Uma IDE Java de sua preferência (IntelliJ IDEA, Eclipse, VS Code).

## ⚙️ Como Executar o Projeto

O projeto utiliza o **Maven Wrapper** (`mvnw`), o que significa que você não precisa ter o Maven instalado globalmente na sua máquina para rodá-lo pelo terminal.

### Opção 1: Via Terminal (Linha de Comando)

1. **Clone ou baixe o repositório:**
   ```bash
   git clone <url-do-seu-repositorio>
   ```

2. **Navegue até a pasta principal do projeto** (onde está o arquivo `pom.xml`):
   ```bash
   cd LojaDeRoupas-PadroesDeProjeto/tela-auditoria-LizReis-main
   ```

3. **Execute o comando de compilação e execução:**
   * **No Windows:**
     ```cmd
     mvnw.cmd clean javafx:run
     ```
   * **No Linux / macOS:**
     ```bash
     ./mvnw clean javafx:run
     ```
   *(Nota: Se o plugin do JavaFX não estiver configurado como `javafx:run` no `pom.xml`, você também pode tentar rodar com `./mvnw clean compile exec:java`).*

### Opção 2: Via IDE (IntelliJ IDEA, Eclipse, VS Code)

1. Abra sua IDE e escolha a opção **"Open"** ou **"Import Project"**.
2. Navegue até a pasta `tela-auditoria-LizReis-main` e selecione o arquivo `pom.xml`.
3. Aguarde a IDE baixar as dependências do Maven e indexar o projeto.
4. Navegue pela estrutura de pastas até encontrar a classe principal:
   `src/main/java/br/edu/ifba/saj/fwads/App.java`
5. Clique com o botão direito sobre `App.java` e selecione **"Run 'App.main()'"**.

## 📁 Estrutura do Projeto

O código-fonte segue o padrão de arquitetura **MVC** (Model-View-Controller) e está organizado da seguinte forma:

* **`br.edu.ifba.saj.fwads.controller`**: Controladores do JavaFX, responsáveis por intermediar as ações das telas (arquivos `.fxml`) com a camada de negócio.
* **`br.edu.ifba.saj.fwads.model`**: Classes de domínio do projeto (Cliente, Produto, Departamento, Carrinho, etc.).
* **`br.edu.ifba.saj.fwads.dao`**: Camada de persistência/acesso a dados (Data Access Object), incluindo implementações de DAO genéricas e de Proxy (`DAOProxySeguro`).
* **`br.edu.ifba.saj.fwads.negocio`**: Regras de negócio da aplicação. Contém as pastas onde os Padrões de Projeto estão brilhantemente aplicados:
    * `CHAIN_OF_RESPONSABILITY/`: Validações encadeadas (ex: `ProdutoValidationChain`).
    * `STRATEGY/`: Estratégias de atualização e validação (ex: `ValidaCadastroCliente`).
    * `facade/`: Interfaces simplificadas para subsistemas complexos (ex: `CompraFacade`).
* **`br.edu.ifba.saj.fwads.exception`**: Exceções customizadas da aplicação.
* **`src/main/resources/`**: Arquivos estáticos da interface (telas `.fxml`, estilos `.css` e imagens na pasta `assets`).

## 🛠️ Autores

* **Liz Reis** - *Desenvolvedora do código POO completo + implementação dos Padrões de Projeto.*
* **Altimar Junior** - *Implementação dos Padrões de Projeto.*
* **João Soares** - *Implementação dos Padrões de Projeto.*
* **Anna Victória** - *Implementação dos Padrões de Projeto.*

***
