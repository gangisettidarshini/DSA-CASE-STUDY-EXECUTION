class PSegNode {
    long sum;
    PSegNode left, right;

    PSegNode(long s, PSegNode l, PSegNode r) {
        sum = s;
        left = l;
        right = r;
    }
}

public class PersistentSegmentTree {

    // Build initial segment tree
    static PSegNode build(int[] arr, int lo, int hi) {

        if (lo == hi)
            return new PSegNode(arr[lo], null, null);

        int mid = (lo + hi) / 2;

        PSegNode left = build(arr, lo, mid);
        PSegNode right = build(arr, mid + 1, hi);

        return new PSegNode(left.sum + right.sum, left, right);
    }

    // Persistent Point Update
    static PSegNode pointUpdate(PSegNode node,
                                int lo,
                                int hi,
                                int idx,
                                long newVal) {

        // Leaf node
        if (lo == hi)
            return new PSegNode(newVal, null, null);

        int mid = (lo + hi) / 2;

        if (idx <= mid) {

            PSegNode newLeft =
                    pointUpdate(node.left,
                                lo,
                                mid,
                                idx,
                                newVal);

            PSegNode sharedRight = node.right;

            return new PSegNode(
                    newLeft.sum + sharedRight.sum,
                    newLeft,
                    sharedRight
            );

        } else {

            PSegNode sharedLeft = node.left;

            PSegNode newRight =
                    pointUpdate(node.right,
                                mid + 1,
                                hi,
                                idx,
                                newVal);

            return new PSegNode(
                    sharedLeft.sum + newRight.sum,
                    sharedLeft,
                    newRight
            );
        }
    }

    // Range Sum Query
    static long rangeSum(PSegNode node,
                         int lo,
                         int hi,
                         int l,
                         int r) {

        if (r < lo || hi < l)
            return 0;

        if (l <= lo && hi <= r)
            return node.sum;

        int mid = (lo + hi) / 2;

        return rangeSum(node.left, lo, mid, l, r)
                + rangeSum(node.right, mid + 1, hi, l, r);
    }

    public static void main(String[] args) {

        int[] stock = {12, 7, 25, 18, 9, 14, 6, 30};

        int n = stock.length;

        // Version v0
        PSegNode v0 = build(stock, 0, n - 1);

        // Update (i): stock[3] += 50
        // position 3 in question = index 2 in Java
        PSegNode v1 = pointUpdate(v0, 0, n - 1, 2, 75);

        // Update (ii): stock[6] -= 4
        // position 6 in question = index 5
        PSegNode v2 = pointUpdate(v1, 0, n - 1, 5, 10);

        // Update (iii): stock[3] -= 12
        // 75 -> 63
        PSegNode v3 = pointUpdate(v2, 0, n - 1, 2, 63);

        System.out.println("Root Sum v0 = " + v0.sum);
        System.out.println("Root Sum v1 = " + v1.sum);
        System.out.println("Root Sum v2 = " + v2.sum);
        System.out.println("Root Sum v3 = " + v3.sum);

        // Accounting Query:
        // Categories 3..6 (1-based)
        // Java indices 2..5

        System.out.println("\nRange Sum [3..6]");

        System.out.println(
                "v0 = " +
                rangeSum(v0, 0, n - 1, 2, 5)
        );

        System.out.println(
                "v1 = " +
                rangeSum(v1, 0, n - 1, 2, 5)
        );

        System.out.println(
                "v2 = " +
                rangeSum(v2, 0, n - 1, 2, 5)
        );

        System.out.println(
                "v3 = " +
                rangeSum(v3, 0, n - 1, 2, 5)
        );
    }
}