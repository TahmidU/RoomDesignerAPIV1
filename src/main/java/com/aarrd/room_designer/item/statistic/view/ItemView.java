package com.aarrd.room_designer.item.statistic.view;

import com.aarrd.room_designer.item.Item;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "item_view")
@Data
public class ItemView
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "view_id", nullable = false)
    private Long viewId;

    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    public ItemView(){}

    public ItemView(Date date) {
        this.date = date;
    }

    public ItemView(Date date, Item item)
    {
        this.date = date;
        this.item = item;
    }
}
