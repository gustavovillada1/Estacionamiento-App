package com.gustavovillada.icesistappsoma.model;

import java.io.Serializable;

public class Modification implements Serializable {
    public static int MODIFICATION_TYPE_ADD_PRODUCT=10;
    public static int MODIFICATION_TYPE_MODIFIED_PRODUCT=11;
    public static int MODIFICATION_TYPE_REMOVE_PRODUCT=12;
    public static int MODIFICATION_STATE_WAITING=0;
    public static int MODIFICATION_STATE_ACEPTED=1;
    public static int MODIFICATION_STATE_DENEGATED=2;

        private String idModification,photoEmprendimiento,nameEmprendimiento,photoProduct,idEmprendimiento,name,description, idProduct,message;
        private int price,stateModification,typeModification;
        private Float qualification;
        private boolean isAvailable,isActive;


        public Modification(){

        }

    public Modification(String idModification, int stateModification, int typeModification, String message, Product product) {

        this.idModification = idModification;
        this.stateModification = stateModification;
        this.typeModification = typeModification;
        this.message=message;

        this.photoEmprendimiento = product.getPhotoEmprendimiento();
        this.nameEmprendimiento = product.getNameEmprendimiento();
        this.photoProduct = product.getPhotoProduct();
        this.idEmprendimiento = product.getIdEmprendimiento();
        this.name = product.getName();
        this.description = product.getDescription();
        this.idProduct = product.getId();
        this.price = product.getPrice();
        this.qualification = product.getQualification();
        this.isAvailable = product.isAvailable();
        this.isActive = product.isActive();

    }

    public Product toProduct(){
            Product product= new Product(photoEmprendimiento,nameEmprendimiento,photoProduct,idEmprendimiento,name,description,idProduct,price,qualification,isActive,isAvailable);
            return product;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getIdProduct() {
        return idProduct;
    }

    public void setId(String id) {
        this.idProduct = id;
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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getIdModification() {
        return idModification;
    }

    public void setIdModification(String idModification) {
        this.idModification = idModification;
    }

    public int getStateModification() {
        return stateModification;
    }

    public void setStateModification(int stateModification) {
        this.stateModification = stateModification;
    }

    public int getTypeModification() {
        return typeModification;
    }

    public void setTypeModification(int typeModification) {
        this.typeModification = typeModification;
    }
}
