package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.gameModel;
import java.util.*;

/**
 * This class contains an HashMap in which every road is matched with its numeric ID.
 
 * 
 * The HashMap implements the singleton pattern. In this way the road map is always unique and every game refers to the same
 * map. The HashMap is initialized with a private constructor. The HashSets are filled through a specific method.
 * 
 * 
 * @author Andrea Celli
 *
 */

public class RoadMap {
	
	private static RoadMap completeMap;
	private HashMap<Integer,Road> roadMap;
	
//the constructor initialize the HashMap and, at the same time, creates the roads	
	private RoadMap(){
		roadMap=new HashMap<Integer,Road>();
//initialization of the roads
		roadMap.put(1,new Road(2,Terrain.P1,Terrain.P2));
		roadMap.put(2,new Road(3,Terrain.C2,Terrain.C1));
		roadMap.put(3,new Road(1,Terrain.C1,Terrain.M2));
		roadMap.put(4,new Road(1,Terrain.P1,Terrain.P2));
		roadMap.put(5,new Road(3,Terrain.P1,Terrain.C3));
		roadMap.put(6,new Road(4,Terrain.C2,Terrain.C3));
		roadMap.put(7,new Road(6,Terrain.C2,Terrain.M1));
		roadMap.put(8,new Road(2,Terrain.M1,Terrain.C1));
		roadMap.put(9,new Road(3,Terrain.M1,Terrain.M2));
		roadMap.put(10,new Road(5,Terrain.D1,Terrain.M2));
		roadMap.put(11,new Road(2,Terrain.M2,Terrain.M3));
		roadMap.put(12,new Road(3,Terrain.D1,Terrain.M3));
		roadMap.put(13, new Road(1,Terrain.M3,Terrain.D2));
		roadMap.put(14, new Road(6,Terrain.D1,Terrain.D2));
		roadMap.put(15, new Road(4,Terrain.M1,Terrain.D1));
		roadMap.put(16, new Road(5,Terrain.C3,Terrain.M1));
		roadMap.put(17, new Road(2,Terrain.P2,Terrain.C3));
		roadMap.put(18, new Road(4,Terrain.P2,Terrain.W1));
		roadMap.put(19, new Road(3,Terrain.P2,Terrain.P3));
		roadMap.put(20, new Road(1,Terrain.C3,Terrain.P3));
		roadMap.put(21, new Road(6,Terrain.C3,Terrain.SHEEPSBURG));
		roadMap.put(22, new Road(1,Terrain.M1,Terrain.SHEEPSBURG));
		roadMap.put(23, new Road(2,Terrain.D1,Terrain.SHEEPSBURG));
		roadMap.put(24, new Road(1,Terrain.D1,Terrain.L1));
		roadMap.put(25, new Road(2,Terrain.D2,Terrain.L1));
		roadMap.put(26, new Road(5,Terrain.D2,Terrain.D3));
		roadMap.put(27, new Road(4,Terrain.L1,Terrain.D3));
		roadMap.put(28, new Road(3,Terrain.L1,Terrain.SHEEPSBURG));
		roadMap.put(29, new Road(4,Terrain.W3,Terrain.SHEEPSBURG));
		roadMap.put(30, new Road(5,Terrain.P3,Terrain.SHEEPSBURG));
		roadMap.put(31, new Road(2,Terrain.W1,Terrain.P3));
		roadMap.put(32, new Road(6,Terrain.P3,Terrain.W3));
		roadMap.put(33, new Road(5,Terrain.L1,Terrain.W3));
		roadMap.put(34, new Road(6,Terrain.L1,Terrain.L2));
		roadMap.put(35, new Road(1,Terrain.L2,Terrain.D3));
		roadMap.put(36, new Road(2,Terrain.W3,Terrain.L2));
		roadMap.put(37, new Road(1,Terrain.W3,Terrain.L3));
		roadMap.put(38, new Road(3,Terrain.W2,Terrain.W3));
		roadMap.put(39, new Road(4,Terrain.P3,Terrain.W2));
		roadMap.put(40, new Road(1,Terrain.W1,Terrain.W2));
		roadMap.put(41, new Road(2,Terrain.W2,Terrain.L3));
		roadMap.put(42, new Road(5,Terrain.L2,Terrain.L3));
	}
	
