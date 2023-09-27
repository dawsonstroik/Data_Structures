//Dawson Stroik
//Final Assignment
//5/11/2023

public class Main {
    public static void main(String[] args) {
        ChessboardGUI board = new ChessboardGUI();
    }
}
/*There is only one issue I have found in the code and that is whenever a bishop, rook or queen is selected the
top left square gets highlighted. The weird part is there was a few times I tested it, and it did not do it, I tried
many things to fix it, but I couldn't find a way. I am positive there is something simple I am missing, but it is what it is.
I did implement different color pieces by adding B for black and W for White to the letters. I also made opposite colored pieces get highlighted
when in a possible movement square and friendly pieces not highlighted since taking an enemy piece is a valid move. I did not do this for
pawns because it became very complicated with only being able to take diagonally while only being able to move vertically. However, black and white
pawns move it opposite directs just like the actual game of chess, so that makes up for that. Since it wasn't specified in the assignment that
individual pieces needed to be movable I didn't do it, but it wouldn't take too many more lines of code to implement that with what I have here.


 */