package chessComponent;

import view.Chessboard;

public class CartoonOver extends Thread{
    private  SquareComponent chess1, chess2;
    private CartoonChessComponent cartoonChess;
    private Chessboard chessboard;

    public CartoonOver(SquareComponent chess1, SquareComponent chess2, CartoonChessComponent cartoonChess, Chessboard chessboard) {
        this.chess1 = chess1;
        this.chess2 = chess2;
        this.cartoonChess = cartoonChess;
        this.chessboard = chessboard;
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
    }

    @Override
    public void run() {
//        synchronized (chessboard.getChessBoardDatas()) {
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (!(chess2 instanceof EmptySlotComponent)) {
                chessboard.remove(chess2);
                chessboard.add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), chessboard.getClickController(), chessboard.CHESS_SIZE));
            }

            chess1.swapLocation(chess2);
            int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
            chessboard.getChessComponents()[row1][col1] = chess1;
            int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
            chessboard.getChessComponents()[row2][col2] = chess2;

            chess1.setVisible(true);
            chess2.setVisible(true);
            chess1.repaint();
            chess2.repaint();

            cartoonChess.setVisible(false);
        }
//    }
}
