# DomusControl 🏠⚡

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21" />
  <img src="https://img.shields.io/badge/Gradle-9.2.0-02303A?style=for-the-badge&logo=gradle&logoColor=white" alt="Gradle" />
  <img src="https://img.shields.io/badge/JUnit-5-25A162?style=for-the-badge&logo=junit5&logoColor=white" alt="JUnit 5" />
  <img src="https://img.shields.io/badge/Architecture-MVC-blue?style=for-the-badge" alt="MVC Architecture" />
  <img src="https://img.shields.io/badge/Status-Completed-success?style=for-the-badge" alt="Status Completed" />
</p>

<p align="center">
  <strong>Sistema Modular de Gestão, Monitorização e Simulação de Automação Doméstica Inteligente</strong><br>
  Desenvolvido no âmbito da unidade curricular de <strong>Programação Orientada aos Objetos (POO)</strong><br>
  Licenciatura em Engenharia Informática — <strong>Universidade do Minho (2025/2026)</strong> • <strong>Grupo 56</strong>
</p>

---

## 📋 Índice

- [📖 Sobre o Projeto](#-sobre-o-projeto)
- [✨ Funcionalidades Principais](#-funcionalidades-principais)
- [🏛️ Arquitetura e Decisões de Design](#️-arquitetura-e-decisões-de-design)
  - [Padrões de Desenho (Design Patterns)](#padrões-de-desenho-design-patterns)
  - [Boas Práticas e Princípios POO](#boas-práticas-e-princípios-poo)
- [🔌 Catálogo de Dispositivos e Sensores](#-catálogo-de-dispositivos-e-sensores)
- [⚙️ Automatismos e Inteligência](#️-automatismos-e-inteligência)
  - [1. Automações Reativas](#1-automações-reativas)
  - [2. Escalonamentos Temporais](#2-escalonamentos-temporais)
  - [3. Cenários Globais](#3-cenários-globais)
  - [4. Motor de Sugestões Inteligentes](#4-motor-de-sugestões-inteligentes)
- [📁 Estrutura do Repositório](#-estrutura-do-repositório)
- [🚀 Instalação e Execução](#-instalação-e-execução)
- [🧪 Testes de Software](#-testes-de-software)
- [📊 Documentação e Modelação UML](#-documentação-e-modelação-uml)
- [👥 Equipa de Desenvolvimento](#-equipa-de-desenvolvimento)

---

## 📖 Sobre o Projeto

O **DomusControl** é uma plataforma abrangente de gestão de domótica inspirada em ecossistemas modernos como o *Home Assistant*. Foi concebida para integrar e orquestrar múltiplos dispositivos heterogéneos dentro de habitações inteligentes, proporcionando uma experiência uniforme quer ao utilizador comum, quer ao administrador do sistema.

O sistema modela a estrutura de uma residência com múltiplos níveis hierárquicos (**Casas ➔ Divisões ➔ Dispositivos**), permitindo controlo granular em tempo real, monitorização rigorosa do consumo energético, simulação de passagem do tempo, execução de cenários compostos e automatismos automáticos contextuais baseados em padrões de utilização.

---

## ✨ Funcionalidades Principais

- 🏢 **Gestão Multi-Habitação & Permissões**:
  - Criação e manutenção de casas, divisões (quartos, sala, cozinha, exterior/jardim, etc.) e utilizadores.
  - Associação de perfis de utilizador com níveis diferenciados de acesso (Administrador vs. Utilizador Comum).

- 🎛️ **Controlo Centralizado de Dispositivos**:
  - Operação individual ou por lote de equipamentos (ligar, desligar, abrir, fechar, ajustar intensidade/cor, definir temperaturas).
  - Registo em tempo real de ativações, tempo acumulado de operação e cálculo de custos/consumos energéticos.

- ⚡ **Automatismos Avançados**:
  - **Automações Condicionais**: Gatilhos baseados em leituras de sensores ambientais (chuva, luz, temperatura, humidade de solo).
  - **Escalonamentos**: Agendamentos com hora marcada acionados pelo motor de simulação temporal.
  - **Cenários Multi-Dispositivo**: Perfis predefinidos que coordenam múltiplos aparelhos simultaneamente (ex: *"Sair de Casa"*, *"Jantar com Amigos"*, *"Deitar"*, *"Acordar"*).

- 🧠 **Motor de Sugestões Automáticas**:
  - Análise preditiva do histórico de ações: identifica padrões de escalonamentos e sugere-os para novas divisões do mesmo utilizador; generaliza automações consolidadas para outras habitações geridas pelo mesmo proprietário.

- 📈 **Métricas e Auditoria Energética**:
  - Identificação da casa com maior pegada de consumo.
  - Top 3 de dispositivos mais ativos (por tempo ligado ou número de ativações).
  - Top 3 de divisões com maior densidade de dispositivos conectados.
  - Cálculo de consumo dinâmico por divisão e por habitação global.

- 💾 **Persistência Robusta de Estado**:
  - Gravação e carregamento transparente de todo o ecossistema através de serialização binária Java (`.obj`), garantindo a preservação total de estados e históricos.

---

## 🏛️ Arquitetura e Decisões de Design

O projeto segue rigorosamente o padrão arquitetural **MVC (Model-View-Controller)** com separação estrita de responsabilidades:

```
┌───────────────────────────────────────────────────────────┐
│                           VIEW                            │
│           (TextUI, MenuAdmin, Menu, Menu.Handler)         │
└─────────────────────────────┬─────────────────────────────┘
                              │ Interações do Utilizador
                              ▼
┌───────────────────────────────────────────────────────────┐
│                        CONTROLLER                         │
│     (Controller: Fachada, Coordenação & Simulação Tempo)   │
└─────────────────────────────┬─────────────────────────────┘
                              │ Invocações de Domínio
                              ▼
┌───────────────────────────────────────────────────────────┐
│                          MODEL                            │
│  ┌───────────────────────┬─────────────────────────────┐  │
│  │     DomusControl      │       Casa & Divisao        │  │
│  ├───────────────────────┼─────────────────────────────┤  │
│  │ Hierarquia Dispositivo│ Automatismos & Cenários     │  │
│  └───────────────────────┴─────────────────────────────┘  │
└───────────────────────────────────────────────────────────┘
```

### Padrões de Desenho (Design Patterns)

- **Factory Method (`DispositivoFactory`, `AutomacaoFactory`)**: Centraliza e desacopla a criação dinâmica de novos dispositivos e automações utilizando registos de `Supplier`. Permite adicionar novos equipamentos respeitando o princípio *Open/Closed*.
- **Strategy (`EstrategiaCenario`)**: Encapsula pares desacoplados de condição e ação, permitindo que cenários apliquem comportamentos heterogéneos sobre diferentes categorias de dispositivos sem dependências rígidas.
- **Façade (`Controller`)**: Expõe uma interface limpa e coesa à camada de apresentação, ocultando a complexidade das manipulações internas do modelo.
- **Functional Extensions (`SerializablePredicate`, `SerializableConsumer`)**: Extensões proprietárias das interfaces funcionais do Java 21 permitindo que lambdas, condições de ativação e filtros sejam integralmente serializados e persistidos em disco.

### Boas Práticas e Princípios POO

- **Encapsulamento & Defesa de Estado**: Todos os atributos são privados. Métodos de consulta (`getters`) devolvem cópias defensivas (`new HashMap<>(...)`, clones) para prevenir mutações indevidas por clientes externos.
- **Single Responsibility (SRP)**: Cada classe tem uma responsabilidade coesa e bem delimitada.
- **Liskov Substitution Principle (LSP)**: Todas as subclasses da hierarquia de dispositivos garantem conformidade estrita com os contratos da superclasse abstrata `Dispositivo`.
- **Don't Repeat Yourself (DRY)**: Reutilização consistente de código através de herança estratégica (ex.: `LampadaCor` estendendo `Lampada`, `MenuAdmin` integrando `TextUI`) e composição flexível nos menus.

---

## 🔌 Catálogo de Dispositivos e Sensores

A hierarquia de dispositivos assenta na classe abstrata `Dispositivo`, subdividindo-se em ramos especializados:

| Dispositivo | Tipo / Categoria | Operações Principais | Métricas / Parâmetros |
| :--- | :--- | :--- | :--- |
| **Lâmpada** (`Lampada`) | Iluminação | Ligar / Desligar | Consumo horário, tempo ligado, ativações |
| **Lâmpada Cor** (`LampadaCor`) | Iluminação Smart | Ligar / Desligar / Mudar Cor | Cor (RGB), intensidade luminosa |
| **Coluna de Som** (`ColunaDeSom`) | Multimédia | Ligar / Desligar / Volume | Volume, reprodução de áudio |
| **Televisão** (`Televisao`) | Multimédia | Ligar / Desligar / Canal / Volume | Canal sintonizado, volume de som |
| **Termóstato** (`Termostato`) | Climatização / Sensor | Regular temperatura / Monitorizar | Temperatura alvo, medição ambiente (°C) |
| **Cortina** (`Cortina`) | Aberturas / Estores | Abrir / Fechar | Percentagem de abertura |
| **Portão de Garagem** (`PortaoGaragem`) | Segurança / Acesso | Abrir / Fechar | Estado de acesso (aberto/fechado) |
| **Dispensador de Ração** (`DispensadorRacao`) | Cuidados com Animais | Dispensar dose / Ligar | Quantidade programada de alimento |
| **Irrigação** (`Irrigacao`) | Exterior / Jardim | Ativar / Desativar rega | Fluxo de água, duração |
| **Relé** (`Rele`) | Atuador Geral | Comutar circuito | Controlo de alimentação elétrica |
| **Sensor de Chuva** (`SensorChuva`) | Sensor Ambiental | Leitura pluviométrica | Deteção de precipitação |
| **Sensor de Luminosidade** (`SensorLuminosidade`) | Sensor Ambiental | Leitura de Lux | Nível de luz ambiente |
| **Sensor Humidade Solo** (`SensorHumidadeSolo`) | Sensor Agrícola/Jardim | Medição de percentagem | Humidade do terreno (%) |

---

## ⚙️ Automatismos e Inteligência

O motor do DomusControl é constituído por 4 pilares:

### 1. Automações Reativas
Baseiam-se na monitorização das leituras de sensores para desencadear ações automáticas imediatas:
- `AutomacaoChuva`: Fecha cortinas ou portões e desativa o sistema de rega perante precipitação.
- `AutomacaoLuzExterior`: Acende a iluminação exterior quando a luminosidade desce abaixo do limiar estipulado.
- `AutomacaoTemperatura`: Liga climatização ou termóstatos perante desvios da temperatura de conforto.
- `AutomacaoRelva`: Ativa a rega se a humidade do solo estiver criticamente baixa e não estiver a chover.
- `AutomacaoEscalonamentoAnimais`: Assegura a ativação pontual de dispensadores e iluminação de suporte.

### 2. Escalonamentos Temporais
Tarefas diferidas coordenadas pelo relógio virtual do sistema (`Controller.simularHora()`):
- Abertura e fecho programado de estores/cortinas ao nascer/pôr do sol.
- Iluminação inteligente programada para horários noturnos.
- Ativação periódica de sistemas de multimédia ou irrigação.

### 3. Cenários Globais
Execução de perfis orquestrados via padrão Strategy:
- 🚪 **Sair de Casa**: Desliga todas as lâmpadas, reduz termóstatos, fecha cortinas e tranca portões.
- 🍷 **Jantar com Amigos**: Ajusta iluminação ambiente para tom acolhedor, ativa a coluna de som e ajusta a climatização da sala.
- 🌙 **Deitar**: Apaga as luzes da habitação, fecha estores e verifica o estado dos portões exteriores.
- ☀️ **Acordar**: Abre suavemente as cortinas, acende a luz de apoio e ajusta o termóstato matinal.

### 4. Motor de Sugestões Inteligentes
O DomusControl analisa padrões de uso do utilizador:
- Se uma configuração de escalonamento é aplicada repetidamente em várias divisões, o sistema passa a sugerir a sua replicação para as restantes divisões do mesmo utilizador.
- Automações consolidadas numa casa são sugeridas para novas habitações do mesmo proprietário, acelerando a configuração de novos espaços residenciais.

---

## 📁 Estrutura do Repositório

```text
projetoPOO2526/
├── README.md                      # Documentação principal do repositório
├── relatorio_poo.pdf              # Relatório técnico completo do projeto
└── domusControl/                  # Projeto Gradle / Código-fonte da aplicação
    ├── build.gradle               # Ficheiro de configuração do Gradle
    ├── settings.gradle            # Definições de módulos do projeto
    ├── gradlew / gradlew.bat      # Gradle Wrapper (Linux / macOS / Windows)
    ├── estado1.obj                # Exemplo de estado de sistema persistido
    ├── uml/
    │   └── domuscontrol.puml      # Diagrama de Classes PlantUML completo
    └── app/
        └── src/
            ├── main/java/
            │   ├── controller/    # Camada de Controlo (Controller.java)
            │   ├── exceptions/    # Exceções de domínio personalizadas
            │   ├── model/
            │   │   ├── automatismos/  # Automações, Escalonamentos e Fábricas
            │   │   ├── casas/         # Casa, Divisao, Cenario, EstrategiaCenario
            │   │   ├── dispositivos/  # Hierarquia de Dispositivos e DispositivoFactory
            │   │   ├── domus/         # Núcleo do Sistema (DomusControl.java)
            │   │   └── utilizadores/  # Entidade Utilizador
            │   ├── utils/         # SerializablePredicate e SerializableConsumer
            │   └── view/          # Apresentação CLI (MainUI, TextUI, MenuAdmin, Menu)
            └── test/java/
                └── domuscontrol/  # Bateria completa de testes unitários JUnit 5
```

---

## 🚀 Instalação e Execução

### Pré-requisitos
- **Java JDK 21** ou superior instalado (`java -version`).
- **Git** instalado.

### 1. Clonar o Repositório
```bash
git clone https://github.com/afonso35costa/projetoPOO2526.git
cd projetoPOO2526/domusControl
git clone git@github.com:POO-UM/2526-G056.git
cd 2526-G056/domusControl
```

### 2. Executar a Aplicação
Utilize o wrapper do Gradle incluído:

- **Linux / macOS**:
  ```bash
  ./gradlew run --console=plain
  ```

- **Windows**:
  ```cmd
  gradlew.bat run --console=plain
  ```

> **Dica:** O argumento `--console=plain` garante uma formatação ideal e interativa dos menus na consola.

---

## 🧪 Testes de Software

O projeto dispõe de uma suite abrangente de testes unitários desenvolvida em **JUnit 5**, validando a lógica de negócio, criação via fábricas, mutabilidade controlada, serialização e automatismos:

```bash
cd domusControl
./gradlew test
```

Os relatórios detalhados de execução dos testes são gerados automaticamente em:
`domusControl/app/build/reports/tests/test/index.html`

---

## 📊 Documentação e Modelação UML

- 📐 **Diagrama de Classes Completo**: Encontra-se disponível em [`domusControl/uml/domuscontrol.puml`](domusControl/uml/domuscontrol.puml), modelado em **PlantUML** abrangendo todas as relações, cardinalidades e heranças.
- 📄 **Relatório Técnico**: O documento [`relatorio_poo.pdf`](relatorio_poo.pdf) descreve pormenorizadamente todo o enquadramento teórico, decisões arquiteturais, padrões adotados e análise crítica dos resultados obtidos.

---

## 👥 Equipa de Desenvolvimento

Trabalho prático realizado pelo **Grupo 56** para a unidade curricular de **Programação Orientada aos Objetos (POO)** — LEI, Universidade do Minho:

| Aluno | Número | GitHub |
| :--- | :---: | :---: |
| **Afonso Costa** | `a110544` | [@afonso35costa](https://github.com/afonso35costa) |
| **Diogo Silva** | `a111493` | [@diogo-20](https://github.com/diogo-20) |
| **Emília Gomes** | `a112428` | [@emiliagomes](https://github.com/emiliagomes) |

---

<p align="center">
  Universidade do Minho • Licenciatura em Engenharia Informática • 2025/2026
</p>
