package com.example.rupizza;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class BuildYourOwnActivity extends AppCompatActivity {

    CheckBox extraCheeseCheckBox, extraSauceCheckBox;
    TextView priceTextView;

    Button addToOrderButton;

    StoreOrders storeOrders = StoreOrders.getInstance();

    List<String> toppings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_your_own);

        Button back_to_main = findViewById(R.id.backtomain_button);
        back_to_main.setOnClickListener(v -> openMainActivity());

        RadioGroup sizeRadioGroup = findViewById(R.id.customize_size_group);
        sizeRadioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            updatePrice();
            onKeyReleased();
        });

        RadioGroup sauceRadioGroup = findViewById(R.id.customize_sauce_group);
        sauceRadioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            onKeyReleased();
        });

        addToOrderButton = findViewById(R.id.customizeAddToOrderButton);
        addToOrderButton.setOnClickListener(v -> handleOrderButtonAction());

        extraCheeseCheckBox = findViewById(R.id.custom_extra_cheese);
        extraCheeseCheckBox.setOnCheckedChangeListener((compoundButton, b) -> updatePrice());

        extraSauceCheckBox = findViewById(R.id.custom_extra_sauce);
        extraSauceCheckBox.setOnCheckedChangeListener((compoundButton, b) -> updatePrice());

        ChipGroup toppingsChipGroup = findViewById(R.id.customize_toppings_group);
        for (int i = 0; i < toppingsChipGroup.getChildCount(); i++) {
            ((androidx.appcompat.widget.AppCompatCheckBox) toppingsChipGroup.getChildAt(i)).setOnCheckedChangeListener((compoundButton, b) -> {
                onCheckedChanged(toppingsChipGroup, compoundButton.getId());
                updatePrice();
            });
        }
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void updatePrice() {
        Pizza pizza = PizzaMaker.createPizza("Custom");
        RadioButton sizeRadioButton = (RadioButton) findViewById(R.id.customize_size_group).findViewById(((RadioGroup) findViewById(R.id.customize_size_group)).getCheckedRadioButtonId());
        if (sizeRadioButton != null) {
            String selectedSize = sizeRadioButton.getText().toString();
            Size tempSize = switch (selectedSize) {
                case "Small" -> Size.SMALL;
                case "Medium" -> Size.MEDIUM;
                case "Large" -> Size.LARGE;
                default -> null;
            };
            pizza.setSize(tempSize);
        }
        else {
            return;
        }
        extraCheeseCheckBox = findViewById(R.id.custom_extra_cheese);
        if (extraCheeseCheckBox.isChecked()) {
            pizza.addExtraCheese();
        }
        extraSauceCheckBox = findViewById(R.id.custom_extra_sauce);
        if (extraSauceCheckBox.isChecked()) {
            pizza.addExtraSauce();
        }

        ChipGroup toppingsChipGroup = findViewById(R.id.customize_toppings_group);
        for (int i = 0; i < toppingsChipGroup.getChildCount(); i++) {
            if (((androidx.appcompat.widget.AppCompatCheckBox) toppingsChipGroup.getChildAt(i)).isChecked()) {
                String topping = ((androidx.appcompat.widget.AppCompatCheckBox) toppingsChipGroup.getChildAt(i)).getText().toString();
                if (!pizza.toppings.contains(Topping.getTopping(topping))) {
                    System.out.println("Adding " + topping);
                    pizza.toppings.add(Topping.getTopping(topping));
                }
            }
        }
        priceTextView = findViewById(R.id.customPrice);
        priceTextView.setText(String.format("$%.2f", pizza.price()));

    }


    private void onCheckedChanged(ChipGroup chipGroup, int i) {
        Chip chip = chipGroup.findViewById(i);
        if (chip != null) {
            if (chip.isChecked()) {
                toppings.add(chip.getText().toString());
            } else {
                toppings.remove(chip.getText().toString());
            }
            if (toppings.size() < 7) {
                for (int j = 0; j < chipGroup.getChildCount(); j++) {
                    ((Chip) chipGroup.getChildAt(j)).setEnabled(true);
                }
            }
            else {
                for (int j = 0; j < chipGroup.getChildCount(); j++) {
                    if (!toppings.contains(((Chip) chipGroup.getChildAt(j)).getText().toString())) {
                        ((Chip) chipGroup.getChildAt(j)).setEnabled(false);
                    }
                }

            }
        }
    }

    private void onKeyReleased() {
        // check if size and sauce are selected
        RadioButton sizeRadioButton = (RadioButton) findViewById(R.id.customize_size_group).findViewById(((RadioGroup) findViewById(R.id.customize_size_group)).getCheckedRadioButtonId());
        RadioButton sauceRadioButton = (RadioButton) findViewById(R.id.customize_sauce_group).findViewById(((RadioGroup) findViewById(R.id.customize_sauce_group)).getCheckedRadioButtonId());
        // if both are selected, enable add to order button
        if (sizeRadioButton != null && sauceRadioButton != null) {
            Button addToOrderButton = findViewById(R.id.customizeAddToOrderButton);
            addToOrderButton.setEnabled(true);
        }
    }

    private void handleOrderButtonAction() {
        Pizza pizza = PizzaMaker.createPizza("Custom");
        // get size from radio button
        RadioButton selectedRadioButton = (RadioButton) findViewById(R.id.customize_size_group).findViewById(((RadioGroup) findViewById(R.id.customize_size_group)).getCheckedRadioButtonId());
        String selectedSize = selectedRadioButton.getText().toString();
        Size size = null;
        switch (selectedSize) {
            case "Small" -> size = Size.SMALL;
            case "Medium" -> size = Size.MEDIUM;
            case "Large" -> size = Size.LARGE;
        }
        pizza.setSize(size);
        // get sauce from radio button
        RadioButton selectedSauceRadioButton = (RadioButton) findViewById(R.id.customize_sauce_group).findViewById(((RadioGroup) findViewById(R.id.customize_sauce_group)).getCheckedRadioButtonId());
        String selectedSauce = selectedSauceRadioButton.getText().toString();
        Sauce sauce = null;
        switch (selectedSauce) {
            case "Tomato" -> sauce = Sauce.TOMATO;
            case "Alfredo" -> sauce = Sauce.ALFREDO;
        }
        pizza.setSauce(sauce);
        // check if extra cheese is selected
        extraCheeseCheckBox = findViewById(R.id.custom_extra_cheese);
        boolean extraCheese = extraCheeseCheckBox.isChecked();
        if (extraCheese) {
            pizza.addExtraCheese();
        }
        // check if extra sauce is selected
        extraSauceCheckBox = findViewById(R.id.custom_extra_sauce);
        boolean extraSauce = extraSauceCheckBox.isChecked();
        if (extraSauce) {
            pizza.addExtraSauce();
        }
        // if less than 3 toppings are selected, have a toast pop up alerting the user to select more toppings
        if (toppings.size() < 3) {
            Toast.makeText(this, "Please select at least 3 toppings", Toast.LENGTH_SHORT).show();
            return;
        }
        // get toppings from list
        for (String topping : toppings) {
            pizza.toppings.add(Topping.getTopping(topping));
        }
        // add pizza to order
        Order currentOrder = storeOrders.getCurrentOrder();
        if (currentOrder != null) {
            currentOrder.addToOrder(pizza);
        }
        else {
            storeOrders.startNewOrder();
            storeOrders.getCurrentOrder().addToOrder(pizza);
        }
        // Alert user that pizza was added to order
        Toast.makeText(this, "Pizza added to order", Toast.LENGTH_SHORT).show();
        // return to main activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}