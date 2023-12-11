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
    String[] pizzaNames, pizzaDescriptions;
    int[] pizzaImages;

    public SpecialtyPizzaAdapter(Context ct, String[] pizzaName, String[] pizzaDescription, int[] pizzaImages) {
        // this.specialtyPizzas = specialtyPizzas;
        this.ct = ct;
        this.pizzaNames = pizzaName;
        this.pizzaDescriptions = pizzaDescription;
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
        holder.pizzaDescription.setText(pizzaDescriptions[position]);
        holder.pizzaImage.setImageResource(pizzaImages[position]);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ct, DetailSpecialtyActivity.class);
                intent.putExtra("pizzaName", pizzaNames[position]);
                intent.putExtra("pizzaDescription", pizzaDescriptions[position]);
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
        TextView pizzaName, pizzaDescription;
        ImageView pizzaImage;
        ConstraintLayout mainLayout;
        public SpecialtyPizzaViewHolder(View itemView) {
            super(itemView);
            pizzaName = itemView.findViewById(R.id.pizzaName);
            pizzaDescription = itemView.findViewById(R.id.pizzaToppings);
            pizzaImage = itemView.findViewById(R.id.pizzaImageView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
