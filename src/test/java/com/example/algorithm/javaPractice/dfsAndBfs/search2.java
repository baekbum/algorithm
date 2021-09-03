package com.example.algorithm.javaPractice.dfsAndBfs;

/*
-문제 설명-
네트워크란 컴퓨터 상호 간에 정보를 교환할 수 있도록 연결된 형태를 의미합니다. 예를 들어, 컴퓨터 A와 컴퓨터 B가 직접적으로 연결되어있고,
컴퓨터 B와 컴퓨터 C가 직접적으로 연결되어 있을 때 컴퓨터 A와 컴퓨터 C도 간접적으로 연결되어 정보를 교환할 수 있습니다.
따라서 컴퓨터 A, B, C는 모두 같은 네트워크 상에 있다고 할 수 있습니다.
컴퓨터의 개수 n, 연결에 대한 정보가 담긴 2차원 배열 computers가 매개변수로 주어질 때, 네트워크의 개수를 return 하도록 solution 함수를 작성하시오.
사용할 수 있는 숫자가 담긴 배열 numbers, 타겟 넘버 target이 매개변수로 주어질 때 숫자를 적절히 더하고 빼서 타겟 넘버를 만드는 방법의 수를 return 하도록 solution 함수를 작성해주세요.

-제한사항-
컴퓨터의 개수 n은 1 이상 200 이하인 자연수입니다.
각 컴퓨터는 0부터 n-1인 정수로 표현합니다.
i번 컴퓨터와 j번 컴퓨터가 연결되어 있으면 computers[i][j]를 1로 표현합니다.
computer[i][i]는 항상 1입니다.

-입출력 예-
n	computers	                        return
3	[[1, 1, 0], [1, 1, 0], [0, 0, 1]]	2
3	[[1, 1, 0], [1, 1, 1], [0, 1, 1]]	1
 */

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.LinkedList;

@SpringBootTest
public class search2 {

    @Test
    public void searchMain() {
        int n = 3;
        //int[][] computers = new int[][]{{1, 1, 0}, {1, 1, 0}, {0, 0, 1}};
        int[][] computers = new int[][]{{1, 1, 0}, {1, 1, 1}, {0, 1, 1}};

        solution(n, computers);
    }

    private void solution(int n, int[][] computers) {
        ArrayList<Computer> list = new ArrayList<>();
        int answer = 0;

        //init
        for (int i = 0; i < n; i++) {
            list.add(new Computer(i, computers[i], false));
        }

        //connectList
        setNestedComputer(list);

        //count
        answer = networkCount(list);

        System.out.println(answer);
    }

    private void setNestedComputer(ArrayList<Computer> list) {
        for (int i = 0; i < list.size(); i++) {
            Computer computer = list.get(i);

            for (int j = 0; j < list.size(); j++) {
                int[] infoList = computer.getConnectionInfo();

                if (i != j) {
                    if (infoList[j] == 1) {
                        computer.addComputer(list.get(j));
                    }
                }
            }
        }
    }

    private void connect(Computer computer) {
        if (!computer.getConnect()) {
            computer.setConnect(true);

            for (Computer c : computer.getConnectedComputer()) {
                connect(c);
            }
        }
    }

    private int networkCount(ArrayList<Computer> list) {
        int connectNum = 0;

        for (Computer computer : list) {
            if (!computer.getConnect()) {
                connectNum++;
            }

            connect(computer);
        }

        return connectNum;
    }
}

class Computer {
    private int idx;
    private int[] connectionInfo;
    private Boolean isConnect;
    private LinkedList<Computer> connectedComputer;

    public Computer(int idx, int[] connectionInfo, Boolean isConnect) {
        this.idx = idx;
        this.connectionInfo = connectionInfo;
        this.isConnect = isConnect;
        this.connectedComputer = new LinkedList<>();
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int[] getConnectionInfo() {
        return connectionInfo;
    }

    public void setConnectionInfo(int[] connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

    public Boolean getConnect() {
        return isConnect;
    }

    public void setConnect(Boolean connect) {
        isConnect = connect;
    }

    public LinkedList<Computer> getConnectedComputer() {
        return connectedComputer;
    }

    public void addComputer(Computer computer) {
        this.connectedComputer.add(computer);
    }
}

