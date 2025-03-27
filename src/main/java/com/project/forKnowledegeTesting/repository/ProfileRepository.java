package com.project.forKnowledegeTesting.repository;

import com.project.forKnowledegeTesting.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
}
