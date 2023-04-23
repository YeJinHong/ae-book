package com.c201.aebook.api.story.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.c201.aebook.api.story.persistence.entity.StoryEntity;

@Repository
public interface StoryRepository extends JpaRepository<StoryEntity, Long> {

}
