package org.sds.besaint;

public class DataJourney {
    private int Id, JourneyUID, SaintId, Level, Days;
    private String Title, Author, Description;
    private byte[] Image;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getJourneyUID() {
        return JourneyUID;
    }

    public void setJourneyUID(int uid) {
        JourneyUID = uid;
    }

    public int getSaintId() {
        return SaintId;
    }

    public void setSaintId(int saintId) {
        SaintId = saintId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public int getDays() {
        return Days;
    }

    public void setDays(int days) {
        Days = days;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }
}
