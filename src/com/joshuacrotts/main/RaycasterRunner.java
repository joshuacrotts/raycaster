package com.joshuacrotts.main;

import com.joshuacrotts.main.projection.RaycasterProjectionPanel;

import java.awt.GridLayout;

/**
 * com.joshuacrotts.main.RaycasterRunner is the driver class where all JPanels and components
 * are initialized and instantiated.
 */
public final class RaycasterRunner extends SwingApplication {

    /**
     * Width of the JFrame.
     */
    private static final int WIDTH = 1280;

    /**
     * Height of the JFrame.
     */
    private static final int HEIGHT = 640;

    /**
     * The frames-per-second that we want this application to achieve.
     */
    private static final int TARGET_FPS = 60;

    /**
     * Number of rows for our JFrame GridLayout.
     */
    private static final int NUM_ROWS = 1;

    /**
     * Number of columns for our JFrame GridLayout (i.e., how many panes we
     * will add).
     */
    private static final int NUM_COLS = 2;

    /**
     * Title of the JFrame.
     */
    private static final String TITLE = "Raycaster";

    /**
     * Panel for drawing the overhead perspective of the rays and scene.
     */
    private final RaycasterPanel RAYCASTER_PANEL;

    /**
     * Pseudo-3d environment projection panel.
     */
    private final RaycasterProjectionPanel RAYCASTER_PROJ_PANEL;

    public RaycasterRunner(final int width, final int height, final int fps, final String title) {
        super(width, height, fps, title);
        this.setFrameLayout(new GridLayout(NUM_ROWS, NUM_COLS));

        // Now, instantiate and add the two raycasting panels.
        this.RAYCASTER_PANEL = new RaycasterPanel(this);
        this.RAYCASTER_PROJ_PANEL = new RaycasterProjectionPanel(this, this.RAYCASTER_PANEL);
        this.addComponent(this.RAYCASTER_PANEL);
        this.addComponent(this.RAYCASTER_PROJ_PANEL);
        this.packComponents();
        this.setVisible(true);
        this.RAYCASTER_PANEL.requestFocus(true); // Very important! Do not delete this line.
    }

    @Override
    public void run() {
        this.RAYCASTER_PANEL.update();
        this.RAYCASTER_PROJ_PANEL.update();
    }

    public static void main(final String[] args) {
        RaycasterRunner runner = new RaycasterRunner(WIDTH, HEIGHT, TARGET_FPS, TITLE);
        runner.run();
    }
}