package leetcode.serialize_deserialize_tree;

import javax.swing.tree.TreeNode;
import java.util.LinkedList;
import java.util.Queue;

public class Codec {

    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (! queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node != null) {
                sb.append(node.val).append(",");
                queue.add(node.left);
                queue.add(node.right);
            } else {
                sb.append("null,");
            }
        }

        return sb.toString(); // the last element is null.
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] arr = data.split(",");
        if (arr.length == 0 || arr[0].equals("null")) return null;

        TreeNode root = new TreeNode(Integer.parseInt(arr[0]));

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int i = 1;
        while (! queue.isEmpty() && i < arr.length - 1) {
            TreeNode node = queue.poll();
            node.left = arr[i].equals("null") ? null : new TreeNode(Integer.parseInt(arr[i]));
            node.right = arr[i+1].equals("null") ? null : new TreeNode(Integer.parseInt(arr[i+1]));
            if (node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
            i += 2;
        }

        return root;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));