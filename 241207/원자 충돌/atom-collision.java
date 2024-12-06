import java.util.*;

public class Main {
	static int n,m,k, time;
	//좌표, 질량, 속력, 방향(0-7)
	static List<Atom> atoms = new ArrayList<>();
	//↑, ↗, →, ↘, ↓, ↙, ←, ↖ 
	static int[] dx = {-1,-1,0,1,1,1,0,-1};
	static int[] dy = {0,1,1,1,0,-1,-1,-1};

	public static void main(String[] args) { //코드트리_원자 충돌
		init_원자충돌();
		
		while(time < k && atoms.size() > 1) {
			time++;
			//이동
			for (int i = 0; i < atoms.size(); i++) {
				int x=atoms.get(i).x; int y=atoms.get(i).y;
				int d=atoms.get(i).d; int s=atoms.get(i).s;
				int nx = (x + dx[d]*s) % n;
				int ny = (y + dy[d]*s) % n;
				
				if(nx < 0) nx = n+nx;
				if(ny < 0) ny = n+ny;

//				System.out.println(x+" "+y+" -> "+nx+" "+ny);
				atoms.get(i).x = nx; atoms.get(i).y = ny;
			}
			
			//합치기
			List<Atom> newAtoms = new ArrayList<>();
			int i = atoms.size()-1;
			while (atoms.size() > 0) {
				List<Atom> samePlace = new ArrayList<>();
				samePlace.add(atoms.remove(i));
				int x = samePlace.get(0).x;
				int y = samePlace.get(0).y;
				for(int j=atoms.size()-1; j>=0; j--) {
					if(x==atoms.get(j).x && y==atoms.get(j).y) {
						samePlace.add(atoms.remove(j));
					}
				}
				i = atoms.size()-1;
				
				//합치기
				//(x,y, 질,속,방)
				if(samePlace.size() == 1) {
					newAtoms.add(samePlace.remove(0));
					continue;
				}
				int nm = 0;
				int ns = 0;
				int sameDir = samePlace.get(0).d % 2;
				boolean dir = true;
				for(Atom atom : samePlace) {
					nm += atom.m;
					ns += atom.s;
					if(atom.d % 2 - sameDir != 0) dir = false;
				}
				nm /= 5;
				if(nm == 0) continue; //질량이 0이 되면 사라짐
				
				ns /= samePlace.size();
				
				if(dir) {//상하좌우
					newAtoms.add(new Atom(x, y, nm, ns, 0));
					newAtoms.add(new Atom(x, y, nm, ns, 2));
					newAtoms.add(new Atom(x, y, nm, ns, 4));
					newAtoms.add(new Atom(x, y, nm, ns, 6));
				} else {
					newAtoms.add(new Atom(x, y, nm, ns, 1));
					newAtoms.add(new Atom(x, y, nm, ns, 3));
					newAtoms.add(new Atom(x, y, nm, ns, 5));
					newAtoms.add(new Atom(x, y, nm, ns, 7));
				}
			}
			atoms = newAtoms;
//			System.out.println(time+" : "+atoms.size());
		}
		
		//답
		int answer = 0;
		for(Atom atom : atoms) {
			answer += atom.m;
		}
		System.out.println(answer);
	}

	private static void init_원자충돌() {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt(); //크기
		m = sc.nextInt(); //초기 원자의 개수
		k = sc.nextInt(); //실험 시간
		
		for (int i = 0; i < m; i++) {
			int x = sc.nextInt()-1;
			int y = sc.nextInt()-1;
			atoms.add(new Atom(x, y, sc.nextInt(), sc.nextInt(), sc.nextInt()));
		}
	}

}
class Atom {
	int x;
	int y;
	int m; //질량
	int s; //속력
	int d;//방향(0~7)
	public Atom(int x,int y,int m,int s,int d) {
		this.x = x;
		this.y = y;
		this.m = m;
		this.s = s;
		this.d = d;
	}
}