package com.homelab.suit.inventory.repository;

import com.homelab.suit.inventory.model.ItemDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemDocumentRepository extends JpaRepository<ItemDocument, Long> {
    List<ItemDocument> findAllByItemId(Long itemId);
}
