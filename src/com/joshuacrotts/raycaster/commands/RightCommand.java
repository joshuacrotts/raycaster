package com.joshuacrotts.raycaster.commands;

import com.joshuacrotts.raycaster.main.Raycaster;
import com.sun.glass.events.KeyEvent;
import com.theta.input.Command;

public class RightCommand extends Command {

  private Raycaster raycaster;
  
  public RightCommand(Raycaster raycaster) {
    this.raycaster = raycaster;
    this.bind(raycaster.getKeyboard(), KeyEvent.VK_D);
  }
  
  @Override
  public void pressed(float dt) {
    raycaster.setAngle(raycaster.getAngle() + 1 % 360);
  }
  
  @Override
  public void down(float dt) {
    raycaster.setAngle(raycaster.getAngle() + 1 % 360);
  }
}
