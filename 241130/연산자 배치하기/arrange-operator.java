import java.util.*;
//n개의 숫자
//덧 뺄 곱

public class Main {
    static int n;
    static int[] nums, ops = new int[3];
    static int ansMin = Integer.MAX_VALUE, ansMax = Integer.MIN_VALUE;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt(); //n개의 수
        nums = new int[n];
        for(int i=0; i<n; i++) {
            nums[i] = sc.nextInt();
        }
        for(int i=0; i<3; i++) {
            ops[i] = sc.nextInt();
        }
        //------------------------------------------

        dfs(1, nums[0]);

        System.out.println(ansMin+" "+ansMax);
    }

    private static void dfs(int idx, int sum) {
        if(idx >= n) {
            ansMax = Math.max(sum, ansMax);
            ansMin = Math.min(sum, ansMin);
            return;
        }
        //-----------------
        //(idx+1, sum은...)

        if(ops[0] > 0) {
            ops[0] --;
            int ssum = sum + nums[idx];
            dfs(idx+1, ssum);
            ops[0] ++;
        }

        if(ops[1] > 0) {
            ops[1] --;
            int ssum = sum - nums[idx];
            dfs(idx+1, ssum);
            ops[1] ++;
        }

        if(ops[2] > 0) {
            ops[2] --;
            int ssum = sum * nums[idx];
            dfs(idx+1, ssum);
            ops[2] ++;
        }

    }
}