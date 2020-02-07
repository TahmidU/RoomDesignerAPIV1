package com.aarrd.room_designer.model;

import com.aarrd.room_designer.item.Item;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "model")
@Data
public class Model
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_id", nullable = false)
    private Long modelId;

    @Column(name = "model_directory", unique = true, nullable = false)
    private String modelDirectory;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "itemId")
    @JsonIdentityReference(alwaysAsId = true)
    @OneToOne(optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    public Model(){}

    public Model(String modelDirectory) {
        this.modelDirectory = modelDirectory;
    }

    public Model(String modelDirectory, Item item) {
        this.modelDirectory = modelDirectory;
        this.item = item;
    }
}
