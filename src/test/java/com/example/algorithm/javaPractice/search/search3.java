package com.example.algorithm.javaPractice.search;

/*
-문제 설명-
Leo는 카펫을 사러 갔다가 아래 그림과 같이 중앙에는 노란색으로 칠해져 있고 테두리 1줄은 갈색으로 칠해져 있는 격자 모양 카펫을 봤습니다.
Leo는 집으로 돌아와서 아까 본 카펫의 노란색과 갈색으로 색칠된 격자의 개수는 기억했지만, 전체 카펫의 크기는 기억하지 못했습니다.
Leo가 본 카펫에서 갈색 격자의 수 brown, 노란색 격자의 수 yellow가 매개변수로 주어질 때 카펫의 가로, 세로 크기를 순서대로 배열에 담아 return 하도록 solution 함수를 작성해주세요.

-제한사항-
갈색 격자의 수 brown은 8 이상 5,000 이하인 자연수입니다.
노란색 격자의 수 yellow는 1 이상 2,000,000 이하인 자연수입니다.
카펫의 가로 길이는 세로 길이와 같거나, 세로 길이보다 깁니다.

-입출력 예-
brown	yellow	return
10	    2	    [4, 3]
8	    1	    [3, 3]
24	    24	    [8, 6]
 */

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;

@SpringBootTest
public class search3 {

    @Test
    public void searchMain() {
        int brown = 8;
        int yellow = 1;

        int[] answer = solution(brown, yellow);

        for(int i : answer) {
            System.out.println(i);
        }
    }

    private int[] solution(int brown, int yellow) {
        HashSet<Integer> set = new HashSet<>();
        ArrayList<Tile> list = new ArrayList<>();
        int total = brown + yellow;
        int num = 2;
        int[] answer;

        while ((total / 2) > num) {
            if (total % num == 0) {
                if (set.contains(num)) {
                    break;
                }
                set.add(num);
                set.add(total / num);
                list.add(new Tile(total / num, num));
            }
            num++;
        }

        Tile tile = selectTarget(list, yellow);

        answer = new int[]{tile.getX(), tile.getY()};

        return answer;

    }

    private Tile selectTarget(ArrayList<Tile> tiles, int yellow) {
        Tile tile = new Tile();

        for (Tile t : tiles) {
            int target = (t.getX() - 2) * (t.getY() -2);
            if (yellow == target) {
                tile = t;
            }
        }

        return tile;
    }
}

class Tile {
    private int x;
    private int y;

    public Tile() {
    }

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
