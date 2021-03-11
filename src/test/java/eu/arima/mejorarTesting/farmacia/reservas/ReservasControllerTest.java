package eu.arima.mejorarTesting.farmacia.reservas;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservasController.class)
class ReservasControllerTest {

    public static final long ID_RESERVA = 5L;
    public static final int UDS_RECOGER_RESERVA = 2;
    public static final int UDS_PTES_RESERVA = 0;
    public static final int ID_MEDICAMENTO = 1;
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ReservasService reservasService;

    @MockBean
    PedidosAlmacenService pedidosAlmacenService;

    @Test
    @DisplayName("La petición (POST) /reservas con la información para una reserva realiza la reserva devolviendo la información de recogida de la misma")
    void reservar_devuelve_informacion_recogida_reserva() throws Exception {
        InfoRecogidaReserva informacionRecogida = new InfoRecogidaReserva(ID_RESERVA, UDS_RECOGER_RESERVA, UDS_PTES_RESERVA);
        when(reservasService.reservarMedicamento(ID_MEDICAMENTO, UDS_RECOGER_RESERVA)).thenReturn(informacionRecogida);

        ResultActions resultado = mockMvc.perform(post("/reservas")
                .contentType(APPLICATION_JSON)
                .content("{\"idMedicamento\": " + ID_MEDICAMENTO + ", \"unidades\": " + UDS_RECOGER_RESERVA + "}"));

        assertAll("La respuesta tiene la información de la recogida de la reserva",
                () -> resultado.andExpect(status().isOk()),
                () -> resultado.andExpect(jsonPath("$.idReserva").value(ID_RESERVA)),
                () -> resultado.andExpect(jsonPath("$.unidadesRecoger").value(UDS_RECOGER_RESERVA)),
                () -> resultado.andExpect(jsonPath("$.unidadesPendientes").value(UDS_PTES_RESERVA)));
    }
}