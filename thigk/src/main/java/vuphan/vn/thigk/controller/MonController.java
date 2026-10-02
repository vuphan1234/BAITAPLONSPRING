package vuphan.vn.thigk.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vuphan.vn.thigk.model.Mon;
import vuphan.vn.thigk.service.OrganizationService;

@RestController
@RequestMapping("/api/mons")
public class MonController {

    private final OrganizationService service;

    public MonController(OrganizationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Mon> getAllMon() {
        return service.getAllMon();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mon> getMonById(@PathVariable Long id) {
        return service.getMonById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Mon> createMon(@RequestBody Mon mon) {
        return service.createMon(mon)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mon> updateMon(@PathVariable Long id, @RequestBody Mon mon) {
        return service.updateMon(id, mon)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMon(@PathVariable Long id) {
        if (service.deleteMon(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}