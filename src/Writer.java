import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Writer {
 File file;
 FileWriter fw;
 BufferedWriter bw;
	public static void main() {
		

	}
	void setFile(String dir) throws IOException{
		file=new File(dir);
		fw= new FileWriter(file,true);
		bw= new BufferedWriter(fw);
	}
	void createFile(String dir) throws IOException{
		PrintWriter pw = new PrintWriter(dir);
		pw.close();
	}
	void write(String text) throws IOException{
		bw.write(text);
		bw.flush();
	}
    void stop() throws IOException{
    	bw.close();
    }
    void newLine() throws IOException {
    	bw.newLine();
    	bw.flush();
    }
}
