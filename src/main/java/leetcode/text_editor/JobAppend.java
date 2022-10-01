package leetcode.text_editor;

public class JobAppend implements Job {

    int cursor;
    int[] selected;
    String text;

    public JobAppend(int cursor, String text, int[] selected) {
        this.cursor = cursor;
        this.text = text;
        this.selected = selected;
    }

    @Override
    public void redo(TextEditor editor) {
        editor.move(cursor);
        editor.append(text);
    }

    @Override
    public void undo(TextEditor editor) {
        editor.delete(cursor, cursor + text.length());
        editor.move(cursor);
    }
}

