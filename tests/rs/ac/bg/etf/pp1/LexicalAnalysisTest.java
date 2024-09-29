package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.util.Log4JUtils;

public class LexicalAnalysisTest {

	static {
		DOMConfigurator.configure("config/log4j.xml");
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static void main(String[] args) throws IOException {
		Logger log = Logger.getLogger(LexicalAnalysisTest.class);
		Reader br = null;
		try {
			
			File sourceCode = new File("tests/official_microjava_programs/test303.mj");
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			
			log.info("Starting lexical analysis.");
			
			Yylex lexer = new Yylex(br);
			Symbol currToken = null;
			while ((currToken = lexer.next_token()).sym != sym.EOF) {
				if (currToken != null && currToken.value != null) {
					log.info("New token created(token class id: '" + currToken + "'; token value: '" + currToken.value.toString() + "').");
				} else if (currToken != null) {
					log.info("New token created(token class id: '" + currToken + "').");
				}
			}
			
			log.info("Ending lexical analysis.");
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}
	}
	
}
