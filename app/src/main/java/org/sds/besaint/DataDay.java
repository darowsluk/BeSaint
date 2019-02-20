package org.sds.besaint;

public class DataDay {
    private int Id, JourneyId;
    private String Title, Day, Inspiration;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getJourneyId() {
        return JourneyId;
    }

    public void setJourneyId(int journeyId) {
        JourneyId = journeyId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getInspiration() {
        return Inspiration;
    }

    public void setInspiration(String inspiration) {
        Inspiration = inspiration;
    }
}
