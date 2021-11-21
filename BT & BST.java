// 105
class Solution {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return helper(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }
    
    private TreeNode helper(int[] preorder, int preStart, int preEnd, int[] inorder, int inStart, int inEnd) {
        if (inStart > inEnd) {
            return null;
        }
        
        TreeNode root = new TreeNode(preorder[preStart]);
        int index = findRoot(inorder, root.val, inStart, inEnd);
        
        root.left = helper(preorder, preStart + 1, preStart + index - inStart, inorder, inStart, index - 1);
        root.right = helper(preorder, preStart + 1 + index - inStart, preEnd, inorder, index + 1, inEnd);
        
        return root;
    }
    
    private int findRoot(int[] inorder, int num, int start, int end) {
        for (int i = start; i <= end; i++) {
            if (inorder[i] == num) {
                return i;
            }
        }
        return -1;
    }
}

// 236
public class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root.val == p.val || root.val == q.val) {
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) {
            return root;
        }
        if (left != null) {
            return left;
        }
        if (right != null) {
            return right;
        }
        return null;
    }
}

// 314
class Solution {
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Map<Integer, List<Integer>> map = new HashMap<>();
        
        int min = 0;
        int max = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        Queue<Integer> level = new LinkedList<>();
        queue.offer(root);
        level.offer(0);
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                int currLevel = level.poll();
                List<Integer> list = map.getOrDefault(currLevel, new ArrayList<>());
                list.add(node.val);
                map.put(currLevel, list);
                if (node.left != null) {
                    min = Math.min(currLevel - 1, min);
                    queue.offer(node.left);
                    level.offer(currLevel - 1);
                }
                if (node.right != null) {
                    max = Math.max(currLevel + 1, max);
                    queue.offer(node.right);
                    level.offer(currLevel + 1);
                }
            }     
        }

        for (int i = min; i <= max; i++) {
            res.add(map.get(i));
        }
        
        return res;
    }
}

// 297
class Codec {
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) {
            return "null";
        }
        String left = serialize(root.left);
        String right = serialize(root.right);
        return String.valueOf(root.val) + "," + left + "," + right;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] strs = data.split(",");
        Queue<String> queue = new LinkedList<>(Arrays.asList(strs));
        return helper(queue);
    }
    
    private TreeNode helper(Queue<String> queue) {
        String str = queue.poll();
        if (str.equals("null")) {
            return null;
        }
        TreeNode root = new TreeNode(Integer.valueOf(str));
        root.left = helper(queue);
        root.right = helper(queue);
        return root;
    }
}

// 199
class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();        
        helper(res, 0, root);
        return res;
    }
    
    private void helper(List<Integer> res, int level, TreeNode node) {
        if (node == null) {
            return;
        }
        if (level == res.size()) {
            res.add(node.val);
        }
        helper(res, level + 1, node.right);
        helper(res, level + 1, node.left);
    }
}

// 426
class Solution {
    public Node treeToDoublyList(Node root) {
        if (root == null) {
            return null;
        }
        
        Node leftHead = treeToDoublyList(root.left);
        Node rightHead = treeToDoublyList(root.right);
        root.left = root;
        root.right = root;
        return connect(connect(leftHead, root), rightHead);
    }
    

    private Node connect(Node n1, Node n2) {
        if (n1 == null) {
            return n2;
        }
        if (n2 == null) {
            return n1;
        }
        
        Node tail1 = n1.left;
        Node tail2 = n2.left;
        
        tail1.right = n2;
        n2.left = tail1;
        tail2.right = n1;
        n1.left = tail2;
        
        return n1;
    }
}