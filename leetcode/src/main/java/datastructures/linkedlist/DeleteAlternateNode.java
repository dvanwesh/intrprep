package datastructures.linkedlist;

public class DeleteAlternateNode {
	public static void main(String[] args) {
		Node n=new Node(1);
		Node head=n;
		for(int i=2;i<12;i++) {
			n.next=new Node(i);
			n=n.next;
		}
		printList(head);
		deleteAlternateNode(head);
		printList(head);
	}

	private static void printList(Node head) {
		while(head!=null) {
			System.out.print(head.val+" ");
			head=head.next;
		}
		System.out.println();
	}
/**
 * method to delete alternate nodes in a list
 * @param head
 */
	private static void deleteAlternateNode(Node head) {
		while(head!=null && head.next!=null) {
			head.next=head.next.next;
			head=head.next;
		}
	}
}
