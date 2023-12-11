package com.example.rupizza;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

public class SpecialtyPizzasActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    String[] names, toppings, sauces;
    int[] images = {R.drawable.challengepizza, R.drawable.cheesesteakpizza, R.drawable.deluxepizza,
            R.drawable.hawaiianpizza, R.drawable.meatzzapizza, R.drawable.pepperonipizza, R.drawable.seafoodpizza,
            R.drawable.supremepizza, R.drawable.surfnturfpizza, R.drawable.vegetarianpizza};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialty_pizza_recycler);

        recyclerView = findViewById(R.id.newRecyclerView);

        names = getResources().getStringArray(R.array.specialty_pizzas);
        toppings = getResources().getStringArray(R.array.toppings);
        sauces = getResources().getStringArray(R.array.sauces);

        SpecialtyPizzaAdapter specialtyPizzaAdapter = new SpecialtyPizzaAdapter(this, names, toppings, sauces, images);
        recyclerView.setAdapter(specialtyPizzaAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button back_to_main = findViewById(R.id.backToMainButton);
        back_to_main.setOnClickListener(v -> openMainActivity());
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}