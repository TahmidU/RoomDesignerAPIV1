package com.aarrd.room_designer.item.variant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IItemVariantRepository extends JpaRepository<ItemVariant, Long> {}
