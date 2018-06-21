package com.solutions.oryc.consuma.Interfaces;

public interface ProductOnItemClickListener {
    void onEditClick(int position);
    void onDeleteClick(int position);
    void onAddToCartClick(int position);
    void onIncreaseQuantityClick(int position);
    void onDecreaseQuantityClick(int position);
}
