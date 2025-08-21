package com.darwinruiz.shoplite.repositories;

import com.darwinruiz.shoplite.models.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepository {

    public boolean save(Product product) {
        String sql = "INSERT INTO products (product_name, price, imageUrl, stock) VALUES (?, ?, ?, ?)";

        try (java.sql.Connection conn = com.darwinruiz.shoplite.databaseshoplite.ConnectionDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setString(3, product.getImageUrl() != null ? product.getImageUrl() : "/imagenes/default.png");
            stmt.setInt(4, product.getStock());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                // Obtener el ID generado y asignarlo al producto
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    product.setId(generatedKeys.getLong(1));
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al guardar producto: " + e.getMessage());
        }

        return false;
    }

    public Optional<Product> findById(long id) {
        String sql = "SELECT id, product_name, price, imageUrl, stock FROM products WHERE id = ?";

        try (java.sql.Connection conn = com.darwinruiz.shoplite.databaseshoplite.ConnectionDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Product product = new Product(
                        rs.getLong("id"),
                        rs.getString("product_name"),
                        rs.getDouble("price"),
                        rs.getString("imageUrl"),
                        rs.getInt("stock"));
                return Optional.of(product);
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar producto por ID: " + e.getMessage());
        }

        return Optional.empty();
    }

    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT id, product_name, price, imageUrl, stock FROM products ORDER BY product_name";

        try (java.sql.Connection conn = com.darwinruiz.shoplite.databaseshoplite.ConnectionDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getLong("id"),
                        rs.getString("product_name"),
                        rs.getDouble("price"),
                        rs.getString("imageUrl"),
                        rs.getInt("stock"));
                products.add(product);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener todos los productos: " + e.getMessage());
        }

        return products;
    }

    public boolean update(Product product) {
        String sql = "UPDATE products SET product_name = ?, price = ?, imageUrl = ?, stock = ? WHERE id = ?";

        try (java.sql.Connection conn = com.darwinruiz.shoplite.databaseshoplite.ConnectionDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setString(3, product.getImageUrl());
            stmt.setInt(4, product.getStock());
            stmt.setLong(5, product.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteById(long id) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (java.sql.Connection conn = com.darwinruiz.shoplite.databaseshoplite.ConnectionDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }

    public List<Product> findByCategory(String category) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT id, product_name, price, imageUrl, stock FROM products WHERE category = ? ORDER BY product_name";

        try (java.sql.Connection conn = com.darwinruiz.shoplite.databaseshoplite.ConnectionDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                        rs.getLong("id"),
                        rs.getString("product_name"),
                        rs.getDouble("price"),
                        rs.getString("imageUrl"),
                        rs.getInt("stock"));
                products.add(product);
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar productos por categoría: " + e.getMessage());
        }

        return products;
    }

    public List<Product> findByPriceRange(double minPrice, double maxPrice) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT id, product_name, price, imageUrl, stock FROM products WHERE price BETWEEN ? AND ? ORDER BY price";

        try (java.sql.Connection conn = com.darwinruiz.shoplite.databaseshoplite.ConnectionDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, minPrice);
            stmt.setDouble(2, maxPrice);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                        rs.getLong("id"),
                        rs.getString("product_name"),
                        rs.getDouble("price"),
                        rs.getString("imageUrl"),
                        rs.getInt("stock"));
                products.add(product);
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar productos por rango de precio: " + e.getMessage());
        }

        return products;
    }
}