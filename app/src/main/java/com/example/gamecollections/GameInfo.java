package com.example.gamecollections;

public class GameInfo {
    private String gameId;
    private String gameName;
    private String releaseYear;
    private String genre;

    public GameInfo() {
    }

    public GameInfo(String gameId, String gameName, String releaseYear, String genre) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
