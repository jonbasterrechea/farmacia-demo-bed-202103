package eu.arima.mejorarTesting.farmacia.medicamentos;

import eu.arima.mejorarTesting.farmacia.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@UnitTest
class MedicamentosServiceTest {

    public static final long ID_MEDICAMENTO = 1L;

    private MedicamentosService medicamentosService;
    @Mock
    private MedicamentosRepository medicamentosRepository;
    @Mock
    private Medicamento medicamentoBD;

    @BeforeEach
    void setUp() {
        medicamentosService = new MedicamentosService(medicamentosRepository);
        when(medicamentosRepository.findById(ID_MEDICAMENTO)).thenReturn(Optional.of(medicamentoBD));
    }

    @Test
    @DisplayName("getMedicamento devuelve el medicamento recuperado de BD")
    void getMedicamento_devuelve_medicamento_BD() {
        Medicamento medicamento = medicamentosService.getMedicamento(ID_MEDICAMENTO);

        assertEquals(medicamentoBD, medicamento);
    }

    @Test
    @DisplayName("getMedicamento lanza MedicamentoCaducadoException si el medicamento recuperado está caducado")
    void getMedicamento_lanza_excepcion_medicamento_caducado() {
        when(medicamentoBD.estaCaducado()).thenReturn(true);
        assertThrows(MedicamentoCaducadoException.class, () -> medicamentosService.getMedicamento(ID_MEDICAMENTO));
    }
}