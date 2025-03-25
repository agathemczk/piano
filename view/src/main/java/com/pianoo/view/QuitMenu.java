package com.pianoo.view;
import com.pianoo.view.IQuitMenu;

public class QuitMenu implements IQuitMenu {
    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }
}
