package at.Codefabrik.anlassManagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class Anlass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String beschreibung;
    private int maxTeilnehmer;
    @Lob
    private byte[] fileData;
    private String fileName;
    private String fileType;


    public Anlass (){}

    public Anlass(String title, String beschreibung, int maxTeilnehmer, byte[] fileData, String fileName, String fileType) {
        this.title = title;
        this.beschreibung = beschreibung;
        this.maxTeilnehmer = maxTeilnehmer;
        this.fileData = fileData;
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public Long getId() { return id; }
    public String getTitel() { return title; }
    public void setTitel(String titel) { this.title = titel; }
    public String getBeschreibung() { return beschreibung; }
    public void setBeschreibung(String beschreibung) { this.beschreibung = beschreibung; }
    public int getMaxTeilnehmer() { return maxTeilnehmer; }
    public void setMaxTeilnehmer(int maxTeilnehmer) { this.maxTeilnehmer = maxTeilnehmer; }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
