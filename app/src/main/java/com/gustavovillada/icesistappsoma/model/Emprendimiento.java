package com.gustavovillada.icesistappsoma.model;

import java.io.Serializable;

public class Emprendimiento implements Serializable {
    String name,sellerName,category,photoPerfil,photoCover,description,id,phone,instagram,facebook,email;
    boolean isActive,isAvailable;
    int orders,score;

    public Emprendimiento(){

    }

    public Emprendimiento(String name, String sellerName, String category, String photoPerfil, String photoCover, String description, String id, String phone, String instagram, String facebook, boolean isActive, boolean isAvailable, int orders, String email, int score) {
        this.name = name;
        this.sellerName = sellerName;
        this.category = category;
        this.photoPerfil = photoPerfil;
        this.photoCover = photoCover;
        this.description = description;
        this.id = id;
        this.phone = phone;
        this.instagram = instagram;
        this.facebook = facebook;
        this.isActive = isActive;
        this.isAvailable = isAvailable;
        this.orders=orders;
        this.email=email;
        this.score=score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPhotoPerfil() {
        return photoPerfil;
    }

    public void setPhotoPerfil(String photoPerfil) {
        this.photoPerfil = photoPerfil;
    }

    public String getPhotoCover() {
        return photoCover;
    }

    public void setPhotoCover(String photoCover) {
        this.photoCover = photoCover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
