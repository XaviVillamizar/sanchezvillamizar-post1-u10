# Envios Service - Métricas de Calidad y SonarQube

## Descripción
Repositorio del post-contenido de la Unidad 10 de Patrones de Diseño de Software.
Un único proyecto Java gestionado con Maven (sin Spring Boot) encargado de calcular los costos de envío de paquetería. El foco del trabajo es **completar el ciclo de inspección de calidad**: diagnosticar un problema de diseño inicial (alta complejidad ciclomática y baja cobertura) mediante análisis estático con **SonarCloud** y **JaCoCo**, para luego refactorizar el código aplicando el patrón **Strategy**. Finalmente, se prueba el nuevo diseño con pruebas unitarias parametrizadas (JUnit 5) y se valida la mejora mediante un **Quality Gate** personalizado.

## Estructura del proyecto

```text
src/main/java/com/universidad/enviosservice/
├── service/
│   └── ServicioCalculoEnvio.java (Orquestador principal)
└── estrategia/
    ├── EstrategiaEnvio.java (Interfaz Strategy)
    ├── EnvioEstandarStrategy.java
    ├── EnvioExpressStrategy.java
    ├── EnvioInternacionalStrategy.java
    └── EnvioOvernightStrategy.java

src/test/java/com/universidad/enviosservice/
├── service/
│   └── ServicioCalculoEnvioTest.java (Pruebas del orquestador)
└── estrategia/
    ├── EnvioEstandarStrategyTest.java (Pruebas parametrizadas)
    ├── EnvioExpressStrategyTest.java
    ├── EnvioInternacionalStrategyTest.java
    └── EnvioOvernightStrategyTest.java

## Ejecución de pruebas y análisis de calidad

Para ejecutar las pruebas unitarias, generar el reporte de cobertura y realizar el análisis estático del código con SonarCloud, utiliza los siguientes comandos:

### 1. Ejecutar pruebas unitarias

```bash
mvn clean test
```

Ejecuta todas las pruebas unitarias del proyecto.

### 2. Generar reporte de cobertura con JaCoCo

```bash
mvn clean verify
```

Ejecuta las pruebas unitarias y genera el reporte de cobertura de código mediante JaCoCo.

El reporte HTML se genera localmente en la siguiente ruta:

```text
target/site/jacoco/index.html
```

### 3. Ejecutar análisis en SonarCloud

Para realizar el análisis estático del código y consultar las métricas de calidad en SonarCloud, ejecuta:

```bash
mvn clean verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.organization=xavivillamizar \
  -Dsonar.token=9e7f683a1ccd2016419359a0073a4b1daa7bdd42
```

---

## Evidencias del análisis

En esta sección se presentan las evidencias obtenidas durante las dos fases del proceso: el diagnóstico inicial y la posterior refactorización del código.



---

## Comparación de métricas (Antes / Después)

La siguiente tabla muestra la evolución de las métricas de calidad del código después del proceso de refactorización y la implementación de nuevas pruebas unitarias.

| **Métrica** | **Antes (Parte 1)** | **Después (Parte 2)** | **Resultado** |
|:---|:---:|:---:|:---|
| Complejidad ciclomática de `calcularCosto()` | **20** | **5** | Reducción del 75 % |
| Cobertura de ramas (General) | **10.0 %** | **95.0 %** | Incremento de 85 puntos porcentuales |
| Cobertura de líneas (General) | **13.7 %** | **98.2 %** | Incremento de 84.5 puntos porcentuales |
| Code Smells | **1** | **0** | Eliminación total |
| Estado del Quality Gate | No configurado | **Passed** | Validación satisfactoria |

---

## Decisiones de diseño

### 1. Configuración del análisis

| **Decisión** | **Elección** | **Justificación** |
|:---|:---|:---|
| **Entorno de análisis** | SonarCloud | Permite realizar el análisis en la nube sin consumir recursos de hardware local, como sucedería con una instancia ejecutada mediante Docker. Además, facilita el acceso al dashboard para cualquier persona que necesite revisar los resultados, sin configuraciones adicionales. |
| **Exclusiones (`sonar.exclusions`)** | Ninguna | Todo el código fuente implementado corresponde a lógica de negocio crítica que debe estar cubierta por las pruebas unitarias. No existen clases de infraestructura vacías que puedan distorsionar las métricas de calidad. |
| **Métrica principal** | Complejidad ciclomática | Permite identificar la cantidad de caminos independientes de ejecución dentro del método original. La complejidad inicial de 20 evidenciaba la presencia de múltiples estructuras condicionales (`if/else`), dificultando la cobertura completa mediante pruebas unitarias. |

### 2. Refactorización y pruebas

| **Decisión** | **Elección** | **Justificación** |
|:---|:---|:---|
| **Patrón de diseño** | Strategy | Cada tipo de envío calcula su costo mediante una fórmula específica a partir de un valor de entrada. No se eligió State porque el objeto no mantiene un estado interno que modifique su comportamiento futuro. Tampoco se utilizó Chain of Responsibility, ya que no existe una secuencia de manejadores que deban evaluar y procesar el mismo paquete. Las reglas de fragilidad se conservaron en el orquestador porque son transversales a todos los tipos de envío. |
| **Criterio de nuevas pruebas** | Límites y ramas sin cobertura | El reporte inicial de JaCoCo identificó ramas sin cubrir correspondientes a los tipos de envío `EXPRESS`, `OVERNIGHT` e `INTERNACIONAL`, además de diferentes recargos. Las nuevas pruebas mediante `@ParameterizedTest` se enfocaron en verificar los límites matemáticos de peso (`<= 5`, `<= 20`, `> 20`) y las excepciones relacionadas con la distancia. Esto permitió aumentar la cobertura de manera efectiva, evitando pruebas triviales. |
| **Configuración del Quality Gate** | Complejidad ciclomática ≤ 10 y cobertura ≥ 70 % | Se establecieron umbrales que permiten mantener una complejidad ciclomática moderada y garantizar una cobertura suficiente de la lógica de negocio. El límite de complejidad evita métodos excesivamente difíciles de mantener, mientras que el 70 % de cobertura establece un objetivo exigente sin incentivar la creación de pruebas vacías únicamente para alcanzar el 100 %. |

#### Umbrales configurados para el Quality Gate

| **Métrica** | **Umbral establecido** |
|:---|:---:|
| Complejidad ciclomática (CC) | **10 máximo** |
| Cobertura de código (Coverage) | **70 % mínimo** |

---

## Herramientas y tecnologías utilizadas

Las siguientes herramientas fueron utilizadas durante el desarrollo, las pruebas y el análisis de calidad del proyecto:

| **Herramienta / Tecnología** | **Uso** |
|:---|:---|
| Java 21 | Lenguaje de programación |
| Apache Maven 3.10 | Gestión de dependencias y construcción del proyecto |
| SonarCloud | Análisis estático y evaluación de calidad del código |
| JaCoCo | Medición y generación de reportes de cobertura |
| JUnit 5 (Jupiter API y Params) | Implementación de pruebas unitarias y parametrizadas |
| Visual Studio Code | Entorno de desarrollo |
| Git | Control de versiones |
| GitHub | Alojamiento y gestión del repositorio |