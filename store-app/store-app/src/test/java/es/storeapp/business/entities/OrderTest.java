package es.storeapp.business.entities;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OrderTest {

    private Order order;

    @Before
    public void setUp() {
        order = new Order();
    }

    @Test
    public void testSetAndGetOrderId() {
        order.setOrderId(1L);
        assertEquals(Long.valueOf(1L), order.getOrderId());
    }

    @Test
    public void testSetAndGetName() {
        order.setName("Pedido 1");
        assertEquals("Pedido 1", order.getName());
    }

    @Test
    public void testSetAndGetTimestamp() {
        Long timestamp = System.currentTimeMillis();
        order.setTimestamp(timestamp);
        assertEquals(timestamp, order.getTimestamp());
    }

    @Test
    public void testSetAndGetPrice() {
        order.setPrice(100);
        assertEquals(Integer.valueOf(100), order.getPrice());
    }

    @Test
    public void testSetAndGetAddress() {
        order.setAddress("Calle Falsa 123");
        assertEquals("Calle Falsa 123", order.getAddress());
    }

    @Test
    public void testSetAndGetState() {
        OrderState state = OrderState.PENDING; // Ajusta si cambia el enum
        order.setState(state);
        assertEquals(state, order.getState());
    }

    @Test
    public void testSetAndGetUser() {
        User user = new User();
        order.setUser(user);
        assertEquals(user, order.getUser());
    }

    @Test
    public void testOrderLinesInitialization() {
        assertNotNull(order.getOrderLines());
        assertTrue(order.getOrderLines().isEmpty());
    }

    @Test
    public void testSetAndGetOrderLines() {
        List<OrderLine> lines = new ArrayList<>();
        lines.add(new OrderLine());

        order.setOrderLines(lines);

        assertEquals(1, order.getOrderLines().size());
        assertEquals(lines, order.getOrderLines());
    }

    @Test
    public void testToStringNotNull() {
        order.setOrderId(1L);
        order.setName("Pedido");
        order.setTimestamp(123456L);
        order.setPrice(50);
        order.setAddress("Dirección");
        order.setState(OrderState.PENDING);
        order.setUser(new User());

        String result = order.toString();

        assertNotNull(result);
        assertTrue(result.contains("Pedido"));
        assertTrue(result.contains("50"));
    }
}
