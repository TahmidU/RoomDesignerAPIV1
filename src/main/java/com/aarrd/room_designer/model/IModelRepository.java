package com.aarrd.room_designer.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IModelRepository extends JpaRepository<Model, Long>
{
    @Query("FROM Model m WHERE m.item.itemId = ?1")
    Model findByItemId(Long itemId);
}
