package com.example.rupizza;

import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CurrentOrderActivity extends AppCompatActivity {

    ListView pizzasListView;
    TextView orderNumber, subTotal, tax, total;

    StoreOrders storeOrders = StoreOrders.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order);

        orderNumber = findViewById(R.id.orderNumber);
        subTotal = findViewById(R.id.subTotal);
        tax = findViewById(R.id.tax);
        total = findViewById(R.id.total);
        updateOrderInfo();

        Button back_to_main = findViewById(R.id.backtomain_button);
        back_to_main.setOnClickListener(v -> openMainActivity());

        Button remove_pizza = findViewById(R.id.removepizza_button);
        remove_pizza.setOnClickListener(v -> handleRemovePizzaButtonAction());
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void updateOrderInfo() {
        if (storeOrders.getCurrentOrder() == null) {
            return;
        }

        List<String> pizzaStrings = storeOrders.getCurrentOrder().getPizzas().stream()
                .map(Pizza::toString)
                .collect(Collectors.toList());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, pizzaStrings);
        pizzasListView = findViewById(R.id.pizzasListView);
        pizzasListView.setAdapter(adapter);

        orderNumber.setText(String.valueOf(storeOrders.getCurrentOrder().getOrderNumber()));
        subTotal.setText(String.format("$%.2f", storeOrders.getCurrentOrder().calculateSubTotal()));
        tax.setText(String.format("$%.2f", storeOrders.getCurrentOrder().calculateTax()));
        total.setText(String.format("$%.2f", storeOrders.getCurrentOrder().calculateTotal()));
    }

    private void handleRemovePizzaButtonAction() {
        int selectedPosition = pizzasListView.getCheckedItemPosition();

        if (selectedPosition != ListView.INVALID_POSITION) {
            Order currentOrder = storeOrders.getCurrentOrder();
            ArrayList<Pizza> pizzas = currentOrder.getPizzas();

            Pizza removedPizza = pizzas.remove(selectedPosition);

            ArrayAdapter<String> adapter = (ArrayAdapter<String>) pizzasListView.getAdapter();
            adapter.remove(removedPizza.toString());
            adapter.notifyDataSetChanged();
        }
        updateOrderInfo();
    }
}