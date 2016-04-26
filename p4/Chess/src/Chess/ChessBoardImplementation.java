package Chess;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;

public class ChessBoardImplementation implements ChessBoard {

	ChessPiece	pieces[] = new ChessPiece[8 * 8];
	
	public ChessBoardImplementation() {
		for (int i = 0; i < 8; i++) {
			pieces[getPieceIndex(i, 1)] = new ChessPieceImplementation(ChessPiece.Color.WHITE, ChessPiece.Type.PAWN);
			pieces[getPieceIndex(i, 6)] = new ChessPieceImplementation(ChessPiece.Color.BLACK, ChessPiece.Type.PAWN);
		}
		pieces[getPieceIndex(0, 0)] = new ChessPieceImplementation(ChessPiece.Color.WHITE, ChessPiece.Type.ROOK);
		pieces[getPieceIndex(7, 0)] = new ChessPieceImplementation(ChessPiece.Color.WHITE, ChessPiece.Type.ROOK);
		pieces[getPieceIndex(0, 7)] = new ChessPieceImplementation(ChessPiece.Color.BLACK, ChessPiece.Type.ROOK);
		pieces[getPieceIndex(7, 7)] = new ChessPieceImplementation(ChessPiece.Color.BLACK, ChessPiece.Type.ROOK);

		pieces[getPieceIndex(1, 0)] = new ChessPieceImplementation(ChessPiece.Color.WHITE, ChessPiece.Type.KNIGHT);
		pieces[getPieceIndex(6, 0)] = new ChessPieceImplementation(ChessPiece.Color.WHITE, ChessPiece.Type.KNIGHT);
		pieces[getPieceIndex(1, 7)] = new ChessPieceImplementation(ChessPiece.Color.BLACK, ChessPiece.Type.KNIGHT);
		pieces[getPieceIndex(6, 7)] = new ChessPieceImplementation(ChessPiece.Color.BLACK, ChessPiece.Type.KNIGHT);

		pieces[getPieceIndex(2, 0)] = new ChessPieceImplementation(ChessPiece.Color.WHITE, ChessPiece.Type.BISHOP);
		pieces[getPieceIndex(5, 0)] = new ChessPieceImplementation(ChessPiece.Color.WHITE, ChessPiece.Type.BISHOP);
		pieces[getPieceIndex(2, 7)] = new ChessPieceImplementation(ChessPiece.Color.BLACK, ChessPiece.Type.BISHOP);
		pieces[getPieceIndex(5, 7)] = new ChessPieceImplementation(ChessPiece.Color.BLACK, ChessPiece.Type.BISHOP);

		pieces[getPieceIndex(3, 0)] = new ChessPieceImplementation(ChessPiece.Color.WHITE, ChessPiece.Type.KING);
		pieces[getPieceIndex(4, 0)] = new ChessPieceImplementation(ChessPiece.Color.WHITE, ChessPiece.Type.QUEEN);
		pieces[getPieceIndex(3, 7)] = new ChessPieceImplementation(ChessPiece.Color.BLACK, ChessPiece.Type.QUEEN);
		pieces[getPieceIndex(4, 7)] = new ChessPieceImplementation(ChessPiece.Color.BLACK, ChessPiece.Type.KING);
	}
	
	@Override
	public ChessPiece[] getPieces(ChessPiece.Color PieceColor) {
		int count = 0;
		for (ChessPiece piece : pieces)
			if (piece != null && piece.getColor() == PieceColor)
				count++;

		if (count == 0)
			return null;
		
		ChessPiece[] result = new ChessPiece[count];
		count = 0;
		for (ChessPiece piece : pieces)
			if (piece != null && piece.getColor() == PieceColor)
				result[count++] = piece;

		return result;
	}
	
	private	int getPieceIndex(int column, int row) {
		return row * 8 + column;
	}

	private	int getPieceIndex(PiecePosition position) {
		return position.getRow() * 8 + position.getColumn();
	}

	private	ChessPiece getPiece(int column, int row) {
		int index = getPieceIndex(column, row);
		return pieces[index];
	}

	@Override
	public ChessPiece getPieceAt(PiecePosition position) {
		if (!PiecePosition.isAvailable(position))
			return null;
		return getPiece(position.getColumn(), position.getRow());
	}

	@Override
	public PiecePosition getPiecePosition(ChessPiece APiece) {
		for (int row = 0; row < 8; row++)
			for (int column = 0; column < 8; column++)
				if (getPiece(column, row) == APiece)
					return new PiecePosition(column, row);
		return null;
	}

	@Override
	public void removePieceAt(PiecePosition Position) {
		pieces[getPieceIndex(Position.getColumn(), Position.getRow())] = null;
	}

	@Override
	public boolean movePieceTo(ChessPiece Piece, PiecePosition Position) {
		PiecePosition oldPosition = getPiecePosition(Piece);
		if (oldPosition != null) {
			int oldIndex = getPieceIndex(oldPosition);
			int newIndex = getPieceIndex(Position);
			pieces[oldIndex] = null;
			pieces[newIndex] = Piece;
			Piece.notifyMoved();
			return true;
		}
		return false;
	}

	@Override
	public boolean containsKing(ChessPiece.Color PieceColor) {
		for (ChessPiece piece : pieces) 
			if (piece != null && piece.getType() == ChessPiece.Type.KING && piece.getColor() == PieceColor)
				return true;
		return false;
	}

