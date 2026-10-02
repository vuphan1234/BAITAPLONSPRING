package vuphan.vn.thigk.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import vuphan.vn.thigk.model.DanhMuc;
import vuphan.vn.thigk.model.Mon;

@Service
public class OrganizationService {

    // Nguồn dữ liệu duy nhất
    private final List<DanhMuc> danhMucList = new ArrayList<>();
    private final AtomicLong monIdCounter = new AtomicLong(1);

    public OrganizationService() {
        // Dữ liệu mẫu cho danh mục
        danhMucList.add(new DanhMuc(101L, "Món chính"));
        danhMucList.add(new DanhMuc(102L, "Đồ uống"));
        danhMucList.add(new DanhMuc(103L, "Tráng miệng"));

        // Dữ liệu mẫu cho món
        createMon(new Mon(null, "Phở bò tái", 55000L, "Phở bò tái nạm, nước dùng hầm xương 12 tiếng", 101L));
        createMon(new Mon(null, "Cơm tấm sườn bì chả", 50000L, "Sườn nướng than, bì, chả trứng, nước mắm chua ngọt", 101L));
        createMon(new Mon(null, "Bún bò Huế", 50000L, "Bún bò giò heo, chả cua, vị cay nồng", 101L));
        createMon(new Mon(null, "Cà phê sữa đá", 25000L, "Cà phê phin pha với sữa đặc", 102L));
        createMon(new Mon(null, "Trà đào cam sả", 35000L, "Trà đào tươi, cam vàng và sả", 102L));
        createMon(new Mon(null, "Chè ba màu", 25000L, "Đậu xanh, đậu đỏ, thạch lá dứa và nước cốt dừa", 103L));
        createMon(new Mon(null, "Bánh flan", 20000L, "Bánh flan caramel mềm mịn", 103L));
    }

    // Helper: tìm danh mục theo ID
    private Optional<DanhMuc> findDanhMucById(Long danhMucId) {
        return danhMucList.stream()
                .filter(danhMuc -> danhMuc.getId().equals(danhMucId))
                .findFirst();
    }

    // --- CRUD MÓN ---

    // Lấy tất cả món của mọi danh mục
    public List<Mon> getAllMon() {
        List<Mon> allMon = new ArrayList<>();
        for (DanhMuc danhMuc : danhMucList) {
            allMon.addAll(danhMuc.getMons());
        }
        return allMon;
    }

    // Lấy một món theo ID
    public Optional<Mon> getMonById(Long monId) {
        for (DanhMuc danhMuc : danhMucList) {
            for (Mon mon : danhMuc.getMons()) {
                if (mon.getId().equals(monId)) {
                    return Optional.of(mon);
                }
            }
        }
        return Optional.empty();
    }

    // Thêm món vào danh mục tương ứng
    public Optional<Mon> createMon(Mon mon) {
        Optional<DanhMuc> danhMucOpt = findDanhMucById(mon.getDanhMucId());
        if (danhMucOpt.isEmpty()) {
            return Optional.empty(); // Không tìm thấy danh mục
        }

        mon.setId(monIdCounter.getAndIncrement());
        danhMucOpt.get().getMons().add(mon);
        return Optional.of(mon);
    }

    // Cập nhật món; nếu đổi danhMucId thì chuyển món sang danh mục mới
    public Optional<Mon> updateMon(Long id, Mon updatedMon) {
        // 1. Kiểm tra danh mục đích có tồn tại không
        Optional<DanhMuc> targetDanhMucOpt = findDanhMucById(updatedMon.getDanhMucId());
        if (targetDanhMucOpt.isEmpty()) {
            return Optional.empty();
        }

        // 2. Tìm món hiện tại và danh mục đang chứa nó
        for (DanhMuc danhMuc : danhMucList) {
            List<Mon> mons = danhMuc.getMons();
            for (int i = 0; i < mons.size(); i++) {
                Mon existing = mons.get(i);
                if (existing.getId().equals(id)) {

                    updatedMon.setId(id);

                    if (!existing.getDanhMucId().equals(updatedMon.getDanhMucId())) {
                        mons.remove(i); // Xóa khỏi danh mục cũ
                        targetDanhMucOpt.get().getMons().add(updatedMon); // Thêm vào danh mục mới
                    } else {
                        mons.set(i, updatedMon); // Cập nhật tại chỗ
                    }
                    return Optional.of(updatedMon);
                }
            }
        }
        return Optional.empty(); // Không tìm thấy món
    }

    // Xóa món khỏi danh mục chứa nó
    public boolean deleteMon(Long monId) {
        for (DanhMuc danhMuc : danhMucList) {
            if (danhMuc.getMons().removeIf(m -> m.getId().equals(monId))) {
                return true;
            }
        }
        return false;
    }

    // --- DANH MỤC ---

    public List<DanhMuc> getAllDanhMuc() {
        return new ArrayList<>(danhMucList);
    }

    public Optional<DanhMuc> getDanhMucWithMons(Long danhMucId) {
        return findDanhMucById(danhMucId);
    }
}