package application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
public class FileManager {
    
        private Gson jsonParser;
		
	public FileManager()
	{
                this.jsonParser = new GsonBuilder().setPrettyPrinting().create();
                this.createBaseDirectory();
	}
        
        public void saveAutomata(String fileName, Automata automata) throws IOException {
            Writer writer = new FileWriter("savedData/" + fileName + ".json");
            this.jsonParser.toJson(automata,writer);   
            writer.close();
        }
        
        public Automata getAutomata(String fileName) throws FileNotFoundException {
            FileReader reader = new FileReader("savedData/" + fileName);
            Automata m = this.jsonParser.fromJson(reader, Automata.class);
            return m;
        }
        
        private void createBaseDirectory() {
            new File("savedData").mkdir();
        }
}
