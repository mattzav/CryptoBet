package it.unical.SudokuEmbASP;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("cell") //specifiva che i nostri oggeti cell sono mappati come predicati chiamati cell
public class Cell {

	@Param(0) //indica che è il primo termine del predicato cell
	private int row;
	@Param(1)
	private int column;
	@Param(2)
	private int value;
	
	public Cell() {}
	
	public Cell(int row, int column,int value) {
		super();
		this.row = row;
		this.column = column;
		this.value = value;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	
}
