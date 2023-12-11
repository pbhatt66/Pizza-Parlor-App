package com.example.rupizza;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailSpecialtyActivity extends AppCompatActivity {
    ImageView pageImageView;
    TextView pagePizzaTitle, pagePizzaToppings, pagePizzaSauce, priceTextView;

    CheckBox extraCheeseCheckBox, extraSauceCheckBox;

    Button addToOrderButton;

    String pizzaName, pizzaToppings, pizzaSauce;
    int pizzaImage;


    StoreOrders storeOrders = StoreOrders.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialty_pizzas);

        pageImageView = findViewById(R.id.pageImageView);
        pagePizzaTitle = findViewById(R.id.pagePizzaTitle);
        pagePizzaSauce = findViewById(R.id.pagePizzaSauce);
        pagePizzaToppings = findViewById(R.id.pagePizzaToppings);

        Button back_to_main = findViewById(R.id.backtomain_button);
        back_to_main.setOnClickListener(v -> openMainActivity());

        // handleRadioButtonSelection();

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
        }
        extraCheeseCheckBox = findViewById(R.id.specialty_extra_cheese);
        if (extraCheeseCheckBox.isChecked()) {
            pizza.addExtraCheese();
        }
        extraSauceCheckBox = findViewById(R.id.specialty_extra_sauce);
        if (extraSauceCheckBox.isChecked()) {
            pizza.addExtraSauce();
        }
        priceTextView = findViewById(R.id.specialtyPizzaPrice);
        priceTextView.setText(String.format("$%.2f", pizza.price()));
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
        Order currentOrder = storeOrders.getCurrentOrder();
        if (currentOrder != null) {
            currentOrder.addToOrder(pizza);
        }
        else {
            storeOrders.startNewOrder();
            storeOrders.getCurrentOrder().addToOrder(pizza);
        }
        Toast.makeText(this, "Pizza added to order", Toast.LENGTH_SHORT).show();
        // return to main activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

//    private void handleRadioButtonSelection() {
//        RadioGroup radioGroup = findViewById(R.id.size_radio_group);
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
//                RadioButton selectedRadioButton = (RadioButton) findViewById(checkedId);
//                String selectedSize = selectedRadioButton.getText().toString();
//                switch (selectedSize) {
//                    case "Small" -> size = Size.SMALL;
//                    case "Medium" -> size = Size.MEDIUM;
//                    case "Large" -> size = Size.LARGE;
//                }
//                // enable the add to cart button if a size is selected
//                findViewById(R.id.addToOrderButton).setEnabled(true);
//            }
//        });
//
//    }

    private void keyReleasedProperty() {
        findViewById(R.id.specialtyAddToOrderButton).setEnabled(true);
    }
}
