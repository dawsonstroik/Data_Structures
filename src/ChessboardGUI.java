import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
//Dawson Stroik
//Final Assignment
//5/11/2023
/*This file is crowded, but it does most of the work. First it creates the gui with 8 by 8 buttons
It colors the buttons, then it places the pieces on the buttoms randomly and assignments them a color
It waits for a button click and finds what piece was seleted then calls that method.
If an empty square is selected Rules.java is called and the gui with rules pops up
When another piece is selected clearboard() goes and clears the highlighted region from the board
The rook and bishop class look complicated at first, but in reality they are simple when broken down
The queen just uses the rook and bishop classes together
 */

public class ChessboardGUI extends JFrame {
    public JButton[][] button;

    public ChessboardGUI() {
        //setting up gui
        setTitle("Chessboard");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(8, 8));

        button = new JButton[8][8];
        //creating array of buttons
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton currentButton = new JButton();
                currentButton.setFocusPainted(false);
                currentButton.setBorder(null);
                currentButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //clears board of any highlighted spaces
                        clearHighlight();
                        JButton clickedButton = (JButton) e.getSource();
                        int row = -1;
                        int col = -1;
                        //Finding the row and column of the button that was pressed
                        for (int i = 0; i < 8; i++) {
                            for (int j = 0; j < 8; j++) {
                                if ((button[i][j] == clickedButton)) {
                                    row = i;
                                    col = j;
                                    break;
                                }
                            }
                            if (row != -1 && col != -1) {
                                break;
                            }
                        }
                        //bunch of if statements determining what piece was selected. then the pieces class is called and moves are highlighted
                        if (currentButton.getText().contains("KB") || currentButton.getText().contains("KW")) {
                            king(row, col);
                        } else if (currentButton.getText().contains("KN")) {
                            knight(row, col);
                        } else if (currentButton.getText().contains("BB") || currentButton.getText().contains("BW")) {
                            bishop(row, col);
                        } else if (currentButton.getText().contains("R")) {
                            rook(row, col);
                        } else if (currentButton.getText().contains("P")) {
                            pawn(row, col);
                        } else if (currentButton.getText().contains("Q")) {
                            //Since queen has rook and bishop moves this is much simpler than creating a queen class to address the moves
                            rook(row, col);
                            bishop(row, col);
                        } else {
                            Rules rule = new Rules();
                            System.out.println("It is empty");
                        }
                    }
                });

                //sets background colors for board
                if ((row + col) % 2 == 0) {
                    currentButton.setBackground(Color.decode("#E4E0BD"));
                } else {
                    currentButton.setBackground(Color.decode("#797979"));
                }
                contentPane.add(currentButton);
                this.button[row][col] = currentButton;
            }
        }

        //Calls the class to add white and black pieces to the board a black knight =KB
        addPieces(Color.BLACK);
        addPieces(Color.WHITE);

        setVisible(true);
    }


    //bishop first calculates what moves can be made then highlights possible moves. One problem I could not find a fix for was the top left sqaure always gets highlighted
    private void bishop(int row, int col) {
        String text = button[row][col].getText();
        int[][] possibleMoves = new int[13][2];

        //Calculates diagonal moves
        //each for loop goes out each direction
        for (int i = 1; i <= 7; i++) {
            if (row + i <= 7 && col + i <= 7) {
                if (button[row + i][col + i].getText().equals("")) {
                    possibleMoves[i - 1] = new int[]{row + i, col + i};
                } else {
                    if ((text.contains("W") && button[row + i][col + i].getText().contains("B")) ||
                            (text.contains("B") && button[row + i][col + i].getText().contains("W"))) {
                        possibleMoves[i - 1] = new int[]{row + i, col + i};
                        break;
                    } else {
                        break;
                    }
                }
            } else {
                break;
            }
        }
        for (int i = 1; i <= 7; i++) {
            if (row + i <= 7 && col - i >= 0) {
                possibleMoves[6 + i] = new int[]{row + i, col - i};
                if (!button[row + i][col - i].getText().equals("") &&
                        ((text.contains("W") && button[row + i][col - i].getText().contains("B")) ||
                                (text.contains("B") && button[row + i][col - i].getText().contains("W")))) {
                    possibleMoves[6 + i] = new int[]{row + i, col - i};
                    break;
                }
            } else {
                break;
            }
        }

        for (int i = 1; i <= 7; i++) {
            if (row - i >= 0 && col + i <= 7) {
                possibleMoves[12 - i] = new int[]{row - i, col + i};
                if (!button[row - i][col + i].getText().equals("") &&
                        ((text.contains("W") && button[row - i][col + i].getText().contains("B")) ||
                                (text.contains("B") && button[row - i][col + i].getText().contains("W")))) {
                    possibleMoves[12 - i] = new int[]{row - i, col + i};
                    break;
                }
            } else {
                break;
            }
        }
        for (int i = 1; i <= 7; i++) {
            if (row - i >= 0 && col - i >= 0) {
                possibleMoves[6 - i] = new int[]{row - i, col - i};
                if (!button[row - i][col - i].getText().equals("") &&
                        ((text.contains("W") && button[row - i][col - i].getText().contains("B")) ||
                                (text.contains("B") && button[row - i][col - i].getText().contains("W")))) {
                    possibleMoves[6 - i] = new int[]{row - i, col - i};
                    break;
                } else if (button[row - i][col - i].getText().equals("")) {
                    continue;
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        //I am not sure what is wrong but the top left corner gets highlighted when I click on any bishop and I cant find the error
        for (int[] move : possibleMoves) {
            int moveRow = move[0];
            int moveCol = move[1];
            if (moveRow >= 0 && moveRow <= 7 && moveCol >= 0 && moveCol <= 7) {
                String moveText = button[moveRow][moveCol].getText();
                if (moveText.equals("") || (text.contains("W") && moveText.contains("B")) || (text.contains("B") && moveText.contains("W"))) {
                    button[moveRow][moveCol].setBackground(Color.yellow);
                }
            }
        }
    }

    //calculates the moves horizontally and vertically for the rook
    private void rook(int row, int col) {
        String text = button[row][col].getText();
        int[][] possibleMoves = new int[28][2];

        // horizontal moves to the left
        for (int i = 1; i <= 7; i++) {
            if (col - i >= 0) {
                String nextButton = button[row][col - i].getText();
                if (nextButton.equals("")) {
                    possibleMoves[7 + i] = new int[]{row, col - i};
                } else {
                    if ((text.contains("W") && nextButton.contains("B")) ||
                            (text.contains("B") && nextButton.contains("W"))) {
                        possibleMoves[7 + i] = new int[]{row, col - i};
                    }
                    break;
                }
            } else {
                break;
            }
        }

            //horizontal moves to the right
        for (int i = 1; i <= 7; i++) {
            if (col + i <= 7) {
                String nextButton = button[row][col + i].getText();
                if (nextButton.equals("")) {
                    possibleMoves[7 - i] = new int[]{row, col + i};
                } else {
                    if ((text.contains("W") && nextButton.contains("B")) ||
                            (text.contains("B") && nextButton.contains("W"))) {
                        possibleMoves[7 - i] = new int[]{row, col + i};
                    }
                    break;
                }
            } else {
                break;
            }
        }

            //vertical moves upwards
        for (int i = 1; i <= 7; i++) {
            if (row - i >= 0) {
                String nextButton = button[row - i][col].getText();
                if (nextButton.equals("")) {
                    possibleMoves[21 + i] = new int[]{row - i, col};
                } else {
                    if ((text.contains("W") && nextButton.contains("B")) ||
                            (text.contains("B") && nextButton.contains("W"))) {
                        possibleMoves[21 + i] = new int[]{row - i, col};
                    }
                    break;
                }
            } else {
                break;
            }
        }

        //vertical moves downwards
        for (int i = 1; i <= 7; i++) {
            if (row + i <= 7) {
                String nextButton = button[row + i][col].getText();
                if (nextButton.equals("")) {
                    possibleMoves[21 - i] = new int[]{row + i, col};
                } else {
                    if ((text.contains("W") && nextButton.contains("B")) ||
                            (text.contains("B") && nextButton.contains("W"))) {
                        possibleMoves[21 - i] = new int[]{row + i, col};
                    }
                    break;
                }
            } else {
                break;
            }
        }
        //Highlights moves
        //Similar to bishop the upper left corner gets highlighted and I cant seem to find a fix for it
        for (int[] move : possibleMoves) {
            int moveRow = move[0];
            int moveCol = move[1];
            if (moveRow >= 0 && moveRow <= 7 && moveCol >= 0 && moveCol <= 7) {
                String moveText = button[moveRow][moveCol].getText();
                if (moveText.equals("") || (text.contains("W") && moveText.contains("B")) || (text.contains("B") && moveText.contains("W"))) {
                    button[moveRow][moveCol].setBackground(Color.yellow);
                }
            }
        }

    }

    //checks spaces that are 2 by 1 or 1 by 2 away from it
    public void knight(int row, int col) {
        String text = button[row][col].getText();
        int[][] possibleMoves = {{row - 2, col - 1}, {row - 2, col + 1}, {row - 1, col - 2}, {row - 1, col + 2},
                {row + 1, col - 2}, {row + 1, col + 2}, {row + 2, col - 1}, {row + 2, col + 1}};
        for (int[] move : possibleMoves) {
            int moveRow = move[0];
            int moveCol = move[1];
            if (moveRow >= 0 && moveRow <= 7 && moveCol >= 0 && moveCol <= 7) {
                String moveText = button[moveRow][moveCol].getText();
                if (moveText.equals("") || (text.contains("W") && moveText.contains("B")) || (text.contains("B") && moveText.contains("W"))) {
                    button[moveRow][moveCol].setBackground(Color.yellow);
                }
            }
        }
    }


    //king class allows possible moves to capture opposite color pieces
    //checks all the spaces around it
    private void king(int row, int col) {
        String text = button[row][col].getText();
        int[][] possibleMoves = {{row - 1, col - 1}, {row - 1, col}, {row - 1, col + 1}, {row, col - 1},
                {row, col + 1}, {row + 1, col - 1}, {row + 1, col}, {row + 1, col + 1}};
        for (int[] move : possibleMoves) {
            int moveRow = move[0];
            int moveCol = move[1];
            if (moveRow >= 0 && moveRow <= 7 && moveCol >= 0 && moveCol <= 7) {
                String moveText = button[moveRow][moveCol].getText();
                if (moveText.equals("") || (text.contains("W") && moveText.contains("B")) || (text.contains("B") && moveText.contains("W"))) {
                    button[moveRow][moveCol].setBackground(Color.yellow);
                }
            }
        }
    }

    //pawn does not implement capturing pieces but black and white pieces move in the opposite direction like the actual game
    private void pawn(int row, int col) {
        String text = button[row][col].getText();
        if (text.contains("W")) {
            if (row > 0 && button[row - 1][col].getText().equals("")) {
                button[row - 1][col].setBackground(Color.yellow);
            }
        }
        if (text.contains("B")) {
            if (row > 0 && button[row + 1][col].getText().equals("")) {
                button[row + 1][col].setBackground(Color.yellow);
            }
        }
    }
//clears the board of highlights
    private void clearHighlight() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton button = this.button[row][col];
                if ((row + col) % 2 == 0) {
                    button.setBackground(Color.decode("#E4E0BD"));
                } else {
                    button.setBackground(Color.decode("#3D4346"));
                }
            }
        }
    }
