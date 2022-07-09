package com.joshuacrotts.raycaster.commands;

import com.joshuacrotts.raycaster.main.Raycaster;
import com.theta.input.Command;

import java.awt.event.KeyEvent;

public class ForwardCommand extends Command {

    /**
     *
     */
    private final int ANG_MOD = 90;

    /**
     *
     */
    private final Raycaster raycaster;

    /**
     *
     */
    private final double speed = 2;


    public ForwardCommand(Raycaster raycaster) {
        this.raycaster = raycaster;
        this.bind(raycaster.getKeyboard(), KeyEvent.VK_W);
    }

    @Override
    public void pressed(float dt) {
        this.activate();
    }

    @Override
    public void down(float dt) {
        this.activate();
    }

    private void activate() {
        double x = this.raycaster.getCameraX();
        double y = this.raycaster.getCameraY();
        int modFOV = this.raycaster.getFOV() / 2;
        double radA = Math.toRadians(this.raycaster.getAngle() - modFOV + ANG_MOD);
        this.raycaster.setCameraX((x + this.speed * Math.cos(radA)));
        this.raycaster.setCameraY((y + this.speed * Math.sin(radA)));
    }
}
