package it.unical.SudokuEmbASP;

import java.lang.reflect.InvocationTargetException;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv.desktop.DLVDesktopService;

public class Application {

	private static int[][] matrix; //matrice del sudoku
	private static int N=9;
	private static String encodingResourche="pathDelNostroEncoding";
	private static Handler handler; // classe di embASP
	
	public static void main(String[] args) {
		handler=new DesktopHandler(new DLVDesktopService("path dell'eseguibile del nostro dlv"));
		InputProgram facts= new ASPInputProgram(); // perchè usiamo ASP
		for(int i=0;i<N;i++)
			for(int j=0;j<N;j++)
				if(matrix[i][j]!=0) {
					try {
						facts.addObjectInput(new Cell(i,j,matrix[i][j]));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
				}
		handler.addProgram(facts);
		InputProgram encoding=new ASPInputProgram();
		//encoding.addProgram(getEncoding(encodingResourche));
		encoding.addFilesPath(encodingResourche); //prende automanticamente il contenuto del file di encoding
		handler.addProgram(encoding);
		
		handler.startAsync(new MyCallBack(matrix));
		Output o = handler.startSync();
		AnswerSets answers=(AnswerSets) o;
		for(AnswerSet ans :answers.getAnswersets()) {
			try {
				for(Object obj: ans.getAtoms()) {
					if(!(obj instanceof Cell))continue;
					Cell cell=(Cell)obj;
					matrix[cell.getRow()][cell.getColumn()]=cell.getValue();
				}
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void displayMatrix() {
		//strampa la matrice
	}
	
	public String getEncoding(String pathEncodingFile) {
		//restituiscxe una stringa contenente tutto il contenuto del nostro file che contiene l'encoding
		return null;
	}
}
