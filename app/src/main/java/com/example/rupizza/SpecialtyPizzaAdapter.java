package com.example.rupizza;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SpecialtyPizzaAdapter extends RecyclerView.Adapter<SpecialtyPizzaAdapter.SpecialtyPizzaViewHolder> {
    // private List<Pizza> specialtyPizzas;
    Context ct;
    String[] pizzaNames, pizzaToppings, pizzaSauces;
    int[] pizzaImages;

    public SpecialtyPizzaAdapter(Context ct, String[] pizzaName, String[] pizzaToppings, String[] pizzaSauces, int[] pizzaImages) {
        this.ct = ct;
        this.pizzaNames = pizzaName;
        this.pizzaToppings = pizzaToppings;
        this.pizzaSauces = pizzaSauces;
        this.pizzaImages = pizzaImages;
    }

    @NonNull
    @Override
    public SpecialtyPizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ct);
        View view = inflater.inflate(R.layout.recycler_layout, parent, false);
        return new SpecialtyPizzaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SpecialtyPizzaViewHolder holder, int position) {
       // Pizza specialityPizza = specialtyPizzas.get(position);
        holder.pizzaName.setText(pizzaNames[position]);
        holder.pizzaToppings.setText(pizzaToppings[position]);
        holder.pizzaSauce.setText(pizzaSauces[position]);
        holder.pizzaImage.setImageResource(pizzaImages[position]);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ct, DetailSpecialtyActivity.class);
                intent.putExtra("pizzaName", pizzaNames[position]);
                intent.putExtra("pizzaDescription", pizzaToppings[position]);
                intent.putExtra("pizzaSauce", pizzaSauces[position]);
                intent.putExtra("pizzaImage", pizzaImages[position]);
                ct.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pizzaNames.length;
    }

    public class SpecialtyPizzaViewHolder extends RecyclerView.ViewHolder {
        TextView pizzaName, pizzaToppings, pizzaSauce;
        ImageView pizzaImage;
        ConstraintLayout mainLayout;
        public SpecialtyPizzaViewHolder(View itemView) {
            super(itemView);
            pizzaName = itemView.findViewById(R.id.pizzaName);
            pizzaToppings = itemView.findViewById(R.id.pizzaToppings);
            pizzaSauce = itemView.findViewById(R.id.pizzaSauce);
            pizzaImage = itemView.findViewById(R.id.pizzaImageView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
