package com.c201.aebook.api.painting.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.c201.aebook.api.painting.persistence.entity.PaintingEntity;

@Repository
public interface PaintingRepository extends JpaRepository<PaintingEntity, Long> {
}
