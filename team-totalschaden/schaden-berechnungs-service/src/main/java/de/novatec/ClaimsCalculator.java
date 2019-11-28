package de.novatec;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class ClaimsCalculator implements JavaDelegate {

    private enum SchadenType {
        HAGEL(0.2, 0.05), BLECHSCHADEN(0.1, 0.01), MOTORSCHADEN(0.4, 0.1), KRATZER(0.1, 0.005), OTHER(0.2, 0.1);

        private double high;
        private double low;

        SchadenType(double high, double low) {
            this.high = high;
            this.low = low;
        }
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int wert = (int) delegateExecution.getVariable("wagenAlter");
        int alter = (int) delegateExecution.getVariable("wagenNeuwert");
        SchadenType schadenType = SchadenType.valueOf(((String) delegateExecution.getVariable("schadenTyp")).toUpperCase());

        double summe = 0;

        if (isNeuwagen(alter) && isTeuer(wert)) {
            summe = wert * schadenType.high;
        } else if (isAlt(alter)) {
            summe = 0;
        } else {
            summe = wert * schadenType.low;
        }

        delegateExecution.setVariable("auszahlung", summe);

    }

    private boolean isNeuwagen(int alter) {
        return alter <= 1;
    }

    private boolean isAlt(int alter) {
        return alter > 6;
    }

    private boolean isTeuer(int wert) {
        return wert > 50000;
    }
}
