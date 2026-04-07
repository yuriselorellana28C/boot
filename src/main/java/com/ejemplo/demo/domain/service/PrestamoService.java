package com.ejemplo.demo.domain.service;

import com.ejemplo.demo.api.dto.PrestamoRequest;
import com.ejemplo.demo.api.dto.PrestamoResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
public class PrestamoService {

    public PrestamoResponse simular(PrestamoRequest request) {
        BigDecimal monto = request.monto();
        BigDecimal tasaAnual = request.tasaAnual();
        int meses = request.meses();

        BigDecimal tasaMensual = tasaAnual
                .divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

        if (tasaMensual.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("La tasa mensual debe ser mayor que 0");
        }

        MathContext mc = new MathContext(20, RoundingMode.HALF_UP);

        BigDecimal unoMasR = BigDecimal.ONE.add(tasaMensual, mc);
        BigDecimal potencia = unoMasR.pow(meses, mc);

        BigDecimal numerador = monto.multiply(tasaMensual, mc).multiply(potencia, mc);
        BigDecimal denominador = potencia.subtract(BigDecimal.ONE, mc);

        if (denominador.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("No se pudo calcular la cuota");
        }

        BigDecimal cuotaMensual = numerador.divide(denominador, 2, RoundingMode.HALF_UP);
        BigDecimal totalPagar = cuotaMensual.multiply(BigDecimal.valueOf(meses)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal interesTotal = totalPagar.subtract(monto).setScale(2, RoundingMode.HALF_UP);

        return new PrestamoResponse(cuotaMensual, interesTotal, totalPagar);
    }
}