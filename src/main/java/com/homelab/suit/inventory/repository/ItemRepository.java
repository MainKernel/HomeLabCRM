package com.homelab.suit.inventory.repository;


import com.homelab.suit.inventory.model.Item;
import com.homelab.suit.inventory.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByLocationId(Long locationId);

    // Шукаємо компонент за артикулом в конкретній локації
    Optional<Item> findByLocationAndSku(Location location, String sku);
}