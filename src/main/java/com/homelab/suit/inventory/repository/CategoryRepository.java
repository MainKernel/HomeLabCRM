package com.homelab.suit.inventory.repository;

import com.homelab.suit.inventory.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByNameAndWorkspaceId(String name, Long id);

    List<Category> findByWorkspaceId(Long id);
}
