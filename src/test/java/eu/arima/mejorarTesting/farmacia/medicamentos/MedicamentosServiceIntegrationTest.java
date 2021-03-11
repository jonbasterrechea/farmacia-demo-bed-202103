package eu.arima.mejorarTesting.farmacia.medicamentos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@Testcontainers
@Tag("testIntegracion")
class MedicamentosServiceIntegrationTest {

    @Container
    private final static PostgreSQLContainer postgresContainer = new PostgreSQLContainer(DockerImageName
            .parse("postgres:13"));

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }


    @Autowired
    private MedicamentosService medicamentosService;
    @Autowired
    private MedicamentosRepository medicamentosRepository;

    @Test
    @DisplayName("actualizarStock modifica en BD las unidades en stock del medicamento")
    void actualizarStock_actualiza_unidades_medicamento() {
        assertEquals(50, medicamentosRepository.findById(1L).orElseThrow().getUnidadesStock());

        medicamentosService.actualizarStock(1L, 150);

        assertEquals(150, medicamentosRepository.findById(1L).orElseThrow().getUnidadesStock());
    }

    @Test
    @DisplayName("actualizarStock para un medicamento no existente lanza la excepción NoSuchElementException")
    void actualizarStock_si_medicamento_no_existe_excepcion() {
        NoSuchElementException infoException = assertThrows(NoSuchElementException.class, () -> medicamentosService
                .actualizarStock(19L, 10));
        assertEquals("No value present", infoException.getMessage());
    }
}