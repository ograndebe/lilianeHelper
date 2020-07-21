package com.github.ograndebe.lilihelper;

import java.awt.*;
import java.util.Set;

public enum ButtonType {
    HOLD {
        @Override
        void action(Robot robot, Set<Integer> keys) {
            keys.forEach(robot::keyPress);
        }
    }, RELEASE {
        @Override
        void action(Robot robot, Set<Integer> keys) {
            keys.forEach(robot::keyRelease);
        }
    }, PRESS_AND_RELEASE {
        @Override
        void action(Robot robot, Set<Integer> keys) {
            keys.forEach(robot::keyPress);
            keys.forEach(robot::keyRelease);
        }
    };

    abstract void action(final Robot robot, final Set<Integer> keys );
}
