import java.util.Iterator;

import components.binarytree.BinaryTree;
import components.binarytree.BinaryTree1;
import components.set.Set;
import components.set.SetSecondary;

/**
 * {@code Set} represented as a {@code BinaryTree} (maintained as a binary
 * search tree) of elements with implementations of primary methods.
 *
 * @param <T>
 *            type of {@code Set} elements
 * @mathdefinitions <pre>
 * IS_BST(
 *   tree: binary tree of T
 *  ): boolean satisfies
 *  [tree satisfies the binary search tree properties as described in the
 *   slides with the ordering reported by compareTo for T, including that
 *   it has no duplicate labels]
 * </pre>
 * @convention IS_BST($this.tree)
 * @correspondence this = labels($this.tree)
 *
 * @author Akshay Anand and Alexander Nistor
 *
 */
public class Set3a<T extends Comparable<T>> extends SetSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Elements included in {@code this}.
     */
    private BinaryTree<T> tree;

    /**
     * Returns whether {@code x} is in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be searched for
     * @return true if t contains x, false otherwise
     * @requires IS_BST(t)
     * @ensures isInTree = (x is in labels(t))
     */
    private static <T extends Comparable<T>> boolean isInTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        //Initialize boolean to track whether object x is in BinaryTree t
        boolean inTree = false;

        //If-statement checks if binary tree is empty before checking for the root
        if (t.size() > 0) {
            //If-statement checks if current root is the inputed object x, else
            //the binary tree is disassembled and checked
            if (t.root().compareTo(x) == 0) {
                inTree = true;
            } else {

                //Initializes left and right binary trees for holding current tree's
                //left and right subtrees
                BinaryTree<T> left = t.newInstance();
                BinaryTree<T> right = t.newInstance();

                //Initializes variable to hold current root
                T tempNode = t.disassemble(left, right);

                //If-statement checks which subtree x would be in and updates boolean
                //based on compareTo value
                if (tempNode.compareTo(x) > 0) {
                    inTree = isInTree(left, x);
                } else {
                    inTree = isInTree(right, x);
                }

                //Puts binary tree back together
                t.assemble(tempNode, left, right);

            }
        }

        return inTree;
    }

    /**
     * Inserts {@code x} in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be inserted
     * @aliases reference {@code x}
     * @updates t
     * @requires IS_BST(t) and x is not in labels(t)
     * @ensures IS_BST(t) and labels(t) = labels(#t) union {x}
     */
    private static <T extends Comparable<T>> void insertInTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        //If-statement checks if current tree's size isn't 0
        //If true the tree will be traversed recursively
        //If false then a new tree will be created
        if (t.size() != 0) {

            //Initializes left and right binary trees to be used in traversing tree
            //or making a new one
            BinaryTree<T> left = t.newInstance();
            BinaryTree<T> right = t.newInstance();

            //Stores current node for later reassembling and disassembles current tree
            T tempNode = t.disassemble(left, right);

            //If-statement checks whether x object is less than root
            //If true then the left tree is traversed recursively
            //If false then the right tree is traversed recursively
            if (tempNode.compareTo(x) > 0) {
                insertInTree(left, x);
            } else {
                insertInTree(right, x);
            }

            //Reassembles binary tree
            t.assemble(tempNode, left, right);
        } else {

            //Initializes empty left and right binary trees for assembling insertion tree
            BinaryTree<T> emptyLeft = t.newInstance();
            BinaryTree<T> emptyRight = t.newInstance();

            //Creates tree with object x as the root
            t.assemble(x, emptyLeft, emptyRight);
        }

    }

    /**
     * Removes and returns the smallest (left-most) label in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove the label
     * @return the smallest label in the given {@code BinaryTree}
     * @updates t
     * @requires IS_BST(t) and |t| > 0
     * @ensures <pre>
     * IS_BST(t)  and  removeSmallest = [the smallest label in #t]  and
     *  labels(t) = labels(#t) \ {removeSmallest}
     * </pre>
     */
    private static <T> T removeSmallest(BinaryTree<T> t) {
        assert t != null : "Violation of: t is not null";
        assert t.size() > 0 : "Violation of: |t| > 0";

        //Initializes object variable with current root
        T removed;

        //Initialize left and right binary trees for disassembling t
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();

        //Stores current node for reassembling later
        T tempNode = t.disassemble(left, right);

        //If-statement checks if left tree is empty
        //If true then the current root is the smallest node and the new tree is
        //the right subtree
        //If false the tree will be recursively traversed
        if (left.size() == 0) {

            //Removed becomes the current root
            removed = tempNode;

            //Transfers right subtree to t and removes the current root
            t.transferFrom(right);

        } else {

            //Recursively runs through left subtree
            removed = removeSmallest(left);

            //Reassembles binary tree t
            t.assemble(tempNode, left, right);
        }

        return removed;
    }

    /**
     * Finds label {@code x} in {@code t}, removes it from {@code t}, and
     * returns it.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove label {@code x}
     * @param x
     *            the label to be removed
     * @return the removed label
     * @updates t
     * @requires IS_BST(t) and x is in labels(t)
     * @ensures <pre>
     * IS_BST(t)  and  removeFromTree = x  and
     *  labels(t) = labels(#t) \ {x}
     * </pre>
     */
    private static <T extends Comparable<T>> T removeFromTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";
        assert t.size() > 0 : "Violation of: x is in labels(t)";

        //Initializes variable to store removed node
        T removed = t.root();

        //Initializes left and right binary tree to be used in traversing
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();

        //Stores current root for later reassembling wile disassembling current tree
        T tempNode = t.disassemble(left, right);

        //If-statement checks if the current root is the node needing to be replaced
        //If true then the smallest node from the right tree is removed and replaces
        //the current root
        //If false then the binary tree is broken down and traversed
        if (tempNode.compareTo(x) == 0) {

            //List of if-statements account for differing sizes of left and right subtrees
            if (left.size() > 0 && right.size() == 0) {
                t.transferFrom(left);
            } else if (left.size() == 0 && right.size() > 0) {
                t.transferFrom(right);
            } else if (left.size() > 0 && right.size() > 0) {

                //Stores node to replace binary tree t's root
                T replaceNode = removeSmallest(right);

                //Reassembles binary tree t to have root replaced
                t.assemble(tempNode, left, right);

                //Replaces root in binary tree t and stores replaced root
                removed = t.replaceRoot(replaceNode);
            }

        } else {

            //If-statement checks if current root is greater than object x
            //If true then the left tree is traversed
            //If false then the right tree is traversed
            if (tempNode.compareTo(x) > 0) {
                removed = removeFromTree(left, x);
            } else {
                removed = removeFromTree(right, x);
            }

            //Reassembles tree
            t.assemble(tempNode, left, right);
        }

        return removed;
    }

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {

        //Creates binary tree representation
        this.tree = new BinaryTree1<T>();

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Set3a() {

        //Creates representation of set
        this.createNewRep();
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final Set<T> newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(Set<T> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof Set3a<?> : ""
                + "Violation of: source is of dynamic type Set3<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Set3a<?>, and
         * the ? must be T or the call would not have compiled.
         */
        Set3a<T> localSource = (Set3a<T>) source;
        this.tree = localSource.tree;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void add(T x) {
        assert x != null : "Violation of: x is not null";
        assert !this.contains(x) : "Violation of: x is not in this";

        //Adds object x to tree by calling insert method
        insertInTree(this.tree, x);

    }

    @Override
    public final T remove(T x) {
        assert x != null : "Violation of: x is not null";
        assert this.contains(x) : "Violation of: x is in this";

        //Calls method to remove object from binary tree and returns removed element
        return removeFromTree(this.tree, x);

    }

    @Override
    public final T removeAny() {
        assert this.size() > 0 : "Violation of: this /= empty_set";

        //Returns element from binary tree
        return removeSmallest(this.tree);
    }

    @Override
    public final boolean contains(T x) {
        assert x != null : "Violation of: x is not null";

        //Call method to check if inputed object is in the binary tree
        return isInTree(this.tree, x);
    }

    @Override
    public final int size() {

        //Returns size of binary tree
        return this.tree.size();
    }

    @Override
    public final Iterator<T> iterator() {
        return this.tree.iterator();
    }

}
