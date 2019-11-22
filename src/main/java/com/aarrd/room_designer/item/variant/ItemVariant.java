package com.aarrd.room_designer.item.variant;

import com.aarrd.room_designer.item.Item;
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

    @Temporal(TemporalType.DATE)
    @Column(name = "date_created", nullable = false)
    private Date dateCreated;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_modified", nullable = false)
    private Date dateModified;

    @OneToMany(mappedBy = "itemVariant")
    private List<Item> items;

    public ItemVariant(){}

    public ItemVariant(Date dateCreated, Date dateModified) {
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }
}
