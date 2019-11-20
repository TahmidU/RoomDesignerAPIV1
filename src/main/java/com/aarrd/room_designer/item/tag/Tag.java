package com.aarrd.room_designer.item.tag;

import com.aarrd.room_designer.item.Item;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tag")
@Data
public class Tag
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private long tagId;

    @Column(name = "tag_name")
    private String tagName;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public Tag(){}

    public Tag(String tagName) {
        this.tagName = tagName;
    }
}
