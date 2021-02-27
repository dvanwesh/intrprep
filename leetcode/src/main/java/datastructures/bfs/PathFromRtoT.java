package datastructures.bfs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Given a 2D array of characters ('-' , 'X' , 'R' , 'T'), find out whether
 * there is a path from the robot 'R' to the target 'T'. The robot can move up,
 * down, left or right. Cells marked with 'X' are
 *
 * @author vdatla
 *
 */
class Point{
	int x;
	int y;
	Point parent;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	public Point(int x,int y){
		this.x=x;
		this.y=y;
	}
	public Point(int x,int y,Point p){
		this(x,y);
		parent=p;
	}
}

public class PathFromRtoT {
public static void main(String[] args) {
	char[][] arr={
			{'-' , '-' , 'R' , 'X'},
			{'-' , 'X' , 'X' , 'T'},
			{'-' , 'X' , 'X' , '-'},
			{'-' , '-' , 'X' , '-'},
			{'-' , '-' , '-' , '-'}};
	System.out.println(isPathPresent(arr,4,5,new Point(0,2),new Point(1,3)));
}

private static boolean isPathPresent(char[][] arr, int columns, int rows, Point robot,
		Point target) {
	Queue<Point> points=new LinkedList<Point>();
	Queue<Point> path=new LinkedList<Point>();
	boolean[][] visited=new boolean[rows][columns];
	robot.parent=robot;
	points.add(robot);
	visited[robot.x][robot.y]=true;
	while(!points.isEmpty()){
		Point curr=points.remove();
		visited[curr.x][curr.y]=true;
		if(curr.equals(target)){
			while(curr!=robot){
				curr=curr.parent;
				path.add(curr);
			}
			while(!path.isEmpty()){
				Point n = path.remove();
				System.out.print("["+n.x+","+n.y+"]"+"->");
			}
			return true;
		}
		if(curr.y-1>=0 && !visited[curr.x][curr.y-1] && arr[curr.x][curr.y-1]!='X'){
			Point p=new Point(curr.x,curr.y-1);
			if(p!=curr.parent){
				p.parent=curr;
				points.add(p);
			}
		}
		if(curr.x-1>=0 && !visited[curr.x-1][curr.y] && arr[curr.x-1][curr.y]!='X'){
			Point p=new Point(curr.x-1,curr.y);
			if(p!=curr.parent){
				p.parent=curr;
				points.add(p);
			}
		}
		if(curr.y+1<columns && !visited[curr.x][curr.y+1] && arr[curr.x][curr.y+1]!='X'){
			Point p=new Point(curr.x,curr.y+1);
			if(p!=curr.parent){
				p.parent=curr;
				points.add(p);
			}
		}
		if(curr.x+1<rows && !visited[curr.x+1][curr.y] && arr[curr.x+1][curr.y]!='X'){
			Point p=new Point(curr.x+1,curr.y);
			if(p!=curr.parent){
				p.parent=curr;
				points.add(p);
			}
		}
	}
	return false;
}

}
