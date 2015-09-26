package aStar;

import java.util.ArrayList;
import java.util.Collections;

import aStar.heuristics.AStarHeuristic;
import aStar.utils.Logger;



public class AStar {
        private AreaMap map;
        private AStarHeuristic heuristic;
        //private int startX;
        //private int startY;
        //private int goalX;
        //private int goalY;
        /**
         * closedList The list of Nodes not searched yet, sorted by their distance to the goal as guessed by our heuristic.
         */
        private ArrayList<Node> closedList;
        private SortedNodeList openList;
        private Path shortestPath;
        Logger log = new Logger();

        public AStar(AreaMap map, AStarHeuristic heuristic) {
                this.map = map;
                this.heuristic = heuristic;

                closedList = new ArrayList<Node>();
                openList = new SortedNodeList();
        }

        public Path calcShortestPath(int startX, int startY, int goalX, int goalY) {
                //this.startX = startX;
                //this.startY = startY;
                //this.goalX = goalX;
                //this.goalY = goalY;

                map.setStartLocation(startX, startY);
                map.setGoalLocation(goalX, goalY);

                if (map.getNode(goalX, goalY).isObstacle) {
                        return null;
                }

                map.getStartNode().setDistanceFromStart(0);
                closedList.clear();
                openList.clear();
                openList.add(map.getStartNode());

                while(openList.size() != 0) {

                        
                        Node current = openList.getFirst();

                        
                        if(current.getX() == map.getGoalLocationX() && current.getY() == map.getGoalLocationY()) {
                                return reconstructPath(current);
                        }

                        
                        openList.remove(current);
                        closedList.add(current);

                        
                        for(Node neighbor : current.getNeighborList()) {
                                boolean neighborIsBetter;

                              
                                if (closedList.contains(neighbor))
                                        continue;

                              
                                if (!neighbor.isObstacle) {

                                       
                                        float neighborDistanceFromStart = (current.getDistanceFromStart() + map.getDistanceBetween(current, neighbor));

                                       
                                        if(!openList.contains(neighbor)) {
                                                openList.add(neighbor);
                                                neighborIsBetter = true;
                                                
                                        } else if(neighborDistanceFromStart < current.getDistanceFromStart()) {
                                                neighborIsBetter = true;
                                        } else {
                                                neighborIsBetter = false;
                                        }
                                       
                                        if (neighborIsBetter) {
                                                neighbor.setPreviousNode(current);
                                                neighbor.setDistanceFromStart(neighborDistanceFromStart);
                                                neighbor.setHeuristicDistanceFromGoal(heuristic.getEstimatedDistanceToGoal(neighbor.getX(), neighbor.getY(), map.getGoalLocationX(), map.getGoalLocationY()));
                                        }
                                }

                        }

                }
                return null;
        }

        
        
        public void printPath() {
                Node node;
                for(int x=0; x<map.getMapWith(); x++) {

                        if (x==0) {
                                for (int i=0; i<=map.getMapWith(); i++)
                                        System.out.print("-");
                                System.out.println();   
                        }
                        System.out.print("|");

                        for(int y=0; y<map.getMapHeight(); y++) {
                                node = map.getNode(x, y);
                                if (node.isObstacle) {
                                        System.out.print("X");
                                } else if (node.isStart) {
                                        System.out.print("s");
                                } else if (node.isGoal) {
                                        System.out.print("g");
                                } else if (shortestPath.contains(node.getX(), node.getY())) {
                                        System.out.print("�");
                                } else {
                                        System.out.print(" ");
                                }
                                if (y==map.getMapHeight())
                                        System.out.print("_");
                        }

                        System.out.print("|");
                        System.out.println();
                }
                for (int i=0; i<=map.getMapWith(); i++)
                        System.out.print("-");
        }

        private Path reconstructPath(Node node) {
                Path path = new Path();
                while(!(node.getPreviousNode() == null)) {
                        path.prependWayPoint(node);
                        node = node.getPreviousNode();
                }
                this.shortestPath = path;
                return path;
        }

        private class SortedNodeList {

                private ArrayList<Node> list = new ArrayList<Node>();

                public Node getFirst() {
                        return list.get(0);
                }

                public void clear() {
                        list.clear();
                }

                public void add(Node node) {
                        list.add(node);
                        Collections.sort(list);
                }

                public void remove(Node n) {
                        list.remove(n);
                }

                public int size() {
                        return list.size();
                }

                public boolean contains(Node n) {
                        return list.contains(n);
                }
        }

}
