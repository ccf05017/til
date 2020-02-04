package com.poppo.effective.item8;

import java.lang.ref.Cleaner;

public class RoomExample implements AutoCloseable {

    private static final Cleaner cleaner = Cleaner.create();

    private static class State implements Runnable {

        private long numJunkPiles;

        State(long numJunkPiles) {
            this.numJunkPiles = numJunkPiles;
        }

        @Override
        public void run() {
            System.out.println("Clean");
            numJunkPiles = 0;
        }
    }

    private State state;

    private final Cleaner.Cleanable cleanable;

    public RoomExample(long numJunkPiles) {
        state = new State(numJunkPiles);
        cleanable = cleaner.register(this, state);
    }

    @Override
    public void close() throws Exception {
        cleanable.clean();
    }
}
