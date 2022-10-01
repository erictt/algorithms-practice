package leetcode.text_editor;

public class JobMove implements Job{

    int cursor;
    int position;

    public JobMove(int cursor, int position) {
        this.cursor = cursor;

        this.position = position;
    }

    @Override
    public void redo(TextEditor editor) {
        editor.move(position);
    }

    @Override
    public void undo(TextEditor editor) {
        editor.move(cursor);
    }
}

