package com.darwinruiz.shoplite.repositories;

import com.darwinruiz.shoplite.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ProductRepository {
    private static final List<Product> DATA = new ArrayList<>();
    private static final AtomicLong ID = new AtomicLong(1000);

    static {
        DATA.add(new Product(ID.incrementAndGet(), "Teclado mecánico", 59.99, "/imagenes/teclado.png"));
        DATA.add(new Product(ID.incrementAndGet(), "Mouse inalámbrico", 29.90, "/imagenes/mouse.png"));
        DATA.add(new Product(ID.incrementAndGet(), "Monitor 24\"", 139.00, "/imagenes/monitor.png"));
        DATA.add(new Product(ID.incrementAndGet(), "Headset", 49.90, "/imagenes/headset.png"));
        DATA.add(new Product(ID.incrementAndGet(), "Webcam", 39.50, "/imagenes/webcam.png"));
    }

    public List<Product> findAll() {
        return DATA;
    }

    public void add(Product p) {
        DATA.add(p);
    }

    public long nextId() {
        return ID.incrementAndGet();
    }
}