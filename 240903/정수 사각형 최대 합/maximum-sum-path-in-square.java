import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[][] map = new int[n][n];
        int[][] dp = new int[n][n];
        for(int i=0; i<n; i++) Arrays.fill(map[i], -1);

        for(int i=0; i<n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int j=0;
            while(st.hasMoreTokens()) {
                map[i][j++] = Integer.parseInt(st.nextToken());
            }
        }

        // for(int i=0; i<n; i++) System.out.println(Arrays.toString(map[i]));
        dp[0][0] = map[0][0];
        for(int j=1; j<n; j++) {
            dp[0][j] = map[0][j] + dp[0][j-1];
        }
        for(int i=1; i<n; i++) {
            dp[i][0] = map[i][0] + dp[i-1][0];
        }

        for(int i=1; i<n; i++) {
            for(int j=1; j<n; j++) {
                int left = dp[i][j-1];
                int up = dp[i-1][j];

                dp[i][j] = map[i][j] + Math.max(left, up);
            }
        }

        // for(int i=0; i<n; i++) System.out.println(Arrays.toString(dp[i]));
        System.out.println(dp[n-1][n-1]);
    }
}