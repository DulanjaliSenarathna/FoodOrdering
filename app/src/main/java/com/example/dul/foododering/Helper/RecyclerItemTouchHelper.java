package com.example.dul.foododering.Helper;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.dul.foododering.Interface.RecyclerItemTouchHelperListner;
import com.example.dul.foododering.ViewHolder.CartViewHolder;
import com.example.dul.foododering.ViewHolder.FavouritesViewHolder;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private RecyclerItemTouchHelperListner listner;

    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs, RecyclerItemTouchHelperListner listner) {
        super(dragDirs, swipeDirs);
        this.listner = listner;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(listner != null)
            listner.onSwiped(viewHolder,i,viewHolder.getAdapterPosition());

    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

        if(viewHolder instanceof CartViewHolder) {

            View foregroundView = ((CartViewHolder) viewHolder).view_background;
            getDefaultUIUtil().clearView(foregroundView);
        }

        else if(viewHolder instanceof FavouritesViewHolder)
        {
            View foregroundView = ((FavouritesViewHolder) viewHolder).view_background;
            getDefaultUIUtil().clearView(foregroundView);
        }

    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if(viewHolder instanceof CartViewHolder)
        {
            View foregroundView = ((CartViewHolder)viewHolder).view_background;
            getDefaultUIUtil().onDraw(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
        }
        else if(viewHolder instanceof FavouritesViewHolder)
        {
            View foregroundView = ((FavouritesViewHolder)viewHolder).view_background;
            getDefaultUIUtil().onDraw(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);

        }

    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if(viewHolder !=null)
        {
            if(viewHolder instanceof CartViewHolder)
            {

                View foregroundView = ((CartViewHolder)viewHolder).view_background;
                getDefaultUIUtil().onSelected(foregroundView);
            }
            else if (viewHolder instanceof FavouritesViewHolder)
            {

                View foregroundView = ((FavouritesViewHolder)viewHolder).view_background;
                getDefaultUIUtil().onSelected(foregroundView);
            }

        }
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (viewHolder instanceof CartViewHolder) {
            View foregroundView = ((CartViewHolder)viewHolder).view_foreground;
            getDefaultUIUtil().onDrawOver(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
        }
        else if(viewHolder instanceof FavouritesViewHolder)
        {
            View foregroundView = ((FavouritesViewHolder)viewHolder).view_foreground;
            getDefaultUIUtil().onDrawOver(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
        }

    }
}
