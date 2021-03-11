package eu.arima.mejorarTesting.farmacia.medicamentos;

import org.springframework.web.bind.annotation.*;

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

    @PutMapping
    void actualizarStock(@RequestBody MedicamentoStockDTO medicamentoStockDTO) {
        medicamentosService.actualizarStock(medicamentoStockDTO.getId(), medicamentoStockDTO.getUnidades());
    }

}
