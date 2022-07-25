package com.gustavovillada.icesistappsoma.model;

import java.io.Serializable;

public class Product implements Serializable {


    private String photoEmprendimiento, nameEmprendimiento, photoProduct,idEmprendimiento,name,description,id;
    private int price;
    private Float qualification;
    private boolean isActive,isAvailable;


    public Product(String photoEmprendimiento, String nameEmprendimiento, String photoProduct, String idEmprendimiento, String name, String description, String id, int price, Float qualification, boolean isActive, boolean isAvailable) {
        this.photoEmprendimiento = photoEmprendimiento;
        this.nameEmprendimiento = nameEmprendimiento;
        this.photoProduct = photoProduct;
        this.idEmprendimiento = idEmprendimiento;
        this.name = name;
        this.description = description;
        this.id = id;
        this.price = price;
        this.qualification = qualification;
        this.isActive = isActive;
        this.isAvailable = isAvailable;
    }

    public Product(){

    }

    public String getPhotoEmprendimiento() {
        return photoEmprendimiento;
    }

    public void setPhotoEmprendimiento(String photoEmprendimiento) {
        this.photoEmprendimiento = photoEmprendimiento;
    }

    public String getNameEmprendimiento() {
        return nameEmprendimiento;
    }

    public void setNameEmprendimiento(String nameEmprendimiento) {
        this.nameEmprendimiento = nameEmprendimiento;
    }

    public String getPhotoProduct() {
        return photoProduct;
    }

    public void setPhotoProduct(String photoProduct) {
        this.photoProduct = photoProduct;
    }

    public String getIdEmprendimiento() {
        return idEmprendimiento;
    }

    public void setIdEmprendimiento(String idEmprendimiento) {
        this.idEmprendimiento = idEmprendimiento;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Float getQualification() {
        return qualification;
    }

    public void setQualification(Float qualification) {
        this.qualification = qualification;
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
