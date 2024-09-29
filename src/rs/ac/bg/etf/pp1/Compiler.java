package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import java_cup.runtime.Symbol;

public class Compiler {
	
	static {
		DOMConfigurator.configure("config/log4j.xml");
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}

	public static void main(String[] args) {
		Logger logger = Logger.getLogger(Compiler.class);
		if (args.length < 1) {
			logger.error("Path to the program file for compilation not specified.");
			System.exit(1);
		}
		try (
			FileReader fileReader = new FileReader(args[0]);
			BufferedReader bufferedReader = new BufferedReader(fileReader)
        ) {
			logger.debug("Compiling source file: " + args[0]);
			Yylex lexer = new Yylex(bufferedReader);
			MJParser parser = new MJParser(lexer);
			Symbol abstractSyntaxTreeRootSymbol = parser.parse();
			if (parser.isSyntaxAnalysisErrorDetected()) {
				logger.error("Syntax analysis has failed, aborting compilation.");
				System.exit(2);
			}
			logger.debug("Syntax analysis has completed successfully.");
			Program program = (Program)abstractSyntaxTreeRootSymbol.value;
			logger.debug("Start of Abstract Syntax Tree (AST).");
			logger.debug(program.toString(""));
			logger.debug("End of Abstract Syntax Tree (AST).");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
