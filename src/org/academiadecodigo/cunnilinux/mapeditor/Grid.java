package org.academiadecodigo.cunnilinux.mapeditor;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Grid {
    private int rows;
    private int cols;
    private Cell[][] cell;
    private int cellSize;
    // ROW COL HIGHLIGHTED
    private int selRow;
    private int selCol;
    public int getRows() {return rows;}
    public int getCols() {return cols;}
    public int getSelCol() {return selCol;}
    public int getSelRow() {return selRow;}
    public void unSelect() {
        if (cell[selCol][selRow].isPainted) paint();
        else cell[selCol][selRow].sq.draw();
    }

    public void select() {
        cell[selCol][selRow].sq.setColor(Color.GRAY);
        cell[selCol][selRow].sq.fill();
    }


    // CONSTRUCTOR FOR NEW GRID
    public Grid(int cellSize, int cols, int rows) {
        this.cellSize = cellSize;
        this.cols = cols;
        this.rows = rows;
        buildNew(cols, rows);
    }

    public void buildNew(int cols, int rows) {
        cell = new Cell[cols][rows];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                cell[i][j] = new Cell(i, j);
            }
        }
        // SET HIGHLIGHT
        selRow = 4;
        selCol = 4;
        select();
    }


    public void move(Direction direction) {
        switch (direction) {
            case LEFT:
                unSelect();
                selCol--;
                select();
                break;
            case RIGHT:
                unSelect();
                selCol++;
                select();
                break;
            case UP:
                unSelect();
                selRow--;
                select();
                break;
            case DOWN:
                unSelect();
                selRow++;
                select();
        }
    }

    public void paint() {
        cell[selCol][selRow].sq.setColor(Color.BLACK);
        cell[selCol][selRow].sq.fill();
        cell[selCol][selRow].isPainted = true;
    }

    public void unPaint() {
        cell[selCol][selRow].sq.draw();
        cell[selCol][selRow].isPainted = false;
    }

    public void clear() {
        for (Cell[] row : cell) {
            for (Cell c : row) {
                System.out.println(c.isPainted);
                if (c.isPainted) {
                    c.sq.draw();
                    c.isPainted = false;
                }
                if (c.col == selCol && c.row == selRow) select();
            }
        }
    }

    public void save() throws IOException {
        String fileSave = "src/save.txt";
        FileOutputStream outputStream = new FileOutputStream(fileSave);
        String nCols = cols + "";
        String nRows = rows + "";
        outputStream.write(nRows.getBytes(StandardCharsets.UTF_8));
        outputStream.write('\n');
        outputStream.write(nCols.getBytes(StandardCharsets.UTF_8));
        outputStream.write('\n');
        for (Cell[] row : cell) {
            for (Cell c : row) {
                System.out.println(c.isPainted);
                outputStream.write(c.isPainted ? "1".getBytes(StandardCharsets.UTF_8) : "0".getBytes(StandardCharsets.UTF_8));
            }
        }
        outputStream.close();
    }

    public void load() {
        String fileSave = "src/save.txt";
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileSave));
            // LINE 1: NUMBER OF COLUMNS
            String line = reader.readLine();
            int nCols = Integer.parseInt(line);
            // LINE 2: NUMBER OF ROWS
            line = reader.readLine();
            int nRows = Integer.parseInt(line);
            // LINE 3: PAINTED TRUE/FALSE
            line = reader.readLine();

            //------------------------------------------
            // IF NUMBER OF COLUMNS AND ROWS IS THE SAME:
            if (nCols == cols && nRows == rows) {
                String[] pBin = line.split("");
                int i = 0;
                int j = 0;
                for (String p : pBin) {
                    if (p.compareTo("1") == 0 ) {
                        selCol = i;
                        selRow = j;
                        paint();
                    }
                    if (j < cols - 1) j++;
                    else {
                        j = 0;
                        if(i < rows - 1) i++;
                    }
                }
            }
            //------------------------------------------
            // IF NUMBER OF COLUMNS AND ROWS IS NOT THE SAME (PREVIOUS SAVE):
            else {
                cols = nCols;
                rows = nRows;
                buildNew(nCols, nRows);
                load();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load2() throws IOException {
        String fileSave = "src/save.txt";
        File file = new File(fileSave);
        FileInputStream inputStream = new FileInputStream(file);
        int fileLength = (int) file.length();
        byte[] data = new byte[fileLength];
        boolean[] output = new boolean[fileLength];

        inputStream.read(data);
        int j = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] != 0) {
                //              c[][] = true;
                continue;
            }
            output[i] = false;
        }

        for (Cell[] row : cell) {
            for (Cell c : row) {
                inputStream.read();
            }
        }
        inputStream.close();
    }


    private class Cell {
        private int col;
        private int row;
        private Rectangle sq;
        private boolean isPainted;

        public Cell(int col, int row) {
            this.col = col;
            this.row = row;
            sq = new Rectangle(col * cellSize, row * cellSize, cellSize, cellSize);
            isPainted = false;
            sq.draw();
        }
    }

}
