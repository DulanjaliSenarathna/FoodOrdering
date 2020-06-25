package com.example.dul.foododering.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dul.foododering.Common.Common;
import com.example.dul.foododering.FoodDetail;
import com.example.dul.foododering.Interface.ItemClickListener;
import com.example.dul.foododering.Model.Favourites;
import com.example.dul.foododering.Model.Order;
import com.example.dul.foododering.R;
import com.example.dul.foododering.databases.Database;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesViewHolder> {

    private Context context;
    private List<Favourites> favouritesList;

    public FavouritesAdapter(Context context, List<Favourites> favouritesList) {
        this.context = context;
        this.favouritesList = favouritesList;
    }

    @NonNull
    @Override
    public FavouritesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.favourites_item,viewGroup,false);

        return new FavouritesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritesViewHolder viewHolder, final int position) {

        viewHolder.food_name.setText(favouritesList.get(position).getFoodName());
        viewHolder.food_price.setText(String.format("Rs %s",favouritesList.get(position).getFoodPrice().toString()));
        Picasso.with(context).load(favouritesList.get(position).getFoodImage())
                .into(viewHolder.food_image);



        viewHolder.quick_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean isExists = new Database(context).checkFoodExists(favouritesList.get(position).getFoodId(), Common.currentUser.getPhone());

                if (!isExists) {
                    new Database(context).addToCart(new Order(
                            Common.currentUser.getPhone(),
                            favouritesList.get(position).getFoodId(),
                            favouritesList.get(position).getFoodName(),
                            "1",
                            favouritesList.get(position).getFoodPrice(),
                            favouritesList.get(position).getFoodDiscount(),
                            favouritesList.get(position).getFoodDescription()

                    ));


                } else {

                    new Database(context).increaseCart(Common.currentUser.getPhone(), favouritesList.get(position).getFoodId());


                }

                Toast.makeText(context, "Added to Cart", Toast.LENGTH_SHORT).show();


            }
        });






        final Favourites local = favouritesList.get(position);
        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent foodDetail = new Intent(context,FoodDetail.class);
                foodDetail.putExtra("FoodId",favouritesList.get(position).getFoodId());
                context.startActivity(foodDetail);
            }
        });



    }

    @Override
    public int getItemCount() {
        return favouritesList.size();
    }

    public void removeItem (int position)
    {
        favouritesList.remove(position);
        notifyItemRemoved(position);
    }
    public void restoreItem (Favourites item,int position)
    {
        favouritesList.add(position,item);
        notifyItemInserted(position);
    }

    public Favourites getItem(int position)
    {
        return favouritesList.get(position);
    }
}
