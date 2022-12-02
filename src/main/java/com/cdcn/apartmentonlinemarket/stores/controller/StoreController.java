package com.cdcn.apartmentonlinemarket.stores.controller;

import com.cdcn.apartmentonlinemarket.stores.domain.dto.StoreDto;
import com.cdcn.apartmentonlinemarket.stores.domain.entity.Store;
import com.cdcn.apartmentonlinemarket.stores.service.StoreService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/stores")
@SecurityRequirement(name = "")
public class StoreController {
    @Autowired
    StoreService storeService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getStore(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(storeService.findById(id));
    }

    @PutMapping("{userid}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> editUser(@PathVariable("userid") String id, @Valid @RequestBody Store store) {
        return ResponseEntity.ok(storeService.updateStore(id, store));
    }

    @DeleteMapping("{userid}")
    public ResponseEntity<?> delete(@PathVariable("userid") String id) {
        StoreService.deleteByIdS(id);
        return ResponseEntity.status(200).body("Delete user id " + id + " successfully." );
    }
}
