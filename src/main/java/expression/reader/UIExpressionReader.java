package expression.reader;

import uidriver.IUIDriver;

public class UIExpressionReader implements IExpressionReader {
    private final IUIDriver uiDriver;

    private String lastExpression = "";

    public UIExpressionReader(IUIDriver uiDriver) {
        this.uiDriver = uiDriver;
    }

    @Override
    public String readExpression() {
        lastExpression = uiDriver.getExpression();
        return lastExpression;
    }

    @Override
    public boolean hasExpression() {
        return !(lastExpression.equalsIgnoreCase("exit") || lastExpression.equalsIgnoreCase("выход"));
    }

    public String getLastExpression() {
        return lastExpression;
    }

    public IUIDriver getUIDriver() {
        return uiDriver;
    }
}