//Use a hashset to gather pairs for placement on board then use the list and asign B for black or W for white for each piece
    private void addPieces(Color color) {
        ArrayList<String> pieces = new ArrayList<String>(
                Arrays.asList("K", "Q", "B", "B", "KN", "KN", "R", "R")
        );
        Collections.shuffle(pieces);

        Random random = new Random();
        Set<String> pairs = new HashSet<>();

        int index = 0;
        while (index < 16) {
            int x = random.nextInt(8);
            int y = random.nextInt(8);

            //check if the position is already occupied
            if (!button[x][y].getText().equals("")) {
                continue;
            }

            //add the piece to the valid position
            if (index == 0 || index == 7) {
                button[x][y].setText("R" + (color == Color.BLACK ? "B" : "W"));
                pieces.remove(0);
            } else if (index == 1 || index == 6) {
                button[x][y].setText("KN" + (color == Color.BLACK ? "B" : "W"));
            } else if (index == 2 || index == 5) {
                button[x][y].setText("B" + (color == Color.BLACK ? "B" : "W"));
            } else if (index == 3) {
                button[x][y].setText("Q" + (color == Color.BLACK ? "B" : "W"));
            } else if (index == 4) {
                button[x][y].setText("K" + (color == Color.BLACK ? "B" : "W"));
            } else {
                button[x][y].setText("P" + (color == Color.BLACK ? "B" : "W"));
            }

            index++;
        }
    }
}






