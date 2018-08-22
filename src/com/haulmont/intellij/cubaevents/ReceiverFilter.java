package com.haulmont.intellij.cubaevents;

import com.intellij.psi.*;
import com.intellij.usages.Usage;
import com.intellij.usages.UsageInfo2UsageAdapter;

import java.util.function.Predicate;

public class ReceiverFilter implements Predicate<Usage> {
    @Override
    public boolean test(Usage usage) {
        PsiElement element = ((UsageInfo2UsageAdapter) usage).getElement();
        if (element instanceof PsiJavaCodeReferenceElement) {
            if ((element = element.getParent()) instanceof PsiTypeElement) {
                if ((element = element.getParent()) instanceof PsiParameter) {
                    if ((element = element.getParent()) instanceof PsiParameterList) {
                        if ((element = element.getParent()) instanceof PsiMethod) {
                            PsiMethod method = (PsiMethod) element;
                            return PsiUtils.isEventsReceiver(method.getNameIdentifier());
                        }
                    }
                }
            }
        }
        return false;
    }
}