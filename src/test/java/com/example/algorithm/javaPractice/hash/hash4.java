package com.example.algorithm.javaPractice.hash;

/*
-문제 설명-
스트리밍 사이트에서 장르 별로 가장 많이 재생된 노래를 두 개씩 모아 베스트 앨범을 출시하려 합니다. 노래는 고유 번호로 구분하며, 노래를 수록하는 기준은 다음과 같습니다.

속한 노래가 많이 재생된 장르를 먼저 수록합니다.
장르 내에서 많이 재생된 노래를 먼저 수록합니다.
장르 내에서 재생 횟수가 같은 노래 중에서는 고유 번호가 낮은 노래를 먼저 수록합니다.
노래의 장르를 나타내는 문자열 배열 genres와 노래별 재생 횟수를 나타내는 정수 배열 plays가 주어질 때, 베스트 앨범에 들어갈 노래의 고유 번호를 순서대로 return 하도록 solution 함수를 완성하세요.

-제한사항-
genres[i]는 고유번호가 i인 노래의 장르입니다.
plays[i]는 고유번호가 i인 노래가 재생된 횟수입니다.
genres와 plays의 길이는 같으며, 이는 1 이상 10,000 이하입니다.
장르 종류는 100개 미만입니다.
장르에 속한 곡이 하나라면, 하나의 곡만 선택합니다.
모든 장르는 재생된 횟수가 다릅니다.

-입출력 예-
genres	                                        plays	                    return
["classic", "pop", "classic", "classic", "pop"]	[500, 600, 150, 800, 2500]	[4, 1, 3, 0]
 */

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class hash4 {

    @Test
    public void hashMain() {
        String[] genres = new String[]{"classic", "pop", "classic", "classic", "pop"};
        int[] plays = new int[]{500, 600, 150, 800, 2500};

        int[] answer = solution(genres, plays);
    }

    private int[] solution(String[] genres, int[] plays) {
        int[] answer;
        LinkedList<Music> totalList = new LinkedList<>();
        HashMap<String, Integer> genreSumMap = new HashMap<>();

        // 초기 작업
        for (int i = 0; i < genres.length; i++) {
            Music music = new Music(genres[i], plays[i], i);
            totalList.add(music);
            genreSumMap.put(genres[i], genreSumMap.getOrDefault(genres[i], 0) + plays[i]);
        }

        // 속한 노래가 많이 재생된 장르를 sort
        LinkedList<Map.Entry<String, Integer>> genreOrderList = genreOrdering(genreSumMap);

        // 장르 내에서 많이 재생된 노래를 sort

        ArrayList<Integer> result = eachGenreOrdering(genreOrderList, totalList);

        answer = new int[result.size()];

        for (int j = 0; j < result.size(); j++) {
            answer[j] = result.get(j);
            System.out.println("answer : " + answer[j]);
        }

        return answer;
    }

    private LinkedList<Map.Entry<String, Integer>> genreOrdering (HashMap<String, Integer> map) {
        LinkedList<Map.Entry<String, Integer>> list = new LinkedList<>(map.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        return list;
    }

    private ArrayList<Integer> eachGenreOrdering (LinkedList<Map.Entry<String, Integer>> genreOrderList, LinkedList<Music> totalList) {
        ArrayList<Integer> result = new ArrayList<>();

        for (Map.Entry<String, Integer> key : genreOrderList) {
            ArrayList<Music> tempList = new ArrayList<>();

            // 전체 리스트에서 해당 장르의 music 객체를 tempList에 추가
            for (Music music : totalList) {
                if (key.getKey().equals(music.getGenre())) {
                    tempList.add(music);
                }
            }

            // 내림차순 정렬
            Collections.sort(tempList, new Comparator<Music>() {
                @Override
                public int compare(Music o1, Music o2) {
                    if (o1.getPlay() > o2.getPlay()) {
                        return -1;
                    } else if (o1.getPlay() < o2.getPlay()) {
                        return 1;
                    } else if (o1.getPlay() == o2.getPlay()) {
                        // 장르 내에서 재생 횟수가 같은 노래 중에서는 고유 번호가 낮은 노래를 먼저 수록합니다.
                        if (o1.getIndex() < o2.getPlay()) {
                            return -1;
                        } else {
                            return 1;
                        }
                    }
                    return 0;
                }
            });

            for (int i = 0; i < 2; i++) {
                result.add(tempList.get(i).getIndex());
            }
        }

        return result;
    }
}


class Music {
    private String genre;
    private int play;
    private int index;

    public Music(String genre, int play, int index) {
        this.genre = genre;
        this.play = play;
        this.index = index;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPlay() {
        return play;
    }

    public void setPlay(int play) {
        this.play = play;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
