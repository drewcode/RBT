import java.util.*;

public class RBT<Key extends Comparable<Key>, Value> {
  private static final boolean RED = true;
  private static final boolean BLACK = false;

  private Node root;

  private class Node {
    private Key key;
    private Value val;
    private Node left, right;
    private boolean color;
    private int N;

    public Node(Key key, Value val, boolean color, int N) {
      this.key = key;
      this.val = val;
      this.color = color;
      this.N = N;
    }
  }

  public RBT() {

  }

  private boolean isRed(Node x) {
    if(x == null) return false;
    return x.color == RED;
  }

  private int size(Node x) {
    if (x == null) return 0;
    return x.N;
  }

  public int size() {
    return size(root);
  }

  public boolean isEmpty() {
    return root == null;
  }

  public Value get(Key key) {
    return get(root, key);
  }

  private Value get(Node x, Key key) {
    while (x != null) {
      int cmp = key.compareTo(x.key);
      if      (cmp < 0) x = x.left;
      else if (cmp > 0) x = x.right;
      else              return x.val;
    }
    return null;
  }

  public boolean contains(Key key) {
    return get(key) != null;
  }

  public void put(Key key, Value val) {
    root = put(root, key, val);
    root.color = BLACK;
  }

  private Node put(Node h, Key key, Value val) {
    if (h == null) return new Node(key, val, RED, 1);

    int cmp = key.compareTo(h.key);
    if (cmp < 0) h.left = put(h.left, key, val);
    else if (cmp > 0) h.right = put(h.right, key, val);
    else h.val = val;

    if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
    if (isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
    if (isRed(h.left)  &&  isRed(h.right))     flipColors(h);
    h.N = size(h.left) + size(h.right) + 1;

    return h;
  }

  public void deleteMin() {
    if (isEmpty()) throw new NoSuchElementException("BST underflow");

    // if both children of root are black, set root to red
    if (!isRed(root.left) && !isRed(root.right))
      root.color = RED;
    root = deleteMin(root);
    if (!isEmpty()) root.color = BLACK;
  }

  private Node deleteMin(Node h) {
    if (h.left == null)
      return null;

    if (!isRed(h.left) && !isRed(h.left.left))
      h = moveRedLeft(h);

    h.left = deleteMin(h.left);
    return balance(h);
  }

  public void delete(Key key) {
    if (!contains(key)) {
      System.err.println("symbol table does not contain " + key);
      return;
    }

    // if both children of root are black, set root to red
    if (!isRed(root.left) && !isRed(root.right))
      root.color = RED;

    root = delete(root, key);
    if (!isEmpty()) root.color = BLACK;
    // assert check();
  }

  private Node delete(Node h, Key key) {
    // assert get(h, key) != null;

    if (key.compareTo(h.key) < 0)  {
      if (!isRed(h.left) && !isRed(h.left.left))
        h = moveRedLeft(h);
      h.left = delete(h.left, key);
    } else {
      if (isRed(h.left))
        h = rotateRight(h);
      if (key.compareTo(h.key) == 0 && (h.right == null))
        return null;
      if (!isRed(h.right) && !isRed(h.right.left))
        h = moveRedRight(h);
      if (key.compareTo(h.key) == 0) {
        Node x = min(h.right);
        h.key = x.key;
        h.val = x.val;
        // h.val = get(h.right, min(h.right).key);
        // h.key = min(h.right).key;
        h.right = deleteMin(h.right);
      } else h.right = delete(h.right, key);
    }
    return balance(h);
  }

  // make a left-leaning link lean to the right
    private Node rotateRight(Node h) {
        // assert (h != null) && isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        return x;
    }

    // make a right-leaning link lean to the left
    private Node rotateLeft(Node h) {
        // assert (h != null) && isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        return x;
    }

    // flip the colors of a node and its two children
    private void flipColors(Node h) {
        // h must have opposite color of its two children
        // assert (h != null) && (h.left != null) && (h.right != null);
        // assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
        //    || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    // Assuming that h is red and both h.left and h.left.left
    // are black, make h.left or one of its children red.
    private Node moveRedLeft(Node h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

        flipColors(h);
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    // Assuming that h is red and both h.right and h.right.left
    // are black, make h.right or one of its children red.
    private Node moveRedRight(Node h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
        flipColors(h);
        if (isRed(h.left.left)) {
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    // restore red-black tree invariant
    private Node balance(Node h) {
        // assert (h != null);

        if (isRed(h.right))                      h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))     flipColors(h);

        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("called min() with empty symbol table");
        return min(root).key;
    }

    // the smallest key in subtree rooted at x; null if no such key
    private Node min(Node x) {
        // assert x != null;
        if (x.left == null) return x;
        else                return min(x.left);
    }

    public NodeInfo[] levelOrder() {

      class TraversalInfo {
        Node node;
        int pos;

        public TraversalInfo(Node node, int pos) {
          this.node = node;
          this.pos = pos;
        }
      }



      NodeInfo[] result = new NodeInfo[(int)(Math.pow(2, height() + 1)) - 1 + 1]; // +1 as im starting from 1 index
      LinkedList<TraversalInfo> q = new LinkedList<TraversalInfo>();
      if(root != null) {
        TraversalInfo r = new TraversalInfo(root, 1);
        q.addLast(r);
      }
      while(!q.isEmpty()) {
        TraversalInfo x = q.removeFirst();
        result[x.pos] = new NodeInfo(x.node.val, x.node.color);
        if(x.node.left == null) {
          blankOut(result ,2 * x.pos);
        } else {
          TraversalInfo left = new TraversalInfo(x.node.left, 2 * x.pos);
          q.addLast(left);
        }

        if(x.node.right == null) {
          blankOut(result, (2*x.pos) + 1);
        } else {
          TraversalInfo right = new TraversalInfo(x.node.right, (2*x.pos) + 1);
          q.addLast(right);
        }
      }

      return result;
    }

    private static void blankOut(Object[] result, int pos) {
      if(pos < result.length) {
        result[pos] = null;
        blankOut(result, 2*pos);
        blankOut(result, (2*pos) + 1);
      }
    }

    public int height() {
        return height(root);
    }
    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }
}
