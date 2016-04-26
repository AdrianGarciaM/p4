package Chess;

public class PiecePosition 
{

	/***
         * Comprueba si la posición que se le indica por parametro está dentro de los márgenes del tablero
         * @param column columna de la posición a comprobar.
         * @param row fila de la posición a comprobar.
         * @return true si la columna y la fila están dentro de los limites del tablero, false en caso contrario.
         */
	public static boolean isAvailable(int column, int row) 
        {
		return column >= 0 && column < 8 && row >= 0 && row < 8;
	}

	/***
         * Comprueba, a partir de la posición de origen de la ficha, si al desplazarse a otra posición se saldría o no del tablero.
         * @param position posición de origen de la ficha.
         * @param columnIncrement desplazamiento en el eje de las columnas de la ficha (puede ser un decremento si el valor es nagativo o incremento si es positivo)
         * @param rowIncrement desplazamiento en el eje de las filas de la ficha (puede ser un decremento si el valor es nagativo o incremento si es positivo)
         * @return true si permaneciera dentro del tablero al desplazarse, flase si se saliera fuera o si la posición de origen es nula.
         */
	static boolean isAvailable(PiecePosition position, int columnIncrement, int rowIncrement) 
        {
		if (position == null)
			return false;
		
		int newColumn = position.getColumn() + columnIncrement;
		int newRow = position.getRow() + rowIncrement;
		return isAvailable(newColumn, newRow);
	}

	/***
         * Comprueba que la posición pasado por parametro esté dentro de los limites del tablero.
         * @param position posición a comprobar si está dentro dentro del tablero.
         * @return true si está dentro del tablero, false si está fuera del tablero o es null.
         */
	static boolean isAvailable(PiecePosition position) 
        {
		if (position == null)
			return false;
		return isAvailable(position.getColumn(), position.getRow());
	}

	private int column, row;

	/***
         * Crea un nuevo objeto de tipo PiecePosition, al cual le indica de inicio su posición en el tablero, la cual está compuesta tanto por la columna como por la fila en la que se encuentra.
         * @param column columna del tablero donde va a estar, si no se altera posteriormente esta variable, la pieza.
         * @param row fila del tablero donde va a estar, si no se altera posteriormente esta variable, la pieza.
         */
	public PiecePosition(int column, int row) 
        {
		this.column = column;
		this.row = row;
	}
	
	/***
         * Devuelve la posición en el eje de las columnas de la pieza.
         * @return la posición en el eje de las columnas de la pieza.
         */
	public int getColumn() 
        {
		return column;
	}

	/***
         * Deuvelve la posición en el eje de las filas de la pieza.
         * @return la posición en el eje de las filas de la pieza.
         */
	public int getRow() 
        {
		return row;
	}
	
        /***
         * Actualiza la posición de la pieza en basa a la indicada por parametro siempre y cuando esta se encuentre dentro de los límites del tablero, lo cual implica que no puede ser nula.
         * @param column posición en el eje de las columnas al que se pretende mover la ficha.
         * @param row posición en el eje de las filas al que se pretende mover la ficha.
         * @return true si se ha podido mover la ficha, false si la ficha no se ha movido.
         */
	public boolean setValues(int column, int row) 
        {
		if (isAvailable(column, row)) 
                {
			this.column = column;
			this.row = row;			
			return true;
		}
		return false;
	}
	
	/***
         * Devuelve una posición de la pieza creada a partir las variables pasadas por parámetro y el valor previamente almacenado en este objeto o null en caso de que esa nueva posición estuviera fuera de los limites del tablero.
         * @param columnCount variación de la posición en el eje de las columnas.
         * @param rowCount variación de la posición en el eje de las filas.
         * @return un nuevo objeto PiecePosition con la posición calculada a partir de la posición almacenada en este objeto y las variaciones indicadas por parámetro o null si estuviera fuera de los limites del tablero.
         */
	public PiecePosition getDisplacedPiece(int columnCount, int rowCount) 
        {		
		if (!isAvailable(this, columnCount, rowCount))
			return null;
		int newColumn = getColumn() + columnCount;
		int newRow = getRow() + rowCount;
		return new PiecePosition(newColumn, newRow);
	}
	
	/***
         * Deuvelve un nuevo objeto con la posición almacenada previamente en este objeto.
         * @return un nuebo objeto PiecePosition a partir del objeto del mismo tipo almacenado en este objeto.
         */
	public PiecePosition clone() 
        {
		return new PiecePosition(column, row);
	}
	
	/***
         * Determina si el objeto PiecePosition pasado por parámetro es igual al objeto del mismo tipo almacenado en este objeto.
         * @param aPosition objeto PiecePosition que se quiere comprobar si es igual al guardado en este objeto.
         * @return true en caso de que los objetos contienen los mismos valores o false en caso contrario.
         */
	public boolean equals(PiecePosition aPosition) 
        {
		return aPosition.getColumn() == getColumn() && aPosition.getRow() == getRow();
	}
}
