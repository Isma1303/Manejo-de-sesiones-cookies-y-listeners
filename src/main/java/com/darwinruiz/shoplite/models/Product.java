package com.darwinruiz.shoplite.models;

public class Product {
    private long id;
    private String name;
    private double price;
    private String imageUrl;
    private int stock;

    // Constructor completo
    public Product(long id, String name, double price, String imageUrl, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.stock = stock;
    }

    // Constructor simplificado (para compatibilidad)

    // Constructor para nuevos productos
    public Product(String name, double price, String imageUrl, int stock) {
        this(0, name, price, imageUrl, stock);
    }

    // Getters
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getStock() {
        return stock;
    }

    // Setters
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

}