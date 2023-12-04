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
        avatar.setDescription("<아바타: 물의 길>은 판도라 행성에서 '제이크 설리'와 '네이티리'가 이룬 가족이 겪게 되는 무자비한 위협과 살아남기 위해 떠나야 하는 긴 여정과 전투, 그리고 견뎌내야 할 상처에 대한 이야기를 그렸다. 월드와이드 역대 흥행 순위 1위를 기록한 전편 <아바타>에 이어 제임스 카메론 감독이 13년만에 선보이는 영화로, 샘 워싱턴, 조 샐다나, 시고니 위버, 스티븐 랭, 케이트 윈슬렛이 출연하고 존 랜도가 프로듀싱을 맡았다.");
        avatar.setGenre("SF");
        avatar.setManufacturer("20세기 폭스");
        avatar.setTotalSeats(80);
        avatar.setRemainingSeats(20);
        avatar.setSupervisor("제임스 카메론");
        avatar.setFilename("avatar.jpg");

        Movie titanic = new Movie("P1235", "타이타닉", 13000);
        titanic.setDescription(""내 인생의 가장 큰 행운은 당신을 만난 거야" 우연한 기회로 티켓을 구해 타이타닉호에 올라탄 자유로운 영혼을 가진 화가 ‘잭’(레오나르도 디카프리오)은 막강한 재력의 약혼자와 함께 1등실에 승선한 ‘로즈’(케이트 윈슬렛)에게 한눈에 반한다. 진실한 사랑을 꿈꾸던 ‘로즈’ 또한 생애 처음 황홀한 감정에 휩싸이고, 둘은 운명 같은 사랑에 빠지는데… 가장 차가운 곳에서 피어난 뜨거운 사랑! 영원히 가라앉지 않는 세기의 사랑이 펼쳐진다!");
        titanic.setGenre("로맨스");
        titanic.setManufacturer("20세기 폭스");
        titanic.setTotalSeats(100);
        titanic.setRemainingSeats(15);
        titanic.setSupervisor("제임스 카메론");
        titanic.setFilename("titanic.jpg");

        Movie ironman = new Movie("P1236", "아이언맨", 12000);
        ironman.setDescription("<어벤져스> 뉴욕 사건의 트라우마로 인해 영웅으로서의 삶에 회의를 느끼는 토니 스타크(로버트 다우니 주니어). 그가 혼란을 겪는 사이 최악의 테러리스트 만다린(벤 킹슬리)을 내세운 익스트리미스 집단 AIM이 스타크 저택에 공격을 퍼붓는다. 이 공격으로 그에게 남은 건 망가진 수트 한벌 뿐. 모든 것을 잃어버린 그는 다시 테러의 위험으로부터 세계와 사랑하는 여인(기네스 팰트로)를 지켜내야 하는 동시에 머릿속을 떠나지 않던 한가지 물음의 해답도 찾아야만 한다. 과연 그가 아이언맨인가? 수트가 아이언맨인가?");
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
