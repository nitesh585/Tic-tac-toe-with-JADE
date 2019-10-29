import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import jade.core.behaviours.TickerBehaviour;
import jade.core.Agent;

public class GUI{
	public String msg = null;
	public int flag = 0;
	public JFrame frame;
	public JButton[][] boxes = new JButton[3][3];
	public String player, opponent; 
	public String ans="";
    public JLabel lable;
    public String turn="";
    public String[][] board;
	public Move move;
	
	public class Move 
    { 
        int row, col; 
    };
      
   boolean isMovesLeft(String board[][]) { 
        for (int i = 0; i<3; i++) 
            for (int j = 0; j<3; j++) 
                if (board[i][j].equals(" ")) 
                    return true; 
        return false; 
    } 
    
    boolean hasXWon(String[][] b) {
    	for (int row = 0; row<3; row++) { 
            if (b[row][0]==b[row][1] && b[row][1]==b[row][2]) { 
                if (b[row][0]=="X") 
                    return true; 
            }
    	}
         
    	  for (int col = 0; col<3; col++) { 
              if (b[0][col]==b[1][col] && b[1][col]==b[2][col]) { 
                  if (b[0][col]=="X") 
                      return true;  
              } 
          } 
    	  
    	  if (b[0][0]==b[1][1] && b[1][1]==b[2][2] && b[0][0]=="X" )
              return true;
    	  if (b[0][2]==b[1][1] && b[1][1]==b[2][0] && b[0][2]=="X" )
              return true;
        
    	return false;
    }
    
    boolean hasOWon(String[][] b) {
    	for (int row = 0; row<3; row++) { 
            if (b[row][0]==b[row][1] && b[row][1]==b[row][2]) { 
                if (b[row][0]=="O") 
                    return true; 
            }
    	}
         
    	  for (int col = 0; col<3; col++) { 
              if (b[0][col]==b[1][col] && b[1][col]==b[2][col]) { 
                  if (b[0][col]=="O") 
                      return true;  
              } 
          } 
    	  
    	  if (b[0][0]==b[1][1] && b[1][1]==b[2][2] && b[0][0]=="O" )
              return true;
    	  if (b[0][2]==b[1][1] && b[1][1]==b[2][0] && b[0][2]=="O" )
              return true;
        
    	return false;
    }
    
    int evaluate(String b[][]) 
    { 
        // Checking for Rows for X or O victory. 
        for (int row = 0; row<3; row++) 
        { 
            if (b[row][0]==b[row][1] && 
                b[row][1]==b[row][2]) 
            { 
                if (b[row][0]==player) 
                    return 10; 
                else if (b[row][0]==opponent) 
                    return -10; 
            } 
        } 
      
        // Checking for Columns for X or O victory. 
        for (int col = 0; col<3; col++) 
        { 
            if (b[0][col]==b[1][col] && 
                b[1][col]==b[2][col]) 
            { 
                if (b[0][col]==player) 
                    return 10; 
      
                else if (b[0][col]==opponent) 
                    return -10; 
            } 
        } 
      
        // Checking for Diagonals for X or O victory. 
        if (b[0][0]==b[1][1] && b[1][1]==b[2][2]) 
        { 
            if (b[0][0]==player) 
                return 10; 
            else if (b[0][0]==opponent) 
                return -10; 
        } 
      
        if (b[0][2]==b[1][1] && b[1][1]==b[2][0]) 
        { 
            if (b[0][2]==player) 
                return 10; 
            else if (b[0][2]==opponent) 
                return -10; 
        } 
      
        return 0; 
    } 
      
    int minimax(String board[][], int depth,boolean isMax) 
    { 
        int score = evaluate(board); 
      
        if (score == 10) 
            return score; 
    
        if (score == -10) 
            return score; 
      
        if (isMovesLeft(board)==false) 
            return 0; 
      
        if (isMax) 
        { 
            int best = -1000; 
      
            // Traverse all cells 
            for (int i = 0; i<3; i++) 
            { 
                for (int j = 0; j<3; j++) 
                { 
                    // Check if cell is empty 
                    if (board[i][j].equals(" ")) 
                    { 
                        // Make the move 
                        board[i][j] = player; 
      
                        best = Math.max( best, minimax(board, 0,!isMax) ); 
      
                        board[i][j] = " "; 
                    } 
                } 
            } 
            return best-depth; 
        } 
      
        else
        { 
            int best = 1000; 
      
            // Traverse all cells 
            for (int i = 0; i<3; i++) 
            { 
                for (int j = 0; j<3; j++) 
                { 
                    // Check if cell is empty 
                    if (board[i][j].equals(" ")) 
                    { 
                        // Make the move 
                        board[i][j] = opponent; 
      
                        best = Math.min(best, minimax(board,0, !isMax)); 
      
                        board[i][j] = " "; 
                    } 
                } 
            } 
            return best+depth; 
        } 
    } 
      
