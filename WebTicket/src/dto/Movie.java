package dto;

import java.io.Serializable;

public class Movie implements Serializable {

private static final long serialVersionUID = -4274700572038677000L;

	private String movieId; 	// 영화 id
	private String title;  		// 영화 제목
	private int price;		// 영화 가격
	private String description;	// 영화 설명
	private String manufacturer;	// 제작사
	private String genre;		// 장르 
	private int remainingSeats;	// 남은 좌석 수 
	private int totalSeats;		// 총 좌석 수
	private String supervisor;	// 감독
	private String filename; // 영화 이미지
	
	
	public Movie() {
		super();
	}
	
	public Movie(String movieId, String title, int price) {
		this.movieId = movieId;
		this.title = title;
		this.price = price;
	}
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getMovieId() {
		return movieId;
	    }
		
	public void setMovieId(String movieId) {
	this.movieId = movieId;
	}
	
	public String getTitle() {
	return title;
	}
	
	public void setTitle(String title) {
	this.title = title;
	}
	
	public int getPrice() {
	return price;
	}
	
	public void setPrice(int price) {
	this.price = price;
	}
	
	public String getDescription() {
	return description;
	}
	
	public void setDescription(String description) {
	this.description = description;
	}
	
	public String getManufacturer() {
	return manufacturer;
	}
	
	public void setManufacturer(String manufacturer) {
	this.manufacturer = manufacturer;
	}
	
	public String getGenre() {
	return genre;
	}
	
	public void setGenre(String genre) {
	this.genre = genre;
	}
	
	public int getRemainingSeats() {
	return remainingSeats;
	}
	
	public void setRemainingSeats(int remainingSeats) {
	this.remainingSeats = remainingSeats;
	}
	
	public int getTotalSeats() {
	return totalSeats;
	}
	
	public void setTotalSeats(int totalSeats) {
	this.totalSeats = totalSeats;
	}
	
	public String getSupervisor() {
	return supervisor;
	}
	
	public void setSupervisor(String supervisor) {
	this.supervisor = supervisor;
	}
}
