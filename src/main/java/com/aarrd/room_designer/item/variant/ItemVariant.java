package com.aarrd.room_designer.item.variant;

import com.aarrd.room_designer.item.Item;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.validator.constraints.br.TituloEleitoral;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "item_variant")
@Data
public class ItemVariant
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "variant_id", nullable = false)
    private Long variantId;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "itemId")
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "itemVariant")
    private List<Item> items;

    public ItemVariant(){}
}
