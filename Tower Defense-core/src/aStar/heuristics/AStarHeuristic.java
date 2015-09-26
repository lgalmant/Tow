package aStar.heuristics;

public interface AStarHeuristic {

        
        public float getEstimatedDistanceToGoal(int startX, int startY, int goalX, int goalY);
}