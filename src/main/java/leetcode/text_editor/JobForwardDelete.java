package leetcode.text_editor;

public class JobForwardDelete implements Job{

    int cursor;
    String text;
    int[] selected;

    public JobForwardDelete(int cursor, String text, int[] selected) {
        this.cursor = cursor;
        this.text = text;
        this.selected = selected;
    }

    @Override
    public void redo(TextEditor editor) {
        if (selected[0] == -1) {
            editor.forwardDelete();
        } else {
            editor.delete(selected[0], selected[1]);
        }
    }

    @Override
    public void undo(TextEditor editor) {
        if (selected[0] != -1) {
            editor.move(cursor);
            editor.append(text);
        } else {
            editor.append(text);
        }
    }
}