    Move findBestMove(String board[][],boolean ismax) { 
    	    int bestVal = -1000; 
    	    Move bestMove = new Move(); 
    	    bestMove.row = -1; 
    	    bestMove.col = -1; 
        for (int i = 0; i<3; i++) 
        { 
            for (int j = 0; j<3; j++) 
            { 
                // Check if cell is empty 
                if (board[i][j].equals(" ")) 
                { 
                    // Make the move 
                    board[i][j] = player; 
                    int moveVal = minimax(board,0, ismax); 
      
                    board[i][j] = " "; 
                    if (moveVal > bestVal) 
                    { 
                        bestMove.row = i; 
                        bestMove.col = j; 
                        bestVal = moveVal; 
                    } 
                } 
            } 
        }
      
        System.out.println("The value of the best Move is : %d\n\n"+bestVal); 
      
        return bestMove; 
    } 
     
    public void printboard(String[][] b) {
    	for(int i=0;i<3;i++) {
    		for(int j=0;j<3;j++) {
    			System.out.print(b[i][j]+" ");
    		}
    		System.out.println();
    	}
    }
    
    public void boardSet(String[][] b) {
    	for(int i=0;i<3;i++) {
    		for(int j=0;j<3;j++) {
    			b[i][j] = " ";
    		}
    	}
    }
    
    
    public void disAbleAll(JButton[][] b) {
    	for(int i=0;i<3;i++) {
    		for(int j=0;j<3;j++) {
    			b[i][j].setEnabled(false);
    		}
    	}
    }
    
    public void enAbleAll(JButton[][] b) {
    	for(int i=0;i<3;i++) {
    		for(int j=0;j<3;j++) {
    			b[i][j].setEnabled(true);
    		}
    	}
    }
    
    
    public GUI() {
		// TODO Auto-generated method stub
		frame = new JFrame();

		ans = JOptionPane.showInputDialog("Which character you want to choose O or X ?");
		if(ans.equals("O")) {
			player = "O";
			opponent = "X";
			turn = "player";
		}else {
			player = "X";
			opponent = "O";
			turn = "opponent";
		}
		
		if(ans!=null) {
			lable = new JLabel("");
			lable.setBounds(300, 30, 250, 100);
		
		//adding boxes
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				boxes[i][j] = new JButton(" ");
				boxes[i][j].setBounds((j + 1) * 150, (i + 1) * 150, 150, 150);
				boxes[i][j].setBackground(Color.ORANGE);
				boxes[i][j].setForeground(Color.BLACK);
                boxes[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
				final Integer in = new Integer(i);
				final Integer jn = new Integer(j);
				
				boxes[i][j].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if(player.equals("O") & turn.equals("player")) {
							boxes[in][jn].setText("O");
							board[in][jn]="O";
						}
						
						if(player.equals("X") & turn.equals("player")) {
							boxes[in][jn].setText("X");
							board[in][jn]="X";
						}
						
						if(turn.equals("player")){
					         turn ="opponent";
				        }
					}
					
				});
				
				frame.add(boxes[i][j]);
			}
		}
		

        lable.setFont(new Font("Arial", Font.PLAIN, 24));
		
		frame.add(lable);
		
		JButton play = new JButton("Replay");
		play.setBounds(300, 650, 150, 80);
		play.setBackground(Color.WHITE);
		play.setForeground(Color.BLACK);
        play.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				for(int i=0;i<3;i++) {
					for(int j=0;j<3;j++) {
						boxes[i][j].setText(" ");
					}
				}
				
				ans = JOptionPane.showInputDialog("Which character you want to choose O or X ?");
				if(ans.equals("O")) {
					player = "O";
					opponent = "X";
					turn = "player";
				}else {
					player = "X";
					opponent = "O";
					turn = "opponent";
				}
				boardSet(board);
				lable.setText("");
				enAbleAll(boxes);
			}
        	
        });
		
		
		frame.add(play);
		frame.setSize(750, 800);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board = new String[3][3];
		boardSet(board);
	   }
	}
}
