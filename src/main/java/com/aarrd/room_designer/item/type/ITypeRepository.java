package com.aarrd.room_designer.item.type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ITypeRepository extends JpaRepository<Type, Long>
{
    @Query("FROM Type WHERE typeName = ?1")
    Type findByName(String name);
}
