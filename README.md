# 📱 Aplicativo para Avaliação de Linguagem em Crianças com TEA

Este repositório contém o código-fonte e documentação do aplicativo **DarlanTCC**, desenvolvido como Trabalho de Conclusão de Curso (TCC) do curso de Engenharia de Computação na UTFPR – Apucarana.

## 🎯 Objetivo

Desenvolver um aplicativo móvel com base na **Teoria das Molduras Relacionais (Relational Frame Theory - RFT)** para avaliar e estimular a associação de palavras e símbolos em crianças com Transtorno do Espectro Autista (TEA).

## 🧠 Fundamentação Teórica

O projeto baseia-se nos princípios da RFT, com foco em:

- **Implicação Mútua (IM)**
- **Transformação de Função do Estímulo (TFE)**

Esses conceitos são aplicados em experimentos que exploram a associação cognitiva de estímulos visuais e linguísticos.

## 🛠️ Tecnologias Utilizadas

- **Java** como linguagem principal
- **Android SDK** com suporte ao AndroidX
- **Android Studio** como ambiente de desenvolvimento (IDE)
- **XML** para criação de layouts de interface
- Componentes Android utilizados: `EditText`, `TextView`, `Button`, `Intent`, `Activity`
- **Room Database** para persistência local dos dados
- **Oracle Cloud** para hospedagem e análise de dados via phpMyAdmin

## 📱 Estrutura do Projeto

```
TCC-Desenvolvimento-Android-main/
├── src/
│   ├── main/
│   │   ├── java/br/edu/utfpr/darlantcc_v1/
│   │   │   ├── ActivityInicio.java
│   │   │   ├── ActivityCapa.java
│   │   │   ├── ActivityCadastrarPessoa.java
│   │   │   ├── ActivityP1.java
│   │   │   ├── ... até ActivityP25.java
│   │   │   └── ActivityFim.java
│   │   ├── res/
│   │   │   ├── layout/ (arquivos XML das telas)
│   │   │   └── drawable/ (ícones e imagens)
│   │   └── AndroidManifest.xml
│   └── androidTest/
│       └── ExampleInstrumentedTest.java
```

🖼️ Telas do Aplicativo

As principais telas do aplicativo incluem:

Tela de Login: Autenticação do usuário via e-mail e senha.

![Tela_login](https://github.com/user-attachments/assets/8ed145a8-0481-4e89-95de-e08a13781340)

Tela Inicial (Menu): Acesso ao questionário, cadastro de usuários e respostas salvas.

![Menu](https://github.com/user-attachments/assets/773e7cc0-88d7-43ae-85f5-66064af89aa2)

Tela de Seleção de Participante: Escolha do usuário para iniciar o questionário.

![Subquest](https://github.com/user-attachments/assets/b7e45afa-c711-4e76-b237-9e68418670b0)


Tela de Resultados: Visualização e exportação das respostas salvas (em PDF/TXT).

![pdf](https://github.com/user-attachments/assets/28cfabc6-a8cf-456f-8595-e6058ae59bc9)

Essas telas foram projetadas com interface acessível e amigável para facilitar o uso por pais, terapeutas e crianças com TEA.


## 🧪 Experimentos Cognitivos

O app aplica 5 experimentos baseados em RFT:

1. **Comparacão de Tamanho**
![Experimento 1_page-0001](https://github.com/user-attachments/assets/7ca797a8-a846-42aa-bd1d-6148b036cb77)


2. **Identificação por Características**

![Experimento 2](https://github.com/user-attachments/assets/28067f7c-7454-46aa-b833-d85cee8fe682)

3. **Análise Sensorial (Cor Vermelha)**

![Experimento 3](https://github.com/user-attachments/assets/90732a42-79cb-4f13-a74d-6ea634f83f50)


4. **Mudança de Foco (Caractere x Cor)**

![Experimento 4](https://github.com/user-attachments/assets/b19c563b-0309-464e-93e1-ee9bd11b7fc2)

5. **Análise Sensorial (Cor Verde)**

![Experimento 5](https://github.com/user-attachments/assets/fc541b5e-03e9-4b37-8f23-64dda5f0851c)


## 🔐 Funcionalidades

- Autenticação de usuário com e-mail e senha
- Cadastro, edição e exclusão de participantes
- Execução de questionários baseados em RFT
- Visualização e exportação das respostas (PDF/TXT)
- Visualização de gráficos com estatísticas

## 📊 Análise de Dados

Os dados coletados pelos experimentos são analisados via scripts PHP conectados ao banco de dados MySQL (Oracle Cloud), com visualização através de gráficos gerados na interface web.

## 📦 Como Executar o Projeto

1. Abra o projeto com o **Android Studio** (versão 2024.1.2 ou superior).
2. Compile e execute o app em um dispositivo ou emulador Android (API 26+).
3. Configure o backend e banco de dados conforme instruções disponíveis em `docs/oracle_cloud_setup.md`.

## 📊 Requisitos

### Funcionais

- Cadastro de usuários
- Autenticação e login
- Preenchimento de questionários
- Visualização de resultados

### Não-Funcionais

- Alta disponibilidade (99.9%)
- Criptografia AES-256 para senhas
- Interface amigável e intuitiva
- Suporte a até 1000 requisições simultâneas

## 📁 Licença

Este projeto está licenciado sob a [Creative Commons 4.0 Internacional](https://creativecommons.org/licenses/by/4.0/deed.pt_BR).

## 👨‍🏫 Autor

**Darlan Oliveira Santos**  
Orientador: Prof. Dr. Lucio Agostinho Rocha  
Universidade Tecnológica Federal do Paraná - UTFPR

---

> "O autismo não é um erro de processamento. É um sistema operacional diferente." – Hendrickx (2024)

