package com.universidad.enviosservice.estrategia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnvioExpressStrategyTest {

    private final EnvioExpressStrategy estrategia = new EnvioExpressStrategy();

    @ParameterizedTest
    @CsvSource({
        "3.0, 100.0, 120.0",  // peso <= 5
        "8.0, 100.0, 124.5",  // 5 < peso <= 20
        "25.0, 100.0, 152.5"  // peso > 20
    })
    void calculaCostoPorTramoDePeso(double pesoKg, double distanciaKm, double esperado) {
        assertEquals(esperado, estrategia.calcular(pesoKg, distanciaKm), 0.01);
    }

    @Test
    void aplicaRecargoDistanciaMayorA500Km() {
        double costo = estrategia.calcular(3.0, 600.0);
        assertEquals(745.0, costo, 0.01);
    }
}