
import java.util.*;

public class Main {
	static int n, map[][], answer;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};

	public static void main(String[] args) { //코드트리_예술성
		init_예술성();
		
		for (int r = 0; r < 4; r++) {
			//그룹화(mapGroup으로 뱉기) o
			int mapGroup[][] = new int[n][n];
			int groupNum = 1;
			for(int i=0; i<n; i++) {
				for(int j=0; j<n; j++) {
					if(mapGroup[i][j] == 0) {
						makeGroup(i,j, groupNum, mapGroup);
						groupNum++;
					}
				}
			}
			
			//+ 각 그룹에서 칸의 개수/ 그룹의 원래 색 o
			int[] cans = new int[groupNum];
			int[] groupCol = new int[groupNum];
			for(int i=0; i<n; i++) {
				for(int j=0; j<n; j++) {
					cans[mapGroup[i][j]]++;
					if(groupCol[mapGroup[i][j]] == 0) {
						groupCol[mapGroup[i][j]] = map[i][j];
					}
				}
			}
			//각 그룹의 조화로움 (콤비 후, 확인)
			int[] johwa = new int[2];
			combi(0, 1, johwa, groupNum ,mapGroup,cans,groupCol);
			
			//회전
			rotate();
		}
		
		System.out.println(answer);
	}

	private static void rotate() {
		int[][] nextMap = new int[n][n];
		//가운데
		nextMap[n/2][n/2] = map[n/2][n/2];
			//y축이 n/2일 때 -> (n/2, x)
		for (int i = 0; i < n; i++) {
			nextMap[n/2][i] = map[i][n/2];
		}
			//x축이 n/2일 때 -> ((n-1) - y, n/2)
		for (int j = 0; j < n; j++) {
			nextMap[n-1-j][n/2] = map[n/2][j];
		}
		
		//4사분면..
			//1
		for (int i = 0; i < n/2; i++) {
			for (int j = 0; j < n/2; j++) {
				if(i==0 && j<n/2-1) {
					nextMap[i][j+1] = map[i][j];
				}
				if(i<n/2-1 && j==n/2-1) {
					nextMap[i+1][j] = map[i][j];
				}
				if(i==n/2-1 && j>0 && j<n/2) {
					nextMap[i][j-1] = map[i][j];
				}
				if(i>0 && i<n/2 && j==0) {
					nextMap[i-1][j] = map[i][j];
				}
			}
		}
			//2
		for (int i = 0; i < n/2; i++) {
			for (int j = n/2+1; j < n; j++) {
				if(i==0 && j<n-1) {
					nextMap[i][j+1] = map[i][j];
				}
				if(i<n/2-1 && j==n-1) {
					nextMap[i+1][j] = map[i][j];
				}
				if(i==n/2-1 && j>n/2+1 && j<n) {
					nextMap[i][j-1] = map[i][j];
				}
				if(i>0 && i<n/2 && j==n/2+1) {
					nextMap[i-1][j] = map[i][j];
				}
			}
		}
			//3
		for (int i = n/2+1; i < n; i++) {
			for (int j = 0; j < n/2; j++) {
				if(i==n/2+1 && j<n/2-1) {
					nextMap[i][j+1] = map[i][j];
				}
				if(i<n-1 && j==n/2-1) {
					nextMap[i+1][j] = map[i][j];
				}
				if(i==n-1 && j>0 && j<n/2) {
					nextMap[i][j-1] = map[i][j];
				}
				if(i>n/2+1 && i<n && j==0) {
					nextMap[i-1][j] = map[i][j];
				}
			}
		}
			//4
		for (int i = n/2+1; i < n; i++) {
			for (int j = n/2+1; j < n; j++) {
				if(i==n/2+1 && j<n-1) {
					nextMap[i][j+1] = map[i][j];
				}
				if(i<n-1 && j==n-1) {
					nextMap[i+1][j] = map[i][j];
				}
				if(i==n-1 && j>n/2+1 && j<n) {
					nextMap[i][j-1] = map[i][j];
				}
				if(i>n/2+1 && i<n && j==n/2+1) {
					nextMap[i-1][j] = map[i][j];
				}
			}
		}
		
		
		map = nextMap;
//		for (int i = 0; i < nextMap.length; i++) {
//			System.out.println(Arrays.toString(nextMap[i]));
//		}
	}

	private static void combi(int cnt, int idx, int[] johwa, int groupNum, int[][] mapGroup,int[] cans,int[] groupCol) {
		if(cnt == 2) {
			answer += calHarmony(johwa[0], johwa[1], mapGroup,cans,groupCol);
			return;
		}
		
		for(int i=idx; i<groupNum; i++) {
			johwa[cnt] = i;
			combi(cnt+1, i+1, johwa, groupNum, mapGroup, cans, groupCol);
		}
	}

	private static int calHarmony(int g1, int g2, int[][] mapGroup, int[] cans, int[] groupCol) {
		int lines = 0; //맞닿은 변 수
		boolean[][] visit = new boolean[n][n];
		boolean find = false;
		for(int i=0; i<n; i++) {
			for (int j = 0; j < n; j++) {
				if(mapGroup[i][j] == g1) {
					find = true;
					visit[i][j] = true;
					Queue<int[]> q = new ArrayDeque<>();
					q.add(new int[] {i, j});
					
					while(!q.isEmpty()) {
						int[] p = q.poll();
//						if(g1==3&&g2==4)System.out.println(Arrays.toString(p));
						
						//맞닿은 변?
						for(int d=0; d<4; d++) {
							int nx = p[0]+dx[d];
							int ny = p[1]+dy[d];
							
							if(nx>=0&&nx<n && ny>=0&&ny<n) {
								if(mapGroup[nx][ny] == g2) {
									lines++;
								}
								if(!visit[nx][ny] && mapGroup[nx][ny] == g1) {
									visit[nx][ny] = true;
									q.add(new int[] {nx, ny});
								}
							}
						}
					}
					if(find) break;
				}
				if(find) break;
			}
			if(find) break;
		}
		int a = (cans[g1] + cans[g2]) * groupCol[g1] * groupCol[g2] * lines;
//		if(g1==3&&g2==4)System.out.println(a+" "+lines);
		return a;
	}

	private static void makeGroup(int x, int y, int groupNum, int[][] mapGroup) {
		mapGroup[x][y] = groupNum;
		int color = map[x][y];
		Queue<int[]> q = new ArrayDeque<>();
		q.add(new int[] {x, y});
		
		while(!q.isEmpty()) {
			int[] p = q.poll();
			
			for (int d = 0; d < 4; d++) {
				int nx = p[0] + dx[d];
				int ny = p[1] + dy[d];
				
				if(nx>=0&&nx<n && ny>=0&&ny<n && map[nx][ny]==color && mapGroup[nx][ny] == 0) {
					mapGroup[nx][ny] = groupNum;
					q.add(new int[] {nx,ny});
				}
			}
		}
	}

	private static void init_예술성() {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		map = new int[n][n];
//		mapGroup = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				map[i][j] = sc.nextInt();
			}
		}
	}

}