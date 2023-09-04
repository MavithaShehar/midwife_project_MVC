package lk.ijse.lastproject.dto.tm;

public class VaccReportTM {

    private String childrenId;
    private String name;
    private String bod;
    private String gender;
    private String vacId;
    private String vacName;
    private String description;
    private int noofDose;
    private int dose;
    private String date;
    private String age;

    public String getChildrenId() {
        return childrenId;
    }

    public void setChildrenId(String childrenId) {
        this.childrenId = childrenId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBod() {
        return bod;
    }

    public void setBod(String bod) {
        this.bod = bod;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getVacId() {
        return vacId;
    }

    public void setVacId(String vacId) {
        this.vacId = vacId;
    }

    public String getVacName() {
        return vacName;
    }

    public void setVacName(String vacName) {
        this.vacName = vacName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNoofDose() {
        return noofDose;
    }

    public void setNoofDose(int noofDose) {
        this.noofDose = noofDose;
    }

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public VaccReportTM(String childrenId, String name, String bod, String gender, String vacId, String vacName, String description, int noofDose, int dose, String date, String age) {
        this.childrenId = childrenId;
        this.name = name;
        this.bod = bod;
        this.gender = gender;
        this.vacId = vacId;
        this.vacName = vacName;
        this.description = description;
        this.noofDose = noofDose;
        this.dose = dose;
        this.date = date;
        this.age = age;
    }

    public VaccReportTM() {
    }
}