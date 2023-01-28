package testdata;

import pojo.Order;

import java.util.Date;
import java.util.Random;

public class BuildOrderData {
    Order order;


    public Order buildOrderDataJSON() {
        order = new Order();

        Random rnd = new Random();
        int id = rnd.nextInt(1000);
        int petId = rnd.nextInt(10000);

        order.setId(id);
        order.setComplete(true);
        order.setPetId(petId);
        order.setQuantity(1);
        order.setStatus("approved");
        order.setShipDate(new Date());

        return order;
    }
}
