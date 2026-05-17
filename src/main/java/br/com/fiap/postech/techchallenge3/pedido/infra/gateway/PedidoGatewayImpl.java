package br.com.fiap.postech.techchallenge3.pedido.infra.gateway;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.fiap.postech.techchallenge3.cardapio.infra.gateway.db.entity.ItemCardapioEntity;
import br.com.fiap.postech.techchallenge3.cardapio.infra.gateway.db.repository.ItemCardapioRepository;
import br.com.fiap.postech.techchallenge3.pedido.core.domain.ItemPedido;
import br.com.fiap.postech.techchallenge3.pedido.core.domain.Pedido;
import br.com.fiap.postech.techchallenge3.pedido.core.dto.CriarPedidoRequestDTO;
import br.com.fiap.postech.techchallenge3.pedido.core.gateway.PedidoGateway;
import br.com.fiap.postech.techchallenge3.pedido.infra.gateway.db.entity.PedidoEntity;
import br.com.fiap.postech.techchallenge3.pedido.infra.gateway.db.entity.PedidoItemEntity;
import br.com.fiap.postech.techchallenge3.pedido.infra.gateway.db.repository.PedidoRepository;
import br.com.fiap.postech.techchallenge3.restaurante.infra.gateway.db.entity.RestauranteEntity;
import br.com.fiap.postech.techchallenge3.restaurante.infra.gateway.db.repository.RestauranteRepository;
import br.com.fiap.postech.techchallenge3.usuario.infra.gateway.db.entity.UsuarioEntity;
import br.com.fiap.postech.techchallenge3.usuario.infra.gateway.db.repository.UsuarioRepository;


@Component
public class PedidoGatewayImpl implements PedidoGateway{

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final RestauranteRepository restauranteRepository;
    private final ItemCardapioRepository itemCardapioRepository;

    public PedidoGatewayImpl(PedidoRepository pedidoRepository, UsuarioRepository usuarioRepository, RestauranteRepository restauranteRepository, ItemCardapioRepository itemCardapioRepository) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.restauranteRepository = restauranteRepository;
        this.itemCardapioRepository = itemCardapioRepository;
    }

    @Override
    public Pedido salvar(CriarPedidoRequestDTO dto) {

        Pedido pedido = new Pedido(
        dto.clienteId(),
        dto.restauranteId(),
        dto.itens().stream()
            .map(i -> {
                ItemCardapioEntity itemCardapio = itemCardapioRepository.findById(i.itemCardapioId())
                    .orElseThrow(() -> new RuntimeException("Item de cardÃ¡pio nÃ£o encontrado"));

                return new ItemPedido(
                    null,                  // id
                    itemCardapio.getId(),  // itemCardapioId
                    itemCardapio.getNome(),// nome
                    i.quantidade(),        // quantidade
                    itemCardapio.getPreco()// preÃ§o
                );
            })
            .toList()
    );


        PedidoEntity entity = toEntity(pedido);
        PedidoEntity saved = pedidoRepository.save(entity);
        return toDomain(saved);
    }

    private Pedido toDomain(PedidoEntity entity) {        
        

        return new Pedido(
            entity.getId(),
            entity.getUsuario().getId(),
            entity.getRestaurante().getId(),
            entity.getItens().stream()
                .map(item -> new ItemPedido(
                    item.getId(),
                    item.getItemCardapio().getId(),
                    item.getItemCardapio().getNome(),
                    item.getQuantidade(),
                    item.getPrecoUnitario()
                ))
                .toList(),
            entity.getValorTotal(),
            entity.getStatus()
        );
    }


    private PedidoEntity toEntity(Pedido pedido) {
        PedidoEntity entity= new PedidoEntity();

        UsuarioEntity usuario = usuarioRepository.getReferenceById(pedido.getClienteId());
        entity.setUsuario(usuario);

        RestauranteEntity restaurante = restauranteRepository.getReferenceById(pedido.getRestauranteId());
        entity.setRestaurante(restaurante);

        entity.setValorTotal(pedido.getValorTotal());
        entity.setStatus(pedido.getStatus());


        // converter itens
        List<PedidoItemEntity> itens = pedido.getItens().stream()
            .map(item -> {
                PedidoItemEntity itemEntity = new PedidoItemEntity();
                itemEntity.setPedido(entity);

                ItemCardapioEntity itemCardapio = itemCardapioRepository.getReferenceById(item.getItemCardapioId());
                itemEntity.setItemCardapio(itemCardapio);

                itemEntity.setQuantidade(item.getQuantidade());
                itemEntity.setPrecoUnitario(item.getPreco());
                return itemEntity;
            }).toList();

        entity.setItens(itens);
        return entity;
    }

    @Override
    public Optional<Pedido> consultarPedido(Long pedidoId) {
     
        return pedidoRepository.findById(pedidoId)
        .map(entity -> {
            // monta o domÃ­nio completo
            return new Pedido(
                entity.getId(),
                entity.getUsuario().getId(),
                entity.getRestaurante().getId(),
                entity.getItens().stream()
                    .map(item -> new ItemPedido(
                        item.getId(),
                        item.getItemCardapio().getId(),
                        item.getItemCardapio().getNome(),
                        item.getQuantidade(),
                        item.getPrecoUnitario()
                    ))
                    .toList(),
                entity.getValorTotal(),
                entity.getStatus()
            );
        });
        
    }

    @Override
    public List<Pedido> consultarPedidosCliente(Long clienteId) {
        
        return pedidoRepository.findByUsuarioId(clienteId)
            .stream()
            .map(this::toDomain) // mÃ©todo que converte PedidoEntity â†’ Pedido (domÃ­nio)
            .toList();        
    }

    @Override
    public Pedido atualizarStatus(Pedido pedido) {
        PedidoEntity entity = pedidoRepository.findById(pedido.getId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + pedido.getId()));
        entity.setStatus(pedido.getStatus());
        return toDomain(pedidoRepository.save(entity));
    }

}