	@Override
	public boolean saveToFile(File location) 
        {
            if (location != null) 
            {
                String[] tagLines = new String[6];
                String[] closeTagLines = new String[6];
                
                tagLines[0] = "   <piece>\n";
                closeTagLines[0] = "   </piece>\n";
                
                tagLines[1] = "      <color>";
                closeTagLines[1] = "</color>\n";
                tagLines[2] = "      <moved>";
                closeTagLines[2] = "</moved>\n";
                tagLines[3] = "      <type>";
                closeTagLines[3] = "</type>\n";
                tagLines[4] = "      <column>";
                closeTagLines[4] = "</column>\n";
                tagLines[5] = "      <row>";
                closeTagLines[5] = "</row>\n";
                
                Charset charset = Charset.forName("US-ASCII");
                String xmlLine = "<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?>\n";
                
                try (BufferedWriter writer = Files.newBufferedWriter(location.toPath(), charset)) 
                {
                    writer.write(xmlLine, 0, xmlLine.length());
                    writer.write("<chess>\n", 0, 8);
                    for(int i = 0; i < 8; i++)
                    {
                        for(int j = 0; j < 8; j++)
                        {
                            ChessPiece savingPiece = this.getPieceAt(new PiecePosition(i,j));
                            if(savingPiece != null)
                            {
                                String savingColor = savingPiece.getColor().toString();
                                String wasMoved = booleanToString(savingPiece.wasMoved());
                                String savingType = savingPiece.getType().toString();

                                writer.write(tagLines[0], 0, tagLines[0].length());

                                writer.write(tagLines[1], 0, tagLines[1].length());
                                writer.write(savingColor,0,savingColor.length());
                                writer.write(closeTagLines[1], 0, closeTagLines[1].length());

                                writer.write(tagLines[2], 0, tagLines[2].length());
                                writer.write(wasMoved,0,wasMoved.length());
                                writer.write(closeTagLines[2], 0, closeTagLines[2].length());

                                writer.write(tagLines[3], 0, tagLines[3].length());
                                writer.write(savingType,0,savingType.length());
                                writer.write(closeTagLines[3], 0, closeTagLines[3].length());

                                writer.write(tagLines[4], 0, tagLines[4].length());
                                writer.write(String.valueOf(i) ,0, 1);
                                writer.write(closeTagLines[4], 0, closeTagLines[4].length());

                                writer.write(tagLines[5], 0, tagLines[5].length());
                                writer.write(String.valueOf(j) ,0, 1);
                                writer.write(closeTagLines[5], 0, closeTagLines[5].length());

                                writer.write(closeTagLines[0], 0, closeTagLines[0].length());
                            }                            
                        }
                    }
                    writer.write("</chess>", 0, 8);
                    return true;
                } 
                catch (IOException x) 
                {
                    System.err.format("IOException: %s%n", x);
                    return false;
                }
            }
            return true;
	}

	@Override
	public boolean loadFromFile(File location) 
        {
            if (location != null) 
            {
                try 
                {
                    Scanner in = new Scanner(location);
                    
                    String lineRead = in.nextLine(); // La primera linea incluye info de xml, no es util
                    lineRead = in.nextLine();
                    
                    if(lineRead.equals("<chess>"))
                    {
                        resetPieces();
                        lineRead = in.nextLine();
                        while(lineRead.contains("<piece>"))
                        {
                            ChessPiece loadingPiece;
                            Chess.ChessPieceImplementation.Color color;
                            boolean wasMoved;
                            Chess.ChessPieceImplementation.Type type;
                            String column;
                            String row;
                            
                            if(in.nextLine().contains("WHITE"))
                                color = Chess.ChessPieceImplementation.Color.WHITE;
                            else
                                color = Chess.ChessPieceImplementation.Color.BLACK;
                            
                            wasMoved = in.nextLine().contains("true");

                            if(in.nextLine().contains("KING"))
                                type = Chess.ChessPieceImplementation.Type.KING;
                            else if(in.nextLine().contains("QUEEN"))
                                type = Chess.ChessPieceImplementation.Type.QUEEN;
                            else if(in.nextLine().contains("ROOK"))
                                type = Chess.ChessPieceImplementation.Type.ROOK;
                            else if(in.nextLine().contains("BISHOP"))
                                type = Chess.ChessPieceImplementation.Type.BISHOP;
                            else if(in.nextLine().contains("KNIGHT"))
                                type = Chess.ChessPieceImplementation.Type.KNIGHT;
                            else
                                type = Chess.ChessPieceImplementation.Type.PAWN;
                            
                            column = in.nextLine().replace(" ", "");
                            column = column.replace("<column>", "");
                            column = column.replace("</column>", "");
                            
                            row = in.nextLine().replace(" ", "");
                            row = row.replace("<row>", "");
                            row = row.replace("</row>", "");
                            
                            pieces[getPieceIndex(Integer.parseInt(column) , Integer.parseInt(row))] = new ChessPieceImplementation(color, type);
                        }
                        return true;
                    }
                    return false;
                } 
                
                catch (IOException x) 
                {
                    System.err.format("IOException: %s%n", x);
                    return false;
                }
            }
            
            return false;
        }
	
        private void resetPieces()
        {
            for(int i = 0; i < 8; i++)
            {
                for(int j = 0; j < 8; j++)
                {
                    pieces[getPieceIndex(i, j)] = null;
                }
            }
        }
        
        private String booleanToString(boolean booleanEnt)
        {
            if(booleanEnt)
                return "true";
            else
                return "false";
        }
}