	//this method adds the roads to the HashSets for each road
	private void addAdjacentRoads(){
		//road 1
		roadMap.get(1).add(roadMap.get(5));
		roadMap.get(1).add(roadMap.get(6));
		//road 2
		roadMap.get(2).add(roadMap.get(7));
		roadMap.get(2).add(roadMap.get(8));
		//road 3
		roadMap.get(3).add(roadMap.get(8));
		roadMap.get(3).add(roadMap.get(9));
		//road 4
		roadMap.get(4).add(roadMap.get(5));
		roadMap.get(4).add(roadMap.get(17));
		//road 5
		roadMap.get(5).add(roadMap.get(1));
		roadMap.get(5).add(roadMap.get(6));
		roadMap.get(5).add(roadMap.get(4));
		roadMap.get(5).add(roadMap.get(17));
		//road 6
		roadMap.get(6).add(roadMap.get(1));
		roadMap.get(6).add(roadMap.get(5));
		roadMap.get(6).add(roadMap.get(7));
		roadMap.get(6).add(roadMap.get(16));
		//road 7
		roadMap.get(7).add(roadMap.get(2));
		roadMap.get(7).add(roadMap.get(6));
		roadMap.get(7).add(roadMap.get(8));
		roadMap.get(7).add(roadMap.get(16));
		//road 8
		roadMap.get(8).add(roadMap.get(2));
		roadMap.get(8).add(roadMap.get(3));
		roadMap.get(8).add(roadMap.get(7));
		roadMap.get(8).add(roadMap.get(9));
		//road 9
		roadMap.get(9).add(roadMap.get(3));
		roadMap.get(9).add(roadMap.get(8));
		roadMap.get(9).add(roadMap.get(10));
		roadMap.get(9).add(roadMap.get(15));
		//road 10
		roadMap.get(10).add(roadMap.get(9));
		roadMap.get(10).add(roadMap.get(11));
		roadMap.get(10).add(roadMap.get(12));
		roadMap.get(10).add(roadMap.get(15));
		//road 11
		roadMap.get(11).add(roadMap.get(10));
		roadMap.get(11).add(roadMap.get(12));
		//road 12
		roadMap.get(12).add(roadMap.get(10));
		roadMap.get(12).add(roadMap.get(11));
		roadMap.get(12).add(roadMap.get(13));
		roadMap.get(12).add(roadMap.get(14));
		//road 13
		roadMap.get(13).add(roadMap.get(12));
		roadMap.get(13).add(roadMap.get(14));
		//road 14
		roadMap.get(14).add(roadMap.get(12));
		roadMap.get(14).add(roadMap.get(13));
		roadMap.get(14).add(roadMap.get(24));
		roadMap.get(14).add(roadMap.get(25));
		//road 15
		roadMap.get(15).add(roadMap.get(9));
		roadMap.get(15).add(roadMap.get(10));
		roadMap.get(15).add(roadMap.get(22));
		roadMap.get(15).add(roadMap.get(23));
		//road 16
		roadMap.get(16).add(roadMap.get(6));
		roadMap.get(16).add(roadMap.get(7));
		roadMap.get(16).add(roadMap.get(21));
		roadMap.get(16).add(roadMap.get(22));
		//road 17
		roadMap.get(17).add(roadMap.get(4));
		roadMap.get(17).add(roadMap.get(5));
		roadMap.get(17).add(roadMap.get(19));
		roadMap.get(17).add(roadMap.get(20));
		//road 18
		roadMap.get(18).add(roadMap.get(19));
		roadMap.get(18).add(roadMap.get(31));
		//road 19
		roadMap.get(19).add(roadMap.get(17));
		roadMap.get(19).add(roadMap.get(18));
		roadMap.get(19).add(roadMap.get(20));
		roadMap.get(19).add(roadMap.get(31));
		//road 20
		roadMap.get(20).add(roadMap.get(17));
		roadMap.get(20).add(roadMap.get(19));
		roadMap.get(20).add(roadMap.get(21));
		roadMap.get(20).add(roadMap.get(30));
		//road 21
		roadMap.get(21).add(roadMap.get(16));
		roadMap.get(21).add(roadMap.get(20));
		roadMap.get(21).add(roadMap.get(22));
		roadMap.get(21).add(roadMap.get(30));
		//road 22
		roadMap.get(22).add(roadMap.get(15));
		roadMap.get(22).add(roadMap.get(16));
		roadMap.get(22).add(roadMap.get(21));
		roadMap.get(22).add(roadMap.get(23));
		//road 23
		roadMap.get(23).add(roadMap.get(15));
		roadMap.get(23).add(roadMap.get(22));
		roadMap.get(23).add(roadMap.get(24));
		roadMap.get(23).add(roadMap.get(28));
		//road 24
		roadMap.get(24).add(roadMap.get(14));
		roadMap.get(24).add(roadMap.get(23));
		roadMap.get(24).add(roadMap.get(25));
		roadMap.get(24).add(roadMap.get(28));
		//road 25
		roadMap.get(25).add(roadMap.get(14));
		roadMap.get(25).add(roadMap.get(24));
		roadMap.get(25).add(roadMap.get(26));
		roadMap.get(25).add(roadMap.get(27));
		//road 26
		roadMap.get(26).add(roadMap.get(25));
		roadMap.get(26).add(roadMap.get(27));
		//road 27
		roadMap.get(27).add(roadMap.get(25));
		roadMap.get(27).add(roadMap.get(26));
		roadMap.get(27).add(roadMap.get(34));
		roadMap.get(27).add(roadMap.get(35));
		//road 28
		roadMap.get(28).add(roadMap.get(23));
		roadMap.get(28).add(roadMap.get(24));
		roadMap.get(28).add(roadMap.get(29));
		roadMap.get(28).add(roadMap.get(33));
		//road 29
		roadMap.get(29).add(roadMap.get(28));
		roadMap.get(29).add(roadMap.get(30));
		roadMap.get(29).add(roadMap.get(32));
		roadMap.get(29).add(roadMap.get(33));
		//road 30
		roadMap.get(30).add(roadMap.get(20));
		roadMap.get(30).add(roadMap.get(21));
		roadMap.get(30).add(roadMap.get(29));
		roadMap.get(30).add(roadMap.get(32));
		//road 31
		roadMap.get(31).add(roadMap.get(18));
		roadMap.get(31).add(roadMap.get(19));
		roadMap.get(31).add(roadMap.get(39));
		roadMap.get(31).add(roadMap.get(40));
		//road 32
		roadMap.get(32).add(roadMap.get(29));
		roadMap.get(32).add(roadMap.get(30));
		roadMap.get(32).add(roadMap.get(38));
		roadMap.get(32).add(roadMap.get(39));
		//road 33
		roadMap.get(33).add(roadMap.get(28));
		roadMap.get(33).add(roadMap.get(29));
		roadMap.get(33).add(roadMap.get(34));
		roadMap.get(33).add(roadMap.get(36));
		//road 34
		roadMap.get(34).add(roadMap.get(27));
		roadMap.get(34).add(roadMap.get(33));
		roadMap.get(34).add(roadMap.get(35));
		roadMap.get(34).add(roadMap.get(36));
		//road 35
		roadMap.get(35).add(roadMap.get(27));
		roadMap.get(35).add(roadMap.get(34));
		//road 36
		roadMap.get(36).add(roadMap.get(33));
		roadMap.get(36).add(roadMap.get(34));
		roadMap.get(36).add(roadMap.get(37));
		roadMap.get(36).add(roadMap.get(42));
		//road 37
		roadMap.get(37).add(roadMap.get(36));
		roadMap.get(37).add(roadMap.get(38));
		roadMap.get(37).add(roadMap.get(41));
		roadMap.get(37).add(roadMap.get(42));
		//road 38
		roadMap.get(38).add(roadMap.get(32));
		roadMap.get(38).add(roadMap.get(37));
		roadMap.get(38).add(roadMap.get(39));
		roadMap.get(38).add(roadMap.get(41));
		//road 39
		roadMap.get(39).add(roadMap.get(31));
		roadMap.get(39).add(roadMap.get(32));
		roadMap.get(39).add(roadMap.get(38));
		roadMap.get(39).add(roadMap.get(40));
		//road 40
		roadMap.get(40).add(roadMap.get(31));
		roadMap.get(40).add(roadMap.get(39));
		//road 41
		roadMap.get(41).add(roadMap.get(37));
		roadMap.get(41).add(roadMap.get(38));
		//road 42
		roadMap.get(42).add(roadMap.get(36));
		roadMap.get(42).add(roadMap.get(37));
	}
	
	/**
	 * getRoadMap() is a static method that creates a RoadMap if it doesn't already exist, otherwise it will return the 
	 * existing road map.
	 * 
	 * @author Andrea Celli
	 */
	
	public static RoadMap getRoadMap(){
		if(completeMap==null){
			completeMap=new RoadMap();
			completeMap.addAdjacentRoads();
		}
		return completeMap;
	}
}