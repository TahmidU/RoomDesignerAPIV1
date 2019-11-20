package com.aarrd.room_designer.item.statistic.view;

import com.aarrd.room_designer.item.Item;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "item_view")
@Data
public class ItemView
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "view_id")
    private long viewId;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public ItemView(){}

    public ItemView(Date date) {
        this.date = date;
    }
}
