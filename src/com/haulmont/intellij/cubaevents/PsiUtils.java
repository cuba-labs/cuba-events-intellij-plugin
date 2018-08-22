package com.haulmont.intellij.cubaevents;

import com.intellij.lang.jvm.JvmClassKind;
import com.intellij.lang.jvm.JvmModifier;
import com.intellij.psi.*;

import java.util.Objects;

import static com.haulmont.intellij.cubaevents.EventsDeclarations.*;

public class PsiUtils {

    public static PsiClass getClass(PsiType psiType) {
        if (psiType instanceof PsiClassType) {
            return ((PsiClassType) psiType).resolve();
        }
        return null;
    }

    public static boolean isEventsReceiver(PsiElement psiElement) {
        if (psiElement instanceof PsiIdentifier
            && psiElement.getParent() instanceof PsiMethod) {

            PsiMethod method = (PsiMethod) psiElement.getParent();
            PsiModifierList modifierList = method.getModifierList();
            for (PsiAnnotation psiAnnotation : modifierList.getAnnotations()) {
                if (Objects.equals(psiAnnotation.getQualifiedName(), EVENTLISTENER_ANNOTATIONNAME)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isEventsPublish(PsiElement psiElement) {
        if (psiElement instanceof PsiCallExpression) {
            PsiCallExpression callExpression = (PsiCallExpression) psiElement;
            PsiMethod method = callExpression.resolveMethod();
            if (method != null) {
                String name = method.getName();
                PsiElement parent = method.getParent();
                if (name.equals(EventsDeclarations.EVENTS_PUBLISH_METHODNAME) && parent instanceof PsiClass) {
                    PsiClass implClass = (PsiClass) parent;
                    return isEventBusClass(implClass) || isSuperClassEventBus(implClass);
                }
            }
        }
        return false;
    }

    private static boolean isEventBusClass(PsiClass psiClass) {
        return Objects.equals(psiClass.getName(), EVENTS_SIMPLE_CLASSNAME);
    }

    private static boolean isSuperClassEventBus(PsiClass psiClass) {
        PsiClass[] supers = psiClass.getSupers();
        if (supers.length == 0) {
            return false;
        }
        for (PsiClass superClass : supers) {
            if (Objects.equals(superClass.getName(), EVENTS_SIMPLE_CLASSNAME)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEventClass(PsiElement psiElement) {
        if (psiElement instanceof PsiIdentifier
                && psiElement.getParent() instanceof PsiClass) {

            PsiClass psiClass = (PsiClass) psiElement.getParent();
            if (psiClass.getQualifiedName() != null
                    && psiClass.getClassKind() == JvmClassKind.CLASS
                    && !psiClass.hasModifier(JvmModifier.ABSTRACT)
                    && psiClass.getQualifiedName().endsWith("Event")) {

                PsiClass[] supers = psiClass.getSupers();
                if (supers.length == 0) {
                    return false;
                }
                for (PsiClass superClass : supers) {
                    if (Objects.equals(superClass.getName(), APPLICATION_EVENT_CLASSNAME)) {
                        return true;
                    }
                }

                return true;
            }
        }
        return false;
    }
}
