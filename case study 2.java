class AVLNode {
    int key;
    AVLNode left, right;
    int height = 1;

    AVLNode(int key) {
        this.key = key;
    }

    static int height(AVLNode n) {
        return n == null ? 0 : n.height;
    }

    static int balance(AVLNode n) {
        return n == null ? 0 : height(n.left) - height(n.right);
    }

    static void updateHeight(AVLNode n) {
        if (n != null)
            n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    // Right Rotation
    static AVLNode rotateRight(AVLNode y) {

        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    // Left Rotation
    static AVLNode rotateLeft(AVLNode x) {

        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    // AVL Insert
    static AVLNode insert(AVLNode node, int key) {

        // Standard BST Insert
        if (node == null)
            return new AVLNode(key);

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else
            return node;

        updateHeight(node);

        int bf = balance(node);

        // LL Case
        if (bf > 1 && key < node.left.key)
            return rotateRight(node);

        // RR Case
        if (bf < -1 && key > node.right.key)
            return rotateLeft(node);

        // LR Case
        if (bf > 1 && key > node.left.key) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // RL Case
        if (bf < -1 && key < node.right.key) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    // Inorder Traversal
    static void inorder(AVLNode root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.key + " ");
            inorder(root.right);
        }
    }

    public static void main(String[] args) {

        int[] ids = {
                20, 30, 35, 40, 45, 50,
                60, 65, 70, 75, 80, 85, 90
        };

        AVLNode root = null;

        for (int x : ids)
            root = insert(root, x);

        System.out.println("Inorder Traversal:");
        inorder(root);

        System.out.println("\n\nRoot = " + root.key);
        System.out.println("Height = " + root.height);
    }
}