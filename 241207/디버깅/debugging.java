import java.util.*;

public class Main {
	static int n,m,h, map[][], answer = -1;

	public static void main(String[] args) { //코드트리_디버깅
		init_디버깅();
//		for (int i = 0; i < h; i++) {
//			System.out.println(Arrays.toString(map[i]));
//		}
		
		//1개만 추가하면?
		combi(0 ,0,1);
		
		System.out.println(answer);
	}

	private static void combi(int cnt, int r, int c) {
		if(answer == 1)
			return;
		if(cnt <= 3) {
			boolean right = program();
			//
//			for(int i=0; i<map.length; i++) {
//				System.out.println(Arrays.toString(map[i]));
//			}
//			System.out.println();
			
			if(right) {
				if(answer == -1)
					answer = cnt;
				else
					answer = Math.min(answer, cnt);
			}
		}
		if(cnt >= 3)
			return;
		
		// (교점에서 다시 이을 순 없음_)
		for (int i = r+1; i < h; i++) {
			if(map[i][c]==1 && canPut(i,c)) {
				map[i][c] = 0;
				combi(cnt+1 ,i,c);
				map[i][c] = 1;
			}
		}
		for (int i = 0; i < h; i++) {
			for (int j = c+2; j < n+n-1; j+=2) {
				if(map[i][j]==1 && canPut(i, j)) { //양 옆으로 길이 막혀있으면
					map[i][j] = 0;
					combi(cnt+1 ,i,j);
					map[i][j] = 1;
				}
			}
		}
	}

	private static boolean canPut(int r, int c) {
		if(c-2>=1 && map[r][c-2]==0) return false;
		if(c+2<n+n-2 && map[r][c+2]==0) return false;
		return true;
	}

	private static boolean program() { //map이 제대로 가는지 확인
		for(int player=0; player<n+n-1; player+=2) { //j임
			int y = player;
			int x = 0;
			
			while(x < h) {
				//옆에 갈 수 있나 확인 후, 아래로 내리기
				if(y-1>=1 && 0==map[x][y-1]) {
					x++;
					y-=2;
				}
				else if(y+1<n+n-2 && 0==map[x][y+1]) {
					x++;
					y+=2;
				}
				else if((y-1<0 || 1==map[x][y-1]) && (y+1>=n+n-1 || 1==map[x][y+1])) {
					x++;
				}
			}
			
			if(player != y) return false;
		}
		
		return true;
	}

	private static void init_디버깅() {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt(); //고객 수(*2가 진짜 고객)
		m = sc.nextInt(); //유실 선수..?
		h = sc.nextInt(); //취약지점수(가로축) 
		map = new int[h][n+n-1];
		//벽:1 (짝수가 사람, 홀수가 선)
		for(int i=0; i<h; i++) {
			for (int j = 1; j <n+n-1 ; j+=2) {
				map[i][j] = 1;
			}
		}
		
		for (int i = 0; i < m; i++) {
			int a = sc.nextInt()-1;
			int b = sc.nextInt();
			map[a][b+b-1] = 0; //길 열기
		}
	}

}
