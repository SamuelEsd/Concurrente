package unam.ciencias.computoconcurrente;

public interface ScreenHL {
    /**
     * Clears the whole screen
     */
    void clear();
    /**
     * Writes <i>str</i> at the end of the screen.
     */
    void addRow(String str);
    /**
     * Erases the content of the screen shown at row <i>row</i>, moving all following rows one position above
     */
    void deleteRow(int row);
}
