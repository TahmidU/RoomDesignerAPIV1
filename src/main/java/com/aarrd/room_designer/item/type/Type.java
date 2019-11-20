package com.aarrd.room_designer.item.type;

import com.aarrd.room_designer.item.Item;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "type")
@Data
public class Type
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private long typeId;

    @Column(name = "type_name", unique = true)
    private String typeName;

    @OneToMany(mappedBy = "type")
    private List<Item> items;

    public Type(){}

    public Type(String typeName) {
        this.typeName = typeName;
    }
}
