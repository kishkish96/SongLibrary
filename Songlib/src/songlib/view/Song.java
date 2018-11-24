/*
 * Group #: 79
 * 
 * Kishan Patel (kp644)
 * Neal D Patel (ndp73)
 * 
 */

package songlib.view;

public class Song {
	
	private String song;
	private String artist;
	private String album;
	private String year;
	
	public Song(String song, String artist, String album, String year) {
		this.song = song;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}

	public String getSong() {
		return song;
	}

	public void setSong(String song) {
		this.song = song;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
}
