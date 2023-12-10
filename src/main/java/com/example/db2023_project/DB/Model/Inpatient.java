package com.example.db2023_project.DB.Model;

public class Inpatient {
    private Long personId;
    private Long inpatientId;
    private String name;
//    private String residentNumber;
//    private boolean gender;
//    private String address;
//    private String phoneNumber;
//    private String guardingNumber;
    private Long diseaseId;
    private Long secId;
    private Long roomId;
    private boolean hgrisk;
//    private Date startDate;
//    private Date endDate;


    public Inpatient(Long personId, Long inpatientId, String name, Long diseaseId, Long secId, Long roomId, boolean hgrisk) {
        this.personId = personId;
        this.inpatientId = inpatientId;
        this.name = name;
        this.diseaseId = diseaseId;
        this.secId = secId;
        this.roomId = roomId;
        this.hgrisk = hgrisk;
    }


    public Long getPersonId() {
        return personId;
    }

    public Long getInpatientId() {
        return inpatientId;
    }

    public String getName() {
        return name;
    }

    public Long getDiseaseId() {
        return diseaseId;
    }

    public Long getSecId() {
        return secId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public boolean isHgrisk() {
        return hgrisk;
    }
}
