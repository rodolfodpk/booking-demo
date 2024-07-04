package com.booking.core;

import com.booking.core.BookingData.Block;

import java.util.Optional;

public interface BlockRepository {

    void createBlock(Block block);

    Optional<Block> updateBlock(int id, Block block);

    Optional<Block> deleteBlock(int id);

    boolean isBlocked(BookingData.Booking booking);

}
