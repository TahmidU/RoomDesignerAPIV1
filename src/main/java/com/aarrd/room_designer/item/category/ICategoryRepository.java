package com.aarrd.room_designer.item.category;

import com.aarrd.room_designer.item.type.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long>
{
    @Query("FROM Type WHERE name = ?1")
    Category findByName(String name);
}
