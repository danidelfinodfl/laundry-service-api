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

    @Override
    public Order salvar(Order order) {

        Map<String, AttributeValue> item = new HashMap<>();

        item.put("id", AttributeValue.builder().s(order.getId()).build());
        item.put("cpfCliente", AttributeValue.builder().s(order.getCpfCliente()).build());
        item.put("nomeCliente", AttributeValue.builder().s(order.getNomeCliente()).build());
        item.put("descricaoPedido", AttributeValue.builder().s(order.getDescricaoPedido()).build());
        item.put("status", AttributeValue.builder().s(order.getStatus().name()).build());

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

        order.setId(item.get("id").s());
        order.setCpfCliente(item.get("cpfCliente").s());
        order.setNomeCliente(item.get("nomeCliente").s());
        order.setDescricaoPedido(item.get("descricaoPedido").s());
        order.setStatus(Enum.valueOf(
                com.laundry.domain.enums.OrderStatus.class,
                item.get("status").s()
        ));

        return order;
    }
}