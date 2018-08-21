package org.strangeway.cubaevents;

import com.intellij.psi.*;
import com.intellij.usages.Usage;
import com.intellij.usages.UsageInfo2UsageAdapter;

import java.util.Objects;

public class SenderFilter implements Filter {

    private final PsiClass eventClass;

    public SenderFilter(PsiClass eventClass) {
        this.eventClass = eventClass;
    }

    @Override
    public boolean shouldShow(Usage usage) {
        PsiElement element = ((UsageInfo2UsageAdapter) usage).getElement();
        if (element instanceof PsiReferenceExpression) {
            if ((element = element.getParent()) instanceof PsiMethodCallExpression) {
                PsiMethodCallExpression callExpression = (PsiMethodCallExpression) element;
                PsiType[] types = callExpression.getArgumentList().getExpressionTypes();
                for (PsiType type : types) {
                    if (Objects.equals(PsiUtils.getClass(type).getName(), eventClass.getName())) {
                        // pattern : events.post(new Event());
                        return true;
                    }
                }

                if ((element = element.getParent()) instanceof PsiExpressionStatement) {
                    if ((element = element.getParent()) instanceof PsiCodeBlock) {
                        PsiCodeBlock codeBlock = (PsiCodeBlock) element;
                        PsiStatement[] statements = codeBlock.getStatements();
                        for (PsiStatement statement : statements) {
                            if (statement instanceof PsiDeclarationStatement) {
                                PsiDeclarationStatement declarationStatement = (PsiDeclarationStatement) statement;
                                PsiElement[] elements = declarationStatement.getDeclaredElements();
                                for (PsiElement variable : elements) {
                                    if (variable instanceof PsiLocalVariable) {
                                        PsiLocalVariable localVariable = (PsiLocalVariable) variable;
                                        PsiClass psiClass = PsiUtils.getClass(localVariable.getTypeElement().getType());
                                        if (psiClass == null || eventClass == null) {
                                            return false;
                                        }

                                        if (Objects.equals(psiClass.getName(), eventClass.getName())) {
                                            // pattern :
                                            //   Event event = new Event();
                                            //   events.post(event);
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }
}
