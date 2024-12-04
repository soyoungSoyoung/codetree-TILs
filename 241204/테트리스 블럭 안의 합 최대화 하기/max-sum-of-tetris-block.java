
import java.util.*;

public class Main { //코드트리_테트리스 블럭 안의 합 최대화 하기
	static int n,m, map[][], ans;

	public static void main(String[] args) {
		init_테트리스_최대();
		
		//1번 (일자막대, 2)
		block1();
		//2번 (2*2)블럭
		block2();
		//3번 (ㄹ모양...)
		block3();
		//4번 (ㅗ 모양)
		block4();
		//5번 (ㄱ 모양)
		block5();
		
		System.out.println(ans);
	}

	private static void block5() {
		//세로3(한당 4번)
		for (int i = 0; i <= n-3; i++) {
			for (int j = 0; j <= m-2; j++) {
				int sum = map[i+2][j] + map[i+2][j+1];
				sum += map[i][j] + map[i+1][j];
				ans = Math.max(ans, sum);
				
				sum -= map[i][j] + map[i+1][j];
				sum += map[i][j+1] + map[i+1][j+1];
				ans = Math.max(ans, sum);
				
				sum = map[i][j] + map[i][j+1];
				sum += map[i+1][j] + map[i+2][j];
				ans = Math.max(ans, sum);
				
				sum -= map[i+1][j] + map[i+2][j];
				sum += map[i+1][j+1] + map[i+2][j+1];
				ans = Math.max(ans, sum);
			}
		}
		//가로3
		for (int i = 0; i <= n-2; i++) {
			for (int j = 0; j <= m-3; j++) {
				int sum = map[i][j] + map[i][j+1] + map[i][j+2];
				sum += map[i+1][j];
				ans = Math.max(ans, sum);
				
				sum -= map[i+1][j];
				sum += map[i+1][j+2];
				ans = Math.max(ans, sum);
				
				sum = map[i+1][j] + map[i+1][j+1] + map[i+1][j+2];
				sum += map[i][j];
				ans = Math.max(ans, sum);
				
				sum -= map[i][j];
				sum += map[i][j+2];
				ans = Math.max(ans, sum);
			}
		}
	}

	private static void block4() {
		//세로3
		for (int i = 0; i <= n-3; i++) {
			for (int j = 0; j <= m-2; j++) {
				int sum = map[i+1][j] + map[i+1][j+1];
				sum += map[i][j] + map[i+2][j];
				ans = Math.max(ans, sum);
				
				sum -= map[i][j] + map[i+2][j];
				sum += map[i][j+1] + map[i+2][j+1];
				ans = Math.max(ans, sum);
			}
		}
		//가로3
		for (int i = 0; i <= n-2; i++) {
			for (int j = 0; j <= m-3; j++) {
				int sum = map[i][j+1] + map[i+1][j+1];
				sum += map[i][j] + map[i][j+2];
				ans = Math.max(ans, sum);
				
				sum -= map[i][j] + map[i][j+2];
				sum += map[i+1][j] + map[i+1][j+2];
				ans = Math.max(ans, sum);
			}
		}
	}

	private static void block3() {
		//세로3
		for (int i = 0; i <= n-3; i++) {
			for (int j = 0; j <= m-2; j++) {
				int sum = map[i+1][j] + map[i+1][j+1];
				sum += map[i][j] + map[i+2][j+1];
				ans = Math.max(ans, sum);
				
				sum -= (map[i][j] + map[i+2][j+1]);
				sum += map[i][j+1] + map[i+2][j];
				ans = Math.max(ans, sum);
			}
		}
		//가로3
		for (int i = 0; i <= n-2; i++) {
			for (int j = 0; j <= m-3; j++) {
				int sum = map[i][j+1] + map[i+1][j+1];
				sum += map[i+1][j] + map[i][j+2];
				ans = Math.max(ans, sum);
				
				sum -= map[i+1][j] + map[i][j+2];
				sum += map[i+1][j+2] + map[i][j];
				ans = Math.max(ans, sum);
			}
		}
	}

	private static void block2() {
		for (int i = 0; i <= n-2; i++) {
			for (int j = 0; j <= m - 2; j++) {
				int sum = map[i][j] + map[i+1][j] + map[i+1][j+1] + map[i][j+1];
				ans = Math.max(ans, sum);
			}
		}
	}

	private static void block1() {
		//1.세로로 긴 블록
		for (int i = 0; i <= n-4; i++) {
			for (int j = 0; j < m; j++) {
				int sum = 0;
				for(int k=i; k<i+4; k++) {
					sum += map[k][j];
				}
				ans = Math.max(ans, sum);
			}
		}
		//2.가로로 긴 블록
		for (int j = 0; j <= m-4; j++) {
			for (int i = 0; i < n; i++) {
				int sum = 0;
				for(int k=j; k<j+4; k++) {
					sum += map[i][k];
				}
				ans = Math.max(ans, sum);
			}
		}
	}

	private static void init_테트리스_최대() {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		m = sc.nextInt();
		
		map = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				map[i][j] = sc.nextInt();
			}
		}
	}

}
