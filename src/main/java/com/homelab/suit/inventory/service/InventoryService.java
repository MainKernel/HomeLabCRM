package com.homelab.suit.inventory.service;

import com.homelab.suit.inventory.model.*;
import com.homelab.suit.inventory.repository.ItemPagingRepository;
import com.homelab.suit.inventory.repository.ItemRepository;
import com.homelab.suit.inventory.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final ItemRepository itemRepository;
    private final LocationRepository locationRepository;

}