package com.aarrd.room_designer.item.category;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "category")
@Data
public class Category
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cat_id")
    private long cat_id;

    @Column(name = "cat_name")
    private String name;

    public Category(){}

    public Category(long cat_id, String name) {
        this.cat_id = cat_id;
        this.name = name;
    }
}
