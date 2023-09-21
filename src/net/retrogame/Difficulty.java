package net.retrogame;

public enum Difficulty {
    BEGINNER(9, 9, 10),
    INTERMEDIATE(16, 16, 40),
    EXPERT(16, 30, 99);
    
    private final DifficultyTuple difficultyDefault;
    
    Difficulty(int row, int column, int bombs) {
        difficultyDefault = new DifficultyTuple(row, column, bombs);
    }
    
    public DifficultyTuple getDifficultyDefaults() {
        return difficultyDefault;
    }
}