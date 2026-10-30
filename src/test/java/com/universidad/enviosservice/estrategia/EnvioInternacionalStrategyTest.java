package com.universidad.enviosservice.estrategia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnvioInternacionalStrategyTest {

    private final EnvioInternacionalStrategy estrategia = new EnvioInternacionalStrategy();

    @ParameterizedTest
    @CsvSource({
        "3.0, 100.0, 230.0",   // peso <= 5
        "8.0, 100.0, 239.0",   // 5 < peso <= 20
        "25.0, 100.0, 297.5"   // peso > 20
    })
    void calculaCostoPorTramoDePeso(double pesoKg, double distanciaKm, double esperado) {
        assertEquals(esperado, estrategia.calcular(pesoKg, distanciaKm), 0.01);
    }

    @Test
    void aplicaRecargoDistanciaMayorA5000Km() {
        double costo = estrategia.calcular(3.0, 6000.0);
        assertEquals(10950.0, costo, 0.01);
    }
}