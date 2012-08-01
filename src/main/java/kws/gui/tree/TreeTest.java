package kws.gui.tree;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: chan
 * Date: 8/1/12
 * Time: 5:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class TreeTest extends JFrame{

    private JPanel topPanel;
    private JPanel bottomPanel;
    private DefaultTreeModel defaultTreeModel;
    private DefaultMutableTreeNode rootNode;
    private JTree jTree;
    private int childIndex = 0;

    public TreeTest() throws HeadlessException {
        init();
    }

    private void init() {
        topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createEtchedBorder());
        getContentPane().setLayout(new GridBagLayout());
        Insets insets = new Insets(1,1,1,1);
        getContentPane().add(topPanel, new GridBagConstraints(0,0,1,1,0,0,GridBagConstraints.PAGE_START,GridBagConstraints.BOTH, insets, 0,0));
        bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEtchedBorder());
        getContentPane().add(bottomPanel, new GridBagConstraints(0,1,1,1,0,0,GridBagConstraints.PAGE_END,GridBagConstraints.BOTH, insets, 0,0));

        JButton addButton = new JButton("Add");
        bottomPanel.add(addButton);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performAdd();
            }
        });

        JButton removeButton = new JButton("RemoveAll");
        bottomPanel.add(removeButton);
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performRemove();
            }
        });

        rootNode = new DefaultMutableTreeNode("Root");
        defaultTreeModel = new DefaultTreeModel(rootNode);
        jTree = new JTree(defaultTreeModel);
        JScrollPane comp = new JScrollPane(jTree);
        comp.setPreferredSize(new Dimension(500,400));
        topPanel.add(comp);
        jTree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);

        defaultTreeModel.insertNodeInto(new DefaultMutableTreeNode("Init 1"), rootNode, 0);
        jTree.expandRow(0);

        jTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                int[] selectedItems = jTree.getSelectionModel().getSelectionRows();
                if(selectedItems == null) {
                    System.out.println("Dont have any selection");
                    return;
                }
                String x = "";
                for (int selectedItem : selectedItems) {
                    System.out.println("Selected index " + selectedItem);
                    DefaultMutableTreeNode childAt = (DefaultMutableTreeNode) rootNode.getChildAt(selectedItem-1);
                }
                System.out.println("-------------------------------");

            }
        });

    }

    private void performRemove() {
        rootNode.removeAllChildren();
        defaultTreeModel.reload();
    }

    private void performAdd() {
        defaultTreeModel.insertNodeInto(new DefaultMutableTreeNode("Child " + childIndex++), rootNode, 0);
        jTree.expandRow(0);
    }

    public static void main(String[] args) {
        TreeTest treeTest = new TreeTest();
        treeTest.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        treeTest.setSize( new Dimension(500,400));
        treeTest.pack();
        treeTest.setVisible(true);
    }
}
