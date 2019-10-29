import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public class PlayAgent extends Agent{

	
	protected void setup() {
		System.out.println("Hello new world ");
		GUI g = new GUI();
		addBehaviour(new  TickerBehaviour(this,500) {

			@Override
			protected void onTick() {
				
				if(g.turn=="opponent") {
				   if(g.opponent=="O") {
					   if(g.hasXWon(g.board)) {
						   g.lable.setText("You Won");
						   g.disAbleAll(g.boxes);
						   g.turn=" ";g.player = " ";
					   }
					   if(g.turn=="opponent") {
					   g.move = g.findBestMove(g.board,false);
					   g.board[g.move.row][g.move.col]="O";
					   g.boxes[g.move.row][g.move.col].setText("O");
					   g.turn = "player";
					   }
					   if(g.hasOWon(g.board)) {
						   g.lable.setText("Computer Won");
						   g.disAbleAll(g.boxes);
						   g.turn=" ";g.player=" ";
					   }
				   }
				   
				   if(g.opponent=="X") {
					   if(g.hasOWon(g.board)) {
						   g.lable.setText("You Won");
						   g.disAbleAll(g.boxes);
						   g.turn=" ";g.player=" ";
					   }
					   if(g.turn=="opponent") {
					   g.move = g.findBestMove(g.board,true);
					   g.board[g.move.row][g.move.col]="X";
					   g.boxes[g.move.row][g.move.col].setText("X");
					   g.turn = "player";
					   if(g.hasXWon(g.board)) {
						   g.lable.setText("Computer Won");
						   g.disAbleAll(g.boxes);
						   g.turn=" ";g.player = " ";
					   } 
					 }
				   }
				}
			}
		});	
	}
	
}
