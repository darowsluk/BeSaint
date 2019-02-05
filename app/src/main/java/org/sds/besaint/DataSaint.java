package org.sds.besaint;

public class DataSaint {
    private int Id;
    private String Name, FullName, Title;
    private byte[] image100, image200;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public byte[] getImage100() {
        return image100;
    }

    public void setImage100(byte[] image100) {
        this.image100 = image100;
    }

    public byte[] getImage200() {
        return image200;
    }

    public void setImage200(byte[] image200) {
        this.image200 = image200;
    }
}
