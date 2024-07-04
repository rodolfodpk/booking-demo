package com.booking.repository;

import com.booking.core.BlockRepository;
import com.booking.core.BookingData;
import com.booking.core.BookingData.Block;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Repository
public class InMemBlockRepository implements BlockRepository {

    private final Map<Integer, Set<Block>> blocksPerProperty = new HashMap<>();

    private final Comparator<Block> blockComparator = Comparator.comparing(Block::start)
            .thenComparing(Block::end);

    private Set<Block> getBlocks(int propertyId) {
        return blocksPerProperty.computeIfAbsent(propertyId, k -> new TreeSet<>(blockComparator));
    }

    @Override
    public void createBlock(Block block) {
        var propertyBlocks = getBlocks(block.property().id());
        propertyBlocks.add(block);
        blocksPerProperty.put(block.property().id(), propertyBlocks);
    }

    @Override
    public Optional<Block> updateBlock(int id, Block block) {
        var propertyBlocks = getBlocks(id);
        if (propertyBlocks.isEmpty()) {
            return Optional.empty();
        }
        var found = propertyBlocks.stream().filter(b -> b.id() == id).findFirst();
        found.ifPresent(it -> {
            propertyBlocks.add(block);
            blocksPerProperty.put(block.property().id(), propertyBlocks); // put it back into the main list
        });
        return found;
    }

    @Override
    public Optional<Block> deleteBlock(int id) {
        var propertyBlocks = getBlocks(id);
        if (propertyBlocks.isEmpty()) {
            return Optional.empty();
        }
        var found = propertyBlocks.stream().filter(b -> b.id() == id).findFirst();
        found.ifPresent(block -> {
            propertyBlocks.remove(block);
            blocksPerProperty.put(block.property().id(), propertyBlocks); // put it back into the main list
        });
        return found;
    }

    @Override
    public boolean isBlocked(BookingData.Booking booking) {
        var propertyBlocks = getBlocks(booking.property().id());
        return propertyBlocks.stream()
                .anyMatch(block -> booking.start().isBefore(block.end()) && booking.end().isAfter(block.start()));
    }
}
