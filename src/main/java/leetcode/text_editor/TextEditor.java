package leetcode.text_editor;

/*
1. APPEND <text> from currenet cursor position
2. MOVE <position> move cursor to position, start from 0. need to deal with out bound position
3. FORWARD_DELETE remove one char after cursor

4. SELECT [<left> <right>) exclude right
5. COPY copy selected text into clipboard
6. PASTE append clipboard into cursor

7. UNDO restore the state before the prev mod
8. REDO redo
    * only the action changed content
*/

import java.util.Stack;

public class TextEditor {

    StringBuilder content;
    int cursor;

    int[] selected;

    String clipboard = null;

    Stack<Job> undoStack;
    Stack<Job> redoStack;

    TextEditor() {
        content = new StringBuilder();
        cursor = 0;
        selected = new int[]{-1, -1};
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    void append(String text) {

        undoStack.add(new JobAppend(cursor, text, selected));
        redoStack.clear();
        if (alreadySelected()) {
            delete(selected[0], selected[1]);
            resetSelect();
        }
        content.insert(cursor, text);
        cursor += text.length();
    }

    void move(int position) {

        if (position < 0) position = 0;
        else if (position >= content.length()) position = content.length();

        undoStack.add(new JobMove(cursor, position));
        cursor = position;
        if (alreadySelected()) {
            resetSelect();
        }
    }

    void forwardDelete() {
        if (cursor >= content.length()) return;

        String text = content.substring(cursor, cursor+1);
        if (alreadySelected()) text = content.substring(selected[0], selected[1]);
        undoStack.add(new JobForwardDelete(cursor, text, selected));
        if (!alreadySelected()) {
            content.deleteCharAt(cursor);
        } else {
            content.delete(selected[0], selected[1]);
            resetSelect();
        }
    }

    void select(int left, int right) {
        if (left < 0) left = 0;
        if (right > content.length()) right = content.length();
        selected = new int[]{left, right};
        cursor = left;
    }

    void copy() {
        //  remains selected after copy
        if (alreadySelected()) {
            clipboard = content.substring(selected[0], selected[1]);
        }
    }

    void paste() {
        if (clipboard != null) {
            append(clipboard);
            // resetSelect();
        }
    }

    void undo() {
        if (undoStack.empty()) return;
        while (undoStack.peek().getClass() == JobMove.class) undoStack.pop();
        if (undoStack.empty()) return;
        Job job = undoStack.pop();
        job.undo(this);
        redoStack.add(job);
    }

    void redo() {
        if (redoStack.empty()) return;
        Job job = redoStack.pop();
        job.redo(this);
        undoStack.add(job);
    }

    boolean alreadySelected() {
        return selected[0] != -1;
    }

    void resetSelect() {
        selected = new int[]{-1, -1};
    }

    void delete(int left, int right) {
        content = new StringBuilder(content.substring(0, left) + content.substring(right, content.length()));
    }

    String getContent() {
        return content.toString();
    }

    void printContent() {
        for (int i = 0; i < content.length(); i++) {
            if (cursor == i) System.out.print("|");
            System.out.print(content.charAt(i));
        }
        if (cursor == content.length()) System.out.print("|");
        System.out.println();
    }
}

