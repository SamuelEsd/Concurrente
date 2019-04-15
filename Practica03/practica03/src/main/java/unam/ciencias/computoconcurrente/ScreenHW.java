package unam.ciencias.computoconcurrente;

public interface ScreenHW {
    /***
     * Return the rows that this screen has
     */
    int getRows();
    /**
     * Return the columns that this screen has
     */
    int getColumns();
    /**
     * Writes <i>character</i> in the position <i>(row, column)</i>
     */
    void write(char character, int row, int column);
}
