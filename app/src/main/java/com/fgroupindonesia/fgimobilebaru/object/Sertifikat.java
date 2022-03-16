package com.fgroupindonesia.fgimobilebaru.object;

public class Sertifikat {

    private int exam_category_id;
    private String exam_category_title;
    private int status;
    private String filename;
    private String url;
    private String exam_date_created;

    public int getExam_category_id() {
        return exam_category_id;
    }

    public void setExam_category_id(int exam_category_id) {
        this.exam_category_id = exam_category_id;
    }

    public String getExam_category_title() {
        return exam_category_title;
    }

    public void setExam_category_title(String exam_category_title) {
        this.exam_category_title = exam_category_title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getExam_date_created() {
        return exam_date_created;
    }

    public void setExam_date_created(String exam_date_created) {
        this.exam_date_created = exam_date_created;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
