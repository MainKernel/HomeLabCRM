package com.homelab.suit.inventory.repository;

import com.homelab.suit.inventory.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ItemPagingRepository extends PagingAndSortingRepository<Item, Long> {
    Page<Item> findByWorkspaceId(Long workspaceId, Pageable pageable);

    // TODO Зробити Application-layer валідацію створення локацій по locationID
    @Query(
            value = "SELECT i.* FROM items i " +
                    // Джойнимо локацію, враховуючи воркспейс
                    "LEFT JOIN locations l ON i.location_id = l.id AND l.workspace_id = :wsId " +
                    "WHERE i.workspace_id = :wsId AND " +
                    "( " +
                    "   i.name ILIKE '%' || :search || '%' OR " +
                    "   :search <% i.name OR " +
                    "   l.name ILIKE '%' || :search || '%' " + // Тепер можна нечітко шукати й по локації!
                    ")",

            countQuery = "SELECT count(i.id) FROM items i " +
                    "LEFT JOIN locations l ON i.location_id = l.id AND l.workspace_id = :wsId " +
                    "WHERE i.workspace_id = :wsId AND " +
                    "( " +
                    "   i.name ILIKE '%' || :search || '%' OR " +
                    "   :search <% i.name OR " +
                    "   l.name ILIKE '%' || :search || '%' " +
                    ")",

            nativeQuery = true
    )
    Page<Item> findFuzzy(
            @Param("wsId") Long wsId,
            @Param("search") String search,
            Pageable pageable
    );
}
