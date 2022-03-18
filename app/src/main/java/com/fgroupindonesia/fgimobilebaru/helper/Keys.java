package com.fgroupindonesia.fgimobilebaru.helper;

public class Keys {

   public static final int ACT_KELAS = 2,
            ACT_OPTIONS = 3,
            ACT_HISTORY = 4,
            ACT_TAGIHAN = 5,
            ACT_USER_PROFILE = 6,
            ACT_DESKTOP = 7,
            ACT_ABSENSI = 8,
            ACT_DOCUMENT = 10,
            ACT_SERTIFIKASI = 11;

   public static final String STATUS_WARN_ATTENDANCE = "Attendance Problem!",
           STATUS_WARN_EXERCISE = "Exercise Problem!",
           STATUS_WARN_PAYMENT = "Payment Problem!";

   public final static int ACT_CAMERA_BILL = 1, ACT_GALLERY_BILL = 2, // for 3secs
   PERIOD_OF_TIME_BILL = 3 * 1000, PERIOD_OF_TIME_WAIT_GENERAL = PERIOD_OF_TIME_BILL+1000,
   ACT_PICK_PICTURE = 4, TIME_OUT_WAIT_WEB_REQUEST = 9000,
           STATUS_RATE_NORMAL = 1, STATUS_RATE_CONFUSED = 0, STATUS_RATE_EXCITED = 2,
   LAYOUT_LOADING = 1, LAYOUT_KELAS_AKTIF = 2, LAYOUT_KELAS_BELUM_AKTIF = 3,
           LAYOUT_KELAS_RATING = 4;

   public static String WARNING_STATUS = "warning_status", USERNAME = "username", PASSWORD = "password",
           EMAIL = "email", MOBILE_PHONE = "mobile_phone", ADDRESS = "address",
           TEAMVIEWER_ID = "teamviewer_id", TEAMVIEWER_PASSWORD = "teamviewer_password",
           NOTIF_KELAS = "notif_kelas", NOTIF_PAYMENT = "notif_payment",
           NOTIF_KELAS_AUDIO = "notif_kelas_audio", NOTIF_PAYMENT_AUDIO = "notif_payment_audio",
           NOTIF_KELAS_LAST_CHECKED = "notif_kelas_last_checked",
           ALL_SCHEDULES = "all_schedules",
           TOKEN = "token", NOTIF_UPDATES = "notif_updates",
            AUTO_LOGOUT = "auto_logout", CLICK_SOUNDS = "click_sounds",
           MIN_TO_GO = "min_to_go", CLASS_REGISTERED = "class_registered",
           SCHEDULE = "schedule", LAST_SIGNATURE_DATE = "last_signature_date",
           LAST_SIGNATURE_DATETIME = "last_signature_date_time",
           LAST_SIGNATURE_STATUS="last_signature_status",
   FILE_PDF_TARGET = "file_pdf_target";


}
