package testData;

import java.util.ArrayList;


public class Order {

    public final ArrayList<String> ingredients;

    public Order(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public static Order generateOrderData(ArrayList<String> ingredientsOrderList) {
        return new Order(ingredientsOrderList);
    }

    public static Order generateOrderData() {
        return new Order(null);
    }


}
