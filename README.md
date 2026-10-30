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