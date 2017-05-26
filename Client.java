import java.lang.Math;
import java.util.Scanner;
import java.util.Arrays;

import javax.swing.*;
import java.awt.*;




public class Client
{
	static final int LEVELS = 4;
	static final int TOTALVERTICES = (int)(Math.pow(2,LEVELS)-1);
	static final int PAGEWIDTH = 1280/2;
	static final int PAGEHEIGHT = 720;

	//to be included in the class as a member
	static Point[] setOfPoints = new Point[TOTALVERTICES + 1];
	static Object[] levelOrderBST;
	static NodeInfo[] levelOrderRBT;

	// OR USE A ,RETURN THE ARRAY' TYPE OF MODEL
	public static void main(String args[])
	{
		assignPoints();
		Scanner sc = new Scanner(System.in);
		//Just for testing purposes :
		/*for(int i = 0 ; i < TOTALVERTICES ; ++i) {
			System.out.println("point "+i+" :    " + setOfPoints[i].x + " , " + setOfPoints[i].y);
		}
		*/
		BST<Integer, Integer> bst = new BST<Integer, Integer>();
		RBT<Integer, Integer> rbt = new RBT<Integer, Integer>();
		levelOrderBST = bst.levelOrder();
		levelOrderRBT = rbt.levelOrder();
		JFrame jftree = new JFrame();
		JFrame jfrbtree = new JFrame();
		JPanel treeDrawing = new tree();
		JPanel rbtreeDrawing = new rbtree();

		jftree.add(treeDrawing);
		jftree.setTitle("Binary Search Tree");
		jftree.setSize(PAGEWIDTH,PAGEHEIGHT);
		jftree.setVisible(true);

		jfrbtree.add(rbtreeDrawing);
		jfrbtree.setTitle("Red Black Tree");
		jfrbtree.setSize(PAGEWIDTH, PAGEHEIGHT);
		jfrbtree.setVisible(true);

		int data;
		int option;
		do {
			System.out.println("Enter the operation to perform on BST");
			System.out.println("1.Insert\n2.Delete\n3.Exit");
			option = sc.nextInt();
			if(option == 1) {
				System.out.println("Enter the integer to insert into BST");
				data = sc.nextInt();
				bst.put(data, data);
				rbt.put(data, data);
			} else if(option == 2) {
				System.out.println("Enter the integer to remove from the BST");
				data = sc.nextInt();
				bst.delete(data);
				rbt.delete(data);
			}

			levelOrderBST = bst.levelOrder();
			levelOrderRBT = rbt.levelOrder();
			treeDrawing.repaint();
			rbtreeDrawing.repaint();
		} while(option != 3);
	}

	public static void assignPoints()
	{	//ALL ARE CENTERS ..... [assigned values]

		//level zero done manually
		setOfPoints[0] = new Point();
		setOfPoints[0].x = PAGEWIDTH/2;
		setOfPoints[0].y = 20+36+18;

		int cnt = 1;
		for(int level = 1, Y = (36+18)+(66+36) ; level < LEVELS ; ++level, Y += 66+36 ) {
			int tempWidth = 0;
			for(int nodenum = 1 ; nodenum <= Math.pow(2,level) ; ++nodenum) {
				setOfPoints[cnt] = new Point();
				if(nodenum == 1) {
					tempWidth = (PAGEWIDTH-setOfPoints[cnt-1].x);
					setOfPoints[cnt].x = tempWidth/2;
				}
				else {
					setOfPoints[cnt].x = tempWidth/2 + tempWidth*(nodenum-1);
				}
				setOfPoints[cnt++].y = Y;
			}
		}

		for(int i = setOfPoints.length - 1; i > 0; i--) {
			setOfPoints[i] = setOfPoints[i-1];
		}

		setOfPoints[0] = null;

	}

	//This is the method which draws. right now its just empty circles with joining lines
	public static class tree extends JPanel
	{
		@Override
		public void paintComponent(Graphics g) {
			g.clearRect(0,0, getWidth(), getHeight());
			for(int i = 1; i < levelOrderBST.length; ++i) {
				if(levelOrderBST[i] != null) {
					g.drawOval(setOfPoints[i].x - 18, setOfPoints[i].y - 18, 36, 36);
					g.drawString(levelOrderBST[i].toString(), setOfPoints[i].x, setOfPoints[i].y); // needs some tweaking for centering the string
					if((2*i) < levelOrderBST.length && levelOrderBST[2*i] != null) {
						g.drawLine(setOfPoints[i].x, setOfPoints[i].y+18, setOfPoints[2*i].x, setOfPoints[2*i].y-18);
					}
					if((2*i) + 1 < levelOrderBST.length && levelOrderBST[(2*i) + 1] != null) {
						g.drawLine(setOfPoints[i].x, setOfPoints[i].y+18, setOfPoints[(2*i) + 1].x, setOfPoints[(2*i) + 1].y-18);
					}
				}
			}
		}
	}

	public static class rbtree extends JPanel {
		@Override
		public void paintComponent(Graphics g) {
			g.clearRect(0, 0, getWidth(), getHeight());
			for(int i = 1; i < levelOrderRBT.length; ++i) {
				if(levelOrderRBT[i] != null) {
					if(levelOrderRBT[i].color) {
						g.setColor(Color.RED);
						g.fillOval(setOfPoints[i].x - 18, setOfPoints[i].y - 18, 36, 36);
					} else {
						g.setColor(Color.BLACK);
						g.fillOval(setOfPoints[i].x - 18, setOfPoints[i].y - 18, 36, 36);
					}
					g.setColor(Color.WHITE);
					g.drawString(levelOrderRBT[i].data.toString(), setOfPoints[i].x, setOfPoints[i].y);

					g.setColor(Color.BLACK);
					if((2*i) < levelOrderRBT.length && levelOrderRBT[2*i] != null) {
						g.drawLine(setOfPoints[i].x, setOfPoints[i].y+18, setOfPoints[2*i].x, setOfPoints[2*i].y-18);
					}

					if((2*i) + 1 < levelOrderRBT.length && levelOrderRBT[(2*i) + 1] != null) {
						g.drawLine(setOfPoints[i].x, setOfPoints[i].y+18, setOfPoints[(2*i) + 1].x, setOfPoints[(2*i) + 1].y-18);
					}
				}
			}
		}
	}

}
