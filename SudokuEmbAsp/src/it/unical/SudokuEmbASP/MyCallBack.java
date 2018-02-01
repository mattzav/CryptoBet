package it.unical.SudokuEmbASP;

import it.unical.mat.embasp.base.Callback;
import it.unical.mat.embasp.base.Output;

public class MyCallBack implements Callback { //interfaccia di libreria

	private int[][] matrix;
	
	public MyCallBack(int[][] matrix) {
		super();
		this.matrix = matrix;
	}


	@Override
	public void callback(Output arg0) {
		// TODO Auto-generated method stub
		//funzione che viene chiamata alla fine del calcolo di ogni answer set
				//potrei mettere la stampa dell'output
				//usare il risultato del mio encoding
				//serve per la chiamata asincrona del calcolo dell'answer set
					
	}
}
