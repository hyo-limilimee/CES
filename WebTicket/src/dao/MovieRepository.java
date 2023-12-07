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
        Movie avatar = new Movie("M1234", "아바타", 12000);
        avatar.setDescription("<아바타: 물의 길>은 판도라 행성에서 '제이크 설리'와 '네이티리'가 이룬 가족이 겪게 되는 무자비한 위협과 살아남기 위해 떠나야 하는 긴 여정과 전투, 그리고 견뎌내야 할 상처에 대한 이야기를 그렸다. 월드와이드 역대 흥행 순위 1위를 기록한 전편 <아바타>에 이어 제임스 카메론 감독이 13년만에 선보이는 영화로, 샘 워싱턴, 조 샐다나, 시고니 위버, 스티븐 랭, 케이트 윈슬렛이 출연하고 존 랜도가 프로듀싱을 맡았다.");
        avatar.setGenre("SF");
        avatar.setManufacturer("20세기 폭스");
        avatar.setTotalSeats(80);
        avatar.setRemainingSeats(20);
        avatar.setSupervisor("제임스 카메론");
        avatar.setFilename("avatar.jpg");

        Movie titanic = new Movie("M1235", "타이타닉", 12000);
        titanic.setDescription("내 인생의 가장 큰 행운은 당신을 만난 거야 우연한 기회로 티켓을 구해 타이타닉호에 올라탄 자유로운 영혼을 가진 화가 ‘잭’(레오나르도 디카프리오)은 막강한 재력의 약혼자와 함께 1등실에 승선한 ‘로즈’(케이트 윈슬렛)에게 한눈에 반한다. 진실한 사랑을 꿈꾸던 ‘로즈’ 또한 생애 처음 황홀한 감정에 휩싸이고, 둘은 운명 같은 사랑에 빠지는데… 가장 차가운 곳에서 피어난 뜨거운 사랑! 영원히 가라앉지 않는 세기의 사랑이 펼쳐진다!");
        titanic.setGenre("로맨스");
        titanic.setManufacturer("20세기 폭스");
        titanic.setTotalSeats(100);
        titanic.setRemainingSeats(15);
        titanic.setSupervisor("제임스 카메론");
        titanic.setFilename("titanic.jpg");

        Movie ironman = new Movie("M1236", "아이언맨", 12000);
        ironman.setDescription("<어벤져스> 뉴욕 사건의 트라우마로 인해 영웅으로서의 삶에 회의를 느끼는 토니 스타크(로버트 다우니 주니어). 그가 혼란을 겪는 사이 최악의 테러리스트 만다린(벤 킹슬리)을 내세운 익스트리미스 집단 AIM이 스타크 저택에 공격을 퍼붓는다. 이 공격으로 그에게 남은 건 망가진 수트 한벌 뿐. 모든 것을 잃어버린 그는 다시 테러의 위험으로부터 세계와 사랑하는 여인(기네스 팰트로)를 지켜내야 하는 동시에 머릿속을 떠나지 않던 한가지 물음의 해답도 찾아야만 한다. 과연 그가 아이언맨인가? 수트가 아이언맨인가?");
        ironman.setGenre("SF");
        ironman.setManufacturer("마블");
        ironman.setTotalSeats(120);
        ironman.setRemainingSeats(30);
        ironman.setSupervisor("셰인 블랙");
        ironman.setFilename("ironman.jpg");
        
        Movie top_gun = new Movie("M1237", "탑건:배버릭", 12000);
        top_gun.setDescription("최고의 파일럿들만이 갈 수 있는 캘리포니아의 한 비행 조종 학교 탑건에서의 사나이들의 우정과 사랑의 모험이 시작된다. 자신을 좇는 과거의 기억과 경쟁자, 그리고 사랑 사이에서 고군분투하는 그의 여정이 펼쳐진다.");
        top_gun.setGenre("밀리터리");
        top_gun.setManufacturer("스카이댄스 미디어");
        top_gun.setTotalSeats(120);
        top_gun.setRemainingSeats(30);
        top_gun.setSupervisor("조셉 코신스키");
        top_gun.setFilename("top_gun.jpg");
        
        Movie harry_potter = new Movie("M1238", "해리포터:죽음의 성물2", 12000);
        harry_potter.setDescription("불사조 기사단은 한층 더 강해진 볼드모트와 죽음을 먹는 자들로부터 해리를 지켜내기 위해 분투한다. 한편 해리와 론 그리고 헤르미온느를 찾아온 마법부 장관 루퍼스 스크림저는 세 사람에게 덤블도어가 남긴 유품을 전해준다. 한편 빌과 플뢰르의 결혼식은 죽음을 먹는 자들의 습격으로 아수라장이 되고, 가까스로 피신한 해리와 친구들은 볼드모트의 호크룩스 중 하나인 로켓을 찾아 떠난다.");
        harry_potter.setGenre("SF");
        harry_potter.setManufacturer("워너브라더스");
        harry_potter.setTotalSeats(120);
        harry_potter.setRemainingSeats(30);
        harry_potter.setSupervisor("데이비드 예이츠");
        harry_potter.setFilename("harry_potter.jpg");
        
        Movie end_game = new Movie("M1239", "어벤져스:엔드게임", 12000);
        end_game.setDescription("인피니티 워 이후 많은 사람이 죽고 또 많은 것을 잃게 된 지구는 더 이상 희망이 남지 않아 절망 속에 살아간다. 전쟁 후 남아 있던 어벤저스는 그런 그들의 모습을 보게 된다. 마지막으로 지구를 살리려 모든 것을 건 타노스와 최후의 전쟁을 치른다.");
        end_game.setGenre("SF");
        end_game.setManufacturer("마블");
        end_game.setTotalSeats(120);
        end_game.setRemainingSeats(30);
        end_game.setSupervisor("앤서니 루소 & 조 루소");
        end_game.setFilename("end_game.jpg");
        

        listOfMovies.add(avatar);
        listOfMovies.add(titanic);
        listOfMovies.add(ironman);
        listOfMovies.add(top_gun);
        listOfMovies.add(harry_potter);
        listOfMovies.add(end_game);
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
