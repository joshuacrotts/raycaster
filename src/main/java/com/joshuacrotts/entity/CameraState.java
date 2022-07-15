package com.joshuacrotts.entity;

public final class CameraState {

    public static final int WALK_FORWARD = 1;
    public static final int WALK_BACKWARD = 2;
    public static final int TURN_LEFT = 4;
    public static final int TURN_RIGHT = 8;
    public static final int RUN_FORWARD = 16;
    public static final int STATIONARY = 0;

    private CameraState() {
    }

    public static boolean isFlagEnabled(int flags, int mask) {
        return (flags & mask) != 0;
    }
}
