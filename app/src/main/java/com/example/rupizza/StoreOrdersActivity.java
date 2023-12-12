package com.example.rupizza;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StoreOrdersActivity extends AppCompatActivity {

    ListView pizzasListView;
    TextView orderTotal;

    Button cancelOrderButton;

    Spinner orders_spinner;

    StoreOrders storeOrders = StoreOrders.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_orders);

        Button back_to_main = findViewById(R.id.backtomain_button);
        back_to_main.setOnClickListener(v -> openMainActivity());

        orders_spinner = findViewById(R.id.storeOrderSpinner);
        List<String> orderNumbers = new ArrayList<>();
        // populate spinner with order numbers
        for (Order order : storeOrders.getAllOrders()) {
            orderNumbers.add(String.valueOf(order.getOrderNumber()));
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                orderNumbers);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orders_spinner.setAdapter(arrayAdapter);
        // for the selected order from spinner, update list view and order total
        orders_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    updateOrderInfo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        cancelOrderButton = findViewById(R.id.cancelOrderButton);
        cancelOrderButton.setOnClickListener(v -> handleCancelOrderButtonAction());
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void updateOrderInfo() {
        // get order number from spinner
        orders_spinner = findViewById(R.id.storeOrderSpinner);
        // int orderNumber = Integer.parseInt(orders_spinner.getPrompt().toString());
        int orderNumber = Integer.parseInt(orders_spinner.getSelectedItem().toString());
        Order order = storeOrders.getOrder(orderNumber);
        if (order == null) {
            return;
        }
        // populate list view with pizzas from order
        pizzasListView = findViewById(R.id.storeOrderList);
        List<String> pizzaStrings = order.getPizzas().stream()
                .map(Pizza::toString)
                .collect(Collectors.toList());
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                pizzaStrings);
        pizzasListView.setAdapter(arrayAdapter);
        // update order total
        orderTotal = findViewById(R.id.editTextNumber2);
        orderTotal.setText(String.format("$%.2f", order.calculateTotal()));

    }

    private void handleCancelOrderButtonAction() {
        orders_spinner = findViewById(R.id.storeOrderSpinner);
        int orderNumber = Integer.parseInt(orders_spinner.getSelectedItem().toString());
        Order order = storeOrders.getOrder(orderNumber);
        if (order != null) {
            if (order == storeOrders.getCurrentOrder()) {
                // use an alert dialog to let user know they can't cancel the current order
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("You can't cancel the current order! Please place the order first.")
                        .setTitle("Error");
                builder.setPositiveButton("OK", null);
                AlertDialog dialog = builder.create();
                dialog.show();
                return;
            }
            storeOrders.removeFromOrders(order);
            // update spinner
            List<String> orderNumbers = new ArrayList<>();
            for (Order o : storeOrders.getAllOrders()) {
                orderNumbers.add(String.valueOf(o.getOrderNumber()));
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    orderNumbers);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            orders_spinner.setAdapter(arrayAdapter);
            // update list view and order total
            updateOrderInfo();
        }
    }
}