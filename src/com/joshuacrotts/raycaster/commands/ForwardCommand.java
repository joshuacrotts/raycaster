package com.joshuacrotts.raycaster.commands;

import com.joshuacrotts.raycaster.main.Raycaster;
import com.sun.glass.events.KeyEvent;
import com.theta.input.Command;

public class ForwardCommand extends Command {

  /**
   * 
   */
  private final int ANG_MOD = 90;
  
  /**
   * 
   */
  private Raycaster raycaster;
  
  /**
   * 
   */
  private int speed = 5;

  
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
    int x = this.raycaster.getCameraX();
    int y = this.raycaster.getCameraY();
    int modFOV = this.raycaster.getFOV() / 2;
    double radA = Math.toRadians(this.raycaster.getAngle() - modFOV + ANG_MOD);
    this.raycaster.setCameraX((int) (x + speed * Math.cos(radA)));
    this.raycaster.setCameraY((int) (y + speed * Math.sin(radA)));
  }
}
