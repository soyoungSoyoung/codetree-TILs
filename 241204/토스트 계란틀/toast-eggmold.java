
import java.util.*;

public class Main { //코드트리_토스트 계란틀
	static int n, L, R, map[][], ans;

	public static void main(String[] args) {
		init_토계();
		
		while(true) {
			int[][] openTheDoor = bfs_토계(); //다 0이면 열 게 없음
			
			int sum = 0;
			for(int i=0; i<n; i++) {
				for(int j=0; j<n; j++) {
					sum += openTheDoor[i][j];
				}
			}
			
			if(sum == 0) break;
			
			//열려 있으면 평균내기
			flatten(openTheDoor);
			ans++;
		}
		
		System.out.println(ans);
	}

	private static void flatten(int[][] openTheDoor) {
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				if(openTheDoor[i][j] != 0) {
					bbfs_토계(openTheDoor, i,j); //방문 처리는 0으로 하기
				}
			}
		}
	}

	private static void bbfs_토계(int[][] openTheDoor, int x, int y) {
		int[] dx = {-1,1,0,0};
		int[] dy = {0,0,-1,1};
		
		int num = openTheDoor[x][y];
		openTheDoor[x][y]=0;
		int cnt = 1;
		int sum = map[x][y];
		List<int[]> group = new ArrayList<>();
		group.add(new int[] {x,y});
		
		Queue<int[]> q = new ArrayDeque<>();
		q.add(new int[] {x,y});
		
		while(!q.isEmpty()) {
			int[] p = q.poll();
			
			for(int d=0; d<4; d++) {
				int nx = p[0] + dx[d];
				int ny = p[1] + dy[d];
				
				if(nx>=0&&nx<n && ny>=0&&ny<n && num == openTheDoor[nx][ny]) {
					openTheDoor[nx][ny] = 0;
					cnt++;
					sum += map[nx][ny];
					group.add(new int[] {nx,ny});
				}
			}
		}
		
		int newEggs = sum / cnt;
		for(int[] p : group) {
			map[p[0]][p[1]] = newEggs;
		}
	}

	private static int[][] bfs_토계() {
		int[][] doors = new int[n][n];
		int num = 1; //덩어리마다 1씩 증가
		
		int[] dx = {-1,1,0,0};
		int[] dy = {0,0,-1,1};
		
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				for(int d=0; d<4; d++) {
					int nx = dx[d]+i;
					int ny = dy[d]+j;
					
					if(nx>=0&&nx<n && ny>=0&&ny<n) {
						if(doors[nx][ny] == 0) {
							if(L <= Math.abs(map[i][j] - map[nx][ny]) && Math.abs(map[i][j] - map[nx][ny]) <= R) {
								Queue<int[]> q = new ArrayDeque<>();
								q.add(new int[] {i,j});
								
								while(!q.isEmpty()) {
									int[] p = q.poll();
									
									for(int dd=0; dd<4; dd++) {
										int nnx = p[0] + dx[dd];
										int nny = p[1] + dy[dd];
										
										if(nnx>=0&&nnx<n && nny>=0&&nny<n && doors[nnx][nny] == 0) {
											if(L <= Math.abs(map[p[0]][p[1]] - map[nnx][nny]) && Math.abs(map[p[0]][p[1]] - map[nnx][nny]) <= R) {
												doors[nnx][nny] = num;
												q.add(new int[] {nnx, nny});
											}
										}
									}
								}
								
								num++;
							}
						}
					}
				}
			}
		}
		return doors;
	}

	private static void init_토계() {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		L = sc.nextInt(); //이동 범위 최솟값
		R = sc.nextInt(); //이동 범위 최댓값
		
		map = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				map[i][j] = sc.nextInt();
			}
		}
	}

}
