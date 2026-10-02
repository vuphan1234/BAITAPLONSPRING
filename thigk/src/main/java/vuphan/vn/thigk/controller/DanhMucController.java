package vuphan.vn.thigk.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vuphan.vn.thigk.model.DanhMuc;
import vuphan.vn.thigk.service.OrganizationService;

@RestController
@RequestMapping("/api/danhmucs")
public class DanhMucController {

    private final OrganizationService service;

    public DanhMucController(OrganizationService service) {
        this.service = service;
    }

    @GetMapping
    public List<DanhMuc> getAllDanhMuc() {
        return service.getAllDanhMuc();
    }

    @GetMapping("/{id}/mons")
    public ResponseEntity<DanhMuc> getDanhMucMons(@PathVariable Long id) {
        return service.getDanhMucWithMons(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}