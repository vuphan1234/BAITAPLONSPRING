package vuphan.vn.thigk.model;

public class Mon {
    private Long id;
    private String name;        // Tên món
    private Long price;         // Giá (VNĐ)
    private String description; // Mô tả
    private Long danhMucId;     // Thuộc danh mục nào

    public Mon() {}

    public Mon(Long id, String name, Long price, String description, Long danhMucId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.danhMucId = danhMucId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getPrice() { return price; }
    public void setPrice(Long price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getDanhMucId() { return danhMucId; }
    public void setDanhMucId(Long danhMucId) { this.danhMucId = danhMucId; }
}