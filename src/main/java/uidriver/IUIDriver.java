package uidriver;

public interface IUIDriver {
    void showMessage(String message);
    void showMessage(String message, boolean newLine);

    void showError(String error);

    String getExpression();

    void showAnswer(double answer);
}
