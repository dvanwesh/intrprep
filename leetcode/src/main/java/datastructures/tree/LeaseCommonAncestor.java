package datastructures.tree;

public class LeaseCommonAncestor {
public static void main(String[] args) {
	TreeNode n=new TreeNode(3);
	n.left=new TreeNode(5);
	n.right=new TreeNode(1);
	n.right.left=new TreeNode(0);
	n.right.right=new TreeNode(8);
	n.left.left=new TreeNode(6);
	n.left.right=new TreeNode(2);
	n.left.right.left=new TreeNode(7);
	n.left.right.right=new TreeNode(4);
	TreeNode t=lowestCommonAncestor(n,n.left,n.right.right);
	System.out.println(t.val);
}
public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    System.out.println("---root---"+root);
	if(root==null || root==p ||root==q) return root;
    TreeNode left=lowestCommonAncestor(root.left,p,q);
    System.out.println("left.val: "+left);
    TreeNode right=lowestCommonAncestor(root.right,p,q);
    System.out.println("right.val: "+right);
    return left==null?right:right==null?left:root;
}
}
