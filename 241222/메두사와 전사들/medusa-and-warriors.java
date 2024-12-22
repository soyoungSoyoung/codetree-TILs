import java.util.*;

public class Main {
	//0:도로 1:도로x(메두사는 못감)
	static int N, M, map[][];
	static int[] park = new int[2];
	static List<int[]> warriors = new ArrayList<>(); //2덱스가 음수면 스턴상태..
	//우선순위 상하좌우
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};

	public static void main(String[] args) {
		int[] medusa = new int[2];
		init_메두사와전사들(medusa);
		
		while(true) {
			int 이동거리합 = 0;
			int 돌된수 = 0;
			int 공격한수 = 0; //
			int endTheGame = 1; //공원 도착했니 0공원도착 -1못감 1겜ㄱㄱ
			
			//1.메두사의 이동 ㅇ
			int 메두사 = 메두사의이동(medusa);
			if(-1 == 메두사 || 0 == 메두사) {
				endTheGame = 메두사;
				System.out.println(endTheGame);
				break;
			}
			 //같은 좌표에 있는 전사 죽이기
			killTheWarrior(medusa);
			
			//2.메두사의 시선 (와이파이..)
			 //가장많은돌 asc, 상하좌우asc
			 //스턴에 가려지면 ㄴㄴ...
			int[][] 시야각 = new int[N][N];
			돌된수 = 메두사의시선(medusa, 시야각); //시야각맵을 보면, 시선 방향 알 수 있음 ==> 이거 대각선 시야는 어떻게 해야 됨ㅋㅋ;;;
			makeStun(시야각); //스턴상태 음수처리 
			
			//3.전사들의 이동 (2번씩 가능)
			 //최대 두 칸 (스턴 상태 양수인 애들만)
			 //이동거리합++
			 //메두사와거리 asc, 상하좌우 asc
			 //시야 안으로는 못 들어감...
			for (int wCnt = 0; wCnt < warriors.size(); wCnt++) {
				if(warriors.get(wCnt)[2] < 0) continue;
				이동거리합 += moveTheWarrior(wCnt, 시야각, medusa);
				이동거리합 += moveTheWarrior(wCnt, 시야각, medusa);
			}
			
			//4.전사의 공격 (사라질수도) ㅇ
			공격한수 = killTheWarrior(medusa); //같은 좌표에 있는 전사 죽이기2, 공격 수++
			removeStunStatus(); //warriors 돌면서, 스턴 상태 풀기
			
			if(1 == endTheGame) System.out.println(이동거리합+" "+돌된수+" "+공격한수);
			else {
				System.out.println(endTheGame);
				break;
			}
		}
	}

	private static int moveTheWarrior(int wCnt, int[][] 시야각, int[] medusa) {
		int moveLen = N*N;
		int dir = -1;
		int x = warriors.get(wCnt)[0], y = warriors.get(wCnt)[1];
		for(int d=0; d<4; d++) {
			int nx = x+dx[d], ny = y+dy[d];
			if(!isInTheMap(nx, ny) || 시야각[nx][ny] > 0) continue;
			if(nx==medusa[0] && ny==medusa[1]) {
				moveLen = 1;
				dir = d;
				break;
			}
			boolean[][] visit = new boolean[N][N];
			visit[x][y] = true;
			visit[nx][ny] = true;
			int tmpLen = bfsWarrior(nx, ny, visit, 시야각, medusa);
			if(moveLen > tmpLen) {
				moveLen = tmpLen;
				dir = d;
			}
		}
		
		if(moveLen == N*N) return 0;
		warriors.get(wCnt)[0] += dx[dir];
		warriors.get(wCnt)[1] += dy[dir];
		return 1;
	}

	private static int bfsWarrior(int x, int y, boolean[][] visit, int[][] 시야각, int[] medusa) {
		Queue<int[]> q = new ArrayDeque<>();
		q.add(new int[] {x, y, 1});
		while(!q.isEmpty()) {
			int[] cur = q.poll();
			
			if(cur[0]==medusa[0] && cur[1]==medusa[1]) {
				return cur[2];
			}
			
			for(int d=0; d<4; d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(isInTheMap(nx, ny) && 시야각[nx][ny] < 0 && !visit[nx][ny]) {
					q.add(new int[] {nx, ny, cur[2]+1});
					visit[nx][ny] = true;
				}
			}
		}
		return N*N;
	}

	private static void removeStunStatus() {
		for (int i = 0; i < warriors.size(); i++) {
			warriors.get(i)[2] = 10;
		}
	}

	private static void makeStun(int[][] 시야각) {
		int cnt = 0;
		for(int i=0; i<warriors.size(); i++) {
			int x = warriors.get(i)[0];
			int y = warriors.get(i)[1];
			if(시야각[x][y] > 0) {
				warriors.get(i)[2] = -10;
				cnt++;
			}
		}
//		return cnt;
	}

	private static int 메두사의시선(int[] medusa, int[][] 시야각) {
		int stunningCnt = 0;
		for(int d=0; d<4; d++) {
			//시야 안:양수(2, 1), 시야 밖:음수(메두사 -9)
			int[][] tmp시야각 = init_시야각(medusa, d);
			int 돌명수 = init_상대시야(tmp시야각, medusa, d);
			
			if(stunningCnt < 돌명수) {
				stunningCnt = 돌명수;
				시야각 = tmp시야각;
			}
		}
		
		return stunningCnt;
	}

	private static int init_상대시야(int[][] tmp시야각, int[] medusa, int dir) {
		//warriors돌면서 가려지는 곳 -1로 바꾸기
		for (int[] warrior : warriors) {
			if(tmp시야각[warrior[0]][warrior[1]] > 0) {
				//상대 시야 가리기
				int x = medusa[0] - warrior[0];
				int y = medusa[1] - warrior[1];
				if(x==0 || y==0) hide시야각_1자(warrior, tmp시야각, dir);
				else hide시야각_대각(x,y, warrior, tmp시야각, dir);
			}
		}
		
		//돌될 명수 세기
		int cnt = 0;
		for (int[] warrior : warriors) {
			if(tmp시야각[warrior[0]][warrior[1]] > 0) cnt++;
		}
		return cnt;
	}

	private static void hide시야각_대각(int x, int y, int[] warrior, int[][] tmp시야각, int dir) {
		if(dir==0) { //상
			if(x>0 && y>0) { //(+,+)
				watchUp1(warrior, tmp시야각, dir);
			}
			else { //(+,-)
				watchUp2(warrior, tmp시야각, dir);
			}
		} else if(dir==1) { //하
			if(x<0 && y>0) { //(-,+)..
				watchDown1(warrior, tmp시야각, dir);
			}
			else { //(-,-)
				watchDown2(warrior, tmp시야각, dir);
			}
		} else if(dir==2) { //좌
			if(x>0 && y>0) { //(+,+)
				watchLeft1(warrior, tmp시야각, dir);
			}
			else { //(-,+)
				watchLeft2(warrior, tmp시야각, dir);
			}
		} else { //우
			if(x>0 && y<0) { //(+,-)
				watchRight1(warrior, tmp시야각, dir);
			}
			else { //(-,-)
				watchRight2(warrior, tmp시야각, dir);
			}
		}
	}

	private static void watchRight2(int[] warrior, int[][] tmp시야각, int dir) {
		//오른, --
		int x = warrior[0], y = warrior[1];
		for(int j=y+1; j<N; j++) {
			tmp시야각[x][j] = -1;
		}
		for(int i=x+1; i<N; i++) {
			for(int j=y+1; j<N; j++) {
				tmp시야각[i][j] = -1;
			}
			y++;
		}
	}

	private static void watchRight1(int[] warrior, int[][] tmp시야각, int dir) {
		//오른, +-
		int x = warrior[0], y = warrior[1];
		for(int j=y+1; j<N; j++) {
			tmp시야각[x][j] = -1;
		}
		for(int i=x-1; i>=0; i--) {
			for(int j=y+1; j<N; j++) {
				tmp시야각[i][j] = -1;
			}
			y++;
		}
	}

	private static void watchLeft2(int[] warrior, int[][] tmp시야각, int dir) {
		//왼, -+
		int x = warrior[0], y = warrior[1];
		for(int j=y-1; j>=0; j--) {
			tmp시야각[x][j] = -1;
		}
		for(int i=x+1; i<N; i++) {
			for(int j=0; j<y; j++) {
				tmp시야각[i][j] = -1;
			}
			y--;
		}
	}

	private static void watchLeft1(int[] warrior, int[][] tmp시야각, int dir) {
		//왼, ++
		int x = warrior[0], y = warrior[1];
		for(int j=y-1; j>=0; j--) {
			tmp시야각[x][j] = -1;
		}
		for(int i=x-1; i>=0; i--) {
			for(int j=0; j<y; j++) {
				tmp시야각[i][j] = -1;
			}
			y--;
		}
	}

	private static void watchDown2(int[] warrior, int[][] tmp시야각, int dir) {
		//아래, --
		int x = warrior[0], y = warrior[1];
		for(int i=x+1; i<N; i++) {
			tmp시야각[i][y] = -1;
		}
		for(int j=y+1; j<N; j++) {
			for(int i=x+1; i<N; i++) {
				tmp시야각[i][j] = -1;
			}
			x++;
		}
	}

	private static void watchDown1(int[] warrior, int[][] tmp시야각, int dir) {
		//아래, -+
		int x = warrior[0], y = warrior[1];
		for(int i=x+1; i<N; i++) {
			tmp시야각[i][y] = -1;
		}
		for(int j=y-1; j>=0; j--) {
			for(int i=x+1; i<N; i++) {
				tmp시야각[i][j] = -1;
			}
			x++;
		}
	}

	private static void watchUp2(int[] warrior, int[][] tmp시야각, int dir) {
		//위, +-
		int x = warrior[0], y = warrior[1];
		for(int i=x-1; i>=0; i--) {
			tmp시야각[i][y] = -1;
		}
		for(int j=y+1; j<N; j++) {
			for(int i=x-1; i>=0; i--) {
				tmp시야각[i][j] = -1;
			}
			x--;
		}
	}

	private static void watchUp1(int[] warrior, int[][] tmp시야각, int dir) {
		//위, ++
		int x = warrior[0], y = warrior[1];
		for(int i=x-1; i>=0; i--) {
			tmp시야각[i][y] = -1;
		}
		for(int j=y-1; j>=0; j--) {
			for(int i=x-1; i>=0; i--) {
				tmp시야각[i][j] = -1;
			}
			x--;
		}
	}

	private static void hide시야각_1자(int[] warrior, int[][] tmp시야각, int dir) {
		for(int i=1; i<N; i++) {
			int nx = warrior[0] + dx[dir]*i;
			int ny = warrior[1] + dy[dir]*i;
			if(!isInTheMap(nx, ny)) break;
			tmp시야각[nx][ny] = -1;
		}
	}

	//ㅇ
	private static int[][] init_시야각(int[] medusa, int dir) {
		int[][] 시야각 = new int[N][N];
		for(int[] 시야 : 시야각) {
			//시야 안:양수, 시야 밖:음수
			Arrays.fill(시야, -1);
		}
		시야각[medusa[0]][medusa[1]] = -9; //메두사
		
		//일자2
		for(int i=1; i<N; i++) {
			int nx = medusa[0] + dx[dir]*i;
			int ny = medusa[1] + dy[dir]*i;
			if(!isInTheMap(nx, ny)) break;
			
			시야각[nx][ny] = 2;
			//방향 찾아서 와이파이...
			for(int dd=0; dd<4; dd++) {
				if(dir<=1 && dd<=1) continue;
				if(dir>=2 && dd>=2) continue;
				for(int j=1; j<=i; j++) {
					int nnx = nx + dx[dd]*j;
					int nny = ny + dy[dd]*j;
					if(!isInTheMap(nnx, nny)) break;
					시야각[nnx][nny] = 1;
				}
			}
		}
		
		return 시야각;
	}

	private static int killTheWarrior(int[] medusa) {//1,4에서 사용
		int killedCnt = 0;
		//warriors
		for(int i=warriors.size()-1; i>=0; i--) {
			if(medusa[0]==warriors.get(i)[0] && medusa[1]==warriors.get(i)[1]) {
				warriors.remove(i);
				killedCnt++;
			}
		}
		return killedCnt;
	}

	private static int 메두사의이동(int[] medusa) {
		//공원까지 경로asc, 상하좌우(0~3:상하좌우)
		 //한 칸만 이동
		 //경로 없으면 endTheGame을 -1.
		int[] dir = {-1, N*N};
		for(int d=0; d<4; d++) {
			int nx = medusa[0] + dx[d];
			int ny = medusa[1] + dy[d];
			if(isInTheMap(nx, ny) && map[nx][ny] ==0) {
				boolean[][] visit = new boolean[N][N];
				visit[nx][ny] = true;
				visit[medusa[0]][medusa[1]] = true;
				int move = 메두사의이동pq(visit, nx, ny);
				if(move < dir[1]) { //상하좌우 순이니까 이렇게만 해도 됨!
					dir[1] = move;
					dir[0] = d;
				}
			}
		}
		if(dir[0] == -1) return -1;
		if(dir[0] != -1 && dir[1] == 1) return 0;
		
		medusa[0] += dx[dir[0]];
		medusa[1] += dy[dir[0]];
		return 1;
	}

	private static boolean isInTheMap(int nx, int ny) {
		if(nx>=0&&nx<N && ny>=0&&ny<N) return true;
		return false;
	}

	private static int 메두사의이동pq(boolean[][] visit, int x, int y) {
		Queue<int[]> pq = new PriorityQueue<>((a,b) -> a[2] - b[2]);
		pq.add(new int[] {x, y, 1});
		while(!pq.isEmpty()) {
			int[] cur = pq.poll();
			
			if(cur[0]==park[0] && cur[1]==park[1]) {
				return cur[2];
			}
			
			for(int d=0; d<4; d++) {
				int nx = cur[0] + dx[d];
				int ny = cur[1] + dy[d];
				
				if(isInTheMap(nx, ny) && map[nx][ny]==0 && !visit[nx][ny]) {
					visit[nx][ny] = true;
					pq.add(new int[] {nx, ny, cur[2]+1});
				}
			}
		}
		return N*N+1;
	}

	private static void init_메두사와전사들(int[] medusa) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt(); //맵 크기(정사각형)
		M = sc.nextInt(); //전사 수
		
		medusa[0] = sc.nextInt();
		medusa[1] = sc.nextInt();
		park[0] = sc.nextInt();
		park[1] = sc.nextInt();
		
		for(int i=0; i<M; i++) {
			int x = sc.nextInt();
			int y = sc.nextInt();
			warriors.add(new int[] {x, y, 10}); //좌표. 스턴상태(0보다 작으면 스턴 상태)
		}
		
		map = new int[N][N];
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				//0:도로 1:도로x(메두사는 못감)
				map[i][j] = sc.nextInt();
			}
		}
	}

}
