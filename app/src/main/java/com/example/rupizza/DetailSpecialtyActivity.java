package com.example.rupizza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicInteger;

public class DetailSpecialtyActivity extends AppCompatActivity {
    ImageView pageImageView;
    TextView pagePizzaTitle, pagePizzaToppings, pagePizzaSauce, priceTextView, quantityTextView;

    CheckBox extraCheeseCheckBox, extraSauceCheckBox;

    Button addToOrderButton, incrementButton, decrementButton;

    String pizzaName, pizzaToppings, pizzaSauce;
    int pizzaImage;


    StoreOrders storeOrders = StoreOrders.getInstance();
    final int[] quantity = {1};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialty_pizzas);

        pageImageView = findViewById(R.id.pageImageView);
        pagePizzaTitle = findViewById(R.id.pagePizzaTitle);
        pagePizzaSauce = findViewById(R.id.pagePizzaSauce);
        pagePizzaToppings = findViewById(R.id.pagePizzaToppings);

        quantityTextView = findViewById(R.id.quantity);
        quantityTextView.setText(String.valueOf(quantity[0]));
        incrementButton = findViewById(R.id.increment);
        decrementButton = findViewById(R.id.decrement);

        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity[0] < 10) {
                    quantity[0]++;  // Increase order quantity
                    quantityTextView.setText(String.valueOf(quantity[0]));
                    if (quantity[0] == 10) {
                        incrementButton.setEnabled(false);  // Disable increment button
                    }
                    decrementButton.setEnabled(true);  // Enable decrement button
                }
                updatePrice();
            }
        });
        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity[0] > 1) {
                    quantity[0]--;  // Decrease order quantity
                    quantityTextView.setText(String.valueOf(quantity[0]));
                    if (quantity[0] == 1) {
                        decrementButton.setEnabled(false);  // Disable decrement button
                    }
                    incrementButton.setEnabled(true);  // Enable increment button
                }
                updatePrice();
            }
        });

        Button back_to_main = findViewById(R.id.backtomain_button);
        back_to_main.setOnClickListener(v -> openMainActivity());

        RadioGroup sizeRadioGroup = findViewById(R.id.specialty_size_radio_group);
        sizeRadioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            keyReleasedProperty();
            updatePrice();
        });

        addToOrderButton = findViewById(R.id.specialtyAddToOrderButton);
        addToOrderButton.setOnClickListener(v -> handleOrderButtonAction());

        extraCheeseCheckBox = findViewById(R.id.specialty_extra_cheese);
        extraCheeseCheckBox.setOnCheckedChangeListener((compoundButton, b) -> updatePrice());

        extraSauceCheckBox = findViewById(R.id.specialty_extra_sauce);
        extraSauceCheckBox.setOnCheckedChangeListener((compoundButton, b) -> updatePrice());

        getData();
        setData();
    }

    private void getData() {
        if (getIntent().hasExtra("pizzaName") && getIntent().hasExtra("pizzaDescription") && getIntent().hasExtra("pizzaImage")) {
            pizzaName = getIntent().getStringExtra("pizzaName");
            pizzaToppings = getIntent().getStringExtra("pizzaDescription");
            pizzaSauce = getIntent().getStringExtra("pizzaSauce");
            pizzaImage = getIntent().getIntExtra("pizzaImage", 1);
        }
        else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        pagePizzaTitle.setText(pizzaName);
        pagePizzaToppings.setText(pizzaToppings);
        pagePizzaSauce.setText(pizzaSauce);
        pageImageView.setImageResource(pizzaImage);

    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void updatePrice() {
        Pizza pizza = PizzaMaker.createPizza(pizzaName);
        RadioButton selectedRadioButton = (RadioButton) findViewById(R.id.specialty_size_radio_group).findViewById(((RadioGroup) findViewById(R.id.specialty_size_radio_group)).getCheckedRadioButtonId());
        if (selectedRadioButton != null) {
            String selectedSize = selectedRadioButton.getText().toString();
            Size tempSize = null;
            switch (selectedSize) {
                case "Small" -> tempSize = Size.SMALL;
                case "Medium" -> tempSize = Size.MEDIUM;
                case "Large" -> tempSize = Size.LARGE;
            }
            pizza.setSize(tempSize);

            extraCheeseCheckBox = findViewById(R.id.specialty_extra_cheese);
            if (extraCheeseCheckBox.isChecked()) {
                pizza.addExtraCheese();
            }
            extraSauceCheckBox = findViewById(R.id.specialty_extra_sauce);
            if (extraSauceCheckBox.isChecked()) {
                pizza.addExtraSauce();
            }
            priceTextView = findViewById(R.id.specialtyPizzaPrice);
            double pizzaPrice = pizza.price() * quantity[0];
            priceTextView.setText(String.format("$%.2f", pizzaPrice));
        }
    }

    private void handleOrderButtonAction() {
        RadioButton selectedRadioButton = (RadioButton) findViewById(R.id.specialty_size_radio_group).findViewById(((RadioGroup) findViewById(R.id.specialty_size_radio_group)).getCheckedRadioButtonId());
        String selectedSize = selectedRadioButton.getText().toString();
        Size size = null;
        switch (selectedSize) {
            case "Small" -> size = Size.SMALL;
            case "Medium" -> size = Size.MEDIUM;
            case "Large" -> size = Size.LARGE;
        }
        extraCheeseCheckBox = findViewById(R.id.specialty_extra_cheese);
        boolean extraCheese = extraCheeseCheckBox.isChecked();

        extraSauceCheckBox = findViewById(R.id.specialty_extra_sauce);
        boolean extraSauce = extraSauceCheckBox.isChecked();

        Pizza pizza = PizzaMaker.createPizza(pizzaName);
        pizza.setSize(size);
        if (extraCheese) {
            pizza.addExtraCheese();
        }
        if (extraSauce) {
            pizza.addExtraSauce();
        }
        for (int i = 0; i < quantity[0]; i++) {
            Order currentOrder = storeOrders.getCurrentOrder();
            if (currentOrder != null) {
                currentOrder.addToOrder(pizza);
            }
            else {
                storeOrders.startNewOrder();
                storeOrders.getCurrentOrder().addToOrder(pizza);
            }
        }
        if (quantity[0] == 1) Toast.makeText(this, "Pizza added to order", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "Pizzas added to order", Toast.LENGTH_SHORT).show();
        // return to main activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void keyReleasedProperty() {
        findViewById(R.id.specialtyAddToOrderButton).setEnabled(true);
    }

    private void handleQuantityButtonAction() {

    }
}
