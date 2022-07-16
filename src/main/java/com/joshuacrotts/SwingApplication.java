package com.joshuacrotts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * This is essentially a wrapper for a Swing JFrame and a Thread starter, so the
 * user doesn't have to mess with it.
 */
public abstract class SwingApplication {

    /**
     * Number of milliseconds per second.
     */
    private static final int SECONDS_TO_MS = 1000;

    /**
     * Root JFrame to add content and panels.
     */
    private final JFrame FRAME;

    /**
     * Timer for updating/repainting the JFrame.
     */
    private Timer timer;

    /**
     * Frames per second to run the timer at.
     */
    private int fps;

    /**
     * Milliseconds to specify the speed of our timer.
     */
    private int ms;

    /**
     * Boolean to set the timer to run or not.
     */
    private boolean isRunning = false;

    public SwingApplication(int width, int height, int fps, String title) {
        System.setProperty("sun.java2d.opengl", "true");

        /* Create the JFrame. */
        this.FRAME = new JFrame(title);
        this.FRAME.setSize(width, height);
        this.FRAME.setResizable(false);
        this.FRAME.setLocationRelativeTo(null);
        this.FRAME.setVisible(true);
        this.FRAME.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.FRAME.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                stop();
            }
        });

        /* Sets the current FPS and the millisecond update time for the Swing timer.*/
        this.fps = fps;
        this.ms = SECONDS_TO_MS / fps;
    }

    /**
     * Sets the layout manager for the current JFrame.
     *
     * @param layout - LayoutManager type.
     */
    public void setFrameLayout(LayoutManager layout) {
        this.FRAME.setLayout(layout);
    }

    /**
     * Adds a Component object to the content pane of this JFrame.
     *
     * @param component - component to add.
     */
    public void addComponent(Component component) {
        this.FRAME.getContentPane().add(component);
    }

    /**
     * Adds a Component object at a specific index to the content pane of this
     * JFrame.
     *
     * @param component - component to add.
     * @param index     - index to add the new component to.
     */
    public void addComponent(Component component, int index) {
        this.FRAME.getContentPane().add(component, index);
    }

    /**
     * Adds a Component object with predefined constraints to the end of the
     * content pane component list.
     *
     * @param component   - component to add.
     * @param constraints - Component constraints.
     */
    public void addComponent(Component component, Object constraints) {
        this.FRAME.getContentPane().add(component, constraints);
    }

    /**
     * Adds a Component object with predefined constraints at a specific index
     * to the content pane of this JFrame.
     *
     * @param component   - component to add.
     * @param constraints - Component constraints.
     * @param index       - index to add the new component to.
     */
    public void addComponent(Component component, Object constraints,
                             int index) {
        this.FRAME.getContentPane().add(component, constraints, index);
    }

    /**
     * Sets the location of the frame to the center of the screen, and packs all
     * components together, updating their dimensions.
     */
    public void packComponents() {
        this.FRAME.pack();
        this.FRAME.setLocationRelativeTo(null);
    }

    /**
     * Invokes the thread loop for this swing application. Any code that needs
     * to be repeated should be placed inside this overridden method.
     */
    public abstract void run();

    /**
     * Starts the program if it is not already running.
     */
    public synchronized void start() {
        if (this.isRunning) {
            return;
        }

        this.isRunning = true;
        SwingUtilities.invokeLater(() -> {
            this.update();
        });
    }

    /**
     * Stops and destroys the timer and frame if it is not already stopped.
     */
    public synchronized void stop() {
        if (!this.isRunning) {
            return;
        }

        this.isRunning = false;
        this.timer.stop();
        this.FRAME.dispose();
        System.exit(0);
    }

    /**
     * Initializes the update loop timer.
     */
    private void update() {
        this.setupAppTimer();
        this.timer.start();
    }

    /**
     * Sets up the application and render timer.
     */
    private void setupAppTimer() {
        this.timer = new Timer(this.ms, timerListener -> {
            this.run();
            this.FRAME.repaint();
        });
    }

    // =================== GETTERS AND SETTERS ====================== //

    public int getFPS() {
        return this.fps;
    }

    public void setFPS(int fps) {
        this.fps = fps;
        this.ms = SECONDS_TO_MS / fps;
        this.timer.setDelay(this.ms);
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public void setRunning(boolean running) {
        this.isRunning = running;
    }

    public void isVisible() {
        this.FRAME.isVisible();
    }

    public void setVisible(boolean visible) {
        this.FRAME.setVisible(true);
    }

    public JFrame getFrame() {
        return this.FRAME;
    }

    public int getWidth() {
        return this.FRAME.getWidth();
    }

    public int getHeight() {
        return this.FRAME.getHeight();
    }
}