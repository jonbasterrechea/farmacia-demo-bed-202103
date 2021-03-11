package eu.arima.mejorarTesting.farmacia.medicamentos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;

import java.time.LocalDate;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

class MedicamentoTest {


    public static final LocalDate HOY = LocalDate.now();

    static Stream<LocalDate> generadorFechasAnteriores() {
        IntStream diasRestar = new Random().ints(10, 1, 3650);
        return diasRestar.mapToObj(HOY::minusDays);
    }

    static Stream<LocalDate> generadorFechasPosteriores() {
        IntStream diasSumar = new Random().ints(10, 1, 3650);
        return diasSumar.mapToObj(HOY::plusDays);
    }

    @ParameterizedTest(name = "Si la fecha caducidad es {0} está caducado")
    @DisplayName("Si la fecha de caducidad del medicamento es anterior a hoy, el medicamento está caducado")
    @MethodSource("generadorFechasAnteriores")
    void estaCaducado_si_fechaCaducidad_anterior_true(LocalDate fechaAnterior) {
        Medicamento medicamento = new Medicamento();
        medicamento.setFechaCaducidad(fechaAnterior);
        assertTrue(medicamento.estaCaducado());
    }

    @ParameterizedTest(name = "Si la fecha caducidad es {0} no está caducado")
    @DisplayName("Si la fecha de caducidad del medicamento es posterior a hoy, el medicamento no está caducado")
    @MethodSource("generadorFechasPosteriores")
    void estaCaducado_si_fechaCaducidad_posterior_false(LocalDate fechaPosterior) {
        Medicamento medicamento = new Medicamento();
        medicamento.setFechaCaducidad(fechaPosterior);
        assertFalse(medicamento.estaCaducado());
    }

    @Test
    @DisplayName("Si la fecha de caducidad del medicamento es hoy, el medicamento no está caducado")
    void estaCaducado_si_fechaCaducidad_hoy_false() {
        LocalDate hoy = LocalDate.of(2021, 3, 16);
        Medicamento medicamento = new Medicamento();
        medicamento.setFechaCaducidad(hoy);
        try (MockedStatic<LocalDate> localDateMock = mockStatic(LocalDate.class)) {
            localDateMock.when(LocalDate::now).thenReturn(hoy);
            assertFalse(medicamento.estaCaducado());
        }
    }

    @Test
    @DisplayName("Si el medicamento no tiene fecha de caducidad, el medicamento no está caducado")
    void estaCaducado_sin_fechaCaducidad_false() {
        Medicamento medicamento = new Medicamento();
        assertFalse(medicamento.estaCaducado());
    }
}