package com.example.algorithm.javaPractice.dfsAndBfs;

/*
-문제 설명-
주어진 항공권을 모두 이용하여 여행경로를 짜려고 합니다. 항상 "ICN" 공항에서 출발합니다.

항공권 정보가 담긴 2차원 배열 tickets가 매개변수로 주어질 때, 방문하는 공항 경로를 배열에 담아 return 하도록 solution 함수를 작성해주세요.

-제한사항-
모든 공항은 알파벳 대문자 3글자로 이루어집니다.
주어진 공항 수는 3개 이상 10,000개 이하입니다.
tickets의 각 행 [a, b]는 a 공항에서 b 공항으로 가는 항공권이 있다는 의미입니다.
주어진 항공권은 모두 사용해야 합니다.
만일 가능한 경로가 2개 이상일 경우 알파벳 순서가 앞서는 경로를 return 합니다.
모든 도시를 방문할 수 없는 경우는 주어지지 않습니다.

-입출력 예-
tickets	                                                                        return
[["ICN", "JFK"], ["HND", "IAD"], ["JFK", "HND"]]	                            ["ICN", "JFK", "HND", "IAD"]
[["ICN", "SFO"], ["ICN", "ATL"], ["SFO", "ATL"], ["ATL", "ICN"], ["ATL","SFO"]]	["ICN", "ATL", "ICN", "SFO", "ATL", "SFO"]
 */

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.stream.Collectors;

@SpringBootTest
public class search4 {

    @Test
    public void searchMain() {
        //String[][] tickets = new String[][]{{"ICN", "JFK"}, {"HND", "IAD"}, {"JFK", "HND"}};
        String[][] tickets = new String[][]{{"ICN", "SFO"}, {"ICN", "ATL"}, {"SFO", "ATL"}, {"ATL", "ICN"}, {"ATL","SFO"}};
        //String[][] tickets = new String[][]{{"ICN", "B"}, {"B", "ICN"}, {"ICN", "A"}, {"A", "D"}, {"D", "A"}};


        String[] answer = solution(tickets);

        StringBuilder route = new StringBuilder();
        for (String r : answer) {
            route.append(r + " -> ");
        }

        System.out.println(route);
    }

    private String[] solution(String[][] tickets) {
        LinkedList<Ticket> extraList = new LinkedList<>();
        LinkedList<String> routeList = new LinkedList<>();
        ArrayList<LinkedList<String>> resultList = new ArrayList<>();
        String[] answer;

        // 티켓 리스트 초기화
        for (int i = 0; i < tickets.length; i++) {
            Ticket ticket = new Ticket(tickets[i][0], tickets[i][1]);
            extraList.add(ticket);
        }

        routeList.add("ICN");

        route(null, extraList, routeList ,resultList);

        answer = new String[resultList.get(0).size()];
        resultList.get(0).toArray(answer);

        return answer;
    }

    private void route(Ticket ticket, LinkedList<Ticket> extraList, LinkedList<String> routeList, ArrayList<LinkedList<String>> resultList) {
        if (extraList.size() == 0) {
            resultList.add(routeList);
        }

        ArrayList<Ticket> fromList = selectFromTicket(ticket == null ? "ICN" : ticket.getTo(), extraList);

        if (fromList.size() != 0) {
            for (Ticket from : fromList) {
                extraList.clone();
                LinkedList<Ticket> copyExtraList = copyExtraTicket(extraList);
                LinkedList<String> copyRouteList = copyRoute(routeList);
                copyExtraList.remove(from);
                copyRouteList.add(from.getTo());

                route(from, copyExtraList, copyRouteList, resultList);
            }
        }
    }

    private ArrayList<Ticket> selectFromTicket(String from, LinkedList<Ticket> extraList) {
        ArrayList<Ticket> fromList = (ArrayList<Ticket>) extraList.stream().filter(ticket -> ticket.getFrom().equals(from)).collect(Collectors.toList());

        if (!fromList.isEmpty()) {
            Collections.sort(fromList, (o1, o2) -> o1.getTo().compareTo(o2.getTo()));
        }

        return fromList;
    }

    private LinkedList<Ticket> copyExtraTicket(LinkedList<Ticket> extraList) {
        LinkedList<Ticket> copyExtraList = new LinkedList<>();
        for (Ticket extra : extraList) {
            copyExtraList.add(extra);
        }
        return copyExtraList;
    }

    private LinkedList<String> copyRoute(LinkedList<String> routeList) {
        LinkedList<String> copyRouteList = new LinkedList<>();
        for (String route : routeList) {
            copyRouteList.add(route);
        }
        return copyRouteList;
    }
}

class Ticket {
    private String from;
    private String to;

    public Ticket(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}