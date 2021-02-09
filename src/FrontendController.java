/*
Code by Cooper Eisman
Created: 02/05/2021
Last Modified: 02/05/2021
*/


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

public class FrontendController {
    //Instance
    private View view;
    private JPanel currScreen;
    private LayoutManager layoutManager;

    private JPanel south;
    private JPanel east;
    private JPanel center;

    private int width;
    private int height;

    private Maze maze;

    public FrontendController(Maze maze) {
        //Initiate Input from maze
        this.maze = maze;
        this.width = maze.getWidth();
        this.height = maze.getHeight();



        //Itialize the View and Layout
        view = new View();
        layoutManager = new BorderLayout();

        //Initialize Current Screen and Set Layout
        currScreen = new JPanel();
        currScreen.setLayout(layoutManager);
        loadLayout();

        //Initial View
        loadScreen();
    }

    public void loadScreen() {
        currScreen.validate();
        view.configureViewPort(currScreen);
    }

    //Loads the Touchy THings
    private void loadLayout() {
        //South Frame
        south = new JPanel();
        south.setMaximumSize(new Dimension(view.getMaxWidth(),(view.getMaxHeight()/5)*2));
        currScreen.add(south, "South");

        //East Frame
        east = new JPanel();
        east.setMaximumSize(new Dimension((view.getMaxWidth()/5),(view.getMaxHeight()/5)*3));
        loadEast();
        currScreen.add(east, "East");

        //Center Frame
        center = new JPanel();
        center.setLayout(new GridLayout(width, height));
        center.setMaximumSize(new Dimension((view.getMaxWidth()/5)*4,(view.getMaxHeight()/5)*3));
        currScreen.add(center);
    }

    //Load the Screen
    public void updateCenter() {
        //Fix layout
        maze.generateNewMaze();
        maze.writeToFile();
        int[][] vars = maze.getArr();

        //center.removeAll();
        center.removeAll();
        center.setMaximumSize(new Dimension((view.getMaxWidth()/5)*4,(view.getMaxHeight()/5)*3));
        GridLayout centerLayout = new GridLayout();

        centerLayout.setColumns(width);
        centerLayout.setRows(height);
        centerLayout.setHgap(1);
        centerLayout.setVgap(1);
        center.setLayout(centerLayout);


        for(int x = 0; x < vars.length; x++) {
            for(int y = 0; y < vars[x].length; y++) {
                Button b;
                if (vars[x][y] == -1) {
                    b = new Button("Barrier");
                    b.setBackground(new Color(0));
                    b.setForeground(new Color(99999999));
                } else if (vars[x][y] == 1) {
                    b = new Button("Start");
                    b.setBackground(new Color(101010));
                } else if (vars[x][y] == 10) {
                    b = new Button("Finish");
                    b.setBackground(new Color(100100100));
                } else {
                    b = new Button("Box");
                }
                center.add(b);
            }
        }
        loadScreen();
    }

    private void loadEast() {
        JButton reset = new JButton("Re-generate");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCenter();
            }
        });
        east.add(reset);
    }


}
