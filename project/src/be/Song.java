package be;

public class Song {
    private final int id;
    private String title;
    private String artist;
    private String category;
    private int time;
    private String filePath;

    public Song(int id,String title, String artist, String category, int time, String filePath){
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.category = category;
        this.time = time;
        this.filePath = filePath;
    }


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return title + " by "+ artist+" from " +category;
    }

    public String getFilePath() {
        return filePath;
    }
}
