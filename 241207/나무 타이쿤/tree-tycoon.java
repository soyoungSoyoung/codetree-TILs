import java.util.*;

public class Main {
	static int n,m, map[][];
	// → ↗ ↑ ↖ ← ↙ ↓ ↘
	static int[] dx = {0,-1,-1,-1,0,1,1,1};
	static int[] dy = {1,1,0,-1,-1,-1,0,1};
	static List<int[]> liquid2 = new ArrayList<>();

	public static void main(String[] args) { //코드트리_나무 타이쿤
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		map = new int[n][n];
		m = sc.nextInt(); // m년 후에..
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				map[i][j] = sc.nextInt();
			}
		}
		
		liquid2.add(new int[] {n-2, 0});
		liquid2.add(new int[] {n-2, 1});
		liquid2.add(new int[] {n-1, 0});
		liquid2.add(new int[] {n-1, 1});
		
		while(m-- > 0) {
			int d = sc.nextInt() - 1;
			int p = sc.nextInt();
			
			//영양제 위치 이동
			boolean[][] liquid = moveLiquidArea(d, p);
			//영양제 투입
			injectLiquid();
			//투입 후, 대각선 영향 (list에서 삭제..)
			diagonal();
			//네모칸 제외, 2이상은 2베어서
			boolean[][] isCut = cutLibro(liquid);
			//영양제 투입이 아니라 그냥 올려두는 거였음;
			putLiquid(isCut, liquid);
		}
		
		//답
		int answer = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				answer += map[i][j];
			}
		}
//		for (int i = 0; i < n; i++) {
//			System.out.println(Arrays.toString(map[i]));
//		}
		System.out.println(answer);
	}

	private static void putLiquid(boolean[][] isCut, boolean[][] liquid) {
		//isCut은 true이면서, liquid는 false인 좌표는 list에 추가
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if(isCut[i][j] && !liquid[i][j]) {
					liquid2.add(new int[] {i,j});
				}
			}
		}
	}

	private static boolean[][] cutLibro(boolean[][] liquid) {
		boolean[][] isCut = new boolean[n][n];
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if(!liquid[i][j] && map[i][j] >= 2) {
					map[i][j] -= 2;
					isCut[i][j] = true;
				}
			}
		}
		
		return isCut;
	}

	private static void diagonal() {
		for (int i = liquid2.size()-1; i>=0; i--) {
			int x = liquid2.get(i)[0];
			int y = liquid2.get(i)[1];
			
			for(int d=1; d<8; d+=2) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(nx>=0&&nx<n && ny>=0&&ny<n && map[nx][ny] >= 1)
					map[x][y]++;
			}
			
			liquid2.remove(i);
		}
	}

	private static void injectLiquid() {
		//list에 있는 - 주입
		for (int i = liquid2.size()-1; i>=0; i--) {
			int x = liquid2.get(i)[0];
			int y = liquid2.get(i)[1];
			map[x][y]++;
		}
	}

	private static boolean[][] moveLiquidArea(int d, int p) {
		boolean[][] liquid = new boolean[n][n];
		
		for (int i = 0; i < liquid2.size(); i++) {
			int nx = (liquid2.get(i)[0] + dx[d]*p + n*p) % n;
			int ny = (liquid2.get(i)[1] + dy[d]*p + n*p) % n;
			
			liquid2.get(i)[0] = nx; liquid2.get(i)[1] = ny;
			liquid[nx][ny] = true;
		}
		
		return liquid;
	}

}