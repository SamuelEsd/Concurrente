package unam.ciencias.computoconcurrente;

import java.util.concurrent.Semaphore;

public class JScreen2 implements ScreenHL {
    private JScreen d;
    private String[] text;
    private int usedRows;
    private Semaphore disponibles;
    private Semaphore usados;
    private Semaphore mutex;

    public JScreen2() {
        d = new JScreen();
        text = new String[100];
        clear();
        disponibles = new Semaphore(d.getRows());
        disponibles.release(d.getRows());
        usados = new Semaphore(d.getRows());
        mutex = new Semaphore(1);
    }

    @Override
    public void clear() {
        for(int i=0; i < d.getRows(); i++) {
            updateRow(i,"");
        }
        usedRows = 0;
    }

    @Override
    public void addRow(String str) {
        try {
            disponibles.acquire();
            mutex.acquire();
            updateRow(usedRows,str);
            flashRow(usedRows,1000);
            usedRows++;
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
        finally{
            mutex.release();
            usados.release();
        }
    }

    @Override
    public void deleteRow(int ren) {
        try {        
            usados.acquire();
            mutex.acquire();
            if (ren < usedRows) {
                for(int i = ren+1; i < usedRows; i++) {
                    updateRow(i-1,text[i]);
                }
                usedRows--;
                updateRow(usedRows,"");
                if(usedRows >= d.getRows()) {
                    flashRow(d.getRows()-1,1000);
                }
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
        finally{
            mutex.release();
            disponibles.release();
        }
    }

    private void updateRow(int row, String str) {
        text[row] = str;
        if (row < d.getRows()) {
            for(int i=0; i < str.length(); i++) {
                d.write(str.charAt(i), row, i);
            }
            for(int i=str.length(); i < d.getColumns(); i++) {
                d.write(' ', row, i);
            }
        }
    }

    private void flashRow(int row, int milliseconds) {
        String txt = text[row];
        try {
            for (int i= 0; i * 200 < milliseconds; i++) {
                updateRow(row,"");
                Thread.sleep(70);
                updateRow(row,txt);
                Thread.sleep(130);
            }
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
}