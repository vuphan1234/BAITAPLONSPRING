package vuphan.vn.thigk.model;

import java.util.ArrayList;
import java.util.List;

public class DanhMuc {
    private Long id;
    private String name;
    private List<Mon> mons = new ArrayList<>();

    public DanhMuc() {}

    public DanhMuc(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Mon> getMons() { return mons; }
    public void setMons(List<Mon> mons) { this.mons = mons; }
}