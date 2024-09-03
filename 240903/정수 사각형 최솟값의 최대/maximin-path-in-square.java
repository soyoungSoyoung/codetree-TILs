import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
       int n = sc.nextInt();
       int[][] map = new int[n][n];
       int[][] dp = new int[n][n];
       for(int i=0; i<n; i++) {
        for(int j=0; j<n; j++) {
            map[i][j] = sc.nextInt();
        }
       }

       //경로에서 나오는 최소값을 dp에 저장 => 그 중 최대값을 출력;
       dp[0][0] = map[0][0];
       for(int i=1; i<n; i++) dp[i][0] = Math.min(dp[i-1][0], map[i][0]);
       for(int j=1; j<n; j++) dp[0][j] = Math.min(dp[0][j-1], map[0][j]);

        for(int i=1; i<n; i++) {
            for(int j=1; j<n; j++) {
                dp[i][j] = Math.min(map[i][j], Math.max(dp[i-1][j], dp[i][j-1]));
            }
        }

    //    for(int i=0; i<n; i++) System.out.println(Arrays.toString(dp[i]));
    System.out.println(dp[n-1][n-1]);
    }
}