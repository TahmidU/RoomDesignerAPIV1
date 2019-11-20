package com.aarrd.room_designer.item.category;

import com.aarrd.room_designer.item.Item;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
@Data
public class Category
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cat_id")
    private long cat_id;

    @Column(name = "cat_name", unique = true)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Item> items;

    public Category(){}

    public Category(String name) {
        this.name = name;
    }
}
