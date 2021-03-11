package eu.arima.mejorarTesting.farmacia.reservas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidosAlmacenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PedidosAlmacenService.class);

    private final PedidosAlmacenRepository pedidosAlmacenRepository;
    private final AlmacenClient almacenClient;


    public PedidosAlmacenService(PedidosAlmacenRepository pedidosAlmacenRepository,
                                 AlmacenClient almacenClient) {
        this.pedidosAlmacenRepository = pedidosAlmacenRepository;
        this.almacenClient = almacenClient;
    }

    public List<PedidoAlmacen> getAllPedidosAlmacen() {
        return pedidosAlmacenRepository.findAll();
    }


    public Optional<PedidoAlmacen> realizarPedido(Long idMedicamento, Integer unidades) {
        try {
            Long idReservaAlmacen = almacenClient.solicitarReservaMedicamento(idMedicamento, unidades);
            return Optional.of(pedidosAlmacenRepository
                    .save(new PedidoAlmacen(idReservaAlmacen, idMedicamento, unidades)));

        } catch (Exception e) {
            //capturamos la excepción porque en realidad lo tratamos ya que queremos devolver empty
            LOGGER.info("No se ha podido realizar el pedido al almacen");
            return Optional.empty();
        }
    }

}
