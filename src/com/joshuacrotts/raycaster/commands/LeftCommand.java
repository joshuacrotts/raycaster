package com.joshuacrotts.raycaster.commands;

import java.awt.event.KeyEvent;

import com.joshuacrotts.raycaster.main.Raycaster;
import com.theta.input.Command;

public class LeftCommand extends Command {

  private final int DELTA_ANGLE = -2;
  
  private Raycaster raycaster;
  
  public LeftCommand(Raycaster raycaster) {
    this.raycaster = raycaster;
    this.bind(raycaster.getKeyboard(), KeyEvent.VK_A);
  }
  
  @Override
  public void pressed(float dt) {
    raycaster.setAngle(raycaster.getAngle() + DELTA_ANGLE + 360 % 360);
  }
  
  @Override
  public void down(float dt) {
    raycaster.setAngle(raycaster.getAngle() + DELTA_ANGLE + 360 % 360);
  }
}
