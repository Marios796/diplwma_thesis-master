package com.diploma.thesis.data;

import javax.persistence.*;
//αναπτυξη της κλασης pojo(setters,getters)



@Entity
public class DummyEntity {
    @Id  //tags της hibernate
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String keyword;

    private double score;

    private double id;


    private String text;

    private double x;

    private double y;

    private String address;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getText() {
        return text;
    }

    public void setText(String name) {
        this.text = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String country) {
        this.keyword = country;
    }







}


