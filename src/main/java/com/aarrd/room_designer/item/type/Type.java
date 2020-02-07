package com.aarrd.room_designer.item.type;

import com.aarrd.room_designer.item.Item;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "type")
@Data
public class Type
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id", nullable = false)
    private Long typeId;

    @Column(name = "type_name", unique = true, nullable = false)
    private String typeName;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "itemId")
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "type")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    private List<Item> items;

    public Type(){}

    public Type(String typeName) {
        this.typeName = typeName;
    }
}
