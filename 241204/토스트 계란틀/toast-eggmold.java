
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
			
//			System.out.println(ans);
//			for(int[] i: openTheDoor) {
//				System.out.println(Arrays.toString(i));
//			}
//			System.out.println();
			
			if(sum == 0) break;
			
			//열려 있으면 평균내기
			flatten(openTheDoor);
			ans++;
			
//			for(int[] i: map) {
//				System.out.println(Arrays.toString(i));
//			}
//			System.out.println("-----------");
		}
		
		System.out.println(ans);
	}

	private static void flatten(int[][] openTheDoor) {
		int maxNum = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				maxNum = Math.max(maxNum, openTheDoor[i][j]);
			}
		}
		
		List<int[]>[] groups = new ArrayList[maxNum];
		for (int i = 0; i < groups.length; i++) {
			groups[i] = new ArrayList<>();
		}
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if(openTheDoor[i][j] != 0) {
					groups[openTheDoor[i][j]-1].add(new int[] {i, j});
				}
			}
		}
		
		for (int i = 0; i < groups.length; i++) {
			int cnt = groups[i].size();
			int sum = 0;
			for (int j = 0; j < cnt; j++) {
				sum += map[groups[i].get(j)[0]][groups[i].get(j)[1]];
			}
			
			int eggs = sum / cnt;
			for (int j = 0; j < cnt; j++) {
				map[groups[i].get(j)[0]][groups[i].get(j)[1]] = eggs;
			}
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
