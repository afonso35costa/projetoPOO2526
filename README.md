# DomusControl 🏠⚡

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21" />
  <img src="https://img.shields.io/badge/Gradle-9.2.0-02303A?style=for-the-badge&logo=gradle&logoColor=white" alt="Gradle" />
  <img src="https://img.shields.io/badge/JUnit-5-25A162?style=for-the-badge&logo=junit5&logoColor=white" alt="JUnit 5" />
  <img src="https://img.shields.io/badge/Architecture-MVC-blue?style=for-the-badge" alt="MVC Architecture" />
  <img src="https://img.shields.io/badge/Status-Completed-success?style=for-the-badge" alt="Status Completed" />
</p>

<p align="center">
  <strong>Modular Smart Home Management, Monitoring, and Simulation System</strong><br>
  Developed for the <strong>Object-Oriented Programming (POO)</strong> course unit<br>
  BSc in Computer Science (LEI) — <strong>University of Minho (2025/2026)</strong> • <strong>Group 56</strong>
</p>

---

## 📋 Table of Contents

- [📖 About the Project](#-about-the-project)
- [✨ Key Features](#-key-features)
- [🏛️ Architecture & Design Decisions](#️-architecture--design-decisions)
  - [Design Patterns](#design-patterns)
  - [OOP Principles & Best Practices](#oop-principles--best-practices)
- [🔌 Device & Sensor Catalog](#-device--sensor-catalog)
- [⚙️ Automations & Smart Engine](#️-automations--smart-engine)
  - [1. Reactive Automations](#1-reactive-automations)
  - [2. Time-Based Schedules](#2-time-based-schedules)
  - [3. Global Scenarios](#3-global-scenarios)
  - [4. Intelligent Suggestion Engine](#4-intelligent-suggestion-engine)
- [📁 Repository Structure](#-repository-structure)
- [🚀 Getting Started](#-getting-started)
- [🧪 Software Testing](#-software-testing)
- [📊 Documentation & UML Modeling](#-documentation--uml-modeling)
- [👥 Development Team](#-development-team)

---

## 📖 About the Project

**DomusControl** is an end-to-end smart home automation and simulation platform inspired by modern ecosystems such as *Home Assistant*. It is designed to seamlessly integrate and orchestrate heterogeneous connected devices across residential properties, delivering a unified interface for standard users and system administrators.

The system models properties using a multi-tier hierarchy (**Houses ➔ Rooms ➔ Devices**), offering granular real-time control, continuous energy consumption metrics, simulated clock time progression, coordinated multi-device scenarios, and intelligent recommendation routines driven by past user habits.

---

## ✨ Key Features

- 🏢 **Multi-Property & Access Management**:
  - Full CRUD operations for houses, rooms (bedrooms, living room, kitchen, garden/outdoors, etc.), and users.
  - Role-based permissions differentiating standard users from administrators.

- 🎛️ **Centralized Device Control**:
  - Individual or batch device operations (turn on/off, open/close, adjust RGB color/brightness, configure thermostat targets).
  - Continuous tracking of activations, operating uptime, and dynamic energy cost calculations.

- ⚡ **Advanced Automations**:
  - **Conditional Automations**: Sensor-driven event triggers (rain, ambient light, temperature fluctuations, soil moisture).
  - **Schedules**: Timed routines executed via the built-in time simulation engine.
  - **Multi-Device Scenarios**: Coordinated batch profiles (*"Leaving Home"*, *"Dinner with Friends"*, *"Going to Sleep"*, *"Waking Up"*).

- 🧠 **Intelligent Suggestion Engine**:
  - Behavioral pattern recognition: detects recurring schedules across rooms and suggests extending them to new rooms; replicates proven house automations across other properties owned by the same user.

- 📈 **Auditing & Energy Analytics**:
  - Identification of the household with the highest power consumption.
  - Global Top 3 most utilized devices (by accumulated runtime or activation count).
  - Global Top 3 rooms with the highest device density.
  - Real-time power breakdown per room and per entire house.

- 💾 **State Persistence**:
  - Effortless save and load functionality using standard Java binary serialization (`.obj`), fully preserving system snapshots and historical states.

---

## 🏛️ Architecture & Design Decisions

The application strictly implements the **Model-View-Controller (MVC)** architectural pattern with clean separation of concerns:

```
┌───────────────────────────────────────────────────────────┐
│                           VIEW                            │
│           (TextUI, MenuAdmin, Menu, Menu.Handler)         │
└─────────────────────────────┬─────────────────────────────┘
                              │ User Interactions
                              ▼
┌───────────────────────────────────────────────────────────┐
│                        CONTROLLER                         │
│       (Controller: Façade, Dispatcher & Time Engine)       │
└─────────────────────────────┬─────────────────────────────┘
                              │ Domain Calls
                              ▼
┌───────────────────────────────────────────────────────────┐
│                          MODEL                            │
│  ┌───────────────────────┬─────────────────────────────┐  │
│  │     DomusControl      │       House & Room          │  │
│  ├───────────────────────┼─────────────────────────────┤  │
│  │   Device Hierarchy    │   Automations & Scenarios   │  │
│  └───────────────────────┴─────────────────────────────┘  │
└───────────────────────────────────────────────────────────┘
```

### Design Patterns

- **Factory Method (`DispositivoFactory`, `AutomacaoFactory`)**: Centralizes and decouples object instantiation through `Supplier` registry maps, strictly honoring the *Open/Closed Principle*.
- **Strategy (`EstrategiaCenario`)**: Encapsulates dynamic pairs of predicates and consumers, allowing scenarios to apply polymorphic actions across disparate device families without tight coupling.
- **Façade (`Controller`)**: Provides a cohesive, high-level API to the presentation layer, insulating the UI from internal model details.
- **Custom Functional Extensions (`SerializablePredicate`, `SerializableConsumer`)**: Serializable extensions of Java 21 functional interfaces, enabling lambda rules, conditional predicates, and actions to be persisted directly to disk.

### OOP Principles & Best Practices

- **Encapsulation & Defensive Copying**: All internal state variables are strictly `private`. Query methods return defensive copies (`new HashMap<>(...)`, clones) to prevent unwanted external state mutations.
- **Single Responsibility (SRP)**: Each class is dedicated to a distinct, well-defined domain responsibility.
- **Liskov Substitution Principle (LSP)**: All subclasses within the device hierarchy faithfully conform to contracts established by the abstract `Dispositivo` base class.
- **Don't Repeat Yourself (DRY)**: Reusable components leverage inheritance (e.g., `LampadaCor` extending `Lampada`, `MenuAdmin` extending `TextUI`) and higher-order functional primitives.

---

## 🔌 Device & Sensor Catalog

All connected equipment extends the abstract `Dispositivo` class, branching into specialized categories:

| Device | Category / Type | Primary Operations | Metrics & Attributes |
| :--- | :--- | :--- | :--- |
| **Lamp** (`Lampada`) | Lighting | Turn On / Turn Off | Power consumption rate, uptime, activations |
| **Smart Color Lamp** (`LampadaCor`) | Smart Lighting | Turn On / Off / Set Color | RGB color tone, brightness level |
| **Speaker** (`ColunaDeSom`) | Multimedia | Turn On / Off / Set Volume | Volume scale, audio playback status |
| **Television** (`Televisao`) | Multimedia | Turn On / Off / Channel / Volume | Current channel, volume level |
| **Thermostat** (`Termostato`) | Climate / Sensor | Set Temperature / Monitor | Target temp, ambient reading (°C) |
| **Blind / Curtain** (`Cortina`) | Openings / Blinds | Open / Close | Open percentage (%) |
| **Garage Gate** (`PortaoGaragem`) | Security / Access | Open / Close | Access state (open/closed) |
| **Pet Feeder** (`DispensadorRacao`) | Pet Care | Dispense Portion / Turn On | Programmed food dosage |
| **Irrigation System** (`Irrigacao`) | Garden / Outdoors | Start / Stop Watering | Water flow, cycle duration |
| **Smart Relay** (`Rele`) | General Actuator | Toggle Circuit | Electrical circuit pass-through status |
| **Rain Sensor** (`SensorChuva`) | Environmental Sensor | Pluviometric reading | Precipitation detection flag |
| **Luminosity Sensor** (`SensorLuminosidade`) | Environmental Sensor | Lux reading | Ambient light level (Lux) |
| **Soil Moisture Sensor** (`SensorHumidadeSolo`) | Garden / Sensor | Percentage reading | Ground moisture level (%) |

---

## ⚙️ Automations & Smart Engine

DomusControl automates routine tasks through 4 integrated mechanisms:

### 1. Reactive Automations
Continuously inspects sensor thresholds to immediately trigger appropriate actuator responses:
- `AutomacaoChuva`: Closes open curtains and garage doors while disabling outdoor irrigation when rain is detected.
- `AutomacaoLuzExterior`: Illuminates exterior lighting automatically whenever ambient light drops below a given threshold.
- `AutomacaoTemperatura`: Engages heating/cooling when room temperatures deviate from target comfort zones.
- `AutomacaoRelva`: Activates lawn irrigation if soil moisture is critically low and no rain is detected.
- `AutomacaoEscalonamentoAnimais`: Guarantees automated pet feeding and support lighting at designated intervals.

### 2. Time-Based Schedules
Scheduled operations evaluated on each step of the virtual clock (`Controller.simularHora()`):
- Automated morning opening and sunset closing of blinds and shutters.
- Scheduled nighttime exterior and safety lighting.
- Timed garden irrigation and media routines.

### 3. Global Scenarios
Preconfigured profiles orchestrated via the Strategy pattern:
- 🚪 **Leaving Home** (*Sair de Casa*): Turns off all interior lamps, lowers thermostats, closes curtains, and secures garage gates.
- 🍷 **Dinner with Friends** (*Jantar com Amigos*): Sets ambient lighting to a warm tone, starts background music, and balances living room temperature.
- 🌙 **Going to Sleep** (*Deitar*): Extinguishes all living area lights, closes blinds, and validates exterior locks.
- ☀️ **Waking Up** (*Acordar*): Gently raises bedroom blinds, enables morning accent lighting, and warms up the thermostat.

### 4. Intelligent Suggestion Engine
Learns from user interactions:
- When a schedule is repeatedly created across multiple rooms, the system automatically suggests applying it to newly configured rooms for the same user.
- Established house-level automations are recommended when the user sets up new residential properties, minimizing manual reconfiguration.

---

## 📁 Repository Structure

```text
projetoPOO2526/
├── README.md                      # Main repository documentation (this file)
├── relatorio_poo.pdf              # Comprehensive technical report (PDF)
└── domusControl/                  # Gradle application source tree
    ├── build.gradle               # Gradle build configuration
    ├── settings.gradle            # Gradle project settings
    ├── gradlew / gradlew.bat      # Gradle Wrapper binaries (Linux / macOS / Windows)
    ├── estado1.obj                # Persisted sample system snapshot
    ├── uml/
    │   └── domuscontrol.puml      # Full PlantUML domain class diagram
    └── app/
        └── src/
            ├── main/java/
            │   ├── controller/    # Application controller (Controller.java)
            │   ├── exceptions/    # Custom domain exceptions
            │   ├── model/
            │   │   ├── automatismos/  # Automations, Schedules & Factories
            │   │   ├── casas/         # House, Room, Scenario & ScenarioStrategy
            │   │   ├── dispositivos/  # Device hierarchy & DeviceFactory
            │   │   ├── domus/         # Core system model (DomusControl.java)
            │   │   └── utilizadores/  # User entity
            │   ├── utils/         # SerializablePredicate & SerializableConsumer
            │   └── view/          # CLI interface (MainUI, TextUI, MenuAdmin, Menu)
            └── test/java/
                └── domuscontrol/  # Complete JUnit 5 test suite
```

---

## 🚀 Getting Started

### Prerequisites
- **Java JDK 21** or later (`java -version`).
- **Git** installed.

### 1. Clone the Repository
```bash
git clone git@github.com:POO-UM/2526-G056.git
cd 2526-G056/domusControl
```

### 2. Run the Application
Use the bundled Gradle wrapper:

- **Linux / macOS**:
  ```bash
  ./gradlew run --console=plain
  ```

- **Windows**:
  ```cmd
  gradlew.bat run --console=plain
  ```

> **Tip:** The `--console=plain` flag ensures clean, interactive terminal rendering for CLI menus.

---

## 🧪 Software Testing

The project includes an extensive suite of automated unit tests built with **JUnit 5**, covering domain business logic, factory creation, defensive copying, serialization, and automation triggers:

```bash
cd domusControl
./gradlew test
```

Detailed HTML test execution reports are automatically generated at:
`domusControl/app/build/reports/tests/test/index.html`

---

## 📊 Documentation & UML Modeling

- 📐 **Class Diagram**: The complete system model is specified in [`domusControl/uml/domuscontrol.puml`](domusControl/uml/domuscontrol.puml), formatted in **PlantUML** and detailing all classes, inheritance hierarchies, and associations.
- 📄 **Technical Report**: The full project documentation is available in [`relatorio_poo.pdf`](relatorio_poo.pdf), covering design decisions, theoretical foundations, and comprehensive project analysis.

---

## 👥 Development Team

Project developed by **Group 56** for the **Object-Oriented Programming (POO)** course — LEI, University of Minho:

| Student | ID | GitHub |
| :--- | :---: | :---: |
| **Afonso Costa** | `a110544` | [@afonso35costa](https://github.com/afonso35costa) |
| **Diogo Silva** | `a111493` | [@diogo-20](https://github.com/diogo-20) |
| **Emília Gomes** | `a112428` | [@emiliagomes](https://github.com/emiliagomes) |

---

<p align="center">
  University of Minho • BSc in Computer Science (LEI) • Academic Year 2025/2026
</p>
