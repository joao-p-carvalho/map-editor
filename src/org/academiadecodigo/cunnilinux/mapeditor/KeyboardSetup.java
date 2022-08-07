package org.academiadecodigo.cunnilinux.mapeditor;

import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

import java.io.IOException;

import static org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent.*;

public class KeyboardSetup {
    Grid grid;
    private int keys[];
    private KeyboardHandler kbHandler;
    private Keyboard keyboard;
    private KeyboardEvent[] kbEventPress;
    private KeyboardEvent[] kbEventRelease;


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    public KeyboardSetup(Grid grid) {
        this.grid = grid;
        // KEYS
        keys = new int[]{KEY_LEFT,KEY_RIGHT,KEY_UP,KEY_DOWN,KEY_P,KEY_U,KEY_C,KEY_S,KEY_L};
        kbHandler = new KeyboardHandlerPlayer();
        keyboard = new Keyboard(kbHandler);
        keyPressListener();
        //keyReleaseListenerPlayer();
    }

    //-----------------------------------
    // KEY LISTENERS
    //-----------------------------------
    public void keyPressListener() {
        kbEventPress = new KeyboardEvent[keys.length];
        for (int i = 0; i < keys.length; i++) {
            kbEventPress[i] = new KeyboardEvent();
            kbEventPress[i].setKey(keys[i]);
            kbEventPress[i].setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
            keyboard.addEventListener(kbEventPress[i]);
        }
    }

    //-----------------------------------
    // KB HANDLER
    //-----------------------------------
    public class KeyboardHandlerPlayer implements KeyboardHandler {

        @Override
        public void keyPressed(KeyboardEvent keyboardEvent) {
                switch (keyboardEvent.getKey()) {
                    case KEY_LEFT:
                        if(grid.getSelCol() != 0 ) grid.move(Direction.LEFT);
                        break;
                    case KEY_RIGHT:
                        if(grid.getSelCol() != grid.getCols() - 1 ) grid.move(Direction.RIGHT);
                        break;
                    case KEY_UP:
                        if(grid.getSelRow() != 0 ) grid.move(Direction.UP);
                        break;
                    case KEY_DOWN:
                        if(grid.getSelRow() != grid.getRows() - 1 ) grid.move(Direction.DOWN);
                        break;
                    case KEY_P:
                        grid.paint();
                        break;
                    case KEY_U:
                        grid.unPaint();
                        break;
                    case KEY_C:
                        grid.clear();
                        break;
                    case KEY_S:
                        try {
                            grid.save();
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case KEY_L:
                        grid.load();
                        break;
                }

        }

        @Override
        public void keyReleased(KeyboardEvent keyboardEvent) {
            }

    }


}
