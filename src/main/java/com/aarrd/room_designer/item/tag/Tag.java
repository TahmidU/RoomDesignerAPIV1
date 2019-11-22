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
    @Column(name = "tag_id", nullable = false)
    private Long tagId;

    @Column(name = "tag_name", nullable = false)
    private String tagName;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    public Tag(){}

    public Tag(String tagName) {
        this.tagName = tagName;
    }
}
