package com.joshuacrotts.raycaster.commands;

import com.joshuacrotts.raycaster.main.Raycaster;
import com.sun.glass.events.KeyEvent;
import com.theta.input.Command;

public class LeftCommand extends Command {

  private Raycaster raycaster;
  
  public LeftCommand(Raycaster raycaster) {
    this.raycaster = raycaster;
    this.bind(raycaster.getKeyboard(), KeyEvent.VK_A);
  }
  
  @Override
  public void pressed(float dt) {
    raycaster.setAngle(raycaster.getAngle() - 1 + 360 % 360);
  }
  
  @Override
  public void down(float dt) {
    raycaster.setAngle(raycaster.getAngle() - 1 + 360 % 360);
  }
}
