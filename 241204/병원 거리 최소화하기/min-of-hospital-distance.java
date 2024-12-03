
import java.util.*;
public class Main {
	static int n,m, map[][], answer = Integer.MAX_VALUE;
	static List<int[]> people = new ArrayList<>(); 
	static List<int[]> hospitals = new ArrayList<>();

	public static void main(String[] args) {
		init_병원거리최소화();
		
		int[][] choice = new int[m][2]; //0:x좌표 1:y조표
		combi(0,0, choice);
		
		System.out.println(answer);
	}

	private static void combi(int idx, int cnt, int[][] choice) {
		if(cnt == m) {
			answer = Math.min(answer, distanceSum(choice));
			return;
		}
		
		for (int i = idx; i < hospitals.size(); i++) {
			choice[cnt][0] = hospitals.get(i)[0];
			choice[cnt][1] = hospitals.get(i)[1];
			
			combi(idx+1, cnt+1, choice);
		}
	}

	private static int distanceSum(int[][] choice) {
		int sum = 0;
		for (int i = 0; i < people.size(); i++) {
			int x = people.get(i)[0];
			int y = people.get(i)[1];
			
			int dis = n*n;
			for (int j = 0; j < choice.length; j++) {
				int hx = choice[j][0];
				int hy = choice[j][1];
				
				dis = Math.min(dis, Math.abs(hx-x) + Math.abs(hy-y));
			}
			
			sum += dis;
		}
		return sum;
	}

	private static void init_병원거리최소화() {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt(); //맵 크기
		m = sc.nextInt(); //남길 병원 개수
		
		map = new int[n][n];
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				map[i][j] = sc.nextInt();
				
				if(map[i][j] == 1) //사람이면
					people.add(new int[] {i, j});
				if(map[i][j] == 2) //병원이면
					hospitals.add(new int[] {i, j});
			}
		}
	}

}
