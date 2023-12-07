package com.example.rupizza;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.specialtypizzas_button);
        button1.setOnClickListener(v -> openSpecialtyPizzasActivity());

        Button button2 = (Button) findViewById(R.id.buildyourown_button);
        button2.setOnClickListener(v -> openBuildYourOwnActivity());

        Button button3 = (Button) findViewById(R.id.currentorder_button);
        button3.setOnClickListener(v -> openCurrentOrderActivity());

        Button button4 = (Button) findViewById(R.id.storeorders_button);
        button4.setOnClickListener(v -> openStoreOrdersActivity());
    }

    public void openSpecialtyPizzasActivity() {
        Intent intent = new Intent(this, SpecialtyPizzasActivity.class);
        startActivity(intent);
    }
    public void openBuildYourOwnActivity() {
        Intent intent = new Intent(this, BuildYourOwnActivity.class);
        startActivity(intent);
    }
    public void openCurrentOrderActivity() {
        Intent intent = new Intent(this, CurrentOrderActivity.class);
        startActivity(intent);
    }
    public void openStoreOrdersActivity() {
        Intent intent = new Intent(this, StoreOrdersActivity.class);
        startActivity(intent);
    }
}