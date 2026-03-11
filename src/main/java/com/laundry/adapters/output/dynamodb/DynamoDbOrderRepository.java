package com.laundry.adapters.output.dynamodb;

import com.laundry.domain.entity.Order;
import com.laundry.domain.repository.OrderRepository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.*;

@Repository
public class DynamoDbOrderRepository implements OrderRepository {

    private final DynamoDbClient dynamoDbClient;

    public DynamoDbOrderRepository(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    private void putIfNotEmpty(Map<String, AttributeValue> item, String key, String value) {
        if (value != null && !value.isBlank()) {
            item.put(key, AttributeValue.builder().s(value).build());
        }
    }

    @Override
    public Order salvar(Order order) {
        Map<String, AttributeValue> item = new HashMap<>();

        putIfNotEmpty(item, "id", order.getId());
        putIfNotEmpty(item, "cpfCliente", order.getCpfCliente());
        putIfNotEmpty(item, "nomeCliente", order.getNomeCliente());
        putIfNotEmpty(item, "descricaoPedido", order.getDescricaoPedido());

        putIfNotEmpty(item, "rua", order.getRua());
        putIfNotEmpty(item, "numero", order.getNumero());
        putIfNotEmpty(item, "complemento", order.getComplemento());
        putIfNotEmpty(item, "cidade", order.getCidade());
        putIfNotEmpty(item, "estado", order.getEstado());

        putIfNotEmpty(item, "createdAt", order.getCreatedAt());

        putIfNotEmpty(item, "status", order.getStatus().name());

        PutItemRequest request = PutItemRequest.builder()
                .tableName("orders")
                .item(item)
                .build();

        dynamoDbClient.putItem(request);

        return order;
    }

    @Override
    public List<Order> listarTodos() {
        return List.of();
    }

    @Override
    public Optional<Order> buscarPorId(String id) {

        Map<String, AttributeValue> key = Map.of(
                "id", AttributeValue.builder().s(id).build()
        );

        GetItemRequest request = GetItemRequest.builder()
                .tableName("orders")
                .key(key)
                .build();

        GetItemResponse response = dynamoDbClient.getItem(request);

        if (!response.hasItem()) {
            return Optional.empty();
        }

        return Optional.of(mapper(response.item()));
    }


    @Override
    public List<Order> buscarPorCpfCliente(String cpf) {

        QueryRequest request = QueryRequest.builder()
                .tableName("orders")
                .indexName("cpf-index")
                .keyConditionExpression("cpfCliente = :cpf")
                .expressionAttributeValues(
                        Map.of(":cpf", AttributeValue.builder().s(cpf).build())
                )
                .build();

        QueryResponse response = dynamoDbClient.query(request);

        List<Order> pedidos = new ArrayList<>();

        for (Map<String, AttributeValue> item : response.items()) {
            pedidos.add(mapper(item));
        }

        return pedidos;
    }
    public void atualizarStatus(String id, String status) {

        Map<String, AttributeValue> key = Map.of(
                "id", AttributeValue.builder().s(id).build()
        );

        UpdateItemRequest request = UpdateItemRequest.builder()
                .tableName("orders")
                .key(key)
                .updateExpression("SET #s = :status")
                .expressionAttributeNames(Map.of("#s", "status"))
                .expressionAttributeValues(
                        Map.of(":status", AttributeValue.builder().s(status).build())
                )
                .build();

        dynamoDbClient.updateItem(request);
    }
    private Order mapper(Map<String, AttributeValue> item) {

        Order order = new Order();

        if(item.containsKey("id"))
            order.setId(item.get("id").s());

        if(item.containsKey("cpfCliente"))
            order.setCpfCliente(item.get("cpfCliente").s());

        if(item.containsKey("nomeCliente"))
            order.setNomeCliente(item.get("nomeCliente").s());

        if(item.containsKey("descricaoPedido"))
            order.setDescricaoPedido(item.get("descricaoPedido").s());

        if(item.containsKey("rua"))
            order.setRua(item.get("rua").s());

        if(item.containsKey("numero"))
            order.setNumero(item.get("numero").s());

        if(item.containsKey("complemento"))
            order.setComplemento(item.get("complemento").s());

        if(item.containsKey("cidade"))
            order.setCidade(item.get("cidade").s());

        if(item.containsKey("estado"))
            order.setEstado(item.get("estado").s());

        if(item.containsKey("createdAt"))
            order.setCreatedAt(item.get("createdAt").s());

        if(item.containsKey("status"))
            order.setStatus(Enum.valueOf(
                    com.laundry.domain.enums.OrderStatus.class,
                    item.get("status").s()
            ));

        return order;
    }
}