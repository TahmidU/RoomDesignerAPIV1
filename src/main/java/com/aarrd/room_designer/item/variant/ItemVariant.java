package com.aarrd.room_designer.item.variant;

import lombok.Data;
import org.hibernate.validator.constraints.br.TituloEleitoral;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "item_variant")
@Data
public class ItemVariant
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "variant_id")
    private long variantId;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_created")
    private Date dateCreated;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_modified")
    private Date dateModified;

    public ItemVariant(){}

    public ItemVariant(long variantId, Date dateCreated, Date dateModified) {
        this.variantId = variantId;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }
}
