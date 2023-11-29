package dao;

import dto.Movie;
import java.util.ArrayList;

public class MovieRepository {
    private final ArrayList<Movie> listOfMovies = new ArrayList<>();
    private static final MovieRepository instance = new MovieRepository();

    public static MovieRepository getInstance() {
        return instance;
    }

    public MovieRepository() {
        Movie avatar = new Movie("P1234", "아바타", 12000);
        avatar.setDescription("유명한 영화1");
        avatar.setGenre("SF");
        avatar.setManufacturer("20세기 폭스");
        avatar.setTotalSeats(80);
        avatar.setRemainingSeats(20);
        avatar.setSupervisor("제임스 카메론");
        avatar.setFilename("avatar.jpg");

        Movie titanic = new Movie("P1235", "타이타닉", 13000);
        titanic.setDescription("유명한 영화2");
        titanic.setGenre("로맨스");
        titanic.setManufacturer("20세기 폭스");
        titanic.setTotalSeats(100);
        titanic.setRemainingSeats(15);
        titanic.setSupervisor("제임스 카메론");
        titanic.setFilename("titanic.jpg");

        Movie ironman = new Movie("P1236", "아이언맨", 12000);
        ironman.setDescription("유명한 영화3");
        ironman.setGenre("SF");
        ironman.setManufacturer("마블");
        ironman.setTotalSeats(120);
        ironman.setRemainingSeats(30);
        ironman.setSupervisor("셰인 블랙");
        ironman.setFilename("ironman.jpg");

        listOfMovies.add(avatar);
        listOfMovies.add(titanic);
        listOfMovies.add(ironman);
    }

    public ArrayList<Movie> getAllMovies() {
        return listOfMovies;
    }

    public Movie getMovieById(String movieId) {
        for (Movie m : listOfMovies)
            if (m.getMovieId().equals(movieId))
                return m;

        return null; // 리스트 내에서 찾지 못하는 경우
    }

    public void addMovie(Movie movie) {
        listOfMovies.add(movie);
    }
}
