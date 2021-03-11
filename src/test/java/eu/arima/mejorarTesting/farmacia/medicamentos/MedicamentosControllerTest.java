package eu.arima.mejorarTesting.farmacia.medicamentos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MedicamentosController.class)
@Tag("testIntegracion")
class MedicamentosControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MedicamentosService medicamentosService;

    @Test
    @DisplayName("La petición (GET) /medicamentos/{idMedicamento} devuelve el medicamento recuperado de BD")
    void getMedicamento_devuelve_medicamento_bd() throws Exception {
        Medicamento medicamento = new Medicamento();
        medicamento.setId(1L);
        medicamento.setCodigo("IBU-600");
        medicamento.setFechaCaducidad(LocalDate.of(2021, 3, 16));
        medicamento.setUnidadesStock(23);

        when(medicamentosService.getMedicamento(1L)).thenReturn(medicamento);

        ResultActions resultado = mockMvc
                .perform(MockMvcRequestBuilders.get("/medicamentos/1"));

        assertAll("La respuesta de la petición tiene la información del medicamento",
                () -> resultado.andExpect(status().isOk()),
                () -> resultado.andExpect(jsonPath("$.id").value(medicamento.getId())),
                () -> resultado.andExpect(jsonPath("$.codigo").value(medicamento.getCodigo())),
                () -> resultado.andExpect(jsonPath("$.fechaCaducidad")
                        .value(medicamento.getFechaCaducidad().toString())),
                () -> resultado.andExpect(jsonPath("$.unidadesStock").value(medicamento.getUnidadesStock()))
        );
    }

    @Test
    @DisplayName("La petición (PUT) /medicamentos actualiza el stock del medicamento con el valor proporcionado")
    void actualizarStock_actualiza_stock_medicamento() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/medicamentos").contentType(MediaType.APPLICATION_JSON)
                                              .content("{\"id\": 1, \"unidades\": 25}"));

        verify(medicamentosService).actualizarStock(1L, 25);
    }
}