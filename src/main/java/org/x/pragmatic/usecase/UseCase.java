package org.x.pragmatic.usecase;

@FunctionalInterface
public interface UseCase<I,O> {
    Void nullRequest = null;
    Void nullResponse = null;

    O execute(final I request);
}
