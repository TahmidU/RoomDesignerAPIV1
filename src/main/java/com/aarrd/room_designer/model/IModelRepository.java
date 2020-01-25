package com.aarrd.room_designer.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IModelRepository extends JpaRepository<Model, Long> {}
