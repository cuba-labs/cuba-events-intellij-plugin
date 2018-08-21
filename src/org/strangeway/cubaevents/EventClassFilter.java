package org.strangeway.cubaevents;

import com.intellij.psi.*;
import com.intellij.usages.Usage;
import com.intellij.usages.UsageInfo2UsageAdapter;

public class EventClassFilter implements Filter {
    @Override
    public boolean shouldShow(Usage usage) {
        PsiElement element = ((UsageInfo2UsageAdapter) usage).getElement();
        if (element instanceof PsiJavaCodeReferenceElement) {
            PsiElement parent = element.getParent();

            if (parent instanceof PsiImportStatement) {
                return false;
            }

            return isNotComment(parent);
        }
        return false;
    }

    private boolean isNotComment(PsiElement psiElement) {
        PsiElement parent = psiElement;
        while (parent != null) {
            if (parent instanceof PsiComment) {
                return false;
            }
            if (parent instanceof PsiClass) {
                return true;
            }
            parent = parent.getParent();
        }
        return true;
    }
}