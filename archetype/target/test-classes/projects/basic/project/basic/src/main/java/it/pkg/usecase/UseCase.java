package it.pkg.usecase;

@FunctionalInterface
public interface UseCase {
    Void nullRequest = null;
    Void nullResponse = null;

    Response execute(final Request request);
}
