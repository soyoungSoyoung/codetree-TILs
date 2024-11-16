import java.util.*;
//n개 식당 체온
//가게당 팀장1, 팀원다'
//검사자 수 최소값

public class Main {
    static int n;
    static long customs[], jang, won, answer; 

    public static void main(String[] args) {
        scan_init();

        answer += n; //팀장은 꼭 필요
        for(int i=0; i<n; i++) {
            customs[i] -= jang;
        }

        //남은 수에 한 해서만 answer늘리기
        for(int i=0; i<n; i++) {
            if(customs[i] > 0) {
                answer += customs[i] / won;
                customs[i] %= won;
                if(customs[i] > 0) {
                    answer++;
                    
                }
            }
        }
        
        System.out.println(answer);
    }

    static public void scan_init() {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt(); //식당 수
        customs = new long[n]; //식당 별 고객 수

        for(int i=0; i<n ;i++) {
            customs[i] = sc.nextLong();
        }

        jang = sc.nextLong();
        won = sc.nextLong();
    }
}