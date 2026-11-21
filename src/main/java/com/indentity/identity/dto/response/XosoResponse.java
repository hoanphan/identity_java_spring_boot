package com.indentity.identity.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class XosoResponse {
    String title;
    String date;
    String province;
    List<String> maDacBiet;
    String giaiDacBiet;
    List<String> giaiNhat;
    List<String> giaiNhi;
    List<String> giaiBa;
    List<String> giaiTu;
    List<String> giaiNam;
    List<String> giaiSau;
    List<String> giaiBay;
    Map<String, List<String>> allPrizes;

    public void printConsole() {
        System.out.println("========== KẾT QUẢ XSMB HÔM NAY ==========");
        System.out.println("Tiêu đề     : " + title);
        System.out.println("Ngày quay   : " + date + " (" + province + ")");
        System.out.println("Mã Đặc Biệt : " + maDacBiet);
        System.out.println("Giải ĐB     : " + giaiDacBiet);
        System.out.println("Giải Nhất   : " + (giaiNhat != null ? String.join(" - ", giaiNhat) : ""));
        System.out.println("Giải Nhì    : " + (giaiNhi != null ? String.join(" - ", giaiNhi) : ""));
        System.out.println("Giải Ba     : " + (giaiBa != null ? String.join(" - ", giaiBa) : ""));
        System.out.println("Giải Tư     : " + (giaiTu != null ? String.join(" - ", giaiTu) : ""));
        System.out.println("Giải Năm    : " + (giaiNam != null ? String.join(" - ", giaiNam) : ""));
        System.out.println("Giải Sáu    : " + (giaiSau != null ? String.join(" - ", giaiSau) : ""));
        System.out.println("Giải Bảy    : " + (giaiBay != null ? String.join(" - ", giaiBay) : ""));
        System.out.println("==========================================");
    }
}
