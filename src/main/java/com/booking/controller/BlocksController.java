package com.booking.controller;

import com.booking.core.BlockRepository;
import com.booking.core.BookingData.Block;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/blocks")
public class BlocksController {

    private final BlockRepository blockRepository;

    @Autowired
    public BlocksController(BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }

    @PostMapping
    public ResponseEntity<?> createBlock(@RequestBody @Valid Block block) {
        blockRepository.createBlock(block);
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(block.id())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Block> updateBlock(@PathVariable int id, @RequestBody @Valid Block block) {
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