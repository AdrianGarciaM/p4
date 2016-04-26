package Chess;

public class ChessPieceImplementation implements ChessPiece
{
    Color pieceColor;
    boolean wasMoved;
    Type pieceType;
    
    public ChessPieceImplementation(Color colorEnt, Type typeEnt)
    {
        pieceColor = colorEnt;
        pieceType = typeEnt;
    }
    
    @Override
    public Color getColor() 
    {
        return pieceColor;
    }

    @Override
    public Type getType() 
    {
        return pieceType;
    }

    @Override
    public void notifyMoved() 
    {
        wasMoved = true;
    }

    @Override
    public boolean wasMoved() 
    {
        return wasMoved;
    }

    @Override
    public PiecePosition[] getAvailablePositions(ChessBoard aBoard) 
    {
        switch(pieceType)
        {
            case KING:
                return ChessMovementManager.getAvailablePositionsOfKing(this, aBoard);
            case QUEEN:
                return ChessMovementManager.getAvailablePositionsOfQueen(this, aBoard);
            case ROOK:
                return ChessMovementManager.getAvailablePositionsOfRook(this, aBoard);
            case BISHOP:
                return ChessMovementManager.getAvailablePositionsOfBishop(this, aBoard);
            case KNIGHT:
                return ChessMovementManager.getAvailablePositionsOfKnight(this, aBoard);
            case PAWN:
                return ChessMovementManager.getAvailablePositionsOfPawn(this, aBoard);
        }
        
        return null;
    }
}
