package eu.arima.mejorarTesting.farmacia.medicamentos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicamentos")
public class MedicamentosController {

    private final MedicamentosService medicamentosService;

    public MedicamentosController(MedicamentosService medicamentosService) {
        this.medicamentosService = medicamentosService;
    }

    @GetMapping("/{idMedicamento}")
    Medicamento getMedicamento(@PathVariable long idMedicamento) {
        return medicamentosService.getMedicamento(idMedicamento);
    }

}
