import javax.swing.*;
import java.awt.*;
//Dawson Stroik
//Final Assignment
//5/11/2023
//Creates a new window with custom background and font color, it also is able to be closed out of without closing everything

public class Rules extends JFrame {
    public Rules(){
        JFrame frame = new JFrame("Chess Rules");
        frame.setSize(700, 500);

        JTextArea rules = new JTextArea();
        rules.setBackground(Color.BLUE);
        rules.setForeground(Color.WHITE);
        rules.setText("Chess Rules:\n\n" +
                "1. Each player starts with 16 pieces: 1 king, 1 queen, 2 rooks, 2 knights, 2 bishops, and 8 pawns.\n" +
                "2. The objective of the game is to checkmate the opponent's king.\n" +
                "3. A player's turn consists of moving one piece to a different square on the board.\n" +
                "4. A piece may only move according to its specific rules. For example, a rook may move horizontally or vertically, while a knight may move in an L-shape.\n" +
                "5. A player cannot make a move that puts or leaves their own king in check.\n" +
                "6. If a player's king is in check, they must make a move to get out of check.\n" +
                "7. The game ends when a player's king is in checkmate, meaning it is in a position to be captured and there is no way to remove it from attack.\n");

        JScrollPane scrollPane = new JScrollPane(rules);
        frame.getContentPane().add(scrollPane);

        frame.setVisible(true);

    }
}


