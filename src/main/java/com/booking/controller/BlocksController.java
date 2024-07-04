package com.booking.controller;

import com.booking.core.BlockRepository;
import com.booking.core.BookingData.Block;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blocks")
public class BlocksController {

    private final BlockRepository blockRepository;

    @Autowired
    public BlocksController(BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }

    @PostMapping
    public ResponseEntity<Void> createBlock(@RequestBody Block block) {
        blockRepository.createBlock(block);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Block> updateBlock(@PathVariable int id, @RequestBody Block block) {
        var updatedBlock = blockRepository.updateBlock(id, block);
        if (updatedBlock.isPresent()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Block> deleteBlock(@PathVariable int id) {
        var deletedBlock = blockRepository.deleteBlock(id);
        if (deletedBlock.isPresent()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}