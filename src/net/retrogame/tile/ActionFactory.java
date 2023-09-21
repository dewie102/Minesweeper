package net.retrogame.tile;

import java.util.EnumMap;

public class ActionFactory {
    private static final EnumMap<Tool, Action> actionOptions = new EnumMap<>(Tool.class);
    
    private ActionFactory() {
    }
    
    public static Action get(Tool tool) {
        if(!actionOptions.containsKey(tool)) {
            switch (tool) {
                case CLICK:
                    actionOptions.put(tool, new Click());
                    break;
                case FLAG:
                    actionOptions.put(tool, new Flag());
                    break;
            }
        }
        
        return actionOptions.get(tool);
    }
}