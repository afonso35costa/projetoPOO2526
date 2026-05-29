# DomusControl

Sistema de simulação de automação doméstica desenvolvido no âmbito da unidade curricular de **Programação Orientada aos Objetos** (2025/2026), Universidade do Minho.

**Grupo 56** — Afonso Costa (a110544) · Diogo Silva (a111493) · Emília Gomes (a112428)

---

## Descrição

O DomusControl é uma aplicação inspirada em plataformas como o Home Assistant. Permite gerir uma habitação organizada em divisões, associar dispositivos conectados de vários tipos a cada divisão, e operar sobre eles de forma individual ou agregada. Para além do controlo direto, suporta automações condicionais, escalonamentos temporais e cenários que coordenam vários dispositivos numa única ação.

---

## Funcionalidades

- **Gestão de entidades** — utilizadores, casas, divisões e dispositivos (criação, listagem e remoção)
- **Operação sobre dispositivos** — ligar/desligar, abrir/fechar, ajustar volume, canal, intensidade, temperatura, etc.
- **Automações** — ações automáticas despoletadas por sensores:
  - `AutomacaoChuva` — fecha cortinas quando o sensor deteta chuva
  - `AutomacaoLuzExterior` — liga lâmpadas com luminosidade baixa
  - `AutomacaoRelva` — ativa rega quando o solo está seco e não chove
  - `AutomacaoTemperatura` — ajusta o termostato fora do intervalo configurado
  - `AutomacaoEscalonamentoAnimais` — acciona o dispensador de ração quando os níveis estão esgotados
- **Escalonamentos** — ações agendadas para uma hora específica (abrir/fechar cortinas, acender/desligar lâmpadas, ligar/desligar colunas)
- **Cenários** — conjuntos de estratégias aplicadas a vários dispositivos de uma vez:
  - `Sair de Casa` — desliga lâmpadas, fecha cortinas, desliga colunas
  - `Jantar com Amigos` — ajusta temperatura de cor, fecha cortinas parcialmente, configura volume
  - `Deitar` — desliga iluminação e multimédia, fecha cortinas
  - `Acordar` — liga lâmpadas, abre cortinas parcialmente, ativa colunas
- **Estatísticas** — casa que mais consome, top 3 dispositivos mais utilizados, top 3 divisões com mais dispositivos, consumo por divisão/casa
- **Sugestão automática** — sugere escalonamentos e automações com base no histórico do utilizador
- **Persistência** — gravação e carregamento do estado em ficheiro binário
- **Simulação do tempo** — avanço manual da hora para testar escalonamentos

---

## Arquitetura

O projeto segue o padrão **MVC (Model–View–Controller)**, organizado nos seguintes pacotes:

| Pacote | Conteúdo |
|---|---|
| `model.domus` | `DomusControl`, `App`, `Testes` |
| `model.casas` | `Casa`, `Divisao`, `Cenario`, `EstrategiaCenario` |
| `model.dispositivos` | Hierarquia de dispositivos, `DispositivoFactory` |
| `model.automatismos` | `Automacao`, implementações concretas, `Escalonamento`, `AutomacaoFactory` |
| `model.utilizadores` | `Utilizador` |
| `controller` | `Controller` (fachada) |
| `view` | `MainUI`, `TextUI`, `MenuAdmin`, `Menu` |
| `exceptions` | Exceções do domínio |
| `utils` | `SerializablePredicate`, `SerializableConsumer` |

### Padrões de design utilizados

- **Factory Method** — `DispositivoFactory` e `AutomacaoFactory` centralizam a criação de objetos via `Map<String, Supplier<T>>`
- **Strategy** — `EstrategiaCenario` encapsula pares condição/ação aplicados a cenários
- **Facade** — `Controller` expõe uma API estável à camada de vista e protege o modelo
- **MVC** — separação clara entre modelo, controlador e vista

### Hierarquia de dispositivos

```
Dispositivo (abstract)
├── Lampada            implements DispositivoQueLiga
│   └── LampadaCor
├── ColunaDeSom        implements DispositivoQueLiga
├── Televisao          implements DispositivoQueLiga
├── Termostato         implements DispositivoQueLiga
├── Rele               implements DispositivoQueLiga
├── Irrigacao          implements DispositivoQueLiga
├── SensorChuva        implements DispositivoQueLiga
├── SensorLuminosidade implements DispositivoQueLiga
├── SensorHumidadeSolo implements DispositivoQueLiga
├── Cortina            implements DispositivoQueAbre
└── PortaoGaragem      implements DispositivoQueAbre
```

---

## Como executar

```bash
# Compilar
javac -d out/production/DomusControl src/**/*.java

# Executar
java -cp out/production/DomusControl view.MainUI
```

> A aplicação corre em modo texto. No arranque é possível carregar um sistema de teste pré-construído.

---

## Estruturas de dados

| Estrutura | Utilização |
|---|---|
| `HashMap` | Indexação de casas, utilizadores, divisões e dispositivos por ID |
| `ArrayList` | Listas de cenários, automações, escalonamentos e estratégias |
| `HashSet` | Utilizadores e administradores de uma casa (sem duplicados) |
| `enum Estado` | Estados dos dispositivos (`LIGADO`, `DESLIGADO`, `ABERTO`, `FECHADO`) |
| `LocalTime` | Hora de execução dos escalonamentos |

---

## Princípios aplicados

O projeto aplica sistematicamente os princípios de POO: **Single Responsibility**, **Encapsulation** (getters com cópias defensivas), **Most Qualified Class**, **Low Coupling**, **Open/Closed**, **Liskov Substitution** e **DRY**. As operações de filtragem e agregação são expressas com streams e interfaces funcionais (`Predicate`, `Consumer`, `Comparator`).

