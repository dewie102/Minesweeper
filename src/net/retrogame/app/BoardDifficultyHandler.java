package net.retrogame.app;

import com.apps.util.Prompter;
import net.retrogame.Difficulty;
import net.retrogame.DifficultyTuple;

class BoardDifficultyHandler {
    private final Prompter prompter;
    
    public BoardDifficultyHandler(Prompter prompter) {
        this.prompter = prompter;
    }
    
    DifficultyTuple promptUserForDifficulty() {
        DifficultyTuple result = null;
        String userInput;
        boolean validInput = false;
        
        System.out.println();
        userInput = prompter.prompt(buildDifficultyPrompt() + "> ").trim();
        
        while(!validInput) {
            if(userInput.matches("\\d")) {
                switch(userInput) {
                    case "1":
                        result = Difficulty.BEGINNER.getDifficultyDefaults();
                        validInput = true;
                        break;
                    case "2":
                        result = Difficulty.INTERMEDIATE.getDifficultyDefaults();
                        validInput = true;
                        break;
                    case "3":
                        result = Difficulty.EXPERT.getDifficultyDefaults();
                        validInput = true;
                        break;
                    case "4":
                        result = getCustomBoardPropertiesFromUser();
                        validInput = true;
                        break;
                }
            }
            
            if(!validInput) {
                userInput = prompter.prompt("Please enter a valid option from the menu.\n> ");
            }
        }
        
        return result;
    }
    
    private String buildDifficultyPrompt() {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Please choose the level difficulty\n");
        
        int count = 1;
        for(Difficulty difficulty : Difficulty.values()) {
            DifficultyTuple difficultyTuple = difficulty.getDifficultyDefaults();
            prompt.append("[").append(count++).append("] ").append(difficulty).append(": Rows: ")
                    .append(difficultyTuple.numberOfRows);
            prompt.append(" Columns: ").append(difficultyTuple.numberOfColumns);
            prompt.append(" Bombs: ").append(difficultyTuple.numberOfBombs);
            prompt.append("\n");
        }
        
        prompt.append("[").append(count).append("] ").append("Custom\n");
        
        return prompt.toString();
    }
    
    private DifficultyTuple getCustomBoardPropertiesFromUser() {
        DifficultyTuple result;
        
        int row = promptUserForNumberToMaxValue("Please choose number of rows (min  2, ", 26, false);
        int column = promptUserForNumberToMaxValue("Please choose number of columns (min  2, ", 99, false);
        int bombs = promptUserForNumberToMaxValue("Please choose number of bombs (min  1, ", (row*column) - 1, true);
        
        result = new DifficultyTuple(row, column, bombs);
        
        return result;
    }
    
    private int promptUserForNumberToMaxValue(String prompt, int maxValue, boolean forBombs) {
        int result = 1;
        
        String userInput;
        boolean validInput = false;
        
        System.out.println();
        userInput = prompter.prompt(prompt + "max  " + maxValue + "): > ").trim();
        
        while(!validInput) {
            if(userInput.matches("[0-9]+")) {
                result = Integer.parseInt(userInput);
                
                if(!forBombs && 2 <= result && result <= maxValue) {
                    validInput = true;
                }
                else if (forBombs && 1 <= result && result <=maxValue){
                    validInput = true;
                }
            }
            
            if(!validInput && forBombs) {
                userInput = prompter.prompt("Please enter a valid number between [1 and " + maxValue + "]\n> ");
            }
            else if (!validInput && !forBombs) {
                userInput = prompter.prompt("Please enter a valid number between [2 and " + maxValue + "]\n> ");
            }
        }
        
        return result;
    }
}