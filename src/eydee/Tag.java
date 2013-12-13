package eydee;

public class Tag {
	private String artist;
	
	private String title;
	
	private String album;
	
	private int year;

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "Tag [album=" + album + ", artist=" + artist + ", title="
				+ title + ", year=" + year + "]";
	}
	
	
}
