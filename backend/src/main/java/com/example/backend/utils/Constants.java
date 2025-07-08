package com.example.backend.utils;

public class Constants {
    public enum Role {
        ADMIN(1, "Quản trị viên"),
        TEACHER(2, "Giáo viên"),
        STUDENT(3, "Học sinh");

        private final int id;
        private final String description;

        Role(int id, String description) {
            this.id = id;
            this.description = description;
        }

        public int getId() { return id; }
        public String getDescription() { return description; }
    }

    public enum ClassStatus {
        UPCOMING("Sắp khai giảng"),
        ACTIVE("Đang học"),
        INACTIVE("Tạm ngưng"),
        COMPLETED("Đã kết thúc");

        private final String description;

        ClassStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum DayOfWeekVN {
        T2("Thứ Hai", "Monday", 1),
        T3("Thứ Ba", "Tuesday", 2),
        T4("Thứ Tư", "Wednesday", 3),
        T5("Thứ Năm", "Thursday", 4),
        T6("Thứ Sáu", "Friday", 5),
        T7("Thứ Bảy", "Saturday", 6),
        CN("Chủ Nhật", "Sunday", 7);

        private final String vietnameseName;
        private final String englishName;
        private final int dayNumber;

        DayOfWeekVN(String vietnameseName, String englishName, int dayNumber) {
            this.vietnameseName = vietnameseName;
            this.englishName = englishName;
            this.dayNumber = dayNumber;
        }

        public String getVietnameseName() {
            return vietnameseName;
        }

        public String getEnglishName() {
            return englishName;
        }

        public int getDayNumber() {
            return dayNumber;
        }

        // Optional: Tìm theo số thứ trong tuần (1 = Monday, 7 = Sunday)
        public static DayOfWeekVN fromDayNumber(int dayNumber) {
            for (DayOfWeekVN day : DayOfWeekVN.values()) {
                if (day.getDayNumber() == dayNumber) {
                    return day;
                }
            }
            return null;
        }
    }

    public enum LearningMode {
        OFFLINE("Học trực tiếp"),
        ONLINE("Học trực tuyến"),
        HYBRID("Học kết hợp");

        private final String description;

        LearningMode(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        // Optional: Tìm theo mô tả (nếu cần)
        public static LearningMode fromDescription(String description) {
            for (LearningMode mode : LearningMode.values()) {
                if (mode.description.equalsIgnoreCase(description)) {
                    return mode;
                }
            }
            return null;
        }
    }

}
