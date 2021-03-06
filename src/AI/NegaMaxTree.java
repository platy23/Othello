package AI;
import java.util.ArrayList;
import java.util.List;

import Genetics.Playground;
import cabinet.*;
import game.Move;

/**
 * the algorithm used for the AI to pick the best move 
 */
public class NegaMaxTree {

	GameState gs;
	Move move;
	Integer color; //1 for AI -1 for opponent
	Heuristic h;
	
	/**
	 * @author Jon Wu & Stephen Chern
	 *
	 * Initializes a negamax search tree using Playground.ply's value
	 *
	 * @param gs The root GameState
	 * @param move The move returned upon completion of search
	 * @param h The heuristic for the NegaMax tree to follow
	 */
	public NegaMaxTree(GameState gs,Move move, Heuristic h){
		this.gs=gs;
		this.h = h;
		this.move=move;
		makeMoves(gs, Playground.ply, Double.MIN_VALUE, Double.MAX_VALUE, 1,true);
	}
	
	/**
	 * @author Jon Wu & Stephen Chern
	 *
	 * Initializes a negamax search tree using ply
	 *
	 * @param gs The root GameState
	 * @param ply The number of ply to move
	 * @param move The move returned upon completion of search
	 * @param h The heuristic for the NegaMax tree to follow
	 */
	public NegaMaxTree(GameState gs,int ply, Move move, Heuristic h){
		this.gs=gs;
		this.h = h;
		this.move=move;
		makeMoves(gs, ply, Double.MIN_VALUE, Double.MAX_VALUE, 1,true);
	}

	private double makeMoves(GameState gs, Integer depth,double A,double B, Integer color,boolean isRoot)
	{
		//System.out.println(depth);
		if (depth == 0 || gs.isGameOver())
		{
			return color * h.calculate(gs); //value
		}

		List<Move> moves=gs.getValidMoves();
		double bestMove = -1000000000.0;
		while(!moves.isEmpty()) 
		{
			Move move=moves.remove(0);
			GameState newgs=gs.copyInstance();
			ArrayList<Move> actionMoves=new ArrayList<Move>();
			actionMoves.add(move);
			newgs.makeMove(actionMoves);

			double m = -makeMoves(newgs, depth-1,-B,-A, -color,false);

			if(m>bestMove) {
				bestMove=m;
				if(isRoot) {
					//System.out.println("BEST MOVE YET"+move.to.x+"::"+move.to.y+"::"+isRoot);
					//System.err.println("CHANGING");
					this.move.to.x=move.to.x;
					this.move.to.y=move.to.y;
				}
			}
			A=Math.max(A, bestMove);

			if(A>=B) {
				break;
			}
		}
		return bestMove;
	}

	public Move returnMove()
	{
		return move;
	}
